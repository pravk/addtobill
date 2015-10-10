package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Transaction;

public interface AccountRepository extends CrudRepository<Account, String> {

	 	@Query(value = "{ 'transactionList.transactionId' : ?0 }", fields = "{ 'transaction' : 1 }")
	    Transaction findOneByTransactionId(String transactionId);

		@Query(value = "{ 'transactionList.merchantReferenceId' : ?0}", fields = "{ 'transaction' : 1 }")
	    Transaction findOneByReferenceId(String merchantReferenceId);

		@Query(value = "{ 'transactionList.transactionId' : ?1, 'accountId' : ?0 }", fields = "{ 'transaction' : 1 }")
	    Transaction findOneByAccountIdAndTransactionId(String accountId, String transactionId);

		@Query(value = "{ 'transactionList.transactionId' : ?1, 'transactionList.merchantId' : ?1 }", fields = "{ 'transaction' : 1 }")
		Transaction findOneByTransactionIdAndMerchantId(String transactionId, String merchantId);


}
