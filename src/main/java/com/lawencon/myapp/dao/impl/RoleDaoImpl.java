package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.RoleDao;
import com.lawencon.myapp.model.Role;

public class RoleDaoImpl implements RoleDao {

	private final Connection conn;

	public RoleDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}

	
	@Override
	public List<Role> getAll() throws SQLException{
		final String sql = "SELECT * FROM t_role";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();
		final List<Role> roles = new ArrayList<>();
		
		while(rs.next()) {
			final Role role = new Role();
			role.setId(rs.getLong("id"));
			role.setRoleName(rs.getString("role_name"));
			role.setRoleCode(rs.getString("role_code"));
			role.setCreatedBy(rs.getLong("created_by"));
			role.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if(rs.getTimestamp("updated_by")!= null) {
				role.setUpdatedBy(rs.getLong("updated_by"));
			}
			if(rs.getTimestamp("updated_at")!= null) {
				role.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			role.setIsActive(rs.getBoolean("is_active"));
			role.setVer(rs.getInt("ver"));
			roles.add(role);
	
	}
		return roles;
	}


	@Override
	public Role getByRoleCode(Role role) throws SQLException {
		final String sql = "SELECT * FROM t_role WHERE role_code = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, role.getRoleCode());
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			role.setId(rs.getLong("id"));
			role.setRoleName(rs.getString("role_name"));
			role.setRoleCode(rs.getString("role_code"));
			role.setCreatedBy(rs.getLong("created_by"));
			role.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if(rs.getTimestamp("updated_by")!= null) {
				role.setUpdatedBy(rs.getLong("updated_by"));
			}
			if(rs.getTimestamp("updated_at")!= null) {
				role.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			role.setIsActive(rs.getBoolean("is_active"));
			role.setVer(rs.getInt("ver"));
		}
		
		return role;
	}

	

}
