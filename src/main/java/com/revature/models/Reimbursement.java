package com.revature.models;

public class Reimbursement {
	private int reimb_id;
	private double reimb_amount;
	private String reimb_submitted;
	private String reimb_resolved;
	private String reimb_description;
	//Missing receipt
	
	private int reimb_status_id;
	private String reimbStatus;
	
	private int reimb_author;
	private String reimbAuthor;
	private int reimb_resolver;
	private String reimbResolver;
	
	private int reimb_type_id;
	private String reimbType;
	
	
	public Reimbursement() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getReimb_id() {
		return reimb_id;
	}


	public void setReimb_id(int reimb_id) {
		this.reimb_id = reimb_id;
	}


	public double getReimb_amount() {
		return reimb_amount;
	}


	public void setReimb_amount(double reimb_amount) {
		this.reimb_amount = reimb_amount;
	}


	public String getReimb_submitted() {
		return reimb_submitted;
	}


	public void setReimb_submitted(String reimb_submitted) {
		this.reimb_submitted = reimb_submitted;
	}


	public String getReimb_resolved() {
		return reimb_resolved;
	}


	public void setReimb_resolved(String reimb_resolved) {
		this.reimb_resolved = reimb_resolved;
	}


	public String getReimb_description() {
		return reimb_description;
	}


	public void setReimb_description(String reimb_description) {
		this.reimb_description = reimb_description;
	}


	public int getReimb_status_id() {
		return reimb_status_id;
	}


	public void setReimb_status_id(int reimb_status_id) {
		this.reimb_status_id = reimb_status_id;
	}


	public String getReimbStatus() {
		return reimbStatus;
	}


	public void setReimbStatus(String reimbStatus) {
		this.reimbStatus = reimbStatus;
	}


	public int getReimb_author() {
		return reimb_author;
	}


	public void setReimb_author(int reimb_author) {
		this.reimb_author = reimb_author;
	}


	public String getReimbAuthor() {
		return reimbAuthor;
	}


	public void setReimbAuthor(String reimbAuthor) {
		this.reimbAuthor = reimbAuthor;
	}


	public int getReimb_resolver() {
		return reimb_resolver;
	}


	public void setReimb_resolver(int reimb_resolver) {
		this.reimb_resolver = reimb_resolver;
	}


	public String getReimbResolver() {
		return reimbResolver;
	}


	public void setReimbResolver(String reimbResolver) {
		this.reimbResolver = reimbResolver;
	}


	public int getReimb_type_id() {
		return reimb_type_id;
	}


	public void setReimb_type_id(int reimb_type_id) {
		this.reimb_type_id = reimb_type_id;
	}


	public String getReimbType() {
		return reimbType;
	}


	public void setReimbType(String reimbType) {
		this.reimbType = reimbType;
	}
	
	
	
}
