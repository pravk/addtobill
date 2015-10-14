package com.mantralabsglobal.addtobill.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2799032508854109701L;

	public InvalidRequestException(){
		super();
	}
	
	public InvalidRequestException(String exception){
		super(exception);
	}
}
