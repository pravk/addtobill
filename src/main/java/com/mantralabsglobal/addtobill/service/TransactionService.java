package com.mantralabsglobal.addtobill.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.exception.InsufficientBalanceException;
import com.mantralabsglobal.addtobill.exception.InvalidRequestException;
import com.mantralabsglobal.addtobill.model.Account;
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
	
	
	public TransactionResult applyTransaction(Transaction transaction) throws InsufficientBalanceException, InvalidRequestException{
		
		validator.validateNewTransaction(transaction);
		
		Account userAccount = accountRepository.findOne(transaction.getUserAccountId());
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
		return null;
	}

	private Transaction createUserTransaction(Transaction transaction, Account userAccount) {
		// TODO Auto-generated method stub
		//t.setTransactionId(ObjectId.get().toString());
		return null;
	}
}
