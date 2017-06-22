package com.controller;

import com.client.AccountDao;
import com.command.Command;
import com.command.DbCommand;
import com.command.PrintBalance;
import com.command.parser_command.DbDelegatedInputParser;
import com.validator.CommandValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

import static com.controller.AccountController.ACCOUNT_ID_ATTRIBUTE_NAME;
import static com.controller.AccountController.ACCOUNT_NAME_MODEL_ATTRIBUTE;

@Controller("commandController")
@RequestMapping("/command")
public class CommandController {

    @Resource(name = "commandValidator")
    private CommandValidator commandValidator;

    private DbDelegatedInputParser delegatedInputParser;

    @Resource(name = "accountDaoImpl")
    private AccountDao accountDao;
    private static final String COMMAND_PAGE_NAME = "command";
    private static final String COMMAND_ATTRIBUTE_NAME = "command";

    @InitBinder(COMMAND_ATTRIBUTE_NAME)
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(commandValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showCommand(ModelMap model) {
        Command command = new Command();
        command.setAccountId((Integer) model.get(ACCOUNT_ID_ATTRIBUTE_NAME));
        command.setAccountName((String) model.get(ACCOUNT_NAME_MODEL_ATTRIBUTE));
        model.addAttribute(COMMAND_ATTRIBUTE_NAME, command);
        return COMMAND_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String checkCommand(@Valid Command command, BindingResult result, ModelMap model) throws SQLException {
        if (result.hasErrors()) {
            return COMMAND_PAGE_NAME;
        }
        DbCommand<List<PrintBalance>> dbCommand = delegatedInputParser.defaultParseInput(command.getCommandName());
        List<PrintBalance> printBalances = dbCommand.executeDb(command.getAccountId());
        model.addAttribute("listPrintBalance", printBalances);
        return COMMAND_PAGE_NAME;
    }

    @Resource(name = "dbDelegatedInputParser")
    public void setDelegatedInputParser(DbDelegatedInputParser delegatedInputParser) {
        this.delegatedInputParser = delegatedInputParser;
    }
}
