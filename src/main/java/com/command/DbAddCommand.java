package com.command;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbAddCommand implements DbCommand {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String currency;
    private int amount;
    private static final Logger LOGGER = Logger.getLogger(DbAddCommand.class);

    public DbAddCommand(NamedParameterJdbcTemplate namedParameterJdbcTemplate, String currency, int amount) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.currency = currency;
        this.amount = amount;
    }

    @Override
    public List<PrintBalance> executeDb(int accountId) {
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
            throw new IllegalStateException("No column has been changed !");
        }
        PrintBalance printBalance = new PrintBalance(currency, amount);
        List<PrintBalance> listAddBalance = new ArrayList<>();
        listAddBalance.add(printBalance);
        String formattedString = String.format("Added %d in currency %s.", amount, currency);
        LOGGER.info(formattedString);
        return listAddBalance;
    }

    @Override
    public CommandName getCommandOperation() {
        return CommandName.ADD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbAddCommand that = (DbAddCommand) o;
        return amount == that.amount &&
                java.util.Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(currency, amount);
    }

}
