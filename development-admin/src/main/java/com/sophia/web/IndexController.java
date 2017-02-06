package com.sophia.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Kim on 2015/9/14.
 */
@Controller
public class IndexController {

    @RequestMapping("")
    @Secured("ROLE_ADMIN")
    public ModelAndView main() {
        return new ModelAndView("main");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping(name="Hello World",value = "/hello", method = RequestMethod.GET)
    public String getAllApi() {
        return "Hello World!";
    }

}
