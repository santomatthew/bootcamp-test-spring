package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.FileDao;
import com.lawencon.myapp.model.File;
import com.lawencon.myapp.service.FileService;

public class FileServiceImpl implements FileService{
	private Connection conn;
	private final FileDao fileDao;
	
	public FileServiceImpl(FileDao fileDao, DataSource dataSource) throws SQLException {
		this.fileDao = fileDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}
	

	
	@Override
	public File getById(Long id) throws SQLException {
		final File file = fileDao.getById(id);
		return file;
	}

	@Override
	public File insert(File file) throws SQLException {
		File newFile =null;
		try {
			newFile = fileDao.insert(file);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return newFile;
	}

	@Override
	public Boolean deleteById(Long id) throws SQLException {
		final Boolean fileBool = fileDao.deleteById(id);
		return fileBool;
	}

}
