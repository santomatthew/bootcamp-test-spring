package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.ProfileDao;
import com.lawencon.myapp.model.File;
import com.lawencon.myapp.model.Profile;

public class ProfileDaoImpl implements ProfileDao{

	private final Connection conn;

	public ProfileDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	@Override
	public Profile insert(Profile profile) throws SQLException {
		final String sql = "INSERT INTO t_profile (photo_id,full_name,address,phone_no,created_by,created_at,is_active,ver)VALUES(?,?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, profile.getPhoto().getId());
		ps.setString(2, profile.getFullName());
		ps.setString(3, profile.getAddress());
		ps.setString(4, profile.getPhoneNo());
		ps.setLong(5, profile.getCreatedBy());
		ps.setTimestamp(6, Timestamp.valueOf(profile.getCreatedAt()));
		ps.setBoolean(7, profile.getIsActive());
		ps.setInt(8, profile.getVer());
		
		final ResultSet rs = ps.executeQuery();

		
		if(rs.next()) {
			profile.setId(rs.getLong("id"));
		}
		
		return profile;
	}

	@Override
	public Profile update(Profile profile) throws SQLException{
		final String sql = "UPDATE t_profile photo_id = ?, full_name = ?, address = ?, phone_no = ?, updated_by = ?, updated_at =?, is_active =?,ver=ver+1  RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, profile.getPhoto().getId());
		ps.setString(2, profile.getFullName());
		ps.setString(3, profile.getAddress());
		ps.setString(4, profile.getPhoneNo());
		ps.setLong(5, profile.getUpdatedBy());
		ps.setTimestamp(6, Timestamp.valueOf(profile.getUpdatedAt()));
		ps.setBoolean(7, profile.getIsActive());
		
		final ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			profile.setVer(rs.getInt("ver"));
		}
		return profile;
	}

	@Override
	public Profile getById(Long id) throws SQLException{
		final String sql = "SELECT * FROM t_profile WHERE id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, id);
		final ResultSet rs = ps.executeQuery();
		Profile profile = null;
		
		if(rs.next()) {
			profile = new Profile();
			final File photoFile = new File();
			photoFile.setId(rs.getLong("photo_id"));
			profile.setPhoto(photoFile);
			
			profile.setFullName(rs.getString("full_name"));
			profile.setAddress(rs.getString("address"));
			profile.setPhoneNo(rs.getString("phone_no"));
			if(rs.getTimestamp("updated_by")!= null) {
				profile.setUpdatedBy(rs.getLong("updated_by"));
			}
			if(rs.getTimestamp("updated_at")!= null) {
				profile.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			profile.setIsActive(rs.getBoolean("is_active"));
			profile.setVer(rs.getInt("ver"));
		}
		
		return profile;
	}

}
