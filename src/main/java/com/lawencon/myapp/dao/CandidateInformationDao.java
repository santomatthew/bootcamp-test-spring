package com.lawencon.myapp.dao;

import java.sql.SQLException;

import com.lawencon.myapp.model.CandidateInformation;

public interface CandidateInformationDao {

	CandidateInformation insert (CandidateInformation candidateInformation) throws SQLException;
	
}
