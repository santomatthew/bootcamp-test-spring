package com.lawencon.myapp.dao;

import java.sql.SQLException;

import com.lawencon.myapp.model.Profile;

public interface ProfileDao {

	Profile insert(Profile profile)throws SQLException;
	Profile update(Profile profile)throws SQLException;
	Profile getById(Long id)throws SQLException;
	
}
