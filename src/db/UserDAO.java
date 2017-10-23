package db;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import model.OrderPojo;
import model.UserPojo;
import util.PasswordUtil;

public class UserDAO {

	private static UserDAO instance;;
	private Connection connection;
	private static final ConcurrentHashMap<String, UserPojo> users = new ConcurrentHashMap<>();

	private UserDAO() {
	}

	public static UserDAO getInstance() throws SQLException {
		if (UserDAO.instance == null) {
			instance = new UserDAO();
			instance.getAllUsers();
		}
		return instance;
	}

	public boolean addUser(UserPojo user) throws SQLException, NoSuchAlgorithmException {
		if (!userExists(user)) {
			this.connection = DBManager.CON1.getConnection();
			PreparedStatement ps = this.connection.prepareStatement(
					"INSERT INTO users (name, email, phone, date_of_birth, password, address, is_admin) VALUES (?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhone());
			java.sql.Date date = convertToDatabaseColumn(user.getDateOfBirth());
			ps.setDate(4, date);
			System.out.println(user.getPassword());
			String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
			ps.setString(5, hashedPassword);
			ps.setString(6, user.getAddress());
			ps.setBoolean(7, user.getIsAdmin());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			users.put(user.getEmail(), new UserPojo(rs.getInt(1), user.getName(), user.getEmail(), user.getPhone(),
					user.getDateOfBirth(), hashedPassword, user.getAddress(), user.getIsAdmin()));
			rs.close();
			ps.close();
			return true;
		} else {
			return false;
		}
	}

	public UserPojo getUser(UserPojo user) throws SQLException {

		UserPojo theUser = users.get(user.getEmail());
		HashSet<OrderPojo> orders = OrderDAO.getInstance().getOrdersForUser(theUser);
		System.out.println(orders.toString());
		theUser.setOrders(orders);
		return theUser;
	}

	public Map<String, UserPojo> getAllUsers() throws SQLException {

		if (!users.isEmpty()) {
			return users;
		}
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * from users");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			users.put(rs.getString("email"),
					new UserPojo(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email"),
							rs.getString("phone"), convertToEntityAttribute(rs.getDate("date_of_birth")),
							rs.getString("password"), rs.getString("address"), rs.getBoolean("is_admin")));
		}
		rs.close();
		ps.close();
		return users;
	}

	// only checking for email
	public boolean userExists(UserPojo user) throws SQLException {
		return users.get(user.getEmail()) != null;
	}

	public boolean userExistsEmailAndPassword(UserPojo user) {
		if (user.getPassword().equals(users.get(user.getEmail()).getPassword())) {
			return true;
		}
		return false;
	}

	private java.sql.Date convertToDatabaseColumn(LocalDate entityValue) {
		return java.sql.Date.valueOf(entityValue);
	}

	private LocalDate convertToEntityAttribute(java.sql.Date databaseValue) {
		return databaseValue.toLocalDate();
	}

	public static void main(String[] args) throws SQLException, ParseException {

		DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
				.withLocale(Locale.GERMAN);
		LocalDate xmas = LocalDate.parse("24.12.2017", germanFormatter);
		System.out.println("Start");
		UserPojo u1 = new UserPojo("name", "email2", "phone", xmas, "password", "address", true);
		// UserDAO.getInstance().addUser(u1);
		System.out.println("End");
	}

	public void updatePass(UserPojo u) throws SQLException {

		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("UPDATE users SET password = ? * WHERE email = ?");
		ps.setString(1, u.getPassword());
		ps.setString(1, u.getEmail());
		ps.executeUpdate();
		System.out.println("Password was set.");
		ps.close();
	}
}
