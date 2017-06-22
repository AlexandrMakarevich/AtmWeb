package com.controller;

import com.client.Account;
import com.validator.AccountValidator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.annotation.Resource;
import javax.validation.Valid;

@Controller("accountController")
@RequestMapping("/account")
public class AccountController {

    @Resource(name = "accountValidator")
    private AccountValidator accountValidator;
    private static final String ACCOUNT_PAGE_NAME = "accountJsp";
    private static final String ACCOUNT = "account";
    public static final String ACCOUNT_ID_ATTRIBUTE_NAME = "accountId";
    public static final String ACCOUNT_NAME_MODEL_ATTRIBUTE = "accountName";
    private static final Logger LOGGER = Logger.getLogger(AccountController.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(accountValidator);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showAccount(ModelMap model) {
        Account account = new Account();
        model.addAttribute(ACCOUNT, account);
        return ACCOUNT_PAGE_NAME;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView checkAccount(
            @Valid
            @ModelAttribute("account") Account account,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView(ACCOUNT_PAGE_NAME);
        }
        redirectAttributes.addFlashAttribute(ACCOUNT_NAME_MODEL_ATTRIBUTE, account.getAccountName());
        redirectAttributes.addFlashAttribute(ACCOUNT_ID_ATTRIBUTE_NAME, account.getId());
        return new ModelAndView("redirect:/command");
    }
}
