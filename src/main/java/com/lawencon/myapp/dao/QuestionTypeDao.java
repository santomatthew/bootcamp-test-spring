package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.QuestionType;

public interface QuestionTypeDao {

	List<QuestionType> getAll() throws SQLException;
	
}
