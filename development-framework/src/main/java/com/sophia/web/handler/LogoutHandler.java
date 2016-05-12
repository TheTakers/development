package com.sophia.web.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.alibaba.fastjson.JSONObject;
import com.sophia.web.constant.Constant;

/**
 * Created by root on 2015/12/14.
 */
public class LogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(Constant.KEY_OF_CODE,Constant.STATUS_CODE_SUCCESS);
        map.put(Constant.KEY_OF_MESSAGE, Constant.RESPONSE_OK);
        map.put(Constant.KEY_OF_RESULT, "");
        String str = JSONObject.toJSON(map).toString();
        httpServletResponse.getWriter().write(new String(str.getBytes(),"UTF-8"));
    }
}
