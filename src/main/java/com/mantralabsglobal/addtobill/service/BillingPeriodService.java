package com.mantralabsglobal.addtobill.service;

import org.springframework.stereotype.Service;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.BillingPeriod;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.repository.BillingPeriodRepository;

@Service
public class BillingPeriodService extends BaseService {

	private BillingPeriodRepository billingPeriodRepository;
	private AccountService billingService;
	private BillingPeriodRepository periodRepository;

	public void rollbackTransaction(Transaction t) {
		BillingPeriod period = getCurrentBillingPeriod(t.getAccountId());
		period.setRunningBalance(period.getRunningBalance() - t.getSignedAmount());
		periodRepository.save(period);
	}

	public BillingPeriod getCurrentBillingPeriod(String accountId) {
		return periodRepository.findByAccountIdAndStatus(accountId, BillingPeriod.BILLING_PERIOD_STATUS_OPEN);
	}

	protected boolean canApplyTransaction(Transaction t, Account account, BillingPeriod period) {
		return period.getRunningBalance() + t.getSignedAmount() <= account.getCreditLimit();
	}
}