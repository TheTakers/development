package com.sophia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Kim on 2015/9/14.
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/api/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/hello").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/**").authenticated();
        
        http    .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login?expired");
        http.formLogin().loginPage("/login").defaultSuccessUrl("/", true).failureUrl("/login?error");
//        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessHandler(new LogoutHandler());
        http.csrf().disable();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers("/static/**");
    }

}