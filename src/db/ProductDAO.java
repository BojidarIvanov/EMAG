package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.ProductPojo;

public class ProductDAO {

	private static ProductDAO instance;

	private ProductDAO() {
	}

	private static synchronized ProductDAO getInstance() {
		if (instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}

	public void addProduct(ProductPojo product) throws SQLException {
		Connection conn = DBManager.CON1.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO `emag_final_project`.`products` (`name`,`price`, available_products , description , category_id, brand_id) VALUES (?,?,?,?,?,?)");
		ps.setString(1, product.getName());
		ps.setBigDecimal(2, product.getPrice());
		ps.setInt(3, product.getAvailability());
		ps.setString(4, product.getDescription());
		ps.setInt(5, product.getCatergoryId());
		ps.setInt(6, product.getBrandId());
        
		ps.executeUpdate();
		ps.close();
	}
	

	public static void main(String[] args) {
		System.out.println("Start");
		ProductPojo p1 = new ProductPojo("SonyVaio", "1000", "Laptop", 100, 1, 1);
		ProductPojo p2 = new ProductPojo("SonyExperia", "831", "Phone", 99, 1, 1);
		ProductPojo p3 = new ProductPojo("SonyBravia", "1500", "TV", 57, 1, 1);
		ProductPojo p4 = new ProductPojo("Lenovo", "850", "Laptop",115,1,2);
	

		ProductDAO pdao = ProductDAO.getInstance();
		try {
			pdao.addProduct(p1);
			pdao.addProduct(p2);
			pdao.addProduct(p3);
			pdao.addProduct(p4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("end");

	}
}