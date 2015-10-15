package com.mantralabsglobal.addtobill.charge;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.AccountBalance;
import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.ChargeRepository;
import com.mantralabsglobal.addtobill.repository.TransactionRepository;
import com.mantralabsglobal.addtobill.requestModel.ChargeRequest;

@Component
public abstract class ChargeProcessor<T extends ChargeRequest> {

	@Autowired
	protected AccountRepository accountRepository;
	
	@Autowired
	protected TransactionRepository transactionRepository;
	
	@Autowired
	protected ChargeRepository chargeRepository;
	
	public  Charge processRequest(T request) throws Exception{
		
			Charge charge = createCharge(request);
			
			charge.setStatus(Charge.CHARGE_STATUS_RECORDED);
			
			charge = chargeRepository.save(charge);
			
			Map<Account, Transaction> transactionMap = createTransactions(charge);
			
			updateAccountBalance(transactionMap);
			
			transactionRepository.save(transactionMap.values());
			
			accountRepository.save(transactionMap.keySet());
			
			charge.setStatus(Charge.CHARGE_STATUS_SUCCESS);
			
			charge = chargeRepository.save(charge);
			
			return charge;
	}
		

	
	protected void updateAccountBalance(Map<Account, Transaction> transactionMap) throws InsufficientBalanceException {
		Iterator<Account> iterator = transactionMap.keySet().iterator();
		while(iterator.hasNext()){
			Account account = iterator.next();
			Transaction transaction = transactionMap.get(account);
			AccountBalance acctBalance = account.getAccountBalance();
			account.applyTransaction(transaction);
			acctBalance.setBalanceUpdateDate(new Date().getTime());
		}
	}



	protected abstract Map<Account, Transaction> createTransactions(Charge charge) ;

	protected abstract Charge createCharge(T request);

	protected Transaction getDebitTransaction(Charge charge, Account debitAccount) {
		Transaction userTransaction = new Transaction();
		userTransaction.setAmount(charge.getAmount());
		userTransaction.setCurrency(charge.getCurrency());
		userTransaction.setChargeId(charge.getChargeId());
		userTransaction.setDebitCreditIndicator(Transaction.DEBIT);
		userTransaction.setTransactionAccountId(debitAccount.getAccountId());
		return userTransaction;
	}

	
}
