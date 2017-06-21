package com.controller;

import com.client.Account;
import com.client.AccountDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import javax.validation.Valid;

@Controller("accountController")
@RequestMapping("/account")
public class AccountController {

    @Resource(name = "accountValidator")
    private AccountValidator accountValidator;
    private AccountDao accountDao;
    private final static String ACCOUNT_PAGE_NAME = "accountJsp";
    private final static String ACCOUNT = "account";

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(accountValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showAccount(ModelMap model) {
        Account account = new Account();
        model.addAttribute(ACCOUNT, account);
        return ACCOUNT_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String checkAccount(@Valid Account account, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return ACCOUNT_PAGE_NAME;
        }
        return "command";
    }

    @Resource(name = "accountDaoImpl")
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

}
