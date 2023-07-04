package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.UserDao;
import com.lawencon.myapp.model.Profile;
import com.lawencon.myapp.model.Role;
import com.lawencon.myapp.model.User;

public class UserDaoImpl implements UserDao{

	private final Connection conn;

	public UserDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}

	
	@Override
	public List<User> getAll() throws SQLException{
		final String sql = "SELECT * FROM t_user";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();
		final List<User> users= new ArrayList<>();
		
		while(rs.next()) {
			final User user = new User();
			user.setId(rs.getLong("id"));
			user.setUserEmail(rs.getString("user_email"));
			user.setUserPassword(rs.getString("user_password"));
			user.setCreatedBy(rs.getLong("created_by"));
			user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			user.setIsActive(rs.getBoolean("is_active"));
			user.setVer(rs.getInt("ver"));
			users.add(user);
		}
		
		return users;
	}

	@Override
	public User insert(User user) throws SQLException {
		final String sql = "INSERT INTO t_user (user_email,user_password,role_id,profile_id,created_by,created_at,is_active,ver) VALUES (?,?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUserEmail());
		ps.setString(2, user.getUserPassword());
		ps.setLong(3, user.getRole().getId());
		ps.setLong(4, user.getProfile().getId());
		ps.setLong(5, user.getCreatedBy());
		ps.setTimestamp(6, Timestamp.valueOf(user.getCreatedAt()));
		ps.setBoolean(7, user.getIsActive());
		ps.setInt(8, user.getVer());
		
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			user.setId(rs.getLong("id"));
		}
		
		return user;
	}

	@Override
	public User update(User user) throws SQLException {
		final String sql = "UPDATE t_user SET "
				+ "updated_by = ?, "
				+ "updated_at = ?, "
				+ "is_active = ?, "
				+ "ver= ver+1 "
				+ "WHERE "
				+ "id = ? RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, user.getUpdatedBy());
		ps.setTimestamp(2, Timestamp.valueOf(user.getUpdatedAt()));
		ps.setBoolean(3, user.getIsActive());
		ps.setLong(4, user.getId());
		
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			user.setVer(rs.getInt("ver"));
		}
		
		return user;
	}

	@Override
	public List<User> getByRoleCode(String roleCode) throws SQLException {
		final String sql = "SELECT * FROM t_user INNER JOIN t_profile ON t_profile.id = t_user.profile_id INNER JOIN t_role ON t_role.id = t_user.role_id WHERE role_code = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, roleCode);
		final ResultSet rs = ps.executeQuery();
		final List<User> users= new ArrayList<>();
		
		while(rs.next()) {
			final User user = new User();
			user.setId(rs.getLong("id"));
			user.setUserEmail(rs.getString("user_email"));
			user.setUserPassword(rs.getString("user_password"));
			
			final Role role = new Role();
			role.setId(rs.getLong("role_id"));
			role.setRoleCode(rs.getString("role_code"));
			user.setRole(role);
			
			final Profile profile = new Profile();
			profile.setId(rs.getLong("profile_id"));
			profile.setFullName(rs.getString("full_name"));
			user.setProfile(profile);
			
			user.setCreatedBy(rs.getLong("created_by"));
			user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			user.setIsActive(rs.getBoolean("is_active"));
			user.setVer(rs.getInt("ver"));
			users.add(user);
		}
		
		return users;
	}

	@Override
	public User getByUsernameAndPassword(String email, String password) throws SQLException {
		final String sql = 
				"SELECT * FROM t_user INNER JOIN t_role ON t_role.id = t_user.role_id INNER JOIN t_profile ON t_profile.id = t_user.profile_id WHERE user_email = ? AND user_password = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, email);
		ps.setString(2, password);
		final ResultSet rs = ps.executeQuery();
		User user = null;
		
		if(rs.next()) {
			final User userGet = new User();
			userGet.setId(rs.getLong("id"));
			userGet.setUserEmail(rs.getString("user_email"));
			userGet.setUserPassword(rs.getString("user_password"));
			
			final Role role= new Role();
			role.setId(rs.getLong("role_id"));
			role.setRoleCode(rs.getString("role_code"));
			
			userGet.setRole(role);
			
			final Profile profile = new Profile();
			profile.setId(rs.getLong("profile_id"));
			profile.setFullName(rs.getString("full_name"));
			userGet.setProfile(profile);
			
			userGet.setCreatedBy(rs.getLong("created_by"));
			userGet.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			
			if(rs.getLong("updated_by")!= 0) {
				userGet.setUpdatedBy(rs.getLong("updated_by"));
			}
			
			if(rs.getTimestamp("updated_at")!= null) {
				userGet.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			userGet.setIsActive(rs.getBoolean("is_active"));
			userGet.setVer(rs.getInt("ver"));
			user = userGet;
		}
		return user;
	}

	@Override
	public User getById(Long userId) throws SQLException {
		final String sql = "SELECT * FROM t_user INNER JOIN t_profile tp ON tp.id = t_user.profile_id WHERE t_user.id = ? ";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, userId);
		
		final ResultSet rs = ps.executeQuery();
		User user = null;
		if(rs.next()) {
			final User newUser =  new User();
			newUser.setId(rs.getLong("id"));
			final Profile profile = new Profile();
			profile.setFullName(rs.getString("full_name"));
			newUser.setProfile(profile);
			user = newUser;
		}
	
		return user;
	}

}
