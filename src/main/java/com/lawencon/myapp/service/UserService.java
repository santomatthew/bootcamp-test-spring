package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.File;
import com.lawencon.myapp.model.Profile;
import com.lawencon.myapp.model.Role;
import com.lawencon.myapp.model.User;

public interface UserService {
	
	List <User> getAll() throws SQLException;
	User insert(User user,Profile profile, File file) throws SQLException;
	User update(User user,Profile profile) throws SQLException;
	Profile changePhoto(Long profileId, File file) throws SQLException;
	User login(String email,String password )throws SQLException;
	List<User> getByRoleCode (String roleCode) throws SQLException;
	String getRoleView(String roleInput);
	List<Role> getRoleExceptSuperAdmin() throws SQLException;
	Role getByCode(String roleCode)  throws SQLException;
}
