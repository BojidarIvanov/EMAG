package model;

public class CategoryPojo {
	
	private int categoryID;
	private String name;
	private String imageURL;
	private int parentID;
	
	public CategoryPojo(String name, String imageURL) {
		this.name = name;
		this.imageURL = imageURL;
	}
	
	public void setID(int id) {
		this.categoryID = id;
	}
	public void setParent(int id) {
		this.parentID = id;
	}
	public String getName() {
		return name;
	}
	public String getImageURL() {
		return imageURL;
	}
	public int getParentID() {
		return parentID;
	}
	
	

}
