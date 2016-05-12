package com.sophia.web.home;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Kim on 2015/9/14.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("")
    @Secured("ROLE_ADMIN")
    public ModelAndView index() {
        return new ModelAndView("home/index");
    }

}
