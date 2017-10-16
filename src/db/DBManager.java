package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DBManager {
//	
//	private static DBManager instance;
//	private Connection connection;
//	
//	private DBManager() {
//		
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			System.out.println("Loading the driver has failed : " + e.getMessage());
//		}
//		final String DB_IP = "localhost";
//		final String DB_PORT = "3306";
//		final String DB_DBNAME = "emag_final_project";
//		final String DB_USER = "root";
//		final String DB_PASS = "admin";
//		
//		try {
//			this.connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_DBNAME, DB_USER, DB_PASS);
//		} catch (SQLException e) {
//			System.out.println("Opening the connection has failed : " + e.getMessage());
//		}
//	}
//	
//	//Getting the instance for the connection
//	public static synchronized DBManager getInstance() {
//		if(instance == null) {
//			instance = new DBManager();
//		}
//		return instance;
//	}
//	public Connection getConnection() {
//		return this.connection;
//	}
//	public void closeConnection() {
//		if(this.connection != null) {
//			try {
//				this.connection.close();
//			} catch (SQLException e) {
//				System.out.println("Closing the connection has failed : " + e.getMessage());
//			}
//		}
//	}
	CON1();
	
	private final String DB_IP = "localhost";
	private final String DB_PORT = "3306";
	private final String DB_NAME = "emag_final_project";
	private final String DB_USER = "root";
	private final String DB_PASS = "admin";
	private final String URL = "jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;
	
	private Connection connection;
	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("class Driver for db not found!");
		}
		try {
			this.connection = DriverManager.getConnection(URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("Error in DB:" + e.getMessage());
		}
	}
	public Connection getConnection() {
		return connection;
	}
	public void closeConnection() throws SQLException {
		connection.close();
	}
}
