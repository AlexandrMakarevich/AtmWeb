package com.validator;

import com.command.Command;
import com.command.parser_command.DelegatedInputParser;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import javax.annotation.Resource;

@Component("commandValidator")
public class CommandValidator implements Validator {

    private DelegatedInputParser delegatedInputParser;

    @Resource(name = "delegatedInputParser")
    public void setDelegatedInputParser(DelegatedInputParser delegatedInputParser) {
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
