package modelDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		Connection conn = DBManager.getInstance().getConnection();
		PreparedStatement ps = conn.prepareStatement("INSERT INTO `emag_final_project`.`categories` (`category_name`, `category_image`) VALUES (?,?)");
		ps.setString(1, category.getName());
		ps.setString(2, category.getImageURL());
		ps.executeUpdate();
	}
	public static void main(String[] args) {
		System.out.println("Start");
		CategoryPojo c1 = new CategoryPojo("Telefoni, tableti, & laptopi", "image1");
		CategoryPojo c2 = new CategoryPojo("Kompiutri & Periferiq", "image2");
		CategoryPojo c3 = new CategoryPojo("TV, elektronika & Gaming", "image3");
		CategoryPojo c4 = new CategoryPojo("Foto, video & Optika", "image4");
		CategoryPojo c5 = new CategoryPojo("Golemi elektrouredi", "image5");
		
		CategoryDAO c = CategoryDAO.getInstance();
		try {
			c.addCategory(c1);
			c.addCategory(c2);
			c.addCategory(c3);
			c.addCategory(c4);
			c.addCategory(c5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
		
	}
}
