package com.mantralabsglobal.addtobill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.mantralabsglobal.addtobill.exception.UserExistsException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.model.UserAccountRole;
import com.mantralabsglobal.addtobill.repository.UserAccountRepository;
import com.mantralabsglobal.addtobill.repository.UserRepository;

@Service
public class UserService  extends BaseService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserAccountRepository userAccountRepository;
	@Autowired
	private AccountService accountService;
	
	
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
		{
			User savedUser = userRepository.save(user);
			Account account = accountService.createAccount(savedUser);
			UserAccount userAccount = new UserAccount();
			userAccount.setAccountId(account.getAccountId());
			userAccount.setUserId(savedUser.getUserId());
			userAccount.setRole(UserAccountRole.OWNER);
			userAccountRepository.save(userAccount);
			return savedUser;
		}
		else
			throw new UserExistsException();
	
	}

	public User getUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}

	public List<UserAccount> getUserAccounts(String userId) {
		return userAccountRepository.findAllByUserId(userId);
	}
	
}
