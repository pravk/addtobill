package com.mantralabsglobal.addtobill.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class UserAccount {

	private static final String ROLE_VIEWER= "VIEWER"; 
	private static final String ROLE_OWNER= "OWNER";
	
	@Id
	private String userAccountId;
	private String userId;
	public String getUserAccountId() {
		return userAccountId;
	}
	public void setUserAccountId(String userAccountId) {
		this.userAccountId = userAccountId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	private String accountId;
	private List<String> roles;
}
