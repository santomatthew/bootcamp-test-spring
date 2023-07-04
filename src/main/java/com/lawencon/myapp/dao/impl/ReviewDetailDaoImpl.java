package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.ReviewDetailDao;
import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;

public class ReviewDetailDaoImpl implements ReviewDetailDao{

	private final Connection conn;

	public ReviewDetailDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	@Override
	public ReviewDetail insert(ReviewDetail reviewDetail) throws SQLException {
		final String sql = "INSERT INTO t_review_detail (review_id ,candidate_assign_id ,created_by,created_at,is_active,ver)VALUES(?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, reviewDetail.getReview().getId());
		ps.setLong(2, reviewDetail.getCandidateAssign().getId());
		ps.setLong(3, reviewDetail.getCreatedBy());
		ps.setTimestamp(4, Timestamp.valueOf(reviewDetail.getCreatedAt()));
		ps.setBoolean(5, reviewDetail.getIsActive());
		ps.setInt(6,reviewDetail.getVer());
		
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			reviewDetail.setId(rs.getLong("id"));
		}
		
		return reviewDetail;
	}

	@Override
	public ReviewDetail update(ReviewDetail reviewDetail) throws SQLException {
		final String sql=  "UPDATE t_review_detail SET grade = ?, notes = ?, ver= ver+1 WHERE review_id = ? AND candidate_assign_id = ? RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setFloat(1, reviewDetail.getGrade());
		ps.setString(2, reviewDetail.getNotes());
		ps.setLong(3, reviewDetail.getReview().getId());
		ps.setLong(4, reviewDetail.getCandidateAssign().getId());
		
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			reviewDetail.setVer(rs.getInt("ver"));
		}
		
		return null;
	}

	@Override
	public List<ReviewDetail> get(ReviewDetail reviewDetail) throws SQLException {
		final String sql = "SELECT * FROM t_review_detail WHERE review_id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, reviewDetail.getReview().getId());
		
		final List<ReviewDetail> reviewDetails = new ArrayList<>();
		final ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			final ReviewDetail revDetail = new ReviewDetail();
			
			final Review review = new Review();
			review.setId(rs.getLong("review_id"));
			revDetail.setReview(review);
			
			final CandidateAssign candidateAssign= new CandidateAssign();
			candidateAssign.setId(rs.getLong("candidate_assign_id"));
			revDetail.setCandidateAssign(candidateAssign);
			
			revDetail.setGrade(rs.getFloat("grade"));
			revDetail.setNotes(rs.getString("notes"));
			revDetail.setCreatedBy(rs.getLong("created_by"));
			revDetail.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			revDetail.setIsActive(rs.getBoolean("is_active"));
			revDetail.setVer(rs.getInt("ver"));
			
			reviewDetails.add(revDetail);
		}
		
		return reviewDetails;
		
	}

}
