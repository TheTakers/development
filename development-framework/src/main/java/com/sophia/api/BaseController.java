package com.sophia.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sophia.response.Response;

/**
 * @author zkning
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected HttpSession getSession() {
        HttpSession session = null;
        try {
            session = getRequest().getSession();
        } catch (Exception e) {
        	logger.error("session获取异常",e);
        }
        return session;
    }

    protected HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Response<Object>> handleRuntimeException(RuntimeException ex,HttpServletRequest request){
        logger.error("拦截运行时异常:"+request.getRequestURL(),ex);
        return new ResponseEntity<Response<Object>>(Response.FAILURE(ex), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object>> handleException(Exception ex,HttpServletRequest request) {
        logger.error("拦截异常:"+request.getRequestURL(),ex);
        return new ResponseEntity<Response<Object>>(Response.FAILURE(ex), HttpStatus.OK);
    }

}