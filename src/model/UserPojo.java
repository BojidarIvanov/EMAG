package model;

import java.time.LocalDate;

public class UserPojo {
	
	private int custommerID;
	private String name;
	private String email;
	private String phone;
	private LocalDate dateOfBirth;
	private String password;
	private String address;
	private boolean isAdmin;
	
	public UserPojo(String name, String email, String phone, LocalDate dateOfBirth, String password, String address,
			boolean isAdmin) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
		this.address = address;
		this.isAdmin = isAdmin;
	}
	
	public void setId(int id) {
		this.custommerID = id;
	}

	public int getCustommerID() {
		return custommerID;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public boolean isAdmin() {
		return isAdmin;
	}
	
	
	

}
