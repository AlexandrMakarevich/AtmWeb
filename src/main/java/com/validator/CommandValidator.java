package com.validator;

import com.command.Command;
import com.command.parser_command.DbDelegatedInputParser;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import javax.annotation.Resource;

@Component("commandValidator")
public class CommandValidator implements Validator {

    private DbDelegatedInputParser delegatedInputParser;

    @Resource(name = "dbDelegatedInputParser")
    public void setDelegatedInputParser(DbDelegatedInputParser delegatedInputParser) {
        this.delegatedInputParser = delegatedInputParser;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Command.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Command command = (Command) o;
        try {
            delegatedInputParser.defaultParseInput(command.getCommandName());
        }
        catch(IllegalArgumentException e) {
            errors.rejectValue("commandName", "command_not_found");
        }
    }
}