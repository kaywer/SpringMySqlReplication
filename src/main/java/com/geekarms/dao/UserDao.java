package com.geekarms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geekarms.entity.User;



@Repository
public class UserDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<User> getAllUser(){
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("From User", User.class).getResultList();
	}

}
