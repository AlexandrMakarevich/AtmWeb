package com.command.parser_command;

import com.command.DbCommand;

public interface DbInputParser {

    boolean commandMatch(String inputString);

    <T extends DbCommand> T parseInput(String inputString);
}
