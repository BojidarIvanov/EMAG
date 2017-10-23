package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import com.mysql.jdbc.Statement;

import model.CategoryPojo;

public class CategoryDAO {

	private static CategoryDAO category;
	private static final Map<Integer, CategoryPojo> allCategories = new TreeMap<>();

	private CategoryDAO() {
	}

	public static CategoryDAO getInstance() throws SQLException {
		if (category == null) {
			category = new CategoryDAO();
			category.getAllCategories();
			category.setParentCategories();
		}
		return category;
	}

	// root categories must be added first with parentCategoryID '0'
	public void addCategory(CategoryPojo category, Integer parentCategoryID) throws SQLException {
		if (!categoryExists(category)) {
			Connection conn = DBManager.CON1.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO emag_final_project.categories (category_name, category_image, parent_category_id) VALUES (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, category.getName());
			ps.setString(2, category.getImageURL());
			ps.setInt(3, parentCategoryID);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			allCategories.put(rs.getInt(1), category);
			allCategories.get(rs.getInt(1)).setParentCategory(allCategories.get(parentCategoryID));
			rs.close();
			ps.close();
		}
	}

	public boolean categoryExists(CategoryPojo category) throws SQLException {

		boolean exists = false;
		exists = allCategories.containsValue(category);
		return exists;
	}

	public Map<Integer, CategoryPojo> setParentCategories() throws SQLException {

		if (!allCategories.isEmpty()) {
			return allCategories;
		} else {
			Connection conn = DBManager.CON1.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT category_id, parent_category_id FROM categories");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				allCategories.get(rs.getInt("category_id"))
						.setParentCategory(allCategories.get(rs.getInt("parent_category_id")));
			}
			rs.close();
			ps.close();
			return allCategories;
		}
	}

	public Map<Integer, CategoryPojo> getAllCategories() throws SQLException {

		if (!allCategories.isEmpty()) {
			return allCategories;
		} else {
			Connection conn = DBManager.CON1.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM categories");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				allCategories.put(rs.getInt("category_id"), new CategoryPojo(rs.getInt("category_id"),
						rs.getString("category_name"), rs.getString("category_image"), null));
			}
			rs.close();
			ps.close();
			return allCategories;
		}
	}

	public static void main(String[] args) throws SQLException {
		System.out.println("Start");
		CategoryPojo c1 = new CategoryPojo("Telefoni, tableti, & laptopi", "image1");
		CategoryPojo c2 = new CategoryPojo("Kompiutri & Periferiq", "image2");
		CategoryPojo c3 = new CategoryPojo("TV, elektronika & Gaming", "image3");
		CategoryPojo c4 = new CategoryPojo("Foto, video & Optika", "image4");
		CategoryPojo c5 = new CategoryPojo("Golemi elektrourediNovo", "image5");

		CategoryDAO c = CategoryDAO.getInstance();

		// c.addCategory(c1);
		// c.addCategory(c2);
		// c.addCategory(c3);
		// c.addCategory(c4);
		c.addCategory(c5, 1);

		System.out.println("end");

	}
}
