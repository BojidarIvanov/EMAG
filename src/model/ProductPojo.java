package model;

import java.math.BigDecimal;

public class ProductPojo implements Comparable<ProductPojo> {

	private int productID;
	private String name;
	private BigDecimal price;
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private static final int DECIMALS = 2;
	private String description;
	private int quantity;
	private CategoryPojo category;
	private BrandPojo brand;
	private String imageURL;

	public ProductPojo(String name, String price, String description, int quantity, CategoryPojo category, BrandPojo brand, String imageURL) {

		this.name = name;
		this.price = rounded(new BigDecimal(price));
		this.description = description;
		this.quantity = quantity;
		this.category = category;
		this.brand = brand;
		this.imageURL = imageURL;
	}

	// adding constructor for product object with product_id 
	public ProductPojo(int product_id, String name, String price, String description, int quantity, CategoryPojo category,
			BrandPojo brand, String imageURL) {
		this(name, price, description, quantity, category, brand, imageURL);
		this.productID = product_id;
	}

	private void setID(int id) {
		this.productID = id;
	}

	private BigDecimal rounded(BigDecimal n) {
		return n.setScale(DECIMALS, ROUNDING_MODE);
	}

	// All products to be considered equal if they have the same product_id
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (productID ^ (productID >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductPojo other = (ProductPojo) obj;
		if (productID != other.productID)
			return false;
		return true;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public int getAvailability() {
		return quantity;
	}

	public String getDescription() {
		return description;
	}

	public CategoryPojo getCategory() {
		return category;
	}

	public BrandPojo getBrand() {
		return brand;
	}

	public int getProductID() {
		return productID;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setImageURL(String url) {
		this.imageURL = url;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	
	@Override
	public String toString() {
		return "ProductPojo [productID=" + productID + ", name=" + name + ", price=" + price + ", description="
				+ description + ", quantity=" + quantity + ", catagory=" + category + ", brand=" + brand + "]";
	}

	@Override
	public int compareTo(ProductPojo product) {
		return this.getProductID()-product.getProductID();
	}
}
