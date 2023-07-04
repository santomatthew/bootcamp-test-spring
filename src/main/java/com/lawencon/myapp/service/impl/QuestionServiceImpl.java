package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.constant.QuestionTypes;
import com.lawencon.myapp.dao.QuestionDao;
import com.lawencon.myapp.dao.QuestionOptionDao;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.QuestionOption;
import com.lawencon.myapp.service.QuestionService;

public class QuestionServiceImpl implements QuestionService{

	private final QuestionDao questionDao;
	private final QuestionOptionDao questionOptionDao;
	private final Connection conn ;
	
	
	public QuestionServiceImpl(QuestionDao questionDao,QuestionOptionDao questionOptionDao,DataSource dataSource) throws SQLException  {
		this.questionDao = questionDao;
		this.questionOptionDao = questionOptionDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}
	
	
	@Override
	public List<Question> getByCode(String typeCode)  {
		List<Question> questions = null;
		try {
			questions = questionDao.getByType(typeCode);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return questions;
	}

	@Override
	public List<Question> filterByCode(List<Question> chosenQuestions, String typeCode) throws SQLException {
		final List<Question> questionByTypeCode = new ArrayList<>();
		for(int i=0;i<chosenQuestions.size();i++) {
			if(chosenQuestions.get(i).getQuestionType().getTypeCode().equals(typeCode)) {
				questionByTypeCode.add(chosenQuestions.get(i));
			}
		}
		
		return questionByTypeCode;
	}


	@Override
	public Question getQuestionByCandidateToQuestion(Long questionId) throws SQLException {
		final Question question = questionDao.getByCandidateQuestion(questionId);
		return question;
		
	}


	@Override
	public Boolean createQuestion(List<Question> questions) throws SQLException {
		Boolean result = false;
		try {
			
			for(int i=0;i<questions.size();i++) {
				final Question question = questionDao.insert(questions.get(i));
				
				if(question.getQuestionType().getTypeCode().equals(QuestionTypes.OPTION.questionTypeCode)) {
					final List<QuestionOption>questionOptions = questions.get(i).getQuestionOption();
					
					for(int j=0;j<questionOptions.size();j++) {
						
						final QuestionOption questionOption = questionOptions.get(j);
						questionOption.setQuestion(question);
						questionOptionDao.insert(questionOption);
					}
				}
			}
			
			result = true;
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}


	

}
