package com.lawencon.myapp.constant;

public enum UserRole {

	SUPERADMIN("SPAD", "Super Admin"), HUMANRESOURCE("HR", "Human Resource"), REVIEWER("REV", "Reviewer"),
	CANDIDATE("CDT", "Candidate");
	
	public final String roleCode;
	public final String roleName;

	UserRole(String roleCode, String roleName) {
		this.roleCode = roleCode;
		this.roleName = roleName;
	}

}
