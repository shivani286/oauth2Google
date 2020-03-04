package com.syncgoogle.springoauth2project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		 http.antMatcher("/**").authorizeRequests()
		 .antMatchers("/oauth2/v1").permitAll()
         .anyRequest().authenticated()
         .and()
         .httpBasic();
    }
}