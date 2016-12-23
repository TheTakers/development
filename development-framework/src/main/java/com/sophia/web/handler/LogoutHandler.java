package com.sophia.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.sophia.response.Response;
import com.sophia.web.constant.StatusCodeConstant;

/**
 * Created by root on 2015/12/14.
 */
public class LogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Response<String> response = new Response<String>(StatusCodeConstant.SUCCESS.code, StatusCodeConstant.SUCCESS.message);
        httpServletResponse.getWriter().write(new String(response.toJsonString().getBytes(),"UTF-8"));
    }
}
