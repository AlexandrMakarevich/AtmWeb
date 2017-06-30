package com.client;

import com.BaseCommandTest;
import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Test;
import javax.annotation.Resource;

public class TestAccountDaoImpl extends BaseCommandTest {

    @Resource(name = "accountDaoImpl")
    private AccountDao accountDao;

    @Test
    public void testFindAccountByName() {
        String accountName = "petrov";
        cleanTable("account");
        insert("account", "account_name", accountName);
        Optional<Account> accountExist = accountDao.findAccountByName(accountName);
        Assert.assertEquals("AccountExist must be the same as accountName", accountExist.get().getAccountName(), accountName);
    }
}
