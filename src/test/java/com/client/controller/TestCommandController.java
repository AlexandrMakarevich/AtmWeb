package com.client.controller;

import com.client.BaseCommandTest;
import com.controller.CommandController;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

public class TestCommandController extends BaseCommandTest {

    @Resource(name = "commandController")
    private CommandController commandController;
    private String accountName = "Vova";
    private String currencyName = "rub";
    private int balance = 200;

    @Before
    public void init() {

    }

    @Test
    public void checkWrongCommand() {
        cleanTable("account", "currency", "debit");
    }
}
