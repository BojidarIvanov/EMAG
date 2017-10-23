package model;

public class CategoryPojo {
	
	private int categoryID;
	private String name;
	private String imageURL;
	private CategoryPojo parentCategory;
	
	public CategoryPojo(String name, String imageURL) {
		this.name = name;
		this.imageURL = imageURL;
	}
	
	public CategoryPojo(int categoryID, String name, String imageURL, CategoryPojo parentCategory) {
		this(name, imageURL);
		this.categoryID = categoryID;
		this.parentCategory = parentCategory;
	}

		
	public String getName() {
		return name;
	}
	public String getImageURL() {
		return imageURL;
	}
	
	public CategoryPojo getParentCategory() {
		return parentCategory;
	}
	
	public void setParentCategory(CategoryPojo categoryPojo) {
 
		this.parentCategory = categoryPojo;
	}

	public int getCategoryID() {
		return this.categoryID;
	}

	public void setcategoryID(int id) {
		this.categoryID = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryID;
		result = prime * result + ((imageURL == null) ? 0 : imageURL.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (parentCategory != other.parentCategory)
			return false;
		return true;
	}

	
}
