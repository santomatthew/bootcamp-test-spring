package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.CandidateToQuestionDao;
import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.User;

public class CandidateToQuestionDaoImpl implements CandidateToQuestionDao{

	private final Connection conn;
	
	
	public CandidateToQuestionDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}

	@Override
	public CandidateToQuestion insert(CandidateToQuestion candidateToQuestion) throws SQLException {
		final String sql = "INSERT INTO t_candidate_to_question (question_id,candidate_id,hr_id,candidate_assign_id,created_by,created_at,is_active,ver)VALUES(?,?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, candidateToQuestion.getQuestion().getId());
		ps.setLong(2, candidateToQuestion.getCandidate().getId());
		ps.setLong(3, candidateToQuestion.getHr().getId());
		ps.setLong(4, candidateToQuestion.getCandidateAssign().getId());
		ps.setLong(5, candidateToQuestion.getCreatedBy());
		ps.setTimestamp(6, Timestamp.valueOf(candidateToQuestion.getCreatedAt()));
		ps.setBoolean(7, candidateToQuestion.getIsActive());
		ps.setInt(8, candidateToQuestion.getVer());
		
		final ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			candidateToQuestion.setId(rs.getLong("id"));
		}
		
		return candidateToQuestion;
	}

	@Override
	public List<CandidateToQuestion> candidateToQuestions(Long candidateId) throws SQLException {
		final String sql = "SELECT * FROM t_candidate_to_question WHERE candidate_id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, candidateId);
		final List<CandidateToQuestion> candidateToQuestions = new ArrayList<>();
		final ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			final CandidateToQuestion newCandidateToQuestion = new CandidateToQuestion();
			final Question question = new Question();
			question.setId(rs.getLong("question_id"));
			newCandidateToQuestion.setQuestion(question);
			
			final User candidate = new User();
			candidate.setId(candidateId);
			newCandidateToQuestion.setCandidate(candidate);
			
			final User hr = new User();
			hr.setId(rs.getLong("hr_id"));
			newCandidateToQuestion.setHr(hr);
			
			final CandidateAssign candidateAssign = new CandidateAssign();
			candidateAssign.setId(rs.getLong("candidate_assign_id"));
			newCandidateToQuestion.setCandidateAssign(candidateAssign);
			
			
			newCandidateToQuestion.setCreatedBy(rs.getLong("created_by"));
			newCandidateToQuestion.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			newCandidateToQuestion.setIsActive(rs.getBoolean("is_active"));
			newCandidateToQuestion.setVer(rs.getInt("ver"));
			
			candidateToQuestions.add(newCandidateToQuestion);
		}
		
		return candidateToQuestions;
	}

	@Override
	public CandidateToQuestion getByQuestionId(Long questionId) throws SQLException {
		final String sql = "SELECT * FROM t_candidate_to_question WHERE question_id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, questionId);
		final CandidateToQuestion candidateToQuestion = new CandidateToQuestion();
		
		final ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			
			final Question question = new Question();
			question.setId(rs.getLong("question_id"));
			candidateToQuestion.setQuestion(question);
			
			final User candidate = new User();
			candidate.setId(rs.getLong("candidate_id"));
			candidateToQuestion.setCandidate(candidate);
			
			final User hr = new User();
			hr.setId(rs.getLong("hr_id"));
			candidateToQuestion.setHr(hr);
			
			final CandidateAssign candidateAssign = new CandidateAssign();
			candidateAssign.setId(rs.getLong("candidate_assign_id"));
			candidateToQuestion.setCandidateAssign(candidateAssign);
			
			
			candidateToQuestion.setCreatedBy(rs.getLong("created_by"));
			candidateToQuestion.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			candidateToQuestion.setIsActive(rs.getBoolean("is_active"));
			candidateToQuestion.setVer(rs.getInt("ver"));
		}
		
		return candidateToQuestion;
		
	}


}
