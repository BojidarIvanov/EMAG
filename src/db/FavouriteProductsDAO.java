package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OrderPojo;
import model.ProductPojo;

public class FavouriteProductsDAO {

	private static FavouriteProductsDAO instance;

	private FavouriteProductsDAO() {
	}

	private static synchronized FavouriteProductsDAO getInstance() {
		if (instance == null) {
			instance = new FavouriteProductsDAO();
		}
		return instance;
	}

	// for now when testing product_id is zero. We have to make sure when adding
	// to favorites the product has
	// a proper product_id
	public void addProductToFavourites(ProductPojo product, int user_id) throws SQLException {
		List<ProductPojo> favouriteList = getFavouriteList(user_id);
		if(!favouriteList.contains(product)) {
		Connection conn = DBManager.CON1.getConnection();
		PreparedStatement ps = conn
				.prepareStatement("INSERT INTO `emag_final_project`.`favourite_products` VALUES (?,?);");
		ps.setLong(1, product.getProductID());
		ps.setLong(2, user_id);

		ps.executeUpdate();
		ps.close();
		} else {
			System.out.println("The product is already in the list.");
		}
	}
	
	public List<ProductPojo> getFavouriteList(int user_id) throws SQLException {
		
		Connection con = DBManager.CON1.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT products.product_id, name, price, available_products, description, category_id, brand_id FROM products \r\n" + 
				"JOIN favourite_products USING (product_id) WHERE customer_id = ?;");
		ps.setLong(1, user_id);
		ResultSet rs = ps.executeQuery();
		List<ProductPojo> favProducts = new ArrayList<>();
		
		while (rs.next()) {
			favProducts.add(new ProductPojo(rs.getInt("product_id"), rs.getString("name"),
					rs.getBigDecimal("price").toString(), rs.getString("description"),
					rs.getInt("available_products"),
					CategoryDAO.getInstance().getAllCategories().get(rs.getInt("category_id")),
					BrandDAO.getInstance().getAllBrands().get(rs.getInt("brand_id")), rs.getString("image_url")));
		}
		ps.close();
		rs.close();
		
		return favProducts;	
	}
	
	
	public static void main(String[] args) {
		System.out.println("Start");
	//	ProductPojo p1 = new ProductPojo("SonyVaio", "1000", "Laptop", 100, 1, 1);
	//	ProductPojo p2 = new ProductPojo("SonyExperia", "831", "Phone", 99, 1, 1);
		
		FavouriteProductsDAO fp = FavouriteProductsDAO.getInstance();
	/*	try {
		//	fp.addProductToFavourites(p1, 1);
		//	fp.addProductToFavourites(p2, 2);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("end");
		
		try {
			System.out.println(fp.getFavouriteList(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}*/

	}

}
