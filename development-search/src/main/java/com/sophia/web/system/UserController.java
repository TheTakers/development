package com.sophia.web.system;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Kim on 2015/9/21.
 */
@Controller
@RequestMapping("/system")
public class UserController {

    @RequestMapping("/user")
    @Secured("ROLE_ADMIN")
    public ModelAndView user() {
        return new ModelAndView("system/user");
    }

}
