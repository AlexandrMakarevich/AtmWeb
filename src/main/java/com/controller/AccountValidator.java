package com.controller;

import com.client.Account;
import com.client.AccountDao;
import com.google.common.base.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.annotation.Resource;

@Repository("accountValidator")
public class AccountValidator implements Validator {

    @Resource(name = "accountDaoImpl")
    private AccountDao accountDao;

    public boolean supports(Class<?> aClass) {
        return Account.class.equals(aClass);
    }

    public void validate(Object o, Errors errors) {
        Account account = (Account) o;
        Optional<Account> accountExist = accountDao.findAccountByName(account.getAccountName());
        if (accountExist.isPresent()) {
            account.setAccountName(accountExist.get().getAccountName());
            account.setId((accountExist.get().getId()));
            return;
        }
        errors.rejectValue("accountName", "account_not_found");
    }
}
