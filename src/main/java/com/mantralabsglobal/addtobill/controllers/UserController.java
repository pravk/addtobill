package com.mantralabsglobal.addtobill.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.exception.ResourceNotFoundException;
import com.mantralabsglobal.addtobill.exception.UserExistsException;
import com.mantralabsglobal.addtobill.model.User;
import com.mantralabsglobal.addtobill.service.UserService;

@RestController(value="/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/user/id/{id}", method=RequestMethod.GET)
	public User getUser(@PathVariable(value="id") String userId) throws ResourceNotFoundException{
		User user = userService.getUser(userId);
		if(user != null)
			return user;
		throw new ResourceNotFoundException(); 
	}
	
	@RequestMapping(value="/user/email/{email:.+}", method=RequestMethod.GET)
	public User getUserByEmail(@PathVariable(value="email") String email) throws ResourceNotFoundException{
		User user = userService.getUserByEmail(email);
		if(user != null)
			return user;
		throw new ResourceNotFoundException(); 
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public User createUser(@RequestBody User user) throws UserExistsException{
		return userService.createUser(user);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
