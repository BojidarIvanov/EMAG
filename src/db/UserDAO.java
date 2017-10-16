package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.UserPojo;

public class UserDAO {
	
	

	private static UserDAO instance;;
	private Connection connection;

	private UserDAO() {
	}

	public static UserDAO getInstance() {
		if (UserDAO.instance == null) {
			instance = new UserDAO();
		}
		return UserDAO.instance;
	}

	public void addUser(UserPojo user) throws SQLException, ParseException {
		if(!userExists(user)) {
			this.connection = DBManager.CON1.getConnection();
			PreparedStatement ps = this.connection.prepareStatement(
					"INSERT INTO users (name, email, phone, date_of_birth, password, address, is_admin) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhone());
			java.sql.Date date = convertToDatabaseColumn(user.getDateOfBirth());
			ps.setDate(4, date);
			ps.setString(5, user.getPassword());
			ps.setString(6, user.getAddress());
			ps.setBoolean(7, user.isAdmin());
	
			ps.executeUpdate();
			ps.close();
		}
	}

	public UserPojo getUser(UserPojo user) throws SQLException {
		if(userExists(user)) {
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * from users");
		ResultSet rs = ps.executeQuery();

		List<UserPojo> users = new ArrayList<>();
		while (rs.next()) {
			users.add(new UserPojo(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email"),
					rs.getString("phone"), convertToEntityAttribute(rs.getDate("date_of_birth")), 
					rs.getString("password"), rs.getString("address"), rs.getBoolean("is_admin")));
		}
			for (UserPojo u : users) {
				if (u.equals(user)) {
					user = u;
					
					break;
				}
			}
		ps.close();
		rs.close();
		}
		
		return user;
		
	}

	public boolean userExists(UserPojo user) throws SQLException {
		boolean exists = false;
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * from users");
		ResultSet rs = ps.executeQuery();
		List<UserPojo> users = new ArrayList<>();
		while (rs.next()) {
			users.add(new UserPojo(rs.getInt("customer_id"), rs.getString("name"), rs.getString("email"),
					rs.getString("phone"), convertToEntityAttribute(rs.getDate("date_of_birth")), 
					rs.getString("password"), rs.getString("address"), rs.getBoolean("is_admin")));
		}
		for (UserPojo u : users) {
			if (u.equals(user)) {
				exists = true;
				break;
			}
		}
		rs.close();
		ps.close();
		return exists;
	}

	// Dosen't work with prepared stmt. I don't know why
	public boolean deleteUser(UserPojo user) throws SQLException {
		boolean isUserDeleted = false;
		if (userExists(user)) {
			UserPojo u = getUser(user);
			int userID = u.getCustommerID();
			if (userID != 0) {
				this.connection = DBManager.CON1.getConnection();
				PreparedStatement ps = this.connection.prepareStatement("DELETE FROM users WHERE customer_id  = " + userID);
				//ps.setInt(1, user.getCustommerID());
				int i = ps.executeUpdate();
				ps.close();
				isUserDeleted = true;
				System.out.println(i);
			}
		}
		return isUserDeleted;

	}

	private java.sql.Date convertToDatabaseColumn(LocalDate entityValue) {
        return java.sql.Date.valueOf(entityValue);
    }
	public LocalDate convertToEntityAttribute(java.sql.Date databaseValue) {
        return databaseValue.toLocalDate();
    }

	public static void main(String[] args) throws SQLException, ParseException {
		
		DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(
		        FormatStyle.MEDIUM).withLocale(Locale.GERMAN);
		LocalDate xmas = LocalDate.parse("24.12.2017", germanFormatter);

		System.out.println("Start");
		UserPojo u1 = new UserPojo("name", "email2", "phone", xmas, "password", "address", true);
		//UserDAO.getInstance().addUser(u1);
		System.out.println(UserDAO.getInstance().deleteUser(u1));
		System.out.println("End");
	}
	

	
	
}
