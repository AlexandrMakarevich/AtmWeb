package com.command;

import java.sql.SQLException;
import java.util.List;

public interface DbCommand {

    List<PrintBalance> executeDb(int accountName) throws SQLException;

    CommandName getCommandOperation();
}
