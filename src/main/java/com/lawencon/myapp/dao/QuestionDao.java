package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;


import com.lawencon.myapp.model.Question;

public interface QuestionDao {

	List<Question> getByType(String typeCode) throws SQLException;
	Question getByCandidateQuestion(Long questionId)throws SQLException;
	Question insert(Question question)throws SQLException;
}
