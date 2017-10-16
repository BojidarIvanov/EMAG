package model;


public class BrandPojo {
	
	private int brandID;
	private String name;
	
	
	public BrandPojo(String name) {
		
		this.name = name;
	}
	public BrandPojo(int brandID, String name) {
		this(name);
		this.brandID = brandID;
	}
	
	
	public String getName() {
		return this.name;
	}
	public int getBrandID() {
		return brandID;
	}
	public void setBrandID(int brandID) {
		this.brandID = brandID;
	}
	

}
