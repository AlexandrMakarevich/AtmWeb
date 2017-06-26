package com.command;

import com.atm_exeption.AtmException;

import java.sql.SQLException;
import java.util.List;

public interface DbCommand {

    List<PrintBalance> executeDb(int accountName);

    CommandName getCommandOperation();
}
