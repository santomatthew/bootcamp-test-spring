package com.lawencon.myapp.dao;


import java.sql.SQLException;

import com.lawencon.myapp.model.CandidateAssign;

public interface CandidateAssignDao {

	CandidateAssign insert(CandidateAssign candidateAssign) throws SQLException;
	Long candidateAssignId (Long questionId, Long candidateId) throws SQLException;
}
