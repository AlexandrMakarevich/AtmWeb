package com.controller;

import com.command.CommandBean;
import com.command.CommandInt;
import com.command.PrintBalance;
import com.command.parser_command.DelegatedInputParser;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class RestCommandController {

    private DelegatedInputParser delegatedInputParser;

    @RequestMapping(value = "/commandTest", method = RequestMethod.POST)
    public List<PrintBalance> processCommand(@RequestBody CommandBean commandBean) {
        CommandInt commandInt = delegatedInputParser.defaultParseInput(commandBean.getCommandName());
        return commandInt.executeDb(commandBean.getAccountId());
    }

    @Resource(name = "delegatedInputParser")
    public void setDelegatedInputParser(DelegatedInputParser delegatedInputParser) {
        this.delegatedInputParser = delegatedInputParser;
    }
}
