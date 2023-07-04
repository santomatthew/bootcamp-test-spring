package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.CandidateAssignDao;
import com.lawencon.myapp.model.CandidateAssign;

public class CandidateAssignDaoImpl implements CandidateAssignDao{
	
	private final Connection conn;
	
	
	public CandidateAssignDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	@Override
	public CandidateAssign insert(CandidateAssign candidateAssign) throws SQLException {
		final String sql = "INSERT INTO t_candidate_assign (candidate_id,question_type_id,start_time,end_time,created_by,created_at,is_active,ver) VALUES (?,?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, candidateAssign.getCandidate().getId());
		ps.setLong(2, candidateAssign.getQuestionType().getId());
		ps.setTimestamp(3, Timestamp.valueOf( candidateAssign.getStartTime()));
		ps.setTimestamp(4, Timestamp.valueOf( candidateAssign.getEndTime()));
		ps.setLong(5,  candidateAssign.getCreatedBy());
		ps.setTimestamp(6, Timestamp.valueOf( candidateAssign.getCreatedAt()));
		ps.setBoolean(7,  candidateAssign.getIsActive());
		ps.setInt(8,  candidateAssign.getVer());
		
		final ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			candidateAssign.setId(rs.getLong("id"));
			
		}
		return candidateAssign;
	}


	@Override
	public Long candidateAssignId(Long questionId, Long candidateId) throws SQLException {
		final String sql = "SELECT * FROM t_candidate_to_question WHERE question_id = ? AND candidate_id=?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, questionId);
		ps.setLong(2, candidateId);
		Long candidateAssignId = null;
		final ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			candidateAssignId = rs.getLong("candidate_assign_id");
		}
		
		return candidateAssignId;
		
	}

	
	
}
