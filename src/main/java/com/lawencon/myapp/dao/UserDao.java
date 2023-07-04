package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.User;

public interface UserDao {
	
	List <User> getAll() throws SQLException;
	User insert(User user) throws SQLException;
	User update(User user) throws SQLException;
	List <User> getByRoleCode(String roleCode) throws SQLException;
	User getByUsernameAndPassword(String email,String password) throws SQLException;
	User getById (Long userId) throws SQLException;
 }
