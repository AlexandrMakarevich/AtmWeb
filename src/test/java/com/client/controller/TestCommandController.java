package com.client.controller;

import com.atm_exeption.ErrorCodes;
import com.client.BaseCommandTest;
import com.command.Command;
import com.command.PrintBalanceService;
import com.controller.CommandController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.MapBindingResult;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

public class TestCommandController extends BaseCommandTest {

    @Resource(name = "commandController")
    private CommandController commandController;
    private String currencyName = "rub";
    private int balance = 200;
    private String accountName = "Vova";
    private Command command;
    private MapBindingResult result;

    @Before
    public void init() {
        cleanTable("debit", "account", "currency");
        result = new MapBindingResult(new HashMap(), "aa");
        int accountId = insert("account", "account_name", accountName);
        int currencyId = insert("currency", "currency_name", currencyName);
        insertBalance(accountId, currencyId, balance);
        command = new Command();
        command.setAccountId(accountId);
        command.setAccountName(accountName);
    }

    @Test
    public void checkProcessCommand() {
        ModelMap modelMap = new ModelMap();
        String commandName = "balance";
        command.setCommandName(commandName);
        String expectedResult = "balance";
        PrintBalanceService expectedObject = new PrintBalanceService(currencyName, balance);
        String actualResult = commandController.processCommand(command, result, modelMap);
        List<PrintBalanceService> resultList = (List<PrintBalanceService>) modelMap.get("operationResult");
        PrintBalanceService actualObject = resultList.get(0);
        Assert.assertEquals("Actual object must be expected", expectedObject, actualObject);
        Assert.assertEquals("Actual pageName must be expectedPageName", expectedResult, actualResult);
    }

    @Test
    public void checkExceptionNotEnoughMoney() {
        String commandName = "- rub 1000";
        String expectedMessage = "Not enough money on the account!";
        String actualMessage = getErrorMessage(commandName);
        Assert.assertEquals("Actual message must be expected", expectedMessage, actualMessage);
    }

    @Test
    public void checkExceptionNoCurrency() {
        String commandName = "- eur 100";
        String expectedMessage = "You don't have money on currency";
        String actualMessage = getErrorMessage(commandName);
        Assert.assertEquals("Actual message must be expected", expectedMessage, actualMessage);
    }

    @Test
    public void checkExceptionNoChanges() {
        String commandName = "+ xxx 100";
        String expectedMessage = "No column has been changed!Currency doesn't exist.";
        String actualMessage = getErrorMessage(commandName);
        Assert.assertEquals("Actual message must be expected", expectedMessage, actualMessage);
    }

    public String getErrorMessage(String commandName) {
        ModelMap modelMap = new ModelMap();
        command.setCommandName(commandName);
        commandController.processCommand(command, result, modelMap);
        ErrorCodes actualResult = (ErrorCodes) modelMap.get("errorCode");
        return actualResult.getErrorMessage();
    }
}
