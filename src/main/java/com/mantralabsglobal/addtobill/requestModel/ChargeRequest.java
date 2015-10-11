package com.mantralabsglobal.addtobill.requestModel;

import com.mantralabsglobal.addtobill.model.Charge;

public class ChargeRequest extends Charge{

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
