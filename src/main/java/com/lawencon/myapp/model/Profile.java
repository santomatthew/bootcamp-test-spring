package com.lawencon.myapp.model;

public class Profile extends BaseModel{

	private File photo;
	private String fullName;
	private String address;
	private String phoneNo;

	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
	
	
	
	
}
