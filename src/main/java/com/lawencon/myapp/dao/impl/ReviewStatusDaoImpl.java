package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.ReviewStatusDao;
import com.lawencon.myapp.model.ReviewStatus;

public class ReviewStatusDaoImpl implements ReviewStatusDao{
	
	private final Connection conn;

	public ReviewStatusDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}

	@Override
	public List<ReviewStatus> getAll() throws SQLException {
		final String sql= "SELECT * FROM t_review_status";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final List<ReviewStatus> allReviewStatus  = new ArrayList<>();
		
		final ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			final ReviewStatus reviewStatus = new ReviewStatus();
			reviewStatus.setId(rs.getLong("id"));
			reviewStatus.setStatusName(rs.getString("status_name"));
			reviewStatus.setCreatedBy(rs.getLong("created_by"));
			reviewStatus.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			reviewStatus.setIsActive(rs.getBoolean("is_active"));
			reviewStatus.setVer(rs.getInt("ver"));
			
			allReviewStatus.add(reviewStatus);
		}
		return allReviewStatus;
		
	}

	
	
	
}
