package controller;

import client.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account")
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    public String showAccount(ModelMap model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "accountJsp";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String checkAccount(Account account, BindingResult result, ModelMap model ) {
        return "command";
    }

}
