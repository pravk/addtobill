package com.mantralabsglobal.addtobill.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mantralabsglobal.addtobill.Application;
import com.mantralabsglobal.addtobill.model.BillingPeriod;
import com.mantralabsglobal.addtobill.repository.BillingPeriodRepository;

@Component
public class BillingScheduler {

	@Autowired
	BillingPeriodRepository billingPeriodRepository;
	
	@Autowired
	Application application;
	
	@Scheduled(fixedDelay=1000*60)
	public void generateBills(){
		
		Date startOfDay = DateUtils.truncate(new Date(), Calendar.DATE);
		Date endOfDay = DateUtils.addMilliseconds(DateUtils.ceiling(new Date(), Calendar.DATE),1);
		
		Iterator<BillingPeriod> billingPeriodIterator = billingPeriodRepository.findAllByEndDateBetween(startOfDay, endOfDay).iterator() ;
		while(billingPeriodIterator.hasNext()){
			BillingPeriod bp = billingPeriodIterator.next();
			if(bp.isOpen()){
				bp.setStatus("L");
				billingPeriodRepository.save(bp);
				application.postBillingPeriodStatusChange(bp);
			}
		}
	}
}
