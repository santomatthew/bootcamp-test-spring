package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.FileType;

public interface GetAllFileTypeDao {

	List<FileType> getAll() throws SQLException;
	
}
