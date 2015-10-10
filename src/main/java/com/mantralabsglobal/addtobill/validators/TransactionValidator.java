package com.mantralabsglobal.addtobill.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;

@Component
public class TransactionValidator {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private MerchantRepository merchantRepository;
	
	public void validateNewTransaction(Transaction t){
		Assert.hasText(t.getAccountId(), "Invalid account Id");
		Assert.hasText(t.getCurrency(), "Invalid currency");
		Assert.hasText(t.getDebitCreditIndicator(), "Invalid credit/debit indicator");
		Assert.hasText(t.getMerchantId(), "Invalid merchant Id");
		Assert.hasText(t.getMerchantReferenceId(), "Invalid reference Id");
		Assert.isTrue(t.getAmount()>0, "Invalid transaction amount");

		Assert.notNull(merchantRepository.findOne(t.getMerchantId()));
		
		Assert.isNull(accountRepository.findOneByReferenceId(t.getMerchantReferenceId()));
		
	}

	public AccountRepository getAccountRepository() {
		return accountRepository;
	}

	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}
