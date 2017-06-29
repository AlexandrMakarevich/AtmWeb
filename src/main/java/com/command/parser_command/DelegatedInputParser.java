package com.command.parser_command;

import com.command.CommandInt;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("delegatedInputParser")
public class DelegatedInputParser {

    private List<InputParser> inputParsers;

    @Resource(name = "listOfParsers")
    public void setInputParsers(List<InputParser> inputParsers) {
        this.inputParsers = inputParsers;
    }

    public CommandInt defaultParseInput(String inputString) {
        for (InputParser inputParser : inputParsers ) {
            if (inputParser.commandMatch(inputString)) {
                return inputParser.parseInput(inputString);
            }
        }
        throw new IllegalArgumentException("Wrong command " + inputString);
    }
}
