package com.sophia.api.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Maps;
import com.sophia.domain.User;
import com.sophia.web.constant.Constant;

public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected void responseOk(Map<String, Object> resultMap, Object obj) {
        resultMap.put(Constant.KEY_OF_CODE, Constant.STATUS_CODE_SUCCESS);
        resultMap.put(Constant.KEY_OF_MESSAGE, Constant.RESPONSE_OK);
        resultMap.put(Constant.KEY_OF_RESULT, obj);
    }

    protected void responseOk(Map<String, Object> resultMap, String msg, Object obj) {
        resultMap.put(Constant.KEY_OF_CODE, Constant.STATUS_CODE_SUCCESS);
        resultMap.put(Constant.KEY_OF_MESSAGE, msg);
        resultMap.put(Constant.KEY_OF_RESULT, obj);
    }

    protected void responseError(Map<String, Object> resultMap, String msg) {
        resultMap.put(Constant.KEY_OF_CODE, Constant.STATUS_CODE_FAILURE);
        resultMap.put(Constant.KEY_OF_MESSAGE, msg);
        resultMap.put(Constant.KEY_OF_RESULT, null);
    }
    protected void responseError(Map<String, Object> resultMap, Object obj) {
        responseError(resultMap, obj, null);
    }

    protected void responseError(Map<String, Object> resultMap, Object obj, Exception e) {
        resultMap.put(Constant.KEY_OF_CODE, Constant.STATUS_CODE_FAILURE);
        resultMap.put(Constant.KEY_OF_MESSAGE, obj);
        resultMap.put(Constant.KEY_OF_RESULT, null);
        if (e != null) {
            logger.error(e.getMessage(), e);
        }
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

    /***
     * 从Session中取得用户信息
     * @return
     */
    protected User getSessionUser() {
    	if(getSession()!=null)
    		return (User) getSession().getAttribute("User");
    	return null;
    }

    /**
     * 设置用户信息到session
     * @param User
     */
    protected void setSessionUser(User User){
    	if(getSession()!=null){
    		getSession().setAttribute("User",User);
    		User.setSessionId(getSession().getId());
    	}
    		
    }

    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> catchRuntimeExp(RuntimeException ex,HttpServletRequest request){
        logger.error(request.getRequestURL()+"拦截运行时异常",ex);
        Map<String, Object> resultMap = Maps.newHashMap();
        responseError(resultMap,Constant.RUNTIME_ERROR_MESSAGE);
        return resultMap;
    }

    @ExceptionHandler(Exception.class)
    public Map<String, Object> catchExp(Exception ex,HttpServletRequest request) {
        logger.error(request.getRequestURL()+"拦截异常",ex);
        Map<String, Object> resultMap = Maps.newHashMap();
        responseError(resultMap,Constant.SYSTEM_ERROR_MESSAGE);
        return resultMap;
    }

}