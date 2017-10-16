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
	
	
	
	public CategoryPojo(int categoryID, String name, String imageURL, int parentID) {
		this(name, imageURL);
		this.categoryID = categoryID;
		this.parentID = parentID;
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



	public int getCategoryID() {
		return this.categoryID;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryID;
		result = prime * result + ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + parentID;
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
		CategoryPojo other = (CategoryPojo) obj;
		if (categoryID != other.categoryID)
			return false;
		if (imageURL == null) {
			if (other.imageURL != null)
				return false;
		} else if (!imageURL.equals(other.imageURL))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentID != other.parentID)
			return false;
		return true;
	}
	
	
	

}
