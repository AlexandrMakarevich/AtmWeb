package com.controller;

import com.BaseCommandTest;
import com.client.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import javax.annotation.Resource;
import java.util.HashMap;

public class TestAccountController extends BaseCommandTest {

    @Resource(name = "accountController")
    private AccountController accountController;
    private MapBindingResult result;
    private Account account;
    private String accountName = "Petya";

    @Before
    public void init() {
        result = new MapBindingResult(new HashMap(), "aa");
        account = new Account();
        account.setAccountName(accountName);
    }

    @Test
    public void checkWrongAccount() {
        String expectedPageName = "accountJsp";
        cleanTable("account");
        result.rejectValue("accountName", "account_not_found");
        ModelAndView actualPageName = accountController.checkAccount(account, result, new RedirectAttributesModelMap());
        Assert.assertEquals("ActualPageName must be expectedPageName ", expectedPageName, actualPageName.getViewName());
    }

    @Test
    public void checkExistAccount() {
        String expectedPageName = "redirect:/command";
        cleanTable("account");
        insert("account", "account_name", accountName);
        ModelAndView actualPageName = accountController.checkAccount(account, result, new RedirectAttributesModelMap());
        Assert.assertEquals("ActualPageName must be expectedPageName ", expectedPageName, actualPageName.getViewName());
    }
}
