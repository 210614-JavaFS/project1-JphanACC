package com.revature.services;

import com.revature.models.User;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;

public class UserService {
	
	private static UserDAO userDao = new UserDAOImpl();
	
	public boolean addUser(User user) {
		return userDao.addUser(user);
	}
	
	public User findUser(String email) {
		return userDao.findUser(email);
	}
}
