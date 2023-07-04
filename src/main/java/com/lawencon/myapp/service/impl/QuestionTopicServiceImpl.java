package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.QuestionTopicDao;
import com.lawencon.myapp.model.QuestionTopic;
import com.lawencon.myapp.service.QuestionTopicService;

public class QuestionTopicServiceImpl implements QuestionTopicService{

	private final QuestionTopicDao questionTopicDao;
	private final Connection conn;
	
	public QuestionTopicServiceImpl( QuestionTopicDao questionTopicDao, DataSource dataSource) throws SQLException {
		this.questionTopicDao = questionTopicDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}
	
	@Override
	public QuestionTopic createQuestionTopic(QuestionTopic questionTopic) throws SQLException {
		QuestionTopic newQuestionTopic = null;
		
		try {
			newQuestionTopic = questionTopicDao.insert(questionTopic);
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return newQuestionTopic;
	}

	@Override
	public List<QuestionTopic> getAll() throws SQLException {
		final List<QuestionTopic> questionTopics = questionTopicDao.getAll();
		return questionTopics;
	}

}
