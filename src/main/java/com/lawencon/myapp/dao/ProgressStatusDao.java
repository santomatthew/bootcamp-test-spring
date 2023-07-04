package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.ProgressStatus;

public interface ProgressStatusDao {

	List <ProgressStatus>  getAll () throws SQLException;
	
}
