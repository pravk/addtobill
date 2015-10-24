package com.mantralabsglobal.addtobill.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mantralabsglobal.addtobill.model.Account;
import com.mantralabsglobal.addtobill.model.Payment;
import com.mantralabsglobal.addtobill.payment.PaymentProcessor;
import com.mantralabsglobal.addtobill.repository.BillPaymentRepository;
import com.mantralabsglobal.addtobill.repository.BillingPeriodRepository;
import com.mantralabsglobal.addtobill.repository.PaymentRequestRepository;
import com.mantralabsglobal.addtobill.requestModel.BillPaymentRequest;
import com.mantralabsglobal.addtobill.requestModel.MerchantPaymentRequest;


@Service
public class PaymentService extends BaseService{
	
	private static final Logger logger =LoggerFactory.getLogger(PaymentService.class);
	
	@Autowired
	protected BillingPeriodRepository billingPeriodRepository;
	
	@Autowired
	protected BillPaymentRepository billPaymentRepository;
	
	@Autowired
	protected PaymentRequestRepository paymentRequestRepository;
	
	@Autowired
	protected PaymentProcessor<BillPaymentRequest> billPaymentProcessor;
	
	@Autowired
	protected PaymentProcessor<MerchantPaymentRequest> merchantPaymentProcessor;
	

	public Payment newBillPayment(BillPaymentRequest request) throws Exception {
		Assert.notNull(request);
		Assert.hasText(request.getAccountId(), "Invalid account Id");
		Assert.isTrue(request.getPaymentAmount()>=0);
		
		//BillingPeriod period = billingPeriodRepository.findOne(request.getBillingPeriodId());
		
		//Assert.notNull(period, "Invalid billing period");
		
		request = paymentRequestRepository.save(request);
		logger.debug("Invoking payment processor");
		
		return billPaymentProcessor.processRequest(request);
		
	}

	public Payment newMerchantPayment(MerchantPaymentRequest request) throws Exception {
		Assert.notNull(request);
		Assert.hasText(request.getAccountId(), "Invalid account Id");
		Assert.notNull(request.getCutOffDate(), "Cutoff date not provided");
		
		Account account = accountRepository.findOne(request.getAccountId());
		
		Assert.notNull(account, "Invalid account Id");
		
		request = paymentRequestRepository.save(request);
		
		logger.debug("Invoking payment processor");
		
		return merchantPaymentProcessor.processRequest(request);
		
	}
}
