package com.controller;

import com.atm_exeption.AtmException;
import com.atm_exeption.CustomAtmException;
import com.command.*;
import com.command.parser_command.DelegatedInputParser;
import com.validator.CommandValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import static com.controller.AccountController.ACCOUNT_ID_ATTRIBUTE_NAME;
import static com.controller.AccountController.ACCOUNT_NAME_MODEL_ATTRIBUTE;

@Controller("commandController")
@RequestMapping("/command")
public class CommandController {

    @Resource(name = "commandValidator")
    private CommandValidator commandValidator;
    private DelegatedInputParser delegatedInputParser;
    private static final String COMMAND_PAGE_NAME = "command";
    private static final String COMMAND_ATTRIBUTE_NAME = "command";
    private Map<CommandName, String> commandPages;

    @InitBinder(COMMAND_ATTRIBUTE_NAME)
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(commandValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showCommand(ModelMap model) {
        CommandBean commandBean = new CommandBean();
        commandBean.setAccountId((Integer) model.get(ACCOUNT_ID_ATTRIBUTE_NAME));
        commandBean.setAccountName((String) model.get(ACCOUNT_NAME_MODEL_ATTRIBUTE));
        model.addAttribute(COMMAND_ATTRIBUTE_NAME, commandBean);
        model.addAttribute("commandNameEnum", "");
        return COMMAND_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processCommand(@Valid CommandBean commandBean, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return COMMAND_PAGE_NAME;
        }
        model.addAttribute("errorCode", "");
        CommandInt commandInt = delegatedInputParser.defaultParseInput(commandBean.getCommandName());
        try {
            List<PrintBalance> resultList = commandInt.executeDb(commandBean.getAccountId());
            model.addAttribute("operationResult", resultList);
        } catch (AtmException errors) {
            model.addAttribute("errorCode", errors.getErrorCodes());
        }
        if (commandPages.get(commandInt.getCommandOperation()) == null) {
            throw new CustomAtmException("Error configurations.Map not initialized.");
        }
        model.addAttribute("commandNameEnum", commandInt.getCommandOperation());
        return commandPages.get(commandInt.getCommandOperation());
    }

    @ExceptionHandler(CustomAtmException.class)
    public ModelAndView handlerCustomException(CustomAtmException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", ex.getErrorMessage());
        return modelAndView;
    }

    @Resource(name = "mappingResultPageAndName")
    public void setCommandPages(Map<CommandName, String> commandPages) {
        this.commandPages = commandPages;
    }

    @Resource(name = "delegatedInputParser")
    public void setDelegatedInputParser(DelegatedInputParser delegatedInputParser) {
        this.delegatedInputParser = delegatedInputParser;
    }
}
