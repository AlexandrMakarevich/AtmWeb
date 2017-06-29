package com.command.parser_command;

import com.command.AddCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("addParser")
public class AddParser implements InputParser {

    private Pattern AddPattern = Pattern.compile("^\\+ ([a-z]{3}) ([0-9]{1,10})$");
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean commandMatch(String inputString) {
        Matcher add = AddPattern.matcher(inputString);
        return add.matches();
    }

    @Override
    public AddCommand parseInput(String inputString) {
        Matcher add = AddPattern.matcher(inputString);
        if(add.matches()) {
            String currency = add.group(1);
            Integer amount = Integer.parseInt(add.group(2));
            return new AddCommand(namedParameterJdbcTemplate, currency, amount);
        }
        throw new IllegalArgumentException("Wrong command : " + inputString );
    }
}
