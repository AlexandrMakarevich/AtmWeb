package com.command;

import java.util.List;

public interface CommandInt {

    List<PrintBalance> executeDb(int accountName);

    CommandName getCommandOperation();
}
