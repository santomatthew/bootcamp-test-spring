package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.Question;


public interface QuestionService {

	List<Question> getByCode(String typeCode) throws SQLException;
	List<Question> filterByCode(List<Question> chosenQuestions,String typeCode)throws SQLException;
	Question getQuestionByCandidateToQuestion(Long questionId) throws SQLException;
	Boolean createQuestion(List<Question> questions) throws SQLException;
}
