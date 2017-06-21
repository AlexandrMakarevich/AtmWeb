package com.command.parser_command;

import com.command.DbCommand;

public interface DbInputParser {

    boolean commandMatch(String inputString);

    DbCommand parseInput(String inputString);
}
