package com.mantralabsglobal.addtobill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.model.UserAccount;
import com.mantralabsglobal.addtobill.model.MerchantAccount;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.TransactionResult;
import com.mantralabsglobal.addtobill.model.TransactionSuccessResult;
import com.mantralabsglobal.addtobill.repository.TransactionRepository;
import com.mantralabsglobal.addtobill.validators.TransactionValidator;

@Service
public class TransactionService extends BaseService{
	
	@Autowired
	protected TransactionRepository transactionRepository;
	@Autowired
	private TransactionValidator validator;
	
	
	public TransactionResult newTransaction(Transaction transaction) throws InsufficientBalanceException, InvalidRequestException{
		
		validator.validateNewTransaction(transaction);
		
		UserAccount userAccount = accountRepository.findOne(transaction.getUserAccountId());
		MerchantAccount merchantAccount = merchantAccountRepository.findOneByMerchantId(transaction.getMerchantId());
		
							
		if(userAccount != null && merchantAccount != null){
			
			Transaction userTransaction = createUserTransaction(transaction, userAccount);
			Transaction merchantTransaction = createMerchantTransaction(transaction, merchantAccount);
			
			if(userAccount.hasSufficientBalance(userTransaction)){
				userAccount.setRemainingCreditBalance(userAccount.getRemainingCreditBalance() + userTransaction.getSignedAmount()) ;
				
				merchantAccount.setBalance(merchantAccount.getBalance() - userTransaction.getSignedAmount());
				
				accountRepository.save(userAccount);
				merchantAccountRepository.save(merchantAccount);
				transactionRepository.save(merchantTransaction);
				return new TransactionSuccessResult(userTransaction, merchantTransaction);
			}
			else
			{
				throw new InsufficientBalanceException();
			}
			
		}
		else 
		{
			throw new InvalidRequestException();
		}
	}

	private Transaction createMerchantTransaction(Transaction transaction, MerchantAccount merchantAccount) {
		Transaction t = clone(transaction, Transaction.class);
		t.setDebitCreditIndicator(t.isDebitTransaction()?Transaction.CREDIT:Transaction.DEBIT);
		t.setTransactionAccountId(merchantAccount.getMerchantAccountId());
		return t;
	}

	private Transaction createUserTransaction(Transaction transaction, UserAccount userAccount) {
		transaction = clone(transaction, Transaction.class);
		transaction.setTransactionAccountId(userAccount.getAccountId());
		return transaction;
	}

	public TransactionResult updateTransaction(Transaction transaction) throws InsufficientBalanceException, InvalidRequestException {
		Transaction original = transactionRepository.findOne(transaction.getTransactionId());
		UserAccount userAccount = accountRepository.findOne(original.getUserAccountId());
		original.setAmount(transaction.getAmount()-original.getAmount());
		if(userAccount.hasSufficientBalance(original)){
		
			cancelTransaction(transaction.getTransactionId(), transaction.getMerchantId());
			Transaction clone = clone(transaction, Transaction.class);
			clone.setTransactionId(null);
			return newTransaction(clone);
		}else
		{
			throw new InsufficientBalanceException();
		}
	}

	public TransactionResult cancelTransaction(String transactionId, String merchantId) throws InsufficientBalanceException {
		Transaction userTransaction = transactionRepository.findOne(transactionId);
		UserAccount userAccount = accountRepository.findOne(userTransaction.getUserAccountId());
		MerchantAccount merchantAccount = merchantAccountRepository.findOneByMerchantId(userTransaction.getMerchantId());
		Transaction merchantTransaction = transactionRepository.findOneByAccountIdAndReferenceId(merchantAccount.getMerchantAccountId(), userTransaction.getMerchantReferenceId());
		
		Transaction reverseUT = clone(userTransaction, Transaction.class);
		if(userAccount.hasSufficientBalance(reverseUT)){
			reverseUT.setDebitCreditIndicator(reverseUT.isDebitTransaction()?Transaction.CREDIT:Transaction.DEBIT);
			userAccount.setRemainingCreditBalance(userAccount.getRemainingCreditBalance() + reverseUT.getSignedAmount()) ;
	
			Transaction reverseMT = clone(merchantTransaction, Transaction.class);
			reverseMT.setDebitCreditIndicator(reverseUT.isDebitTransaction()?Transaction.CREDIT:Transaction.DEBIT);
			merchantAccount.setBalance(merchantAccount.getBalance() + reverseMT.getSignedAmount()) ;
	
			transactionRepository.save(reverseUT);
			transactionRepository.save(reverseMT);
			merchantAccountRepository.save(merchantAccount);
			accountRepository.save(userAccount);
			
			return new TransactionSuccessResult(reverseUT, reverseMT);
		}
		else
		{
			throw new InsufficientBalanceException();
		}
		
		
	}
}
