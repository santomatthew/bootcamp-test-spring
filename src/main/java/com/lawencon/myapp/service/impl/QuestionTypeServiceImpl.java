package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.QuestionTypeDao;
import com.lawencon.myapp.model.QuestionType;
import com.lawencon.myapp.service.QuestionTypeService;

public class QuestionTypeServiceImpl implements QuestionTypeService{

	private final QuestionTypeDao questionTypeDao;
	private final Connection conn;
	
	public QuestionTypeServiceImpl(QuestionTypeDao questionTypeDao,DataSource dataSource) throws SQLException {
		this.questionTypeDao = questionTypeDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}
	
	
	@Override
	public List<QuestionType> getQuestionTypes() throws SQLException {
		List<QuestionType> questionTypes= null;
		
		try {
			 questionTypes = questionTypeDao.getAll();
			 conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return questionTypes;

	}

}
