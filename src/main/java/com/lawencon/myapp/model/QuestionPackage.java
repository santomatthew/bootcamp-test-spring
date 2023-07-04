package com.lawencon.myapp.model;

public class QuestionPackage extends BaseModel {

	private User hr;
	private String packageCode;
	private String packageName;


	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public User getHr() {
		return hr;
	}

	public void setHr(User hr) {
		this.hr = hr;
	}

}
