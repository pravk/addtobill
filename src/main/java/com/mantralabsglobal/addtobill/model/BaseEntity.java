package com.mantralabsglobal.addtobill.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

public class BaseEntity{

	public Date getCreationTS() {
		return creationTS;
	}

	public void setCreationTS(Date creationTS) {
		this.creationTS = creationTS;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdateTS() {
		return updateTS;
	}

	public void setUpdateTS(Date updateTS) {
		this.updateTS = updateTS;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@CreatedDate
	private Date creationTS;
	@CreatedBy
	private String createdBy;
	@LastModifiedDate
	private Date updateTS;
	@LastModifiedBy
	private String updatedBy;
		
}
