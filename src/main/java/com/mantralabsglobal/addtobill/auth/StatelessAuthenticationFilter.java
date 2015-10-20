package com.mantralabsglobal.addtobill.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.mantralabsglobal.addtobill.service.MerchantAuthenticationService;
import com.mantralabsglobal.addtobill.service.TokenAuthenticationService;

@Component
public class StatelessAuthenticationFilter extends GenericFilterBean {

	@Autowired
    private TokenAuthenticationService tokenAuthenticationService;
	
	@Autowired
    private MerchantAuthenticationService merchantAuthenticationService;
	
 
     @Override
     public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
             throws IOException, ServletException {
         HttpServletRequest httpRequest = (HttpServletRequest) request;
         Authentication authentication = tokenAuthenticationService.getAuthentication(httpRequest);
         if(authentication == null)
        	 authentication = merchantAuthenticationService.authenticate(httpRequest);
         
         SecurityContextHolder.getContext().setAuthentication(authentication);
         filterChain.doFilter(request, response);
         SecurityContextHolder.getContext().setAuthentication(null);
     }
}