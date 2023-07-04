package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;

public interface CandidateService {

	Boolean candidateService(List<CandidateAssign> candidateAssigns, Review review, List<ReviewDetail> reviewDetail,
			List<CandidateToQuestion> candidateToQuestion) throws SQLException;

}
