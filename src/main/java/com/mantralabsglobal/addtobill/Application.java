package com.mantralabsglobal.addtobill;

import java.util.Arrays;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.eventbus.AsyncEventBus;
import com.mantralabsglobal.addtobill.model.Transaction;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.UserRepository;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application {

	private AsyncEventBus transactionEventBus;
	
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
    }

	public void registerForTransactionBus(Object subscriber) {
		transactionEventBus.register(subscriber);
	}

	public void postTransaction(Transaction next) {
		transactionEventBus.post(next);
	}

	
}
