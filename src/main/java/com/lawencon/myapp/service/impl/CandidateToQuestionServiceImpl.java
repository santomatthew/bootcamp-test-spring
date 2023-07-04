package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.CandidateToQuestionDao;
import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.CandidateToQuestionService;

public class CandidateToQuestionServiceImpl implements CandidateToQuestionService {

	private final CandidateToQuestionDao candidateToQuestionDao;
	private final Connection conn ;
	
	public CandidateToQuestionServiceImpl(CandidateToQuestionDao candidateToQuestionDao, DataSource dataSource) throws SQLException {
		this.candidateToQuestionDao = candidateToQuestionDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}
	
	@Override
	public CandidateToQuestion assignCandidateToQuestion(Long questionId, Long candidateId, Long hrId, Long candidateAssignId) throws SQLException {
		CandidateToQuestion candidateToQuestion = null;
		
		try 
		{
			final CandidateToQuestion newCandidateToQuestion = new CandidateToQuestion();
			
			//Question
			final Question question = new Question();
			question.setId(questionId);
			newCandidateToQuestion.setQuestion(question);
			
			//User
			final User candidate = new User();
			candidate.setId(candidateId);
			newCandidateToQuestion.setCandidate(candidate);
			
			//HR
			final User hr = new User();
			hr.setId(hrId);
			newCandidateToQuestion.setHr(hr);
			
			//Candidate_assign_id
			final CandidateAssign candidateAssign = new CandidateAssign();
			candidateAssign.setId(candidateAssignId);
			newCandidateToQuestion.setCandidateAssign(candidateAssign);
			
			//Created_by
			newCandidateToQuestion.setCreatedBy(hrId);
			//Created_at
			final LocalDateTime timeNow = LocalDateTime.now();
			newCandidateToQuestion.setCreatedAt(timeNow);
			
			//Is_active
			newCandidateToQuestion.setIsActive(true);
			//Version
			newCandidateToQuestion.setVer(1);
		
			candidateToQuestion = candidateToQuestionDao.insert(newCandidateToQuestion);
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return candidateToQuestion;
	}

	@Override
	public List<CandidateToQuestion> getCandidateToQuestionByCandidateId(Long candidateId) throws SQLException {
		final List<CandidateToQuestion> candidateToQuestions = candidateToQuestionDao.candidateToQuestions(candidateId);
		return candidateToQuestions;
	}

}
