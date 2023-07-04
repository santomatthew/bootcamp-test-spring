package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.QuestionPackage;
import com.lawencon.myapp.model.User;

public interface QuestionPackageService {

	QuestionPackage createNew(String name,String code, User hr) throws SQLException;
	List<QuestionPackage> getAll() throws SQLException;
	
}
