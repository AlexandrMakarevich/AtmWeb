package com.command;


import com.atm_exeption.AtmException;
import com.atm_exeption.ErrorCodes;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DbWithdrawCommand implements DbCommand {

    private String currency;
    private int amount;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger LOGGER = Logger.getLogger(DbWithdrawCommand.class);

    public DbWithdrawCommand(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String currency, int amount) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public List<PrintBalance> executeDb(int accountName) {
        Optional<Integer> currencyExist = checkCurrency(currency);
        if (!currencyExist.isPresent()) {
            LOGGER.info("You don't have money on currency " + currency);
            throw new AtmException(ErrorCodes.NO_CURRENCY);
        }
        int balance = getBalance(accountName, currencyExist.get());
        if ((balance - amount) < 0) {
            LOGGER.info("Not enough money on the account!");
            throw new AtmException(ErrorCodes.NOT_ENOUGH_MONEY);
        }
        withdrawProcesss(currencyExist.get(), accountName);
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

    public void withdrawProcesss(int currencyId, int accountName) {
        String query = "update debit set balance = balance - :p_balance " +
                "where account_id = :p_account_id and currency_id = :p_currency_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("p_balance", amount);
        namedParameters.addValue("p_account_id", accountName);
        namedParameters.addValue("p_currency_id", currencyId);
        namedParameterJdbcTemplate.update(query, namedParameters);
    }

    public int getBalance(int accountName, int currencyExist) {
        String query = "select balance b from debit " +
                "where account_id = :p_account_id and currency_id = :p_currency_id  for update";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("p_account_id", accountName);
        namedParameters.addValue("p_currency_id", currencyExist);
        List<Integer> balanceList = namedParameterJdbcTemplate.query(query, namedParameters, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("b");
            }
        });

        if (!balanceList.isEmpty()) {
            return balanceList.get(0);
        }
        System.out.println("You don't have money on currency " + currency);
        LOGGER.warn("You don't have money on currency " + currency);
        throw new IllegalStateException("You don't have money on this currency!");
    }

    public Optional<Integer> checkCurrency(String currencyInput) {
        String query = "select id i, currency_name from currency where currency_name = :p_currency_name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_currency_name", currencyInput);
        List<Integer> listOfCurrencyId = namedParameterJdbcTemplate.query(query, namedParameters, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("i");
            }
        });
        if (listOfCurrencyId.isEmpty()) {
            return Optional.absent();
        }
        return Optional.of(listOfCurrencyId.get(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbWithdrawCommand that = (DbWithdrawCommand) o;
        return amount == that.amount &&
                Objects.equal(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(currency, amount);
    }
}
