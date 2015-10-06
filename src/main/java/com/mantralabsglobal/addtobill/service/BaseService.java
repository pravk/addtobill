package com.mantralabsglobal.addtobill.service;

import com.google.gson.Gson;

public abstract class BaseService {
	
	protected <T> T clone(T object, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(object), type);
	}

}
