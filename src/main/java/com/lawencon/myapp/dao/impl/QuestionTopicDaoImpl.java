package com.lawencon.myapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.QuestionTopicDao;
import com.lawencon.myapp.model.QuestionTopic;

public class QuestionTopicDaoImpl implements QuestionTopicDao{

	private final Connection conn;

	public QuestionTopicDaoImpl (DataSource dataSource) throws SQLException {
		this.conn = dataSource.getConnection();
	}
	
	
	@Override
	public QuestionTopic insert(QuestionTopic questionTopic) throws SQLException {
		final String sql = "INSERT INTO t_question_topic (reviewer_id,topic_code,question_topic,created_by,created_at,is_active,ver)VALUES(?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, questionTopic.getReviewer().getId());
		ps.setString(2, questionTopic.getTopicCode());
		ps.setString(3, questionTopic.getQuestionTopic());
		ps.setLong(4,questionTopic.getCreatedBy());
		ps.setTimestamp(5, Timestamp.valueOf(questionTopic.getCreatedAt()));
		ps.setBoolean(6, questionTopic.getIsActive());
		ps.setInt(7, questionTopic.getVer());
	
		final ResultSet rs= ps.executeQuery();
		if(rs.next()) {
			questionTopic.setId(rs.getLong("id"));
		}
	
		return questionTopic;
	}


	@Override
	public List<QuestionTopic> getAll() throws SQLException {
		final String sql = "SELECT * FROM t_question_topic";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();
		final List<QuestionTopic> questionTopics= new ArrayList<>();
		
		while(rs.next()) {
			final QuestionTopic questionTopic = new QuestionTopic();
			questionTopic.setId(rs.getLong("id"));
			questionTopic.setTopicCode(rs.getString("topic_code"));
			questionTopic.setQuestionTopic(rs.getString("question_topic"));
			questionTopics.add(questionTopic);
		}
		return questionTopics;
	}

}
