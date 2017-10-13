package modelDB;

import java.sql.Connection;
import java.sql.PreparedStatement;

import model.ProductPojo;

public class ProductDAO {
	
	private static ProductDAO instance;
	
	private ProductDAO() {
		
	}
	
	private static synchronized ProductDAO getInstance() {
		if(instance == null) {
			instance = new ProductDAO();
		}
		return instance;
	}
	
//	public boolean addProduct(ProductPojo product) {
//		Connection connection = DBManager.getInstance().getConnection();
//		
////		PreparedStatement ps = connection.prepareStatement("INSERT INTO products VALUES (")
//	}

}
