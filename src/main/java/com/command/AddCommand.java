package com.command;

import com.atm_exeption.AtmException;
import com.atm_exeption.CustomAtmException;
import com.atm_exeption.ErrorCodes;
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

public class AddCommand implements CommandInt {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String currency;
    private int amount;
    private static final Logger LOGGER = Logger.getLogger(AddCommand.class);

    public AddCommand(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String currency, int amount) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public List<PrintBalanceService> executeDb(int accountId) {
        Optional<Integer> currencyExist = checkCurrency(currency);
        if (!currencyExist.isPresent()) {
            LOGGER.info("You don't have money on currency " + currency);
            throw new CustomAtmException("You don't have money on currency !");
        }
        String query = " insert into debit(account_id, currency_id, balance) "+
        "select :p_account_id account_id, c.id, IFNULL(d.balance,:p_balance) "+
        "from currency c "+
        "left join debit d "+
        "on d.currency_id = c.id "+
        "where c.currency_name = :p_currency_name "+
        "ON DUPLICATE KEY UPDATE balance = d.balance + :p_balance";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("p_account_id", accountId);
        namedParameters.addValue("p_balance", amount);
        namedParameters.addValue("p_currency_name", currency);
        namedParameters.addValue("p_balance", amount);
        int rowCount = namedParameterJdbcTemplate.update(query, namedParameters);
        if (rowCount == 0) {
            throw new AtmException(ErrorCodes.NO_CHANGES);
        }
        PrintBalanceService printBalanceService = new PrintBalanceService(currency, amount);
        List<PrintBalanceService> listAddBalance = new ArrayList<>();
        listAddBalance.add(printBalanceService);
        String formattedString = String.format("Added %d in currency %s.", amount, currency);
        LOGGER.info(formattedString);
        return listAddBalance;
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
    public CommandName getCommandOperation() {
        return CommandName.ADD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddCommand that = (AddCommand) o;
        return amount == that.amount &&
                java.util.Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(currency, amount);
    }

}
