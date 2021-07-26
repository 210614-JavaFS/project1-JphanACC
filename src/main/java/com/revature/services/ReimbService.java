package com.revature.services;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.repos.ReimbDAO;
import com.revature.repos.ReimbDAOImpl;

public class ReimbService {

	private static ReimbDAO reimbDao = new ReimbDAOImpl();
	
	public boolean addTicket(Reimbursement reimb, int userID) {
		return reimbDao.addTicket(reimb, userID);
	};
	
	public List<Reimbursement> getReimbFromOneEmployee(int userID) {
		return reimbDao.getReimbFromOneEmployee(userID);
	}
}
