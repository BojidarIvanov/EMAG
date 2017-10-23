package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import com.mysql.jdbc.Statement;

import model.BrandPojo;

public class BrandDAO {

	private static BrandDAO instance;
	private Connection connection;
	private static final TreeMap<Integer, BrandPojo> allBrands = new TreeMap<>();

	private BrandDAO() {
	}

	public static synchronized BrandDAO getInstance() throws SQLException {
		if (instance == null) {
			instance = new BrandDAO();
			instance.getAllBrands();
		}
		return instance;
	}

	public boolean addBrand(BrandPojo brand) throws SQLException {
		boolean brandAdded = false;
		if (!brandExists(brand.getName())) {
			this.connection = DBManager.CON1.getConnection();
			PreparedStatement ps = this.connection.prepareStatement("INSERT INTO brands (brand_name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, brand.getName());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			allBrands.put(rs.getInt(1), new BrandPojo(rs.getInt(1), brand.getName()));
			brandAdded = true;
			rs.close();
			ps.close();

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
			allBrands.remove(brand.getBrandID());
		}
		return result > 0;
	}

	public boolean brandExists(String brandName) throws SQLException {

		return allBrands.values().contains(brandName);
	}

	public BrandPojo getBrand(String brandName) throws SQLException {

		BrandPojo brand = null;
		for (BrandPojo currentBrand : allBrands.values()) {
			if (currentBrand.getName().equals(brandName)) {
				brand = currentBrand;
				break;
			}
		}
		return brand;
	}

	public Map<Integer, BrandPojo> getAllBrands() throws SQLException {

		if (!allBrands.isEmpty()) {
			return allBrands;
		} else {
			Connection conn = DBManager.CON1.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM brands");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				allBrands.put(rs.getInt("brand_id"), new BrandPojo(rs.getInt("brand_id"), rs.getString("brand_name")));
			}
			rs.close();
			ps.close();
			return allBrands;
		}
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
