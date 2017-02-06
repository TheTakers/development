package com.sophia.web.console;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

/**
 * Created by Kim on 2015/9/18.
 */
@Controller
@RequestMapping("/console")
public class MemoryController {

    @RequestMapping("/memory")
    @Secured("ROLE_ADMIN")
    public ModelAndView memory(ModelMap result) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        result.put("heap", memoryMXBean.getHeapMemoryUsage());
        result.put("nonHeap", memoryMXBean.getNonHeapMemoryUsage());
        return new ModelAndView("console/memory", result);
    }

    @RequestMapping(value = "/memory/gc", method = RequestMethod.POST)
    @Secured("ROLE_ADMIN")
    public ModelAndView gc() {
        Runtime.getRuntime().gc();
        return new ModelAndView("redirect:/#/console/memory?success");
    }

}
