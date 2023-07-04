package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.ProgressStatusDao;
import com.lawencon.myapp.model.ProgressStatus;

public class ProgressStatusDaoImpl implements ProgressStatusDao{
	
	private final Connection conn;

	public ProgressStatusDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}


	@Override
	public List<ProgressStatus> getAll() throws SQLException {
		final String sql = "SELECT * FROM t_progress_status";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final List<ProgressStatus> allProgressStatus = new ArrayList<>();
		
		final ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			final ProgressStatus newProgStat = new ProgressStatus();
			newProgStat.setId(rs.getLong("id"));
			newProgStat.setStatusName(rs.getString("status_name"));
			newProgStat.setStatusCode(rs.getString("status_code"));
			newProgStat.setCreatedBy(rs.getLong("created_by"));
			newProgStat.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			newProgStat.setIsActive(rs.getBoolean("is_active"));
			newProgStat.setVer(rs.getInt("ver"));
			allProgressStatus.add(newProgStat);
		}
		
		return allProgressStatus;
		
		
	}

}
