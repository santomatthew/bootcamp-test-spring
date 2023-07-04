package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.CandidateToQuestion;

public interface CandidateToQuestionDao {

	CandidateToQuestion insert (CandidateToQuestion candidateToQuestion) throws SQLException;
	List<CandidateToQuestion> candidateToQuestions (Long candidateId) throws SQLException;
	CandidateToQuestion getByQuestionId (Long questionId) throws SQLException;
	
}
