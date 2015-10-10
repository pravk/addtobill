package com.mantralabsglobal.addtobill.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, String> {

	List<UserAccount> findAllByUserId(String userId);


}
