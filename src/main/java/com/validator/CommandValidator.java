package com.validator;

import com.command.CommandBean;
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
        return CommandBean.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CommandBean commandBean = (CommandBean) o;
        try {
            delegatedInputParser.defaultParseInput(commandBean.getCommandName());
        }
        catch(IllegalArgumentException e) {
            errors.rejectValue("commandName", "command_not_found");
        }
    }
}
