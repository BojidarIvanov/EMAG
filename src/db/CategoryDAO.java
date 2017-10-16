package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CategoryPojo;

public class CategoryDAO {

	private static CategoryDAO category;
	private Connection connection;
	
	private CategoryDAO() {};
	
	public static CategoryDAO getInstance() {
		if(category == null) {
			category = new CategoryDAO();
		}
		return category;
	}
	public void addCategory(CategoryPojo category) throws SQLException {
		if(!categoryExists(category)) {
			Connection conn = DBManager.CON1.getConnection();
			PreparedStatement ps = conn.prepareStatement("INSERT INTO `emag_final_project`.`categories` (`category_name`, `category_image`) VALUES (?,?)");
			ps.setString(1, category.getName());
			ps.setString(2, category.getImageURL());
			ps.executeUpdate();
		}
	}
	
	public CategoryPojo getCategory(CategoryPojo category) throws SQLException {
		if(categoryExists(category)) {
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * from categories");
		ResultSet rs = ps.executeQuery();

		List<CategoryPojo> categories = new ArrayList<>();
		while (rs.next()) {
			categories.add(new CategoryPojo(rs.getInt("category_id"), rs.getString("category_name"), 
							rs.getString("category_image"), rs.getInt("parent_category_id")));
		}
			for (CategoryPojo c : categories) {
				if (c.getName().equalsIgnoreCase(category.getName())) {
					category = c;
					break;
				}
			}
		ps.close();
		rs.close();
		}
		
		return category;
		
	}
	
	public boolean categoryExists(CategoryPojo category) throws SQLException {
		
		boolean exists = false;
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * from categories");
		ResultSet rs = ps.executeQuery();
		List<CategoryPojo> categories = new ArrayList<>();
		while (rs.next()) {
			categories.add(new CategoryPojo(rs.getInt("category_id"), rs.getString("category_name"), 
					rs.getString("category_image"), rs.getInt("parent_category_id")));
		}
		for (CategoryPojo c : categories) {
			if (c.getName().equalsIgnoreCase(category.getName())) {
				exists = true;
				break;
			}
		}
		rs.close();
		ps.close();
		return exists;
		
	}
	
	public boolean deleteCategory(CategoryPojo category) throws SQLException {
		
		boolean isCategoryDeleted = false;
		if (categoryExists(category)) {
			category = getCategory(category);
			CategoryPojo c = getCategory(category);
			int categoryID = c.getCategoryID();
			if (categoryID != 0) {
				this.connection = DBManager.CON1.getConnection();
				PreparedStatement ps = this.connection.prepareStatement("DELETE FROM categories WHERE category_id  = ?");
				ps.setInt(1, category.getCategoryID()); 
				ps.executeUpdate();
				ps.close();
				isCategoryDeleted = true;
				
			}
		}
		return isCategoryDeleted;

	}
	
	public static void main(String[] args) throws SQLException {
		System.out.println("Start");
		CategoryPojo c1 = new CategoryPojo("Telefoni, tableti, & laptopi", "image1");
		CategoryPojo c2 = new CategoryPojo("Kompiutri & Periferiq", "image2");
		CategoryPojo c3 = new CategoryPojo("TV, elektronika & Gaming", "image3");
		CategoryPojo c4 = new CategoryPojo("Foto, video & Optika", "image4");
		CategoryPojo c5 = new CategoryPojo("Golemi elektrouredi", "image5");
		
		CategoryDAO c = CategoryDAO.getInstance();


//			c.addCategory(c1);
//			c.addCategory(c2);
//			c.addCategory(c3);
//			c.addCategory(c4);
//			c.addCategory(c5);
			c.deleteCategory(c1);
			
		
		
		System.out.println("end");
		
	}
}
