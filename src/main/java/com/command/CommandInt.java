package com.command;

import java.util.List;

public interface CommandInt {

    List<PrintBalanceService> executeDb(int accountName);

    CommandName getCommandOperation();
}
