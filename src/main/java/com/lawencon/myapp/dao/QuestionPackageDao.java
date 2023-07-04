package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.QuestionPackage;

public interface QuestionPackageDao {

	QuestionPackage insert (QuestionPackage questionPackage) throws SQLException;
	List<QuestionPackage> getAll()throws SQLException;
}
