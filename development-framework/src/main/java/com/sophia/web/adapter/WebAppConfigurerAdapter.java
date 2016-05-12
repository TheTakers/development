package com.sophia.web.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sophia.web.interceptor.DiagnosticInterceptor;
import com.sophia.web.interceptor.RestSecurityInterceptor;

/**
 * Created by Kim on 2015/9/22.
 */
@Configuration
public class WebAppConfigurerAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RestSecurityInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(new DiagnosticInterceptor()).addPathPatterns("/**");
    }
}