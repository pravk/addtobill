package com.mantralabsglobal.addtobill.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mantralabsglobal.addtobill.model.User;

@RestController(value="user")
public class UserController {

	@RequestMapping(method=RequestMethod.GET)
	public User getUser(@RequestParam(value="id", required=true) String userId){
		return null;
	}
}
