package com.command.parser_command;

import com.command.WithdrawCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("withdrawParser")
public class WithdrawParser implements InputParser {

    private Pattern withdrawPattern = Pattern.compile("^- ([a-z]{3}) ([0-9]{1,10})$");
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean commandMatch(String inputString) {
        Matcher withdraw = withdrawPattern.matcher(inputString);
        return withdraw.matches();
    }

    @Override
    public WithdrawCommand parseInput(String inputString) {
        Matcher withdraw = withdrawPattern.matcher(inputString);
        if (withdraw.matches()) {
            String currency = withdraw.group(1);
            Integer amount = Integer.valueOf(withdraw.group(2));
            return new WithdrawCommand(namedParameterJdbcTemplate, currency, amount);
        }
        throw new IllegalArgumentException("Wrong command : " + inputString);
    }
}
