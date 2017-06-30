package com.command;

import com.AtmExceptionMatcher;
import com.BaseCommandTest;
import com.atm_exeption.AtmException;
import com.atm_exeption.ErrorCodes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

public class TestWithdrawCommand extends BaseCommandTest {

    private WithdrawCommand withdrawCommand;
    private String currencyName = "rub";
    private String accountName = "petrov";
    int withdrawAmount = 345;
    int balance = 600;

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Before
    public void init() {
        withdrawCommand = new WithdrawCommand(namedParameterJdbcTemplate, currencyName, withdrawAmount);
        cleanTable("debit", "account", "currency");
    }

    @Rule
    public ExpectedException testRuleException = ExpectedException.none();

    @Test
    @Transactional
    public void testExecuteDb() {
        int accountId = insert("account", "account_name", accountName);
        int currencyId = insert("currency", "currency_name", currencyName);
        insertBalance(accountId, currencyId, balance);
        withdrawCommand.executeDb(accountId);
        int expectedResult = balance - withdrawAmount;
        int actualResult = withdrawCommand.getBalance(accountId, currencyName);
        Assert.assertEquals("Actual result must be expected", expectedResult, actualResult);
    }

    @Test
    public void testNotEnoughMoney() {
        int balanceLitleThanCanWithdraw = 100;
        int accountId = insert("account", "account_name", accountName);
        int currencyId = insert("currency", "currency_name", currencyName);
        insertBalance(accountId, currencyId, balanceLitleThanCanWithdraw);
        this.testRuleException.expect(AtmException.class);
        this.testRuleException.expect(new AtmExceptionMatcher(new AtmException(ErrorCodes.NOT_ENOUGH_MONEY)));
        withdrawCommand.executeDb(accountId);
    }

    @Test
    public void testGetBalance() {
        int accountId = insert("account", "account_name", accountName);
        int currencyId = insert("currency", "currency_name", currencyName);
        insertBalance(accountId, currencyId, balance);
        int expectedResult = balance;
        int actualResult = withdrawCommand.getBalance(accountId, currencyName);
        Assert.assertEquals("Actual result must be expected", expectedResult, actualResult);
    }
}
