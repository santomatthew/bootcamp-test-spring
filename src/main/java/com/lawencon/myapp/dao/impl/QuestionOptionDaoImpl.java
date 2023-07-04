package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.QuestionOptionDao;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.QuestionOption;

public class QuestionOptionDaoImpl implements QuestionOptionDao{

	private final Connection conn;

	public QuestionOptionDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}

	@Override
	public List<QuestionOption> getByQuestionId(Long questionId) throws SQLException {
		final String sql = "SELECT * FROM t_question_option WHERE question_id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, questionId);
		
		final ResultSet rs = ps.executeQuery();
		final List<QuestionOption> questionOptions = new ArrayList<>();
		while(rs.next()) {
			final QuestionOption questionOption = new QuestionOption();
			questionOption.setId(rs.getLong("id"));
			
			final Question question = new Question();
			question.setId(rs.getLong("question_id"));
			
			questionOption.setQuestion(question);
			questionOption.setOptLabel(rs.getString("opt_label"));
			questionOption.setCorrect(rs.getBoolean("correct"));
			questionOption.setCreatedBy(rs.getLong("created_by"));
			questionOption.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			questionOption.setIsActive(rs.getBoolean("is_active"));
			questionOption.setVer(rs.getInt("ver"));
			
			questionOptions.add(questionOption);
			
		}
		
		return questionOptions;
	}

	@Override
	public QuestionOption insert(QuestionOption questionOption) throws SQLException {
		final String sql = "INSERT INTO t_question_option(question_id,opt_label,correct,created_by,created_at,is_active,ver) VALUES (?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, questionOption.getQuestion().getId());
		ps.setString(2, questionOption.getOptLabel());
		ps.setBoolean(3, questionOption.getCorrect());
		ps.setLong(4, questionOption.getCreatedBy());
		ps.setTimestamp(5, Timestamp.valueOf(questionOption.getCreatedAt()));
		ps.setBoolean(6, questionOption.getIsActive());
		ps.setInt(7, questionOption.getVer());
		
		final ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			questionOption.setId(rs.getLong("id"));
		}
		
		return questionOption;
	}

}
