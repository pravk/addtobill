package com.mantralabsglobal.addtobill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.mantralabsglobal.addtobill.exception.UserExistsException;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.UserRepository;

@Service
public class UserService  extends BaseService {

	@Autowired
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

	public User getUser(String userId) {
		return userRepository.findOne(userId);
	}

	public User createUser(User user) throws UserExistsException {
		Assert.notNull(user);
		Assert.hasText(user.getEmail());
		
		if(userRepository.findOneByEmail(user.getEmail())== null)
			return userRepository.save(user);
		else
			throw new UserExistsException();
	
	}

	public User getUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}
	
}
