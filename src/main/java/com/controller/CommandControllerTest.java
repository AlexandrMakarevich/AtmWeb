package com.controller;

import com.command.Command;
import com.command.PrintBalanceService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CommandControllerTest {

    @RequestMapping(value = "/commandTest", method = RequestMethod.POST)
    public List<PrintBalanceService> processCommand(@RequestBody Command command) {
        List<PrintBalanceService> list = new ArrayList<>();
        list.add(new PrintBalanceService("eur", 88));
        list.add(new PrintBalanceService("usd", 44));
        return list;
    }
}
