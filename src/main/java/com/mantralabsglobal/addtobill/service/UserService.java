package com.mantralabsglobal.addtobill.service;

import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.UserRepository;

@Service
public class UserService  extends BaseService {

	private UserRepository userRepository;
	
	public boolean signUp(User user){
		return true;
	}
	
	public boolean login(User user){
		return true;
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
}
