package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.CandidateToQuestionDao;
import com.lawencon.myapp.dao.ProgressStatusDao;
import com.lawencon.myapp.dao.QuestionAnswerDao;
import com.lawencon.myapp.dao.QuestionDao;
import com.lawencon.myapp.dao.ReviewDao;
import com.lawencon.myapp.dao.ReviewDetailDao;
import com.lawencon.myapp.dao.ReviewStatusDao;
import com.lawencon.myapp.dao.UserDao;
import com.lawencon.myapp.dao.impl.CandidateToQuestionDaoImpl;
import com.lawencon.myapp.dao.impl.ProgressStatusDaoImpl;
import com.lawencon.myapp.dao.impl.QuestionAnswerDaoImpl;
import com.lawencon.myapp.dao.impl.QuestionDaoImpl;
import com.lawencon.myapp.dao.impl.ReviewDaoImpl;
import com.lawencon.myapp.dao.impl.ReviewDetailDaoImpl;
import com.lawencon.myapp.dao.impl.ReviewStatusDaoImpl;
import com.lawencon.myapp.dao.impl.UserDaoImpl;
import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.ProgressStatus;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.QuestionAnswer;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;
import com.lawencon.myapp.model.ReviewStatus;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.ReviewService;

public class ReviewServiceImpl implements ReviewService {

	private final Connection conn ;
	private final ReviewDao reviewDao;
	private final UserDao userDao;
	private final ReviewStatusDao reviewStatusDao;
	private final ProgressStatusDao progressStatusDao;
	private final QuestionAnswerDao questionAnswerDao;
	private final CandidateToQuestionDao candidateToQuestionDao;
	private final QuestionDao questionDao;
	private final ReviewDetailDao reviewDetailDao;

	public ReviewServiceImpl(ReviewDaoImpl reviewDao, UserDaoImpl userDao, ReviewStatusDaoImpl reviewStatusDao,
			ProgressStatusDaoImpl progressStatusDao, QuestionAnswerDaoImpl questionAnswerDao,
			CandidateToQuestionDaoImpl candidateToQuestionDao, QuestionDaoImpl questionDao,
			ReviewDetailDaoImpl reviewDetailDao, DataSource dataSource) throws SQLException  {
		this.reviewDao = reviewDao;
		this.userDao = userDao;
		this.reviewStatusDao = reviewStatusDao;
		this.progressStatusDao = progressStatusDao;
		this.questionAnswerDao = questionAnswerDao;
		this.candidateToQuestionDao = candidateToQuestionDao;
		this.questionDao = questionDao;
		this.reviewDetailDao = reviewDetailDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}
	

	@Override
	public List<Review> getOwnedReview(Long reviewerId, String statusCode) throws SQLException  {
		final List<Review> allReviews = reviewDao.getAll();

		final List<Review> ownedReviews = new ArrayList<>();
		for (int i = 0; i < allReviews.size(); i++) {

			if (allReviews.get(i).getReviewer().getId() == reviewerId
					&& allReviews.get(i).getReviewStatus().getStatusCode().equals(statusCode)) {
				ownedReviews.add(allReviews.get(i));
			}
		}

		return ownedReviews;

	}

	@Override
	public User getCandidate(Long candidateId) throws SQLException {
		final User userData = userDao.getById(candidateId);
		return userData;
	}

	@Override
	public String getReviewStatusName(Long statusId) throws SQLException {
		final List<ReviewStatus> allReviewStatus = reviewStatusDao.getAll();
		String statusName = "";

		for (int i = 0; i < allReviewStatus.size(); i++) {
			if (allReviewStatus.get(i).getId() == statusId) {
				statusName = allReviewStatus.get(i).getStatusName();
			}
		}

		return statusName;

	}

	@Override
	public String getProgressStatusName(Long statusId) throws SQLException {
		final List<ProgressStatus> allProgressStatus = progressStatusDao.getAll();
		String statusName = "";

		for (int i = 0; i < allProgressStatus.size(); i++) {
			if (allProgressStatus.get(i).getId() == statusId) {
				statusName = allProgressStatus.get(i).getStatusName();
			}
		}

		return statusName;
	}

	@Override
	public List<Review> getReviewByStatus(Long reviewerId, String reviewStatus) throws SQLException {
		final List<Review> allReviews = reviewDao.getAll();

		final List<Review> ownedReviews = new ArrayList<>();
		for (int i = 0; i < allReviews.size(); i++) {

			if (allReviews.get(i).getReviewer().getId() == reviewerId
					&& allReviews.get(i).getReviewStatus().getStatusCode().equals(reviewStatus)) {
				ownedReviews.add(allReviews.get(i));
			}
		}

		return ownedReviews;
	}

	@Override
	public List<QuestionAnswer> getQuestionAnswer(CandidateAssign candidateAssign) throws SQLException {
		final List<QuestionAnswer> questionAnswers = questionAnswerDao.getByCandidateAssignId(candidateAssign);
		return questionAnswers;
	}

	@Override
	public List<CandidateToQuestion> getCandidateQuestion(Long candidateId) throws SQLException {
		final List<CandidateToQuestion> candidateToQuestions = candidateToQuestionDao.candidateToQuestions(candidateId);
		return candidateToQuestions;
	}

	@Override
	public Question getQuestionByCandidateToQuestion(Long questionId) throws SQLException {
		final Question question = questionDao.getByCandidateQuestion(questionId);
		return question;
	}

	@Override
	public Boolean updateReviewDetail(ReviewDetail reviewDetail)  {
		Boolean result = false;

		try {
			reviewDetailDao.update(reviewDetail);
			result = true;
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public List<ReviewDetail> getReviewDetails(ReviewDetail reviewDetail) throws SQLException {
		final List<ReviewDetail> reviewDetails = reviewDetailDao.get(reviewDetail);
		return reviewDetails;
	}

	@Override
	public List<ReviewStatus> getAllReviewStatusExceptNeedsReview() throws SQLException {
		final List<ReviewStatus> reviewStatus = reviewStatusDao.getAll();
		reviewStatus.remove(0);
		return reviewStatus;
	}

	@Override
	public Boolean updateReview(Review review)  {
		Boolean result = false;

		try {

			review = reviewDao.update(review);
			result = true;
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;
	}

}
