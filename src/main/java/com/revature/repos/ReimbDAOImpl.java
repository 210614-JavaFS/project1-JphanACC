package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Reimbursement;
import com.revature.utils.ConnectionUtils;

public class ReimbDAOImpl implements ReimbDAO {
	
	private static ReimbDAO reimbDao = new ReimbDAOImpl();
	private static Logger log = LoggerFactory.getLogger(ReimbDAOImpl.class);
	
	
	@Override
	public boolean addTicket(Reimbursement reimb, int userID) {
		try (Connection conn = ConnectionUtils.getConnection()){
			String sql = "INSERT INTO ERS_REIMBURSEMENT(REIMB_AMOUNT, REIMB_DESCRIPTION , REIMB_AUTHOR , REIMB_STATUS_ID , REIMB_TYPE_ID)"
					+ "VALUES (?,?,?,?,?)";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			
			int index = 0;
			statement.setDouble(++index, reimb.getReimb_amount());
			statement.setString(++index, reimb.getReimb_description());
			statement.setInt(++index, userID);
			statement.setInt(++index, 0);
			statement.setInt(++index, reimb.getReimb_type_id());
			statement.execute();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ReimDAOImpl addTicket Query failed.");
			log.error("ReimbDAOImpl addTicket Query crashed...");
		}
		return false;
	}


	@Override
	public List<Reimbursement> getReimbFromOneEmployee(int userID) {
		try (Connection conn = ConnectionUtils.getConnection()){
			List<Reimbursement> reimbList = new ArrayList<>();
			
			String sql = "SELECT * FROM ERS_REIMBURSEMENT ER WHERE REIMB_AUTHOR = ?";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, userID);
			ResultSet result = statement.executeQuery();

			System.out.println("The userID query is " + userID);
			while (result.next() ) {
				Reimbursement reimb = new Reimbursement();
				
				reimb.setReimb_id(result.getInt("reimb_id"));
				reimb.setReimb_amount(result.getDouble("reimb_amount"));
				reimb.setReimb_submitted(result.getString("reimb_submitted"));
				reimb.setReimb_resolved(result.getString("reimb_resolved"));
				reimb.setReimb_description(result.getString("reimb_description"));
				//No Receipt
				reimb.setReimb_author(result.getInt("reimb_author"));
				reimb.setReimb_resolver(result.getInt("reimb_resolver"));
				reimb.setReimb_type_id(result.getInt("reimb_type_id"));
				
				String findUserSQL = "SELECT * FROM ers_users WHERE ers_user_id = ?";
				PreparedStatement statementUser = conn.prepareStatement(findUserSQL);
				statementUser.setInt(1, userID);
				ResultSet resultUser = statementUser.executeQuery();
				
				while (resultUser.next()) {
					reimb.setReimbAuthor(resultUser.getString("ers_username"));
				}
				
				String findReimTypeSQL = "SELECT * FROM ers_reimbursement_type WHERE reimb_type_id = ?";
				PreparedStatement statemenReimType = conn.prepareStatement(findReimTypeSQL);
				statemenReimType.setInt(1, reimb.getReimb_type_id());
				ResultSet resultReimType = statemenReimType.executeQuery();
				while (resultReimType.next()) {
					reimb.setReimbType(resultReimType.getString("reimb_type"));
				}
				
				String findReimStatusSQL = "SELECT reimb_status FROM ers_reimbursement_status WHERE reimb_status_id = ?";
				PreparedStatement statemenReimStatus = conn.prepareStatement(findReimStatusSQL);
				statemenReimStatus.setInt(1, reimb.getReimb_status_id());
				ResultSet resulReimStatus = statemenReimStatus.executeQuery();
				while (resulReimStatus.next()) {
					reimb.setReimbStatus(resulReimStatus.getString("reimb_status"));
				}
				
				reimbList.add(reimb);
			}
			
			
			return reimbList;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ReimbDAOImpl getReimbFromOneEmployee Query failed.");
			log.error("ReimbDAOImpl getReimbFromOneEmployee crashed...");
		}
		return null;
	}


	@Override
	public List<Reimbursement> getAllReimb() {
		try (Connection conn = ConnectionUtils.getConnection()){
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ReimbDAOImpl getReimbFromOneEmployee Query failed.");
			log.error("ReimbDAOImpl getReimbFromOneEmployee crashed...");
		}
		return null;
	}


	@Override
	public boolean editReimbStatus(int reimID) {
		try (Connection conn = ConnectionUtils.getConnection()){
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ReimbDAOImpl editReimbStatus Query failed.");
			log.error("ReimbDAOImpl editReimbStatus crashed...");
		}
		return false;
	}
}
