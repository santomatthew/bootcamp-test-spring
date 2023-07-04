package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.GetAllFileTypeDao;
import com.lawencon.myapp.model.FileType;

public class GetAllFileTypeDaoImpl implements GetAllFileTypeDao{

	private final Connection conn;

	public GetAllFileTypeDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	
	@Override
	public List<FileType> getAll() throws SQLException {
		final String sql = "SELECT * FROM t_file_type";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();
		final List<FileType> filetypes = new ArrayList<>();
		
		while(rs.next()) {
			final FileType fileType = new FileType();
			fileType.setId(rs.getLong("id"));
			fileType.setTypeName(rs.getString("type_name"));
			fileType.setTypeCode(rs.getString("type_code"));
			fileType.setCreatedBy(rs.getLong("created_by"));
			fileType.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			fileType.setIsActive(rs.getBoolean("is_active"));
			fileType.setVer(rs.getInt("ver"));
			
			filetypes.add(fileType);
		}
		return filetypes;
	}

}
