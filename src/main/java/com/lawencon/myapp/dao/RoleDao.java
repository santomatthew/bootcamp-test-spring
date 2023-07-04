package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.Role;

public interface RoleDao {

	List<Role> getAll() throws SQLException;
	Role getByRoleCode(Role role) throws SQLException;
}
