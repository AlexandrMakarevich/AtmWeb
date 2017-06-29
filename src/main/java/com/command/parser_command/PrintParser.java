package com.command.parser_command;

import com.command.PrintBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("printParser")
public class PrintParser implements InputParser {

    private Pattern printPattern = Pattern.compile("^balance$");
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean commandMatch(String inputString) {
        Matcher print = printPattern.matcher(inputString);
        return print.matches();
    }

    @Override
    public PrintBalance parseInput(String inputString) {
        Matcher print = printPattern.matcher(inputString);
        if (print.find()) {
            return new PrintBalance(namedParameterJdbcTemplate);
        }
        throw new IllegalArgumentException("Wrong command : " + inputString);
    }
}
