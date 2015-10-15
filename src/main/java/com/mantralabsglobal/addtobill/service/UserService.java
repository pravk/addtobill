package com.mantralabsglobal.addtobill.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.User;

@Service
public class UserService  extends BaseService {

	
	
	public boolean signUp(User user){
		return true;
	}
	
	public boolean login(User user){
		return true;
	}

	public User getUserDetails(Principal principal) {
		return userRepository.findOneByEmail(principal.getName());
	}

	public List<Account> getUserAccounts(Principal principal) {
	 	String userId = getUserIdByEmail(principal.getName());
		return accountRepository.findAllByOwnerId(userId);
	}

		
}
