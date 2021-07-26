package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.ReimbController;
import com.revature.controller.UserController;
import com.revature.models.User;

public class FrontControlServlet extends HttpServlet {
	Logger log = LoggerFactory.getLogger(FrontControlServlet.class);
	
	private UserController userController = new UserController();
	private ReimbController reimbController = new ReimbController();
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		System.out.println("Request URI: " + request.getRequestURI() + ". Method: " + request.getMethod());
		
		response.setContentType("application/json");
		response.setStatus(404);
		
		final String URLLogin = request.getRequestURI().replace("/project1/", "");
		final String URLUser = request.getRequestURI().replace("/project1/user/", "");
		
		
		
		String[] urlLoginSection = URLLogin.split("/");
		String[] urlUserSection = URLUser.split("/");
//		System.out.println(request.getRequestURI());
//		System.out.println(request.getMethod());
		
		
		//Login - Register
		switch (urlLoginSection[0]) {
			case "userSignup":
				if (request.getMethod().equals("POST")) {
					userController.addUser(request, response);
					System.out.println("User Servlet Registration is successful");
					log.info("User registration is successful");
				} else {
					System.out.println("Got userSignup but it's unknown request.");
				}
				break;
			case "userLogin":
				if (request.getMethod().equals("POST")) {
					if (userController.validateUser(request, response)) {
//						response.setStatus(201);
						System.out.println("Check Final set status: " + response.getStatus());
						System.out.println("User Servlet Login is successful");
						log.info("User Login is successful");
										
					} else {
						System.out.println("User Login is NOT successful");
					}
					
				} else {
					System.out.println("Got userLogin but it's unknown request.");
				}
				break;
			default:
				System.out.println("URL request for /project1/ is barebone.");
				break;
		}
		
		System.out.println("Final check Request URI: " + request.getRequestURI() + ". Method: " + request.getMethod());
		
		for (int i = 0; i < urlLoginSection.length; i++) {
			System.out.println("Index: " + i + ". String: " + urlLoginSection[i]);
		}
		//User requests handling
		System.out.println("Is my session null? " + (session==null));
		
		//Stopped here
		
		if (urlLoginSection.length > 1 && urlLoginSection[0].equals("user")) {
			if (session==null) {
				System.out.println("session is null");
				response.setStatus(404);
				log.warn("User attempted to login without session");
				
			} else if (session != null) {
				response.setStatus(201);
				
				switch (urlUserSection[0]) {
				case "check":
					String userName = session.getAttribute("userName").toString();
					String userID = session.getAttribute("userID").toString();
					String userEmail = session.getAttribute("userEmail").toString();
					String userPassword = session.getAttribute("password").toString();
					
					User foundUser = userController.findUser(userEmail);
					String json = objectMapper.writeValueAsString(foundUser);
					response.getWriter().print(json);
					
					System.out.println("I see user name logged in is: " + userName + "and ID number is: " + userID);
					break;
					
				case "employee":
					if (urlUserSection.length == 1) {
						
						if (request.getMethod().equals("POST")) {
							reimbController.addTicket(request, response);
							System.out.println("Servlet Employee added ticket successfully");
							log.info("Servlet Employee added ticket successfully");
						} else {
							System.out.println("Servlet Employee failed to recognize...");
							log.info("Servlet Employee failed to recognize....");
						}
						
					} else if (urlUserSection.length == 2) {
						//URI: project1/user/employee/1
						if (request.getMethod().equals("GET")) {
							reimbController.findTicketsFromEmployee(response, urlUserSection[1].toLowerCase());
							System.out.println("Servlet Employee founds tickets successfully");
							log.info("Servlet Employee founds tickets successfully");
						}
					}
					//add ticket
					
					break;
				case "manager":
					
					break;
					
				case "userLogout":
					session.invalidate();
					
					break;
				default:
					System.out.println("Invalid url.");
					break;
				}
			}
			
		}

	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request,response);
	}
}
