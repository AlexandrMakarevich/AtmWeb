package com.controller;

import com.command.Command;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("commandController")
@RequestMapping("/command")
public class CommandController {


    @RequestMapping(method = RequestMethod.GET)
    public String showCommand(ModelMap model) {
        Command command = new Command();
        model.addAttribute("command" ,command);
        return "command";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String checkCommand(Command command, BindingResult result, ModelMap model) {
        return "operationDone";
    }
}
