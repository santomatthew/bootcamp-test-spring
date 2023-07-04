package com.lawencon.myapp.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.QuestionAnswer;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;
import com.lawencon.myapp.model.ReviewStatus;
import com.lawencon.myapp.model.User;

public interface ReviewService {

	List<Review> getOwnedReview(Long reviewerId, String statusCode) throws SQLException;
	List<Review>  getReviewByStatus(Long reviewerId, String statusCode) throws SQLException;
	
	User getCandidate (Long candidateId) throws SQLException;
	
	String getReviewStatusName(Long statusId) throws SQLException;
	String getProgressStatusName(Long statusId) throws SQLException;
	
	List<QuestionAnswer> getQuestionAnswer(CandidateAssign candidateAssign) throws SQLException;
	List<CandidateToQuestion> getCandidateQuestion (Long candidateId) throws SQLException;
	Question getQuestionByCandidateToQuestion (Long questionId) throws SQLException;
	
	Boolean updateReviewDetail(ReviewDetail reviewDetail)  throws SQLException; 
	List<ReviewDetail> getReviewDetails(ReviewDetail reviewDetail) throws SQLException; 
	List<ReviewStatus> getAllReviewStatusExceptNeedsReview() throws SQLException;
	
	Boolean updateReview(Review review) throws SQLException;
	
	
	
}
