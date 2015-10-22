package com.mantralabsglobal.addtobill.schedule;

import java.util.Date;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.Application;
import com.mantralabsglobal.addtobill.model.BillingPeriod;
import com.mantralabsglobal.addtobill.repository.BillingPeriodRepository;

@Component
public class BillingScheduler {

	Logger logger =LoggerFactory.getLogger(BillingScheduler.class);
	@Autowired
	BillingPeriodRepository billingPeriodRepository;
	
	@Autowired
	Application application;
	
	@Scheduled(fixedDelay=1000*60*5)
	public void lockBillingPeriods(){
		
		logger.info("Start locking billing periods");
		
		Iterator<BillingPeriod> billingPeriodIterator = billingPeriodRepository.findAllByPeriodsToBeLocked(new Date()).iterator() ;
		
		while(billingPeriodIterator.hasNext()){
			BillingPeriod bp = billingPeriodIterator.next();
			if(bp.isOpen()){
				bp.setStatus("L");
				bp = billingPeriodRepository.save(bp);
				application.postBillingPeriodStatusChange(bp);
			}
		}
	}
}
