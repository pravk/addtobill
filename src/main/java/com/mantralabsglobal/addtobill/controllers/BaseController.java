package com.mantralabsglobal.addtobill.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class BaseController {

	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleException(HttpServletRequest req, IllegalArgumentException exception) {
		logger.warn("Request failed", exception);
		ErrorResponse response = new ErrorResponse();
		response.setMessage(exception.getMessage());
		response.setPath(req.getServletPath());
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		response.setTimestamp(new Date().getTime());
		return new ResponseEntity<ErrorResponse>(response , HttpStatus.BAD_REQUEST);
	}
	
	
	public static class ErrorResponse{
		private long timestamp;
		private int status;
		private String error;
		private String message;
		private String path;
		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}
		
		
	}
}
