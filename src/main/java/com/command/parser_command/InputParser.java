package com.command.parser_command;

import com.command.CommandInt;

public interface InputParser {

    boolean commandMatch(String inputString);

    <T extends CommandInt> T parseInput(String inputString);
}
