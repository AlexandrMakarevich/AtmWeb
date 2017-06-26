package com.command.parser_command;

import com.command.DbWithdrawCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("dbWithdrawParser")
public class DbWithdrawParser implements DbInputParser {

    private Pattern dbWithdrawPattern = Pattern.compile("^- ([a-z]{3}) ([0-9]{1,10})$");
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean commandMatch(String inputString) {
        Matcher dbWithdraw = dbWithdrawPattern.matcher(inputString);
        return dbWithdraw.matches();
    }

    @Override
    public DbWithdrawCommand parseInput(String inputString) {
        Matcher dbWithdraw = dbWithdrawPattern.matcher(inputString);
        if (dbWithdraw.matches()) {
            String currency = dbWithdraw.group(1);
            Integer amount = Integer.valueOf(dbWithdraw.group(2));
            return new DbWithdrawCommand(namedParameterJdbcTemplate, currency, amount);
        }
        throw new IllegalArgumentException("Wrong command : " + inputString);
    }
}
