package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.QuestionPackageDao;
import com.lawencon.myapp.model.QuestionPackage;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.QuestionPackageService;

public class QuestionPackageServiceImpl implements QuestionPackageService{

	private final Connection conn;
	private QuestionPackageDao questionPackageDao;
	
	
	public QuestionPackageServiceImpl(QuestionPackageDao questionPackageDao, DataSource dataSource) throws SQLException {
		this.questionPackageDao = questionPackageDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}
	
	@Override
	public QuestionPackage createNew(String name,String code, User hr) throws SQLException {
		QuestionPackage newQuestionPackage = new QuestionPackage();
		
		try {
			final LocalDateTime timeNow = LocalDateTime.now();
			newQuestionPackage.setPackageName(name);
			newQuestionPackage.setPackageCode(code);
			newQuestionPackage.setCreatedBy(hr.getId());
			newQuestionPackage.setHr(hr);
			newQuestionPackage.setCreatedAt(timeNow);
			newQuestionPackage.setIsActive(true);
			newQuestionPackage.setVer(1);
			newQuestionPackage = questionPackageDao.insert(newQuestionPackage);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return newQuestionPackage;
	}

	@Override
	public List<QuestionPackage> getAll() throws SQLException {
		final List<QuestionPackage> questionPackages = questionPackageDao.getAll();
		return questionPackages;
	}

}
