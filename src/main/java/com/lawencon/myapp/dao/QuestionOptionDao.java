package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.QuestionOption;

public interface QuestionOptionDao {

	List<QuestionOption> getByQuestionId(Long questionId) throws SQLException;
	QuestionOption insert (QuestionOption questionOption)throws SQLException;
}
