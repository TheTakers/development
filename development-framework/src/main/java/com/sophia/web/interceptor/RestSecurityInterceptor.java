package com.sophia.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

/**
 * Created by Kim on 2015/9/22.
 */
public class RestSecurityInterceptor implements HandlerInterceptor {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    public static List<String> IGNORE_PATH = Lists.newArrayList();

    public static final String PERMIT_PREFIX_URI = "/api/permit";

    static {
        IGNORE_PATH.add("/api/security/login");
        IGNORE_PATH.add("/hello");
        IGNORE_PATH.add("/api/security/keepLogin");
        IGNORE_PATH.add("/api/security/sendVerifyNo");
        IGNORE_PATH.add("/api/security/register");
        IGNORE_PATH.add("/api/apiMgr");
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    	
    	String uri = httpServletRequest.getRequestURI();
    	String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    	logger.info("userName:{},sessionId:{},requestURI:{}",
    			userName,httpServletRequest.getSession().getId(),httpServletRequest.getRequestURI());
        if (uri.contains(PERMIT_PREFIX_URI)) {
            return true;
        }

        if (IGNORE_PATH.contains(uri)) {
            return true;
        }

        if (StringUtils.isBlank(userName) || StringUtils.equals("anonymousUser", userName)) {
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            logger.error("response sessionId ：" + httpServletResponse.getHeader("Set-Cookie") );
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    	if(httpServletResponse.getStatus() == HttpStatus.FORBIDDEN.value())
    		logger.error("未授权请求,返回403");
    }
}
