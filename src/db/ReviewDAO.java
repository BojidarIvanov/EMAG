package db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.BrandPojo;
import model.ReviewPojo;

public class ReviewDAO {
	
	private static ReviewDAO instance;
	private Connection connection;
	
	private ReviewDAO() {}

	public static ReviewDAO getInstance() {
		if(ReviewDAO.instance == null) {
			instance = new ReviewDAO();
		}
		return instance;
	}
	
	public boolean addReview(ReviewPojo review) throws SQLException {
		int i = 0;
		boolean isAdded = false;
		if(!reviewExist(review)) {
			
			this.connection = DBManager.CON1.getConnection();
			System.out.println(++i);
			PreparedStatement ps = this.connection.prepareStatement("INSERT INTO reviews (reviewRating, reviewText, reviewer, productID) VALUES (?, ?, ?, ?)");
			ps.setInt(1,review.getReviewingRating());
			ps.setString(2, review.getReviewText());
			ps.setInt(3,review.getReviewerID());
			ps.setInt(4,review.getProductID());
			ps.executeUpdate();
			isAdded = true;
		}
		return isAdded;
	}
	public boolean reviewExist(ReviewPojo review) throws SQLException {
		ReviewPojo reviewToCompare = null;
		reviewToCompare = getReview(review);
		return reviewToCompare != null;
	}
	public ReviewPojo getReview(ReviewPojo review) throws SQLException {
		ReviewPojo reviewToGet = null;
		ArrayList<ReviewPojo> reviews = new ArrayList<>();
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM reviews");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			reviews.add(new ReviewPojo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
		}
		for (ReviewPojo r : reviews) {
			if (r.equals(review)){
				reviewToGet = r;
			}
		}
		return reviewToGet;
	}
	public boolean deleteReview(ReviewPojo review) throws SQLException {
		int result = 0;
		if (reviewExist(review)) {
			review = getReview(review);
			this.connection = DBManager.CON1.getConnection();
			PreparedStatement ps = this.connection.prepareStatement("DELETE FROM brands WHERE reviewID = ?");
			ps.setInt(1, review.getReviewID());
			result = ps.executeUpdate();
		}
		return result > 0;
	}
	
	public static void main(String[] args) throws SQLException {
		System.out.println("Start");
		boolean exists = false;
		ReviewDAO r = ReviewDAO.getInstance();
		System.out.println(r.addReview(new ReviewPojo(1, "something", 1, 1)));
		int count = 0;

		System.out.println("End");

	}
}
