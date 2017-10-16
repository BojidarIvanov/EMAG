package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.BrandPojo;

public class BrandDAO {

	private static BrandDAO instance;
	private Connection connection;

	private BrandDAO() {
	}

	private static synchronized BrandDAO getInstance() {
		if (instance == null) {
			instance = new BrandDAO();
		}
		return instance;
	}

	public boolean addBrand(BrandPojo brand) throws SQLException {
		boolean brandAdded = false;
		if (!brandExists(brand.getName())) {
			this.connection = DBManager.CON1.getConnection();
			PreparedStatement ps = this.connection.prepareStatement("INSERT INTO brands (brand_name) VALUES (?)");
			ps.setString(1, brand.getName());
			ps.executeUpdate();
			brandAdded = true;
		}
		return brandAdded;
	}

	public boolean deleteBrand(String brandName) throws SQLException {
		int result = 0;
		if (brandExists(brandName)) {
			BrandPojo brand = null;
			brand = getBrand(brandName);
			this.connection = DBManager.CON1.getConnection();
			PreparedStatement ps = this.connection.prepareStatement("DELETE FROM brands WHERE brand_id = ?");
			ps.setInt(1, brand.getBrandID());
			result = ps.executeUpdate();
		}
		return result > 0;
	}

	public boolean brandExists(String brandName) throws SQLException {
		BrandPojo brand = null;
		brand = getBrand(brandName);
		return brand != null;

	}

	public BrandPojo getBrand(String brandName) throws SQLException {
		BrandPojo brand = null;
		ArrayList<BrandPojo> brands = new ArrayList<>();
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM brands WHERE brand_name = ?");
		ps.setString(1, brandName);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			brands.add(new BrandPojo(rs.getInt("brand_id"), rs.getString("brand_name")));
		}
		for (BrandPojo b : brands) {
			if (b.getName().equalsIgnoreCase(brandName)) {
				brand = b;
			}
		}
		return brand;

	}

	public static void main(String[] args) throws SQLException {
		System.out.println("Start");
		boolean exists = false;
		BrandDAO b = BrandDAO.getInstance();
		BrandPojo b1 = new BrandPojo("brand 37");
		int count = 0;
		for (int i = 0; i < 100_000; ++i) {
			
				System.out.println(b.addBrand(new BrandPojo("brand " + (i))));
				System.out.println(++count);
				System.out.println(b.deleteBrand("brand " + (i)));
				System.out.println(++count);
			
		}

		System.out.println("End");

	}

}
