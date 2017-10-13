package model;

import modelDB.ProductDAO;

public class BrandPojo {
	
	private int brandID;
	private String name;
	
	
	public BrandPojo(String name) {
		
		this.name = name;
	}
	
	public void setId(int id) {
		this.brandID = id;
	}
	public String getName() {
		return this.name;
	}

}
