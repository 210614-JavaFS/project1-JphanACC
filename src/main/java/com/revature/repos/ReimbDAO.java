package com.revature.repos;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbDAO {

	public boolean addTicket(Reimbursement reimb, int userID);
	
	public List<Reimbursement> getReimbFromOneEmployee(int userID);
	
	public List<Reimbursement> getAllReimb();
	
	public boolean editReimbStatus(int reimID);
}
