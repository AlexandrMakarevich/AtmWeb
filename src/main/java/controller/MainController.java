package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @RequestMapping("/start")
    public ModelAndView someMethod( @RequestParam(value = "name", required = false) String urlParameter) {
        ModelAndView modelAndView = new ModelAndView("redegit");
        modelAndView.addObject("modelName", urlParameter);
        return modelAndView;
    }
}
