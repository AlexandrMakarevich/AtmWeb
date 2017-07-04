package com.command;

import com.atm_exeption.AtmException;
import com.atm_exeption.ErrorCodes;
import com.google.common.base.Objects;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WithdrawCommand implements CommandInt {

    private String currency;
    private int amount;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger LOGGER = Logger.getLogger(WithdrawCommand.class);

    public WithdrawCommand(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String currency, int amount) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public List<PrintBalance> executeDb(int accountName) {
        int balance = getBalance(accountName, currency);
        if ((balance - amount) < 0) {
            LOGGER.info("Not enough money on the account!");
            throw new AtmException(ErrorCodes.NOT_ENOUGH_MONEY);
        }
        withdrawProcess(currency, accountName);
        PrintBalance printBalance = new PrintBalance(currency, amount);
        List<PrintBalance> listWithdrawBalance = new ArrayList<>();
        listWithdrawBalance.add(printBalance);
        String formattedString = String.format("Removed from your account %d %s.", amount, currency);
        LOGGER.info(formattedString);
        return listWithdrawBalance;
    }

    @Override
    public CommandName getCommandOperation() {
        return CommandName.WITHDRAW;
    }

    public void withdrawProcess(String currencyName, int accountName) {
        String query = "update debit d inner join currency c on c.id = d.currency_id " +
                "set balance = balance - :p_balance where account_id = :p_account_id " +
                "and currency_name = :p_currency_name";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("p_balance", amount);
        namedParameters.addValue("p_account_id", accountName);
        namedParameters.addValue("p_currency_name", currencyName);
        namedParameterJdbcTemplate.update(query, namedParameters);
    }

    public int getBalance(int accountName, String currencyName) {
        String query = "select balance b from debit d inner join currency c on c.id = d.currency_id " +
                "where account_id = :p_account_id and currency_name = :p_currency_name for update";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("p_account_id", accountName);
        namedParameters.addValue("p_currency_name", currencyName);
        List<Integer> balanceList = namedParameterJdbcTemplate.query(query, namedParameters, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("b");
            }
        });

        if (!balanceList.isEmpty()) {
            return balanceList.get(0);
        }
        LOGGER.warn("You don't have money on currency " + currency);
        throw new AtmException(ErrorCodes.NO_MONEY_ON_THIS_CURRENCY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WithdrawCommand that = (WithdrawCommand) o;
        return amount == that.amount &&
                Objects.equal(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currency, amount);
    }
}
