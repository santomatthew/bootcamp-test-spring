package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.QuestionType;

public interface QuestionTypeService {

	List<QuestionType> getQuestionTypes() throws SQLException;
	
}
