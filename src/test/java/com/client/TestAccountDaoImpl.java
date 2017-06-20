package com.client;

import com.google.common.base.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:atmWeb-test.xml")
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
