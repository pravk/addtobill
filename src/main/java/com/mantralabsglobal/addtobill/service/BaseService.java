package com.mantralabsglobal.addtobill.service;

import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

public abstract class BaseService {
	
	protected <T> T clone(T object, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(gson.toJson(object), type);
	}

	protected <T> Lock acquireLock(T object, int time, TimeUnit timeunit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void releaseLock(Lock lock){
		
	}
}
