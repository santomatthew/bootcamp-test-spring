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

import com.lawencon.myapp.dao.QuestionAnswerDao;
import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.QuestionAnswer;

public class QuestionAnswerDaoImpl implements QuestionAnswerDao{

	private final Connection conn;

	public QuestionAnswerDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}

	
	@Override
	public QuestionAnswer insert(QuestionAnswer questionAnswer) throws SQLException {
		final String sql = "INSERT INTO t_question_answer(candidate_assign_id,question_id,essay_answer,question_option_id,created_by,created_at,is_active,ver) VALUES (?,?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1,questionAnswer.getCandidateAssign().getId());
		ps.setLong(2, questionAnswer.getQuestion().getId());
		
		if(questionAnswer.getEssayAnswer()!=null) {
			ps.setString(3, questionAnswer.getEssayAnswer());			
		}
		else {
			ps.setNull(3, Types.BIGINT);
		}
		
		if(questionAnswer.getQuestionOption() != null) {
			ps.setLong(4, questionAnswer.getQuestionOption().getId());
		}
		else {
			ps.setNull(4, Types.BIGINT);
		}
		
		ps.setLong(5, questionAnswer.getCreatedBy());
		ps.setTimestamp(6, Timestamp.valueOf(questionAnswer.getCreatedAt()));
		ps.setBoolean(7, questionAnswer.getIsActive());
		ps.setInt(8, questionAnswer.getVer());
		
		final ResultSet rs = ps.executeQuery();
		
			if(rs.next()) {
				questionAnswer.setId(rs.getLong("id"));
			}
		
		return questionAnswer;
	}

	@Override
	public List<QuestionAnswer> getByCandidateAssignId(CandidateAssign candidateAssign) throws SQLException {
		final String sql = "SELECT * FROM t_question_answer WHERE candidate_assign_id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, candidateAssign.getId());
		
		final List<QuestionAnswer> questionAnswers = new ArrayList<>();
		final ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			final QuestionAnswer questAns = new QuestionAnswer();
			
			//Candidate assign
			final CandidateAssign newCandidateAssign = new CandidateAssign();
			newCandidateAssign.setId(rs.getLong("candidate_assign_id"));
			questAns.setCandidateAssign(newCandidateAssign);
			
			//Question
			final Question question = new Question();
			question.setId(rs.getLong("question_id"));
			questAns.setQuestion(question);
			
			//Essay Answer
			questAns.setEssayAnswer(rs.getString("essay_answer"));
			questAns.setCreatedBy(rs.getLong("created_by"));
			questAns.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			questAns.setIsActive(rs.getBoolean("is_active"));
			questAns.setVer(rs.getInt("ver"));
			
			questionAnswers.add(questAns);
		}
		
		return questionAnswers;
		
	}

}
