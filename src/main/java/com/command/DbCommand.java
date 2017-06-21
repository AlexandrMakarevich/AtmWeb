package com.command;

import java.sql.SQLException;

public interface DbCommand {

    void executeDb(int accountName) throws SQLException;
}
