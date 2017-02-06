package com.sophia.web.console;

import com.google.common.collect.Lists;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Kim on 2015/9/14.
 */
@Controller
@RequestMapping("/console")
public class ContextController {

    @RequestMapping("/context")
    @Secured("ROLE_ADMIN")
    public ModelAndView context(HttpServletRequest request, ModelMap result) {
        ApplicationContext context = RequestContextUtils.findWebApplicationContext(request);

        List<BeanDefination> controllers = Lists.newArrayList();
        List<BeanDefination> handlerMappings = Lists.newArrayList();
        List<BeanDefination> viewResolvers = Lists.newArrayList();
        for (String name : context.getBeanDefinitionNames()) {
            Class<?> type = context.getType(name);
            if (name.endsWith("Controller")) {
                controllers.add(new BeanDefination(name, type));
            } else if (HandlerMapping.class.isAssignableFrom(type)) {
                handlerMappings.add(new BeanDefination(name, type));
            } else if (ViewResolver.class.isAssignableFrom(type)) {
                viewResolvers.add(new BeanDefination(name, type));
            }
        }
        result.put("controllers", controllers);
        result.put("handlerMappings", handlerMappings);
        result.put("viewResolvers", viewResolvers);
        return new ModelAndView("console/context", result);
    }

    class BeanDefination {
        private String name;
        private Class<?> type;

        public BeanDefination(String name, Class<?> type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }
    }

}
