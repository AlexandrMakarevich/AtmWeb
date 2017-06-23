package com.command.parser_command;

import com.command.DbAddCommand;
import com.command.DbCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("dbAddParser")
public class DbAddParser implements DbInputParser{

    private Pattern dbAddPattern = Pattern.compile("^\\+ ([a-z]{3}) ([0-9]{1,10})$");
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean commandMatch(String inputString) {
        Matcher dbAdd = dbAddPattern.matcher(inputString);
        return dbAdd.matches();
    }

    @Override
    public DbAddCommand parseInput(String inputString) {
        Matcher dbAdd = dbAddPattern.matcher(inputString);
        if(dbAdd.matches()) {
            String currency = dbAdd.group(1);
            Integer amount = Integer.parseInt(dbAdd.group(2));
            return new DbAddCommand(namedParameterJdbcTemplate, currency, amount);
        }
        throw new IllegalArgumentException("Wrong command : " + inputString );
    }
}
