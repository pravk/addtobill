package com.mantralabsglobal.addtobill.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

public class BaseEntity{

	public String getCreationTS() {
		return creationTS;
	}

	public void setCreationTS(String creationTS) {
		this.creationTS = creationTS;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdateTS() {
		return updateTS;
	}

	public void setUpdateTS(String updateTS) {
		this.updateTS = updateTS;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@CreatedDate
	private String creationTS;
	@CreatedBy
	private String createdBy;
	@LastModifiedDate
	private String updateTS;
	@LastModifiedBy
	private String updatedBy;
		
}
