package com.sophia.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Maps;
import com.sophia.web.constant.Constant;

/**
 * @author zkning
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Map<String, Object> responseOk(Object obj) {
    	Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put(Constant.KEY_OF_CODE, Constant.STATUS_CODE_SUCCESS);
        resultMap.put(Constant.KEY_OF_MESSAGE, Constant.RESPONSE_OK);
        resultMap.put(Constant.KEY_OF_RESULT, obj);
        return resultMap;
    }

    protected Map<String, Object> responseOk(String msg, Object obj) {
    	Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put(Constant.KEY_OF_CODE, Constant.STATUS_CODE_SUCCESS);
        resultMap.put(Constant.KEY_OF_MESSAGE, msg);
        resultMap.put(Constant.KEY_OF_RESULT, obj);
        return resultMap;
    }

    protected Map<String, Object> responseError(String msg) {
    	Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put(Constant.KEY_OF_CODE, Constant.STATUS_CODE_FAILURE);
        resultMap.put(Constant.KEY_OF_MESSAGE, msg);
        resultMap.put(Constant.KEY_OF_RESULT, null);
        return resultMap;
    }
    
    protected Map<String, Object> responseError(Object obj) {
       return responseError(obj, null);
    }

    protected Map<String, Object> responseError(Object obj, Exception e) {
    	Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put(Constant.KEY_OF_CODE, Constant.STATUS_CODE_FAILURE);
        resultMap.put(Constant.KEY_OF_MESSAGE, obj);
        resultMap.put(Constant.KEY_OF_RESULT, null);
        if (e != null) {
            logger.error(e.getMessage(), e);
        }
        return resultMap;
    }

    protected HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {}
        return session;
    }

    protected HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return attrs.getRequest();
    }

    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> catchRuntimeExp(RuntimeException ex,HttpServletRequest request){
        logger.error(request.getRequestURL()+"拦截运行时异常",ex);
        return responseError(Constant.RUNTIME_ERROR_MESSAGE);
    }

    @ExceptionHandler(Exception.class)
    public Map<String, Object> catchExp(Exception ex,HttpServletRequest request) {
        logger.error(request.getRequestURL()+"拦截异常",ex);
        return responseError(Constant.SYSTEM_ERROR_MESSAGE);
    }

}