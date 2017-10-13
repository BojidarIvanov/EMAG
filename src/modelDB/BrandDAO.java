package modelDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.BrandPojo;

public class BrandDAO {
	
	private static BrandDAO instance;
	
	private BrandDAO() {}
	
	private static synchronized BrandDAO getInstance() {
		if(instance == null) {
			instance = new BrandDAO();
		}
		return instance;
	}
	
	//Adding the brand in the DB
	public void addBrand(BrandPojo brand) throws SQLException {
		Connection conn = DBManager.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO brands (brand_name) VALUES (?)");
		ps.setString(1, brand.getName());
		ps.executeUpdate();
	}
	

}
