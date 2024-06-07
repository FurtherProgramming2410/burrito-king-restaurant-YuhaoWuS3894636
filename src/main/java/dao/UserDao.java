package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Order;
import model.User;
import model.UserandOrder;

public interface UserDao {
	void setup() throws SQLException;
	boolean updateOrderStatus(int orderId, String status) throws SQLException;
	User getUser(String username, String password) throws SQLException;
	User createUser(String username, String password, String firstname, String lastname) throws SQLException;
	User updateOrder(String username, Double burritonumber, Double friesnumber, Double sodanumber, Double mealnumber) throws SQLException;
	User updateUser(String username, String password, String firstname, String lastname) throws SQLException;
	User updatePriceandTime(String username, Double waitingTime, Double totalprice) throws SQLException;
	Order getOrderById(int orderId) throws SQLException;

    User updatenonevipOrder(String username, Double burritonumber, Double friesnumber, Double sodanumber) throws SQLException;

    User updateUserEmail(String username, String email) throws SQLException;
	User updateUserStatus(String username, String status) throws SQLException;



	Order createOrder(String username, Double burritonumber, Double friesnumber, Double sodanumber, Double mealnumber, Double totalprice, Double waitingTime) throws SQLException;


	ArrayList<Order> getOrdersByUsername(String username) throws SQLException;


	ArrayList<User> getUsersByUsername(String username) throws SQLException;





	User getUserByUsername(String username, Connection connection) throws SQLException;

	UserandOrder getorderUserByUsername(String username) throws SQLException;

	User getUserOrderDetails(String username, Connection connection) throws SQLException;
}

