package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.OrderPojo;
import model.ProductPojo;

public class OrderDAO {

	private static OrderDAO instance;

	private OrderDAO() {
	}

	public static synchronized OrderDAO getInstance() {
		if (instance == null) {
			instance = new OrderDAO();
		}
		return instance;
	}

	// returns list of orders made by a customer the search is made by user_id
	public List<OrderPojo> getOrdersForUser(int user_id) throws SQLException {
		Connection con = DBManager.CON1.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT order_id, created_at, shipping_address, billing_address, amount, status FROM orders WHERE customer_id = ?");
		ps.setLong(1, user_id);
		ResultSet rs = ps.executeQuery();
		List<OrderPojo> orders = new ArrayList<>();
		
		while (rs.next()) {
			orders.add(new OrderPojo(rs.getInt("order_id"), rs.getTimestamp("created_at").toLocalDateTime(),
					user_id, rs.getString("shipping_address"), rs.getString("billing_address"),
					rs.getBigDecimal("amount"), rs.getInt("status")));
		}
		ps.close();
		rs.close();
		
		return orders;	
	}

	public void addOrder(OrderPojo order) throws SQLException {
		Connection con = DBManager.CON1.getConnection();
		PreparedStatement ps = con.prepareStatement(
				"INSERT INTO orders (customer_id, shipping_address, billing_address, amount, created_at, status) VALUES(?,?,?,?,?,?);",
				Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, 1);
		ps.setString(2, order.getShippingAddress());
		ps.setString(3, order.getBillingAddress());
		ps.setBigDecimal(4, order.getTotalPrice());
		ps.setString(5, order.getDateTime().toString());
		ps.setInt(6, 0);
		ps.executeUpdate();
		// getting auto-generated key
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		long key = rs.getLong(1);
		System.out.println(key);
		// calling the method to fill the ordered_products table
		addToOrderedProducts(order, key);
		ps.close();
		rs.close();
	}

	// no need to create separate Pojo for ordered_product table it's being populated by below method.
	public void addToOrderedProducts(OrderPojo order, long key) throws SQLException {
		Connection con = DBManager.CON1.getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO ordered_products  VALUES(?,?,?);");
		Map<ProductPojo, Integer> busket = order.getCollection();

		for (Entry<ProductPojo, Integer> entry : busket.entrySet()) {
			ps.setLong(1, key);
	    	ps.setLong(2, entry.getKey().getProductID());
			ps.setInt(3, entry.getValue());
			ps.executeUpdate();
		}
		ps.close();
	}

	public static void main(String[] args) throws SQLException {
		System.out.println("Start");
		OrderDAO od = OrderDAO.getInstance();
		OrderPojo op1 = new OrderPojo(LocalDateTime.now(), 1);
		OrderPojo op2 = new OrderPojo(LocalDateTime.now().minusDays(5), 2);
		OrderPojo op3 = new OrderPojo(LocalDateTime.now().minusDays(10), 1);

		od.addOrder(op1);
		od.addOrder(op2);
		od.addOrder(op3);
		System.out.println("End");
		System.out.println(od.getOrdersForUser(1));

	}
}
