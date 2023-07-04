package com.lawencon.myapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.QuestionAnswer;

public interface QuestionAnswerDao {

	QuestionAnswer insert (QuestionAnswer questionAnswer) throws SQLException;
	List<QuestionAnswer> getByCandidateAssignId (CandidateAssign candidateAssign) throws SQLException;
}
