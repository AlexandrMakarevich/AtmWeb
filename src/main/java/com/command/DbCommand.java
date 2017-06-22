package com.command;

import java.sql.SQLException;

public interface DbCommand<T> {

    T executeDb(int accountName) throws SQLException;
}
