package com.mantralabsglobal.addtobill.service;

import org.springframework.data.annotation.Id;

public class Lock {
	
	@Id
	private String objectId;

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

}
