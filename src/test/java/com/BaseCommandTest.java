package com;

import org.junit.runner.RunWith;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:atmWeb-test.xml")
public class BaseCommandTest {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void cleanTable(String... tables) {
        for (String table : tables) {
            String query = "delete from " + table;
            namedParameterJdbcTemplate.getJdbcOperations().update(query);
        }
    }

    public int insert(String tableName, String columnName, String value) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String query = String.format("insert into %s(%s) value(:p_value)", tableName, columnName);
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_value", value);
        int rowCount = namedParameterJdbcTemplate.update(query, namedParameters, keyHolder);
        if (rowCount == 0) {
            throw new IllegalStateException("No column has been changed!");
        }
        return keyHolder.getKey().intValue();
    }

    public void insertBalance(int accountId, int currencyId, int balance) {
        String query = "insert into debit(account_id, currency_id, balance) " +
                "values(:p_account_id, :p_currency_id, :p_balance)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("p_account_id", accountId);
        namedParameters.addValue("p_currency_id", currencyId);
        namedParameters.addValue("p_balance", balance);
        int rowCount = namedParameterJdbcTemplate.update(query, namedParameters);
        if (rowCount == 0) {
            throw new IllegalStateException("No column has been changed!");
        }
    }
}
