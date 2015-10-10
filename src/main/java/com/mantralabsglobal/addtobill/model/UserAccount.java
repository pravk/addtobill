package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.Id;

public class UserAccount {

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
	
	public UserAccountRole getRole() {
		return role;
	}
	public void setRole(UserAccountRole role) {
		this.role = role;
	}

	private String accountId;
	private UserAccountRole role;
}
