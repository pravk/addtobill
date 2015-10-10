package com.mantralabsglobal.addtobill.service;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.model.UserAccount;

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

	public List<UserAccount> getUserAccounts(Principal principal) {
	 	String userId = getUserIdByEmail(principal.getName());
		return userAccountRepository.findAllByUserId(userId);
	}

	public Transaction getUserTransaction(Principal principal, String transactionId) {
		String userId = getUserIdByEmail(principal.getName());
		return getUserTransaction(userId, transactionId);
	}

	
	
}
