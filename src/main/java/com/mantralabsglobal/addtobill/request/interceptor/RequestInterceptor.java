package com.mantralabsglobal.addtobill.request.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mantralabsglobal.addtobill.auth.RequestAuthenticator;

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter{

	RequestAuthenticator authenticator;
	/**
	 * This implementation always returns {@code true}.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		
		return authenticator.authenticate(request, response);
				
	}
	
}
