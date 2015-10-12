package com.mantralabsglobal.addtobill.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mantralabsglobal.addtobill.model.MerchantAccount;
import com.mantralabsglobal.addtobill.model.Transaction;

public interface MerchantAccountRepository extends CrudRepository<MerchantAccount, String> {

	 	@Query(value = "{ 'transactionList.transactionId' : ?0 }", fields = "{ 'transaction' : 1 }")
	    Transaction findOneByTransactionId(String transactionId);

		@Query(value = "{ 'transactionList.merchantReferenceId' : ?0}", fields = "{ 'transaction' : 1 }")
	    Transaction findOneByReferenceId(String merchantReferenceId);

		@Query(value = "{ 'transactionList.transactionId' : ?1, 'accountId' : ?0 }", fields = "{ 'transaction' : 1 }")
	    Transaction findOneByAccountIdAndTransactionId(String accountId, String transactionId);

		@Query(value = "{ 'transactionList.transactionId' : ?1, 'transactionList.merchantId' : ?1 }", fields = "{ 'transaction' : 1 }")
		Transaction findOneByTransactionIdAndMerchantId(String transactionId, String merchantId);

		MerchantAccount findOneByMerchantId(String merchantId);

		MerchantAccount findOneByMerchantIdAndCurrency(String merchantId, String currency);


}
