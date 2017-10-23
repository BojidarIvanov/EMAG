package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OrderPojo {

	private int orderId;
	private LocalDateTime date;
	private int userId;
	private HashMap<ProductPojo, Integer> currentOrderProducts;
	private String shippingAddress = "";
	private String billingAddress = "";
	private BigDecimal totalPrice;
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private static final int DECIMALS = 2;
    private int status;
    private Map<String, Integer> historyForOrderedProducts;
    
	// constructor for creating an order during user session, the user will be able 
	// to add products to a collection HashMap, if customer decides to go to a checkout 
	// more info will be requested to submit an order the additional setters provided for
	// shipping and billing addresses will handle order submission.
		
	// it is important to have date and time to differentiate orders made within one day
	public OrderPojo(LocalDateTime date, int userId) {
		this.date = date;
		this.userId = userId;
		this.currentOrderProducts = new HashMap<>();
		this.totalPrice = new BigDecimal("0");
		this.status = 0;
	}
	
	// a separate constructor for retrieving orders from database
	public OrderPojo(int orderId, LocalDateTime date, int userId,
			String shippingAddress, String billingAddress, BigDecimal totalPrice, int status) {
		this(date,userId);
		this.orderId = orderId;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.totalPrice = totalPrice;
		this.status = status;
	}


	public void setOrderId(int id) {
		this.orderId = id;
	}

	public int getOrderID() {
		return orderId;
	}
	
	// method adding products to current order
	public void addProductToOrder(ProductPojo product) {
		// products considered equal if their product_id are equal
		Integer value = currentOrderProducts.get(product);
		if (value != null) {
			currentOrderProducts.put(product, value + 1);
		} else {
			currentOrderProducts.put(product, 1);
		}
		
		//adding price of the product to the total amount
		totalPrice.add(product.getPrice());
	}
	
	// method removing products from current order
	public void removeProductFromOrder(ProductPojo product) {
		currentOrderProducts.remove(product);
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

	public LocalDateTime getDate() {
		return date;
	}

	public  Map<ProductPojo, Integer> getCollection() {
		return Collections.unmodifiableMap(currentOrderProducts);
	}
	
	public void setHistoryForOrderedProducts(HashMap<String, Integer> products) {
		this.historyForOrderedProducts = products;
	}
	
	public Map<String, Integer> getHistoryForOrderedProducts() {
		return historyForOrderedProducts;
	}


	@Override
	public String toString() {
		return "OrderPojo [orderId=" + orderId + ", date=" + date + ", user_id=" + userId + ", products=" + currentOrderProducts
				+ ", totalPrice=" + totalPrice + ", status=" + status + "]";
	}
	
}
