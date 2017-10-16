package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class OrderPojo {

	private int order_id;
	private LocalDateTime date;
	private int user_id;
	private HashMap<ProductPojo, Integer> products;
	private String shippingAddress = "";
	private String billingAddress = "";
	private BigDecimal totalPrice;
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private static final int DECIMALS = 2;
    private int status;
    
	// constructor for creating an order during user session, the user will be able 
	// to add products to a collection HashMap, if customer decides to go to a checkout 
	// more info will be requested to submit an order the additional setters provided for
	// shipping and billing addresses will handle order submission.
		
	// it is important to have date and time to differentiate orders made within one day
	public OrderPojo(LocalDateTime date, int user_id) {
		this.date = date;
		this.user_id = user_id;
		this.products = new HashMap<>();
		this.totalPrice = new BigDecimal("0");
		this.status = 0;
	}
	
	// a separate constructor for retrieving orders from database
	public OrderPojo(int order_id, LocalDateTime date, int user_id,
			String shippingAddress, String billingAddress, BigDecimal totalPrice, int status) {
		this(date,user_id);
		this.order_id = order_id;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.totalPrice = totalPrice;
		this.status = status;
	}


	public void setId(int id) {
		this.order_id = id;
	}

	public int getID() {
		return order_id;
	}
	
	// method adding products to current order
	public void addProductToOrder(ProductPojo product) {
		// products considered equal if their product_id are equal
		Integer value = products.get(product);
		if (value != null) {
			products.put(product, value + 1);
		} else {
			products.put(product, 1);
		}
		
		//adding price of the product to the total amount
		totalPrice.add(product.getPrice());
	}
	
	// method removing products from current order
	public void removeProductFromOrder(ProductPojo product) {
		products.remove(product);
		//Subtracting the price of the product if customer decides to cancel this product
		totalPrice.subtract(product.getPrice());

	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public LocalDateTime getDateTime() {
		return date;
	}

	public  Map<ProductPojo, Integer> getCollection() {
		return Collections.unmodifiableMap(products);
	}


	@Override
	public String toString() {
		return "OrderPojo [order_id=" + order_id + ", date=" + date + ", user_id=" + user_id + ", products=" + products
				+ ", totalPrice=" + totalPrice + ", status=" + status + "]";
	}
	
}
