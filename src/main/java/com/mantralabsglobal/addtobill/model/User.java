package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class User {

	@Id
	private String userId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
