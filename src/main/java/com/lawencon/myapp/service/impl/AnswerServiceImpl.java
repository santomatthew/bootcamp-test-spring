package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.CandidateAssignDao;
import com.lawencon.myapp.dao.CandidateInformationDao;
import com.lawencon.myapp.dao.CandidateToQuestionDao;
import com.lawencon.myapp.dao.FileDao;
import com.lawencon.myapp.dao.ProgressStatusDao;
import com.lawencon.myapp.dao.QuestionAnswerDao;
import com.lawencon.myapp.dao.QuestionOptionDao;
import com.lawencon.myapp.dao.ReviewDao;
import com.lawencon.myapp.dao.ReviewDetailDao;
import com.lawencon.myapp.dao.ReviewStatusDao;
import com.lawencon.myapp.dao.UserDao;
import com.lawencon.myapp.model.CandidateInformation;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.File;
import com.lawencon.myapp.model.ProgressStatus;
import com.lawencon.myapp.model.QuestionAnswer;
import com.lawencon.myapp.model.QuestionOption;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;
import com.lawencon.myapp.model.ReviewStatus;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.AnswerService;

public class AnswerServiceImpl implements AnswerService {

	private final QuestionAnswerDao questionAnswerDao;
	private final CandidateInformationDao candidateInformationDao;
	private final QuestionOptionDao questionOptionDao;
	private final CandidateAssignDao candidateAssignDao;
	private final FileDao fileDao;
	private final ReviewDao reviewDao;
	private final ProgressStatusDao progressStatusDao;
	private final ReviewStatusDao reviewStatusDao;
	private final ReviewDetailDao reviewDetailDao;
	private final CandidateToQuestionDao candidateToQuestionDao;
	private final UserDao userDao;

	private final Connection conn;

	public AnswerServiceImpl(QuestionAnswerDao questionAnswerDao,
			CandidateInformationDao candidateInformationDao, QuestionOptionDao questionOptionDao,
			CandidateAssignDao candidateAssignDao, FileDao fileDao, ReviewDao reviewDao,
			ProgressStatusDao progressStatusDao, ReviewStatusDao reviewStatusDao,
			ReviewDetailDao reviewDetailDao, CandidateToQuestionDao candidateToQuestionDao,
			UserDao userDao,DataSource dataSource) throws SQLException {
		
		this.questionAnswerDao = questionAnswerDao;
		this.candidateInformationDao = candidateInformationDao;
		this.questionOptionDao = questionOptionDao;
		this.candidateAssignDao = candidateAssignDao;
		this.fileDao = fileDao;
		this.reviewDao = reviewDao;
		this.progressStatusDao =progressStatusDao;
		this.reviewStatusDao= reviewStatusDao;
		this.reviewDetailDao = reviewDetailDao;
		this.candidateToQuestionDao = candidateToQuestionDao;
		this.userDao = userDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);

	}

	@Override
	public Boolean insertFileAndAnswer(List<CandidateInformation> candidateInformations, List<QuestionAnswer> answers)
			throws SQLException {
		Boolean result = false;
		try {

			for (int i = 0; i < candidateInformations.size(); i++) {
				final File file = candidateInformations.get(i).getFile();
				fileDao.insert(file);
				candidateInformationDao.insert(candidateInformations.get(i));
			}
			// Question Answer
			for (int i = 0; i < answers.size(); i++) {
				questionAnswerDao.insert(answers.get(i));
			}

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
	public List<QuestionOption> getOptions(Long questionId) throws SQLException {
		final List<QuestionOption> options = questionOptionDao.getByQuestionId(questionId);
		return options;
	}

	@Override
	public Long candidateAssignId(Long questionId, Long candidateId) throws SQLException {
		final Long candidateAssignId = candidateAssignDao.candidateAssignId(questionId, candidateId);
		return candidateAssignId;
	}

	@Override
	public Review updateCandidateReview(Review review) throws SQLException {
		Review reviewUpdt = null;

		try {
			reviewUpdt = reviewDao.update(review);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return reviewUpdt;

	}

	@Override
	public List<ProgressStatus> getProgressStatus() throws SQLException {
		final List<ProgressStatus> progressStatus = progressStatusDao.getAll();
		return progressStatus;
	}

	@Override
	public List<ReviewStatus> getReviewStatus() throws SQLException {
		final List<ReviewStatus> reviewStatus = reviewStatusDao.getAll();
		return reviewStatus;
	}

	@Override
	public Boolean updateReviewDetail(ReviewDetail reviewDetail) throws SQLException {
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
	public Float getGrade(List<Boolean> optionAnswers) throws SQLException {
		Float gradeTotal = (float) 0;
		for (int i = 0; i < optionAnswers.size(); i++) {
			if (optionAnswers.get(i)) {
				gradeTotal += 100;
			}
		}

		gradeTotal = gradeTotal / optionAnswers.size();
		return gradeTotal;
	}

	@Override
	public CandidateToQuestion getCandidateToQuestionByQuestionId(Long questionId) throws SQLException {
		final CandidateToQuestion candidateToQuestion = candidateToQuestionDao.getByQuestionId(questionId);
		return candidateToQuestion;
	}

	@Override
	public void setIsActive(User user) throws SQLException {
		try {
			user = userDao.update(user);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

}
