package com.client;

import com.google.common.base.Optional;

public interface AccountDao {

    Optional<Account> findAccountByName(String accountInput);

    int addAccount(String accountName);
}
