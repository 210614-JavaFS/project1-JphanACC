package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.UserService;


public class UserController {

	private static UserService userService = new UserService();
	private ObjectMapper objectMapper = new ObjectMapper();
	Logger log = LoggerFactory.getLogger(UserController.class);
	
	public void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder stringBuilder = new StringBuilder();
		String line = reader.readLine();
		
		while (line != null) {
			stringBuilder.append(line);
			line = reader.readLine();
		}
		
		String body = new String(stringBuilder);
		
		User user = objectMapper.readValue(body, User.class);
		
		if (userService.addUser(user)) {
			response.setStatus(201);
			log.info("User registeration is completed");
		} else {
			response.setStatus(406);
			log.error("User registeration is failed");
		}
	}

	public boolean validateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		BufferedReader reader = request.getReader();
		StringBuilder stringBuilder = new StringBuilder();
		String line = reader.readLine();

		while (line != null) {
			stringBuilder.append(line);
			line = reader.readLine();
		}
		String body = new String(stringBuilder);
		User userObjectInput = objectMapper.readValue(body, User.class);
		
		String inputEmail = userObjectInput.getUser_email();
		String inputPassword = userObjectInput.getErs_password();
		
		User retrievedUser = userService.findUser(inputEmail);
		String retrievedHash = retrievedUser.getErs_password();
		
		boolean password_verified = false;
		password_verified = BCrypt.checkpw(inputPassword, retrievedHash);
		
		if (retrievedUser.getUser_email() == null) {
			System.out.println("Error: Account not found...");
			response.setStatus(404);
			return false;
		} else if (retrievedUser.getUser_email().equals(inputEmail) && password_verified) {
			System.out.println("Login is successful!");
			response.setStatus(201);
			
			//create new session
			HttpSession session = request.getSession();
			session.setAttribute("userID", retrievedUser.getErs_user_id());
			session.setAttribute("userEmail", retrievedUser.getUser_email());
			session.setAttribute("userName", retrievedUser.getErs_username());
			session.setAttribute("password", retrievedUser.getErs_password());
			session.setAttribute("userRoleID", retrievedUser.getUser_role_id());
			session.setAttribute("userRole", retrievedUser.getUser_role());
			
			return true;
		} else {
			System.out.println("Bad request...");
			log.error("Login failed. Bad request?");
			response.setStatus(501);
			
			return false;
		}
	}
	
	public User findUser(String email) {
		User retrievedUser = userService.findUser(email);
		
		if (retrievedUser==null) {
			System.out.println("Can't find user");
			log.error("can't find the user");
		}
		return retrievedUser;
	}
	
}
