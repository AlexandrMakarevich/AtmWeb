package com.command;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("dbPrintBalance")
public class DbPrintBalance implements DbCommand {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final Logger LOGGER = Logger.getLogger(DbPrintBalance.class);

    @Resource
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public DbPrintBalance() {
    }

    public DbPrintBalance(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void executeDb(int accountId) throws SQLException {
        String query = "select currency_name cn,balance b" +
                " from debit d inner join account a on a.id = d.account_id" +
                " inner join currency c on c.id = d.currency_id" +
                " where d.account_id = :p_account_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_account_id", accountId);
        List<PrintBalanceService> accountBalance = namedParameterJdbcTemplate.query(query, namedParameters, new RowMapper<PrintBalanceService>() {

            public PrintBalanceService mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new PrintBalanceService(rs.getString("cn"), rs.getInt("b"));
            }
        });

        if (accountBalance.isEmpty()) {
            System.out.println("You don't have money on balance");
            return;
        }
        for (PrintBalanceService balanceService : accountBalance) {
            String formattedString = String.format("Your balance is %d in currency %s.", balanceService.getBalance(),
                    balanceService.getCurrency());
            System.out.println(formattedString);
            LOGGER.info(formattedString);
        }
    }
}
