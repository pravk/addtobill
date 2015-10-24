package com.mantralabsglobal.addtobill.payment;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.Application;
import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.PaymentFailedException;
import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.AccountBalance;
import com.mantralabsglobal.addtobill.model.Payment;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.PaymentRepository;
import com.mantralabsglobal.addtobill.repository.TransactionRepository;
import com.mantralabsglobal.addtobill.requestModel.PaymentRequest;
import com.mantralabsglobal.addtobill.service.DistributedLockService;

@Component
public abstract class PaymentProcessor<T extends PaymentRequest> {

	@Autowired
	protected AccountRepository accountRepository;
	
	@Autowired
	protected TransactionRepository transactionRepository;
	
	@Autowired
	protected DistributedLockService lockService;
	
	@Autowired
	protected Application application;
	
	
	@Autowired
	protected PaymentRepository paymentRepository;
	
	public  Payment processRequest(T request) throws Exception{
		
			Payment payment = createPayment(request);
			
			payment.setStatus(Payment.PAYMENT_STATUS_RECORDED);
			
			payment = paymentRepository.save(payment);
			
			if(lockService.acquireLock(payment.getAccountId()))
			{		
				try
				{
					Map<Account, Transaction> transactionMap = createTransactions(payment);
					
					updateAccountBalance(transactionMap);
					
					Iterator<Transaction> transIterator = transactionRepository.save(transactionMap.values()).iterator();
					
					accountRepository.save(transactionMap.keySet());
					
					payment.setStatus(Payment.PAYMENT_STATUS_SUCCESS);
					
					payment = paymentRepository.save(payment);
					
					while(transIterator.hasNext()){
						application.postTransaction(transIterator.next());
					}
					
				}
				catch(Exception exp)
				{
					payment.setStatus(Payment.PAYMENT_STATUS_FAILED);
					payment.setFailureMessage(exp.getMessage());
					payment = paymentRepository.save(payment);
					throw exp;
				}
				finally{
					lockService.releaseLock(payment.getAccountId());
				}
			}
			else
			{
				payment.setStatus(Payment.PAYMENT_STATUS_FAILED);
				payment.setFailureMessage("Another transaction in process");
				payment = paymentRepository.save(payment);
				throw new PaymentFailedException(payment.getFailureMessage());
			}
			
			return payment;
	}

	
	protected void updateAccountBalance(Map<Account, Transaction> transactionMap) throws InsufficientBalanceException {
		Iterator<Account> iterator = transactionMap.keySet().iterator();
		while(iterator.hasNext()){
			Account account = iterator.next();
			if(account.isDebitAccount()){
				Transaction transaction = transactionMap.get(account);
				AccountBalance acctBalance = account.getAccountBalance();
				account.applyTransaction(transaction);
				acctBalance.setBalanceUpdateDate(new Date());
			}
		}
	}

	protected abstract Map<Account, Transaction> createTransactions(Payment payment) ;

	protected abstract Payment createPayment(T request);
	
}
