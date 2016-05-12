package com.sophia.web.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

/**
 * Created by Kim on 2015/9/22.
 */
public class SecurityUtils {

    public static void login(String username, String password) {
        SecurityContextImpl securityContext = new SecurityContextImpl();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(username, password, null));
        SecurityContextHolder.setContext(securityContext);
    }

}
