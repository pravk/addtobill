package com.mantralabsglobal.addtobill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mantralabsglobal.addtobill.auth.StatelessAuthenticationFilter;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	StatelessAuthenticationFilter authFilter;
	@Autowired
	UserService userService;
	
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().
		antMatchers("/user/register").permitAll().
		antMatchers("/user/login").permitAll().
		antMatchers("/merchant/register").permitAll().
		antMatchers("/merchant/**").access("hasRole('" + Merchant.ROLE_MERCHANT + "')").
		antMatchers("/user/**").access("hasRole('"+ User.ROLE_USER +"')").
		//antMatchers("/admin/**").access("hasRole('"+ User.ROLE_ADMIN + "')").
		anyRequest().access("hasRole('ROLE_ADMIN')").
		and().csrf().disable().
		addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return passwordEncoder;
	}
	
	@Bean
	public GlobalAuthenticationConfigurerAdapter getAuthConfAdapter(){
		return new GlobalAuthenticationConfigurerAdapter() {
			 @Override
			  public void init(AuthenticationManagerBuilder auth) throws Exception {
				 auth.userDetailsService(userService)
			    	.passwordEncoder(passwordEncoder());
			  }
		};
	}

}   