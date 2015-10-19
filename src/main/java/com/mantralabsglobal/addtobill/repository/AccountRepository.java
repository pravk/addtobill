package com.mantralabsglobal.addtobill.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.Account;

public interface AccountRepository extends CrudRepository<Account, String> {

	 	Account findOneByOwnerIdAndCurrency(String ownerId, String currency);

		List<Account> findAllByOwnerId(String userId);

		Iterable<Account> findAllByAccountStatus(String accountStatus);


}
