package modelDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import model.UserPojo;

public class UserDAO {
	
	private static UserDAO instance;;
	private Connection connection;
	
	private UserDAO() {}
	
	public static UserDAO getInstance() {
		if(UserDAO.instance == null) {
			instance = new UserDAO();
		}
		return UserDAO.instance;
	}
	public void addUser(UserPojo user) throws SQLException {
		this.connection = DBManager.getInstance().getConnection();
		PreparedStatement ps = this.connection.prepareStatement("INSERT INTO users (name, email, phone, date_of_birth, password, address, is_admin) VALUES (?, ?, ?, ?, ?, ?, ?)");
		ps.setString(1, user.getName());
		ps.setString(2, user.getEmail());
		ps.setString(3, user.getPhone());
		ps.setString(4, user.getDateOfBirth().toString());
		ps.setString(5, user.getPassword());
		ps.setString(6, user.getAddress());
		ps.setBoolean(7, user.isAdmin());
		
		ps.executeUpdate();
		ps.close();
	}
	
	public static void main(String[] args) {
		System.out.println("start");
		UserPojo u1 = new UserPojo("Sasho", "email1", "phone1: +213111331", LocalDate.now(), "dsadsa", "fdsfdasf", true);
		
		try {
			UserDAO.getInstance().addUser(u1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
	}
	
	

}
