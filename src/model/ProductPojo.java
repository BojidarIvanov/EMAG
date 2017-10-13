package model;

import java.math.BigDecimal;

public class ProductPojo {
	
	private long productID;
	private String name;
	private BigDecimal price;
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private static final int DECIMALS = 2;
	private String description;
	private int quantity;
	private int catagoryID;
	private int brandID;
	
	
	
	public ProductPojo(String name, BigDecimal price, String description, int quantity, int catagoryID, int brandID) {
		
		this.name = name;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.catagoryID = catagoryID;
		this.brandID = brandID;
	}

	private void setID(int id) {
		this.productID = id;
	}
	
	private BigDecimal rounded(BigDecimal n) {
		return n.setScale(DECIMALS, ROUNDING_MODE);
	}

}
