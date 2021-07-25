package com.revature.repos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.utils.ConnectionUtils;

public class UserDAOImpl implements UserDAO {
	
	private static UserDAO userDAO = new UserDAOImpl();
	private static Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
			
	@Override
	public List<User> findAll() {
		try (Connection conn = ConnectionUtils.getConnection()){
			String sql = "SELECT * FROM ers_users INNER JOIN ers_user_roles ON (ers_users.) WHERE ";
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserDAOImpl findAll Query failed.");
			log.error("findAll crashed...");
		}
		return null;
	}

	@Override
	public User findUser(String email, String password) {
		
		try (Connection conn = ConnectionUtils.getConnection()){
			String sql = "SELECT * FROM ers_users WHERE user_email = ? AND ers_password = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, email);
			statement.setString(2, password);
			
			ResultSet result = statement.executeQuery();
			User user = new User();
			
			while (result.next()) {
				user.setErs_user_id(result.getInt("ers_user_id"));
				user.setErs_username(result.getString("ers_username"));
				user.setErs_password(result.getString("ers_password"));
				user.setUser_first_name(result.getString("user_first_name"));
				user.setUser_last_name(result.getString("user_last_name"));
				user.setUser_email(result.getString("user_email"));
				user.setUser_role_id(result.getInt("user_role_id"));
			}
			
			//retrieve role
			String userRoleSQL = "SELECT * FROM ers_user_roles WHERE ERS_USER_ROLE_ID = ?";
			PreparedStatement statementRole = conn.prepareStatement(userRoleSQL);
			
			statementRole.setInt(1, user.getUser_role_id());
			
			ResultSet resultRole = statementRole.executeQuery();
			while (resultRole.next()) {
				user.setUser_role(resultRole.getString("user_role"));
			}
			
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("UserDAOImpl findUser Query failed.");
			log.error("findUser crashed...");
		}
		return null;
	}

	@Override
	public boolean addUser(User user) {
		try(Connection conn = ConnectionUtils.getConnection()){
			String sql = "INSERT INTO ers_users(ERS_USERNAME, ERS_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_EMAIL, USER_ROLE_ID)"
					+ "VALUES (?,?,?,?,?,?)";
			
			PreparedStatement statement = conn.prepareStatement(sql);
		
			int index = 0;
			statement.setString(++index, user.getErs_username());
			statement.setString(++index, user.getErs_password());
			statement.setString(++index, user.getUser_first_name());
			statement.setString(++index, user.getUser_last_name());
			statement.setString(++index, user.getUser_email());
			statement.setInt(++index, user.getUser_role_id());
			statement.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("UserDAOImpl addUser Query failed.");
			log.error("addUser Query crashed...");
		}
		return false;
	}
	
	
}
