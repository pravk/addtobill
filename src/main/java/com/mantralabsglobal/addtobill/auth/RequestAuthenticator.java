package com.mantralabsglobal.addtobill.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestAuthenticator {

	boolean authenticate(HttpServletRequest request, HttpServletResponse response);
}
