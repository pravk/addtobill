package com.mantralabsglobal.addtobill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.model.Payment;
import com.mantralabsglobal.addtobill.requestModel.BillPaymentRequest;
import com.mantralabsglobal.addtobill.service.PaymentService;

@RestController
public class ServiceController {

	@Autowired
	private PaymentService paymentService;
	
	@RequestMapping(value="/user/payment", method=RequestMethod.POST)
	public Payment newBillPayment(@RequestBody BillPaymentRequest request) throws Exception{
		return paymentService.newBillPayment(request);
	}
	
	@RequestMapping(value="/merchant/payment", method=RequestMethod.POST)
	public Payment newMerchantPayment(@RequestBody BillPaymentRequest request) throws Exception{
		return paymentService.newBillPayment(request);
	}
	
}
