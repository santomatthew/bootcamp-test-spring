package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.CandidateToQuestion;

public interface CandidateToQuestionService {

	CandidateToQuestion assignCandidateToQuestion(Long questionId, Long candidateId, Long hrId, Long candidateAssignId) throws SQLException;
	List<CandidateToQuestion> getCandidateToQuestionByCandidateId(Long candidateId) throws SQLException;
}	
