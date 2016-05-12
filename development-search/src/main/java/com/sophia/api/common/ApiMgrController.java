package com.sophia.api.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sophia.domain.Api;

/**
 * Created by Kim on 2015/9/11.
 */
@RestController
@RequestMapping(name="API管理类",value = "/api/apiMgr")
public class ApiMgrController extends BaseController {

    @RequestMapping(name="获取所有API",value = "", method = RequestMethod.GET)
    public List<Api> getAllApi() {
        List<Api> apiList = new ArrayList<Api>();
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest req = attrs.getRequest();
        WebApplicationContext web =(WebApplicationContext)
                req.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        Map map = web.getBeansWithAnnotation(RequestMapping.class);
        try {
            for (Iterator it=map.keySet().iterator();it.hasNext();){
                String key = (String)it.next();
                Object ctrl = (Object)map.get(key);
                RequestMapping rm = ctrl.getClass().getAnnotation(RequestMapping.class);
                if (rm==null || rm.value() == null) {
                    continue;
                }
                String uri = rm.value()[0];
                Method[] methods = ctrl.getClass().getMethods();
                for (int i = 0 ; i<methods.length; i++) {
                    RequestMapping requestMapping = methods[i].getAnnotation(RequestMapping.class);
                    if (requestMapping!=null) {
                        Api api = new Api();
                        api.setName(requestMapping.name());
                        api.setUri(uri + ((requestMapping.value().length == 0) ? "" : requestMapping.value()[0]));
                        api.setMethod((requestMapping.method().length == 0) ? RequestMethod.GET : requestMapping.method()[0]);
                        api.setCtrlName(ctrl.getClass().getName());
                        apiList.add(api);
                    }
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return  apiList;
    }

}
