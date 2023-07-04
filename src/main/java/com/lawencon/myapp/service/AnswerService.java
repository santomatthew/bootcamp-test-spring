package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.CandidateInformation;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.ProgressStatus;
import com.lawencon.myapp.model.QuestionAnswer;
import com.lawencon.myapp.model.QuestionOption;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;
import com.lawencon.myapp.model.ReviewStatus;
import com.lawencon.myapp.model.User;

public interface AnswerService {

	Boolean insertFileAndAnswer(List<CandidateInformation> candidateInformations , List<QuestionAnswer> answers) throws SQLException;
	List<QuestionOption> getOptions(Long questionId) throws SQLException;
	Long candidateAssignId (Long questionId, Long candidateId) throws SQLException;
	
	Review updateCandidateReview (Review review) throws SQLException;
	List<ProgressStatus> getProgressStatus() throws SQLException;
	List<ReviewStatus> getReviewStatus() throws SQLException;
	
	Boolean updateReviewDetail(ReviewDetail reviewDetail)  throws SQLException; 
	Float getGrade(List<Boolean> optionAnswers) throws SQLException; 
	
	CandidateToQuestion getCandidateToQuestionByQuestionId(Long questionId)  throws SQLException;
	
	void setIsActive(User user )throws SQLException; 
	
}