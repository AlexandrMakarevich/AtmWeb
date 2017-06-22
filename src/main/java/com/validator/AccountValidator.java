package com.validator;

import com.client.Account;
import com.client.AccountDao;
import com.google.common.base.Optional;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import javax.annotation.Resource;

@Component("accountValidator")
public class AccountValidator implements Validator {

    private AccountDao accountDao;

    @Resource(name = "accountDaoImpl")
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Account.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Account account = (Account) o;
        Optional<Account> accountExist = accountDao.findAccountByName(account.getAccountName());
        if (!accountExist.isPresent()) {
            errors.rejectValue("accountName", "account_not_found");
            return;
        }
        account.setAccountName(accountExist.get().getAccountName());
        account.setId((accountExist.get().getId()));
    }
}
