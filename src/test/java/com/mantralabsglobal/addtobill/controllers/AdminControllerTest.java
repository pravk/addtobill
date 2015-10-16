package com.mantralabsglobal.addtobill.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

import java.io.IOException;
import java.util.Arrays;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mantralabsglobal.addtobill.Application;
import com.mantralabsglobal.addtobill.model.CreditAccount;
import com.mantralabsglobal.addtobill.model.DebitAccount;
import com.mantralabsglobal.addtobill.model.Merchant;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.repository.MerchantRepository;
import com.mantralabsglobal.addtobill.repository.UserRepository;
import com.mantralabsglobal.addtobill.requestModel.MerchantAccountRequest;
import com.mantralabsglobal.addtobill.requestModel.NewChargeRequest;
import com.mantralabsglobal.addtobill.requestModel.UserAccountRequest;
import com.mantralabsglobal.addtobill.service.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AdminControllerTest {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	 @Value("${local.server.port}")  
	 int port;
	 
	private Merchant merchant;
	private User user;
	
	 private String getBaseUrl() {
		return "http://localhost:" + port;
	 }
	private RestTemplate restTemplate = new TestRestTemplate();
		
	 
	@Before
    public void setUp() {
        
		merchant = new Merchant();
		merchant.setChargesEnabled(false);
		merchant.setDefaultCurrency("inr");
		merchant.setDisplayName("XYZ Merchant");
		merchant.setEmail("support@xyzmerchant.com");
		merchant.setMerchantName("XYZ Merchant");
		merchant.setSecretKey(ObjectId.get().toString());
		merchant.setTransfersEnabled(false);

		merchant = merchantRepository.save(merchant);
        
		user = new User();
		user.setEmail("hello.praveen@gmail.com");
		user.setEmailVerified(true);
		user.setPassword("secret");
		user.setRoles(Arrays.asList(User.ROLE_USER));
		
		user = userRepository.save(user);
		
		User test = new User();
		test.setEmail("test");
		test.setPassword("test");
    	test.setRoles( Arrays.asList("ROLE_ADMIN"));
    	userRepository.save(test);
    }
	
	protected MultiValueMap<String, String> getAuthHeaders(){
		String plainCreds = "test:test";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encode(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		MultiValueMap<String, String> headers= new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return headers;
	}
	
	@Test
	public void createMerchantAccount() throws JsonParseException, JsonMappingException, IOException{
		MerchantAccountRequest merchantAccountRequest = new MerchantAccountRequest();
		merchantAccountRequest.setCurrency("inr");
		merchantAccountRequest.setMerchantId(merchant.getMerchantId());
		HttpEntity<MerchantAccountRequest> entity = new HttpEntity<MerchantAccountRequest>(merchantAccountRequest, getAuthHeaders());
		
		ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl() + "/admin/merchant", entity, String.class);
		assertThat( response.getStatusCode() , equalTo(HttpStatus.OK));
		ObjectMapper mapper = new ObjectMapper();
		CreditAccount acct =mapper.readValue(response.getBody(), CreditAccount.class);
		assertThat(acct.getAccountId(), notNullValue());
	}
	
	@Test
	public void createUserAccount() throws JsonParseException, JsonMappingException, IOException{
		UserAccountRequest userAccountRequest = new UserAccountRequest();
		userAccountRequest.setCurrency("inr");
		userAccountRequest.setUserId(user.getUserId());
		HttpEntity<UserAccountRequest> entity = new HttpEntity<UserAccountRequest>(userAccountRequest, getAuthHeaders());
		
		ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl() + "/admin/user", entity, String.class);
		assertThat( response.getStatusCode() , equalTo(HttpStatus.OK));
		ObjectMapper mapper = new ObjectMapper();
		DebitAccount acct =mapper.readValue(response.getBody(), DebitAccount.class);
		assertThat(acct.getAccountId(), notNullValue());
	}
	
	@Test
	public void createChargeRequest() throws JsonParseException, JsonMappingException, IOException{
		NewChargeRequest chargeRequest = new NewChargeRequest();
		chargeRequest.setAmount(100);
		chargeRequest.setCurrency("inr");
		chargeRequest.setMerchantId(merchant.getMerchantId());
		chargeRequest.setUserId(user.getUserId());
		chargeRequest.setToken(null);
		HttpEntity<NewChargeRequest> entity = new HttpEntity<NewChargeRequest>(chargeRequest, getAuthHeaders());
		
		ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl() + "/admin/user", entity, String.class);
		assertThat( response.getStatusCode() , equalTo(HttpStatus.BAD_REQUEST));
	}
	

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
}
