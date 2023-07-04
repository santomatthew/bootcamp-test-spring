package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.FileType;

public interface FileTypeService {

	List<FileType> getAllFileTypes() throws SQLException;
	
}
