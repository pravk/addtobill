package com.mantralabsglobal.addtobill.service;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.auth.MerchantAuthentication;
import com.mantralabsglobal.addtobill.model.Merchant;

@Component
public class MerchantAuthenticationService {

	private Logger logger = LoggerFactory.getLogger(MerchantAuthenticationService.class);
	public static final String AUTHENTICATION_HEADER = "Authorization";
	
	@Autowired
    private MerchantService merchantService;

	public Authentication authenticate(HttpServletRequest request) {

		if(request == null)
			return null;
		
		String authCredentials = request.getHeader(AUTHENTICATION_HEADER);
		// header value format will be "Basic encodedstring" for Basic
		// authentication. Example "Basic YWRtaW46YWRtaW4="
		final String encodedUserPassword = authCredentials.replaceFirst("Basic"
				+ " ", "");
		String usernameAndPassword = null;
		try {
			byte[] decodedBytes = Base64.decodeBase64(
					encodedUserPassword);
			usernameAndPassword = new String(decodedBytes, "UTF-8");
		} catch (IOException e) {
			logger.warn("Failed to parse token", e);
		}
		final StringTokenizer tokenizer = new StringTokenizer(
				usernameAndPassword, ":");
		final String userName = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		
		Merchant merchant = null;
		try {
			merchant = merchantService.athenticateMerchant(userName, password);
		} catch (AuthenticationException e) {
			logger.warn("Failed to authenticate merchant", e);
		}
		
		if(merchant != null)
			return new MerchantAuthentication(merchant);
		else
			return null;
	}
}