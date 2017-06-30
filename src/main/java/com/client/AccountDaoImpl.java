package com.client;

import com.google.common.base.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("accountDaoImpl")
public class AccountDaoImpl implements AccountDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Resource
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<Account> findAccountByName(String accountInput) {
        String query = "select id i, account_name a from account where account_name = :p_account_name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_account_name", accountInput);
        List<Account> listOfAccounts = namedParameterJdbcTemplate.query(query, namedParameters, new RowMapper<Account>() {
            @Override
            public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
                Account account = new Account();
                account.setId(rs.getInt("i"));
                account.setAccountName(rs.getString("a"));
                return account;
            }
        });

        if (listOfAccounts.isEmpty()) {
            return Optional.absent();
        }
        if (listOfAccounts.size() > 1) {
            throw new IllegalStateException("Found more than one account with name " + accountInput +
                    " please check your database data!");
        }
        return Optional.of(listOfAccounts.get(0));
    }

    @Override
    public int addAccount(String accountName) {
        String query = "insert into account (account_name) value(:p_account_name)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("p_account_name", accountName);
        int rows = namedParameterJdbcTemplate.update(query, namedParameters);
        if (rows == 0) {
            System.out.println("No column was changed!");
            throw new IllegalStateException("No column was changed!");
        }
        return rows;
    }
}
