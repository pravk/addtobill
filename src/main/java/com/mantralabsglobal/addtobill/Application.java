package com.mantralabsglobal.addtobill;

import java.util.Arrays;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.eventbus.AsyncEventBus;
import com.mantralabsglobal.addtobill.model.BillingPeriod;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.UserRepository;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:application.properties")
public class Application {

	private AsyncEventBus transactionEventBus;
	private AsyncEventBus billingEventBus;
	
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        
        UserRepository respository = context.getBean(UserRepository.class);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        User user = respository.findOneByEmail("admin");
        if(user == null)
        {
        	//Create admin user
        	user = new User();
        	user.setEmail("admin");
        	user.setPassword(passwordEncoder.encode("admin"));
        	user.setRoles( Arrays.asList("ROLE_ADMIN"));
        	respository.save(user);
        }
    }
    
    @PostConstruct
    public void init(){
    	transactionEventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());
    	billingEventBus = new AsyncEventBus(Executors.newSingleThreadExecutor());
    }

	public void registerForTransactionBus(Object subscriber) {
		transactionEventBus.register(subscriber);
	}
	
	public void registerForBillingBus(Object subscriber) {
		billingEventBus.register(subscriber);
	}

	public void postTransaction(Transaction next) {
		transactionEventBus.post(next);
	}

	public void postBillingPeriodStatusChange(BillingPeriod bp) {
		billingEventBus.post(bp);
	}

	public AsyncEventBus getBillingEventBus() {
		return billingEventBus;
	}

	public void setBillingEventBus(AsyncEventBus billingEventBus) {
		this.billingEventBus = billingEventBus;
	}

	
}
