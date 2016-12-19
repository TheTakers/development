package com.sophia.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sophia.response.Response;
import com.sophia.web.constant.StatusCodeConstant;

/**
 * @author zkning
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {}
        return session;
    }

    protected HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    @ExceptionHandler(RuntimeException.class)
    public Object catchRuntimeExp(RuntimeException ex,HttpServletRequest request){
        logger.error(request.getRequestURL()+"拦截运行时异常",ex);
        return Response.FAILURE(StatusCodeConstant.SERVICE_UNACCESSABLE.getCode(),StatusCodeConstant.SERVICE_UNACCESSABLE.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Object catchExp(Exception ex,HttpServletRequest request) {
        logger.error(request.getRequestURL()+"拦截异常",ex);
        return Response.FAILURE(StatusCodeConstant.SYSTEM_ERROR.getCode(),StatusCodeConstant.SYSTEM_ERROR.getMessage());
    }

}