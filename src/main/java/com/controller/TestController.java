package com.controller;

import com.client.Currency;
import org.springframework.web.bind.annotation.*;

@RestController

public class TestController {

//    @RequestMapping(value = "/currency/{name}", method = RequestMethod.GET)
//    public Currency getCurrency(@PathVariable ("name") String name) {
//        return new Currency(name);
//    }
//
//    @RequestMapping(value = "/currency", method = RequestMethod.GET)
//    public Currency getCurrencyParam(@RequestParam(value = "name") String name) {
//        return new Currency(name);
//    }

    @RequestMapping(value = "/currency", method = RequestMethod.POST)
    public Currency createCurrency(@RequestBody Currency currency) {
        return currency;
    }
}
