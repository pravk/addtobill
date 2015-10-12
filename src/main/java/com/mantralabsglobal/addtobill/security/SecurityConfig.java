package com.mantralabsglobal.addtobill.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
		antMatchers("/merchant/**").access("hasRole('ROLE_MERCHANT')").
		antMatchers("/user/**").access("hasRole('ROLE_USER')").
		antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").
		and().httpBasic().
		and().csrf().disable();
	}
	
}   