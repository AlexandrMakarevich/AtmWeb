package com.command.parser_command;

import com.command.DbCommand;
import com.command.DbPrintBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("dbPrintParser")
public class DbPrintParser implements DbInputParser {

    private Pattern dbPrintPattern = Pattern.compile("^balance$");
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean commandMatch(String inputString) {
        Matcher dbPrint = dbPrintPattern.matcher(inputString);
        return dbPrint.matches();
    }

    @Override
    public DbCommand parseInput(String inputString) {
        Matcher dbPrint = dbPrintPattern.matcher(inputString);
        if (dbPrint.find()) {
            return new DbPrintBalance(namedParameterJdbcTemplate);
        }
        throw new IllegalArgumentException("Wrong command : " + inputString);
    }
}
