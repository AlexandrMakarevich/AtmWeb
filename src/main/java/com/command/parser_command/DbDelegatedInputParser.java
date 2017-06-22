package com.command.parser_command;

import com.command.DbCommand;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("dbDelegatedInputParser")
public class DbDelegatedInputParser {

    private List<DbInputParser> inputParsers;

    @Resource(name = "listOfParsers")
    public void setInputParsers(List<DbInputParser> inputParsers) {
        this.inputParsers = inputParsers;
    }

    public DbCommand defaultParseInput(String inputString) {
        for (DbInputParser inputParser : inputParsers ) {
            if (inputParser.commandMatch(inputString)) {
                return inputParser.parseInput(inputString);
            }
        }
        throw new IllegalArgumentException("Wrong command " + inputString);
    }
}
