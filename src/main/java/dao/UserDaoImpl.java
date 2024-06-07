package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Order;
import model.User;
import model.UserandOrder;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}

	@Override
	public void setup() throws SQLException {
		System.out.println("Setting up the database...");
		try (Connection connection = Database.getConnection();
			 Statement stmt = connection.createStatement()) {

			String sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
					" (username VARCHAR(10) NOT NULL," +
					" password VARCHAR(8) NOT NULL," +
					" firstname VARCHAR(50)," +
					" lastname VARCHAR(50)," +
					" burritonumber DOUBLE," +
					" friesnumber DOUBLE," +
					" sodanumber DOUBLE," +
					" mealnumber DOUBLE," +
					" waitingTime DOUBLE," +
					" totalprice DOUBLE," +
					" email VARCHAR(100)," +
					" status VARCHAR(10) DEFAULT 'nonvip'," +
					" PRIMARY KEY (username))";
			stmt.executeUpdate(sqlCreateTable);

			if (!columnExists(connection, TABLE_NAME, "email")) {
				stmt.executeUpdate("ALTER TABLE " + TABLE_NAME + " ADD COLUMN email VARCHAR(100)");
			}
			if (!columnExists(connection, TABLE_NAME, "mealnumber")) {
				stmt.executeUpdate("ALTER TABLE " + TABLE_NAME + " ADD COLUMN mealnumber DOUBLE DEFAULT '0'");
			}
			if (!columnExists(connection, TABLE_NAME, "status")) {
				stmt.executeUpdate("ALTER TABLE " + TABLE_NAME + " ADD COLUMN status VARCHAR(10) DEFAULT 'nonvip'");
			}

			String sqlCreateOrdersTable = "CREATE TABLE IF NOT EXISTS orders (" +
					"orderId INTEGER PRIMARY KEY AUTOINCREMENT," +
					"username VARCHAR(10) NOT NULL," +
					"burritonumber DOUBLE," +
					"friesnumber DOUBLE," +
					"sodanumber DOUBLE," +
					"mealnumber DOUBLE," +
					"totalprice DOUBLE," +
					"waitingTime DOUBLE," +
					"orderTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
					"status VARCHAR(20) DEFAULT 'placed'," +  // Add this line
					"FOREIGN KEY (username) REFERENCES users(username))";
			stmt.executeUpdate(sqlCreateOrdersTable);

			if (!columnExists(connection, "orders", "status")) {
				stmt.executeUpdate("ALTER TABLE orders ADD COLUMN status VARCHAR(20) DEFAULT 'placed'");
			}
			if (!columnExists(connection, "orders", "mealnumber")) {
				stmt.executeUpdate("ALTER TABLE orders ADD COLUMN mealnumber DOUBLE");
			}
		}
	}

	private boolean columnExists(Connection connection, String tableName, String columnName) throws SQLException {
		String sql = "PRAGMA table_info(" + tableName + ")";
		try (Statement stmt = connection.createStatement();
			 ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				if (rs.getString("name").equalsIgnoreCase(columnName)) {
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setString(2, password);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFirstName(rs.getString("firstname"));
					user.setLastName(rs.getString("lastname"));
					user.setBurritonumber(rs.getDouble("burritonumber"));
					user.setFriesnumber(rs.getDouble("friesnumber"));
					user.setSodanumber(rs.getDouble("sodanumber"));
					user.setMealnumber(rs.getDouble("mealnumber"));
					user.setWaitingTime(rs.getDouble("waitingTime"));
					user.setTotalprice(rs.getDouble("totalprice"));
					user.setEmail(rs.getString("email"));
					user.setStatus(rs.getString("status"));
					return user;
				}
				return null;
			}
		}
	}
	@Override
	public Order getOrderById(int orderId) throws SQLException {
		String sql = "SELECT * FROM orders WHERE orderId = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, orderId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getInt("orderId"));
					order.setUsername(rs.getString("username"));
					order.setBurritonumber(rs.getDouble("burritonumber"));
					order.setFriesnumber(rs.getDouble("friesnumber"));
					order.setSodanumber(rs.getDouble("sodanumber"));
					order.setTotalprice(rs.getDouble("totalprice"));
					order.setWaitingTime(rs.getDouble("waitingTime"));
					order.setOrderTime(rs.getTimestamp("orderTime"));
					order.setStatus(rs.getString("status"));
					return order;
				}
				return null;
			}
		}
	}


	@Override
	public User createUser(String username, String password, String firstName, String lastName) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " (username, password, firstname, lastname) VALUES (?, ?, ?, ?)";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, firstName);
			stmt.setString(4, lastName);

			stmt.executeUpdate();
			return new User(username, password, firstName, lastName);
		}
	}




	@Override
	public User updatenonevipOrder(String username, Double burritonumber, Double friesnumber, Double sodanumber) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET burritonumber = ?, friesnumber = ?, sodanumber = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDouble(1, burritonumber);
			stmt.setDouble(2, friesnumber);
			stmt.setDouble(3, sodanumber);

			stmt.setString(4, username);

			stmt.executeUpdate();
			User user = getUserByUsername(username, connection);
			return user;
		}
	}
	@Override
	public User updateUserEmail(String username, String email) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET email = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, email);

			stmt.setString(2, username);

			stmt.executeUpdate();
			User user = getUserByUsername(username, connection);
			return user;
		}
	}
	@Override
	public User updateUserStatus(String username, String status) throws SQLException {
		String sql = "UPDATE users SET status = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1,status);
			stmt.setString(2,username);
			stmt.executeUpdate();
			User user = getUserByUsername(username, connection);
			return user;

		}

	}
	@Override
	public User updateUser(String username, String password, String firstname, String lastname) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET password = ?, firstname = ?, lastname = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, password);
			stmt.setString(2, firstname);
			stmt.setString(3, lastname);
			stmt.setString(4, username);

			stmt.executeUpdate();
			User user = getUserByUsername(username, connection);
			return user;
		}
	}
	@Override
	public User updatePriceandTime(String username, Double waitingTime, Double totalprice) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET waitingTime = ?, totalprice = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDouble(1, waitingTime);
			stmt.setDouble(2, totalprice);

			stmt.setString(3, username);

			stmt.executeUpdate();
			User user = getUserByUsername(username, connection);
			return user;
		}
	}
	@Override
	public Order createOrder(String username, Double burritonumber, Double friesnumber, Double sodanumber, Double mealnumber, Double totalprice, Double waitingTime) throws SQLException {


		String sql = "INSERT INTO orders (username, burritonumber, friesnumber, sodanumber, mealnumber, totalprice, waitingTime, status) VALUES (?, ?, ?, ?, ?, ?, ?, 'placed')";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setDouble(2, burritonumber);
			stmt.setDouble(3, friesnumber);
			stmt.setDouble(4, sodanumber);
			stmt.setDouble(5, mealnumber);
			stmt.setDouble(6, totalprice);
			stmt.setDouble(7, waitingTime);

			stmt.executeUpdate();
			return new Order(username, burritonumber, friesnumber, sodanumber, mealnumber, totalprice, waitingTime, "placed");  // Include status in the constructor
		}
	}
	@Override
	public User updateOrder(String username, Double burritonumber, Double friesnumber, Double sodanumber, Double mealnumber) throws SQLException {
		String sql = "UPDATE " + TABLE_NAME + " SET burritonumber = ?, friesnumber = ?, sodanumber = ?, mealnumber = ? WHERE username = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setDouble(1, burritonumber);
			stmt.setDouble(2, friesnumber);
			stmt.setDouble(3, sodanumber);
			stmt.setDouble(4, mealnumber);
			stmt.setString(5, username);

			stmt.executeUpdate();
			User user = getUserByUsername(username, connection);
			return user;
		}
	}
	public boolean updateOrderStatus(int orderId, String status) throws SQLException {
		String sql = "UPDATE orders SET status = ? WHERE orderId = ?";
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, status);
			stmt.setInt(2, orderId);
			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;
		}
	}




	@Override
	public ArrayList<Order> getOrdersByUsername(String username) throws SQLException {
		String sql = "SELECT * FROM orders WHERE username = ?";
		ArrayList<Order> orders = new ArrayList<>();
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Order order = new Order();
					order.setOrderId(rs.getInt("orderId"));
					order.setUsername(rs.getString("username"));
					order.setBurritonumber(rs.getDouble("burritonumber"));
					order.setFriesnumber(rs.getDouble("friesnumber"));
					order.setSodanumber(rs.getDouble("sodanumber"));
					order.setMealnumber(rs.getDouble("mealnumber"));
					order.setTotalprice(rs.getDouble("totalprice"));
					order.setWaitingTime(rs.getDouble("waitingTime"));
					order.setOrderTime(rs.getTimestamp("orderTime"));
					order.setStatus(rs.getString("status"));
					orders.add(order);
				}
			}
		}
		return orders;
	}
	@Override
	public ArrayList<User> getUsersByUsername(String username) throws SQLException {
		String sql = "SELECT * FROM users WHERE username = ?";
		ArrayList<User> users = new ArrayList<>();
		try (Connection connection = Database.getConnection();
			 PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					User user = new User();

					user.setUsername(rs.getString("username"));
					user.setBurritonumber(rs.getDouble("burritonumber"));
					user.setFriesnumber(rs.getDouble("friesnumber"));
					user.setSodanumber(rs.getDouble("sodanumber"));
					user.setMealnumber(rs.getDouble("mealnumber"));
					user.setTotalprice(rs.getDouble("totalprice"));
					user.setWaitingTime(rs.getDouble("waitingTime"));

					users.add(user);
				}
			}
		}
		return users;
	}


	@Override
	public User getUserByUsername(String username, Connection connection) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try (
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFirstName(rs.getString("firstname"));
					user.setLastName(rs.getString("lastname"));
					user.setBurritonumber(rs.getDouble("burritonumber"));
					user.setFriesnumber(rs.getDouble("friesnumber"));
					user.setSodanumber(rs.getDouble("sodanumber"));
					user.setMealnumber(rs.getDouble("mealnumber"));
					user.setEmail(rs.getString("email"));
					user.setStatus(rs.getString("status"));
					return user;
				}
				return null;
			}
		}
	}
	@Override
	public UserandOrder getorderUserByUsername(String username) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					UserandOrder user = new UserandOrder();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setFirstName(rs.getString("firstname"));
					user.setLastName(rs.getString("lastname"));
					user.setBurritonumber(rs.getDouble("burritonumber"));
					user.setFriesnumber(rs.getDouble("friesnumber"));
					user.setSodanumber(rs.getDouble("sodanumber"));
					user.setMealnumber(rs.getDouble("mealnumber"));
					user.setTotalprice(rs.getDouble("totalprice"));
					user.setWaitingtime(rs.getDouble("waitingTime"));
					user.setEmail(rs.getString("email"));
					user.setStatus(rs.getString("status"));
					return user;
				}
				return null;
			}
		}
	}
	@Override
	public User getUserOrderDetails(String username, Connection connection) throws SQLException {
		String sql = "SELECT * FROM users WHERE username = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setBurritonumber(rs.getDouble("burritonumber"));
				user.setFriesnumber(rs.getDouble("friesnumber"));
				user.setSodanumber(rs.getDouble("sodanumber"));
				user.setMealnumber(rs.getDouble("mealnumber"));
				user.setTotalprice(rs.getDouble("totalprice"));
				user.setWaitingtime(rs.getDouble("waitingtime"));
				return user;
			} else {
				return null;
			}
		}
	}
}}


