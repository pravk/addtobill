package com.mantralabsglobal.addtobill.charge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.model.Charge;
import com.mantralabsglobal.addtobill.repository.AccountRepository;
import com.mantralabsglobal.addtobill.repository.ChargeRepository;
import com.mantralabsglobal.addtobill.repository.MerchantAccountRepository;
import com.mantralabsglobal.addtobill.repository.TransactionRepository;
import com.mantralabsglobal.addtobill.requestModel.ChargeRequest;

@Component
public abstract class ChargeProcessor<T extends ChargeRequest> {

	@Autowired
	protected AccountRepository userAccountRepository;
	
	@Autowired
	protected MerchantAccountRepository merchantAccountRepository;
	@Autowired
	protected TransactionRepository transactionRepository;
	
	@Autowired
	protected ChargeRepository chargeRepository;
	
	public abstract Charge processRequest(T request) throws Exception;
		
}
