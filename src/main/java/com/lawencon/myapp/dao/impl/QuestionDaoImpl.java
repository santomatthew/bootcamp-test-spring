package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.QuestionDao;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.QuestionPackage;
import com.lawencon.myapp.model.QuestionTopic;
import com.lawencon.myapp.model.QuestionType;
import com.lawencon.myapp.model.User;

public class QuestionDaoImpl implements QuestionDao {

	private final Connection conn;

	public QuestionDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	@Override
	public List<Question> getByType(String typeCode) throws SQLException {
		final String sql = "SELECT * FROM t_question INNER JOIN t_question_type ON t_question_type.id = t_question.question_type_id WHERE t_question_type.type_code = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, typeCode);
		final ResultSet rs =ps.executeQuery();
		final List<Question> questions = new ArrayList<Question>();
		
		while(rs.next()) {
			final Question question = new Question();
			question.setId(rs.getLong("id"));
			
			final User reviewer = new User();
			reviewer.setId(rs.getLong("reviewer_id"));
			question.setReviewer(reviewer);
			
			question.setQuestion(rs.getString("question"));
			question.setQuestionCode(rs.getString("question_code"));
			
			final QuestionType questionType= new QuestionType();
			questionType.setId(rs.getLong("question_type_id"));
			questionType.setTypeCode(rs.getString("type_code"));
			question.setQuestionType(questionType);
			
			final QuestionTopic questionTopic = new QuestionTopic();
			questionTopic.setId(rs.getLong("question_topic_id"));
			question.setQuestionTopic(questionTopic);
			
			final QuestionPackage questionPackage= new QuestionPackage();
			questionPackage.setId(rs.getLong("question_package_id"));
			question.setQuestionTopic(questionTopic);
			
			questions.add(question);
		}
		return questions;
	}

	@Override
	public Question getByCandidateQuestion(Long questionId) throws SQLException {
		final Question question = new Question();
		final String sql = "SELECT * "
				+ "FROM t_question tq INNER JOIN t_question_type tqt ON tq.question_type_id =tqt.id "
				+ "INNER JOIN t_candidate_to_question "
				+ "ON t_candidate_to_question.question_id = tq.id WHERE tq.id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, questionId);
		final ResultSet rs= ps.executeQuery();
		
		if(rs.next()) {
			question.setId(rs.getLong("id"));
			
			final User reviewer = new User();
			reviewer.setId(rs.getLong("reviewer_id"));
			question.setReviewer(reviewer);
			
			question.setQuestion(rs.getString("question"));
			question.setQuestionCode(rs.getString("question_code"));

			final QuestionType questionType= new QuestionType();
			questionType.setId(rs.getLong("question_type_id"));
			questionType.setQuestionType(rs.getString("type_code"));
			question.setQuestionType(questionType);
			
			final QuestionTopic questionTopic = new QuestionTopic();
			questionTopic.setId(rs.getLong("question_topic_id"));
			question.setQuestionTopic(questionTopic);
			
			final QuestionPackage questionPackage= new QuestionPackage();
			questionPackage.setId(rs.getLong("question_package_id"));
			question.setQuestionTopic(questionTopic);
		}
		
		return question;
	}

	@Override
	public Question insert(Question question) throws SQLException {
		final String sql = "INSERT INTO t_question(reviewer_id,question,question_code,question_type_id,question_topic_id,question_package_id,created_by,created_at,is_active,ver) VALUES (?,?,?,?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, question.getReviewer().getId());
		ps.setString(2, question.getQuestion());
		ps.setString(3, question.getQuestionCode());
		ps.setLong(4, question.getQuestionType().getId());
		ps.setLong(5, question.getQuestionTopic().getId());
		ps.setLong(6, question.getQuestionPackage().getId());
		ps.setLong(7, question.getCreatedBy());
		ps.setTimestamp(8, Timestamp.valueOf(question.getCreatedAt()));
		ps.setBoolean(9, question.getIsActive());
		ps.setInt(10, question.getVer());
		
		final ResultSet rs= ps.executeQuery();
		
		if(rs.next()) {
			question.setId(rs.getLong("id"));
		}
		
		return question;
		
	}
	

}
