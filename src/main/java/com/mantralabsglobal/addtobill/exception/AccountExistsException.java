package com.mantralabsglobal.addtobill.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8839872327812417266L;

}
