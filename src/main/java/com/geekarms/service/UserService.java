package com.geekarms.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geekarms.dao.UserDao;
import com.geekarms.entity.User;

@Service
@Transactional
public class UserService {
	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	public List<User> getAllUser(){
		return userDao.getAllUser();
	}
}
