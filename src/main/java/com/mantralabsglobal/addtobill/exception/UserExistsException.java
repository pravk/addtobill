package com.mantralabsglobal.addtobill.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9043136507393181632L;
	
	
}
