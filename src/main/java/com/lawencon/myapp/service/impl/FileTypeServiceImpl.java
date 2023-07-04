package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.GetAllFileTypeDao;
import com.lawencon.myapp.model.FileType;
import com.lawencon.myapp.service.FileTypeService;

public class FileTypeServiceImpl implements FileTypeService{

	private final GetAllFileTypeDao fileTypeDao;

	private final Connection conn ;
	
	public FileTypeServiceImpl(GetAllFileTypeDao fileTypeDao, DataSource dataSource) throws SQLException {
		this.fileTypeDao = fileTypeDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}
	
	@Override
	public List<FileType> getAllFileTypes() throws SQLException {
		final List<FileType> fileTypes = fileTypeDao.getAll();
		return fileTypes;
	}

}
