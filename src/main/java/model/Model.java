package model;

import java.sql.SQLException;

import dao.UserDao;
import dao.UserDaoImpl;

public class Model {
	private UserDao userDao;
	private User currentUser; 
	
	public Model() {
		userDao = new UserDaoImpl();
	}
	
	public void setup() throws SQLException {
		userDao.setup();
	}
	public UserDao getUserDao() {
		return userDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}

	
	public void setCurrentUser(User user) {
		currentUser = user;
	}
	public boolean updateOrderStatus(int orderId, String status) throws SQLException {
		return userDao.updateOrderStatus(orderId, status);
	}

}
