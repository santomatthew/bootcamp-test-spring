package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.ReviewDao;
import com.lawencon.myapp.model.ProgressStatus;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewStatus;
import com.lawencon.myapp.model.User;

public class ReviewDaoImpl implements ReviewDao{
	
	private final Connection conn;

	public ReviewDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	@Override
	public Review insert(Review review) throws SQLException {
		final String sql = "INSERT INTO t_review (candidate_id ,reviewer_id ,created_by,created_at,is_active,ver)VALUES(?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, review.getCandidate().getId());
		ps.setLong(2, review.getReviewer().getId());
		ps.setLong(3,  review.getCreatedBy());
		ps.setTimestamp(4, Timestamp.valueOf( review.getCreatedAt()));
		ps.setBoolean(5,  review.getIsActive());
		ps.setInt(6,  review.getVer());
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			review.setId(rs.getLong("id"));
		}
		
		return review;
	}

	@Override
	public Review update(Review review) throws SQLException {
		final String sql = "UPDATE t_review SET "
				+ "review_status_id = ? , "
				+ "progress_status_id = ? , "
				+ "updated_at= ? ,"
				+ "updated_by = ?, "
				+ "ver = ver + 1 "
				+ "WHERE candidate_id = ? "
				+ "RETURNING * ";
		final PreparedStatement ps = conn.prepareStatement(sql);
		
		if(review.getReviewStatus()!=null) {
			ps.setLong(1, review.getReviewStatus().getId());			
		}
		else {
			ps.setNull(1, Types.BIGINT);
		}
		
		ps.setLong(2, review.getProgressStatus().getId());
		ps.setTimestamp(3, Timestamp.valueOf(review.getUpdatedAt()));
		ps.setLong(4, review.getUpdatedBy());

		ps.setLong(5, review.getCandidate().getId());
		
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			review.setId(rs.getLong("id"));
			review.setVer(rs.getInt("ver"));
		}
		
		return review;
	}

	@Override
	public List<Review> getAll() throws SQLException {
		final String sql = "SELECT * FROM t_review INNER JOIN t_review_status trs ON trs.id  = t_review.review_status_id INNER JOIN t_progress_status tps ON tps.id  = t_review.progress_status_id";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final List<Review> reviews= new ArrayList<>();
		final ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			final Review newReview = new Review();
			newReview.setId(rs.getLong("id"));
			
			//Add Review Status
			final ReviewStatus reviewStatus = new ReviewStatus();
			reviewStatus.setId(rs.getLong("review_status_id"));
			reviewStatus.setStatusCode(rs.getString("status_code"));
			newReview.setReviewStatus(reviewStatus);
			
			//Add Progress Status
			final ProgressStatus progressStatus = new ProgressStatus();
			progressStatus.setId(rs.getLong("progress_status_id"));
			progressStatus.setStatusCode(rs.getString("status_code"));
			newReview.setProgressStatus(progressStatus);
			
			//Add Candidate
			final User candidate = new User();
			candidate.setId(rs.getLong("candidate_id"));	
			newReview.setCandidate(candidate);
			
			//Add Reviewer
			final User reviewer = new User();
			reviewer.setId(rs.getLong("reviewer_id"));	
			newReview.setReviewer(reviewer);
			
			//Add BaseModel
			newReview.setCreatedBy(rs.getLong("created_by"));
			newReview.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			newReview.setIsActive(rs.getBoolean("is_active"));
			newReview.setVer(rs.getInt("ver"));
			
			reviews.add(newReview);
		}
		
		return reviews;
	}

}
