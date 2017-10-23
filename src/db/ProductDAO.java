package db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;

import model.ProductPojo;

public class ProductDAO {

	private static ProductDAO instance;
	private static final TreeSet<ProductPojo> products = new TreeSet<>();

	private ProductDAO() {
	}

	public static synchronized ProductDAO getInstance() throws SQLException {
		if (instance == null) {
			instance = new ProductDAO();
			instance.getAllProducts();
		}
		return instance;
	}

	public void addProduct(String name, BigDecimal priceBD, String description, int categoryId, int brandId,
			int availableProducts, String imageUrl) throws SQLException {
		Connection conn = DBManager.CON1.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO emag_final_project.products (name, price, description, category_id, brand_id, available_products, image_url) VALUES (?,?,?,?,?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, name);
		ps.setBigDecimal(2, priceBD);
		ps.setString(3, description);
		ps.setInt(4, categoryId);
		ps.setInt(5, brandId);
		ps.setInt(6, availableProducts);
		ps.setString(7, imageUrl);
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		products.add(new ProductPojo(rs.getInt(1), name,
				priceBD.toString(), description, categoryId,
				CategoryDAO.getInstance().getAllCategories().get(categoryId),
				BrandDAO.getInstance().getAllBrands().get(brandId), imageUrl));
		rs.close();
		ps.close();
	}

	public TreeSet<ProductPojo> getAllProducts() throws SQLException {

		if (!products.isEmpty()) {
			return products;
		} else {
			Connection conn = DBManager.CON1.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM products");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				products.add(new ProductPojo(rs.getInt("product_id"), rs.getString("name"),
						rs.getBigDecimal("price").toString(), rs.getString("description"),
						rs.getInt("available_products"),
						CategoryDAO.getInstance().getAllCategories().get(rs.getInt("category_id")),
						BrandDAO.getInstance().getAllBrands().get(rs.getInt("brand_id")), rs.getString("image_url")));
			}
			return products;
		}
	}

	public HashMap<String, Integer> getProductsForOrder(int orderId) throws SQLException {
		Connection conn = DBManager.CON1.getConnection();
		PreparedStatement ps = conn.prepareStatement(
				"SELECT p.name as name, o.items as quantity FROM ordered_products as " + "o JOIN products as p ON "
						+ "o.product_id = p.product_id WHERE " + "o.order_id = ? ORDER BY quantity;");
		ps.setLong(1, orderId);
		ResultSet rs = ps.executeQuery();
		LinkedHashMap<String, Integer> productsForOrder = new LinkedHashMap<String, Integer>();
		while (rs.next()) {
			productsForOrder.put(rs.getString("name"), rs.getInt("quantity"));
		}
		rs.close();
		ps.close();
		return productsForOrder;
	}

	public void handleEdit(String productId, String productName, BigDecimal priceBD, String description,
			int categoryId, int brandId, int availability, String imageUrl) throws SQLException  {
		
		int productIdParsed = Integer.parseInt(productId);
		String sql = "UPDATE products SET name = ?, price = ?, description = ?, category_id = ?, brand_id = ?, available_products = ?, image_url = ? where product_id = ?";
		Connection conn = DBManager.CON1.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, productName);
		pstmt.setBigDecimal(2, priceBD);
		pstmt.setString(3, description);
		pstmt.setInt(4, categoryId);
		pstmt.setInt(5, brandId);
		pstmt.setInt(6, availability);
		pstmt.setString(7, imageUrl);
		pstmt.setInt(8, productIdParsed);
		pstmt.executeUpdate();
	}

	public static void main(String[] args) {

		System.out.println("Start");
		// ProductDAO pdao = ProductDAO.getInstance();
		System.out.println("end");

	}

}
