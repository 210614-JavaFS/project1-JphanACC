package com.revature.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbService;
import com.revature.services.UserService;

import jdk.internal.org.jline.utils.Log;

public class ReimbController {

	private static ReimbService reimbService = new ReimbService();
	private static UserService userService = new UserService();
	private ObjectMapper objectMapper = new ObjectMapper();
	Logger log = LoggerFactory.getLogger(ReimbController.class);
	
	public void addTicket(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader reader = request.getReader();
		StringBuilder stringBuilder = new StringBuilder();
		String line = reader.readLine();
		
		while (line != null) {
			stringBuilder.append(line);
			line = reader.readLine();
		}
		
		String body = new String(stringBuilder);
		
		Reimbursement reimb = objectMapper.readValue(body, Reimbursement.class);
		
		int employeeID = reimb.getReimb_author();
		
		if (reimbService.addTicket(reimb, employeeID)) {
			response.setStatus(201);
			log.info("reimbController added Ticket successfully");
		} else {
			response.setStatus(406);
			log.info("reimbController failed to add Ticket");
		}
	}

	public void findTicketsFromEmployee(HttpServletResponse response, String userID) throws IOException {
		// TODO Auto-generated method stub
		int convertedID = Integer.parseInt(userID);
		List<Reimbursement> reimbList = reimbService.getReimbFromOneEmployee(convertedID);
		
		String json = objectMapper.writeValueAsString(reimbList);
		response.getWriter().print(json);
		
		response.setStatus(200);
	}



}
