package com.geekarms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.geekarms.entity.User;
import com.geekarms.service.UserService;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public List<User> getAllUser() {
		return userService.getAllUser();
	}
	
}
