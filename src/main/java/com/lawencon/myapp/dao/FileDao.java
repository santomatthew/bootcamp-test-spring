package com.lawencon.myapp.dao;


import java.sql.SQLException;

import com.lawencon.myapp.model.File;

public interface FileDao {

	File getById(Long id) throws SQLException;
	File insert(File file) throws SQLException;
	Boolean deleteById(Long id) throws SQLException;
}
