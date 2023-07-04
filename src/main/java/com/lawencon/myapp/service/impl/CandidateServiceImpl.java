package com.lawencon.myapp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.lawencon.myapp.dao.CandidateAssignDao;
import com.lawencon.myapp.dao.CandidateToQuestionDao;
import com.lawencon.myapp.dao.ReviewDao;
import com.lawencon.myapp.dao.ReviewDetailDao;
import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;
import com.lawencon.myapp.service.CandidateService;

public class CandidateServiceImpl implements CandidateService {

	private final Connection conn;

	private final CandidateAssignDao candidateAssignDao;
	private final ReviewDao reviewDao;
	private final ReviewDetailDao reviewDetailDao;
	private final CandidateToQuestionDao candidateToQuestionDao;

	public CandidateServiceImpl(CandidateAssignDao candidateAssignDao, ReviewDao reviewDao,
			ReviewDetailDao reviewDetailDao, CandidateToQuestionDao candidateToQuestionDao,
			DataSource dataSource) throws SQLException {
		this.candidateAssignDao = candidateAssignDao;
		this.reviewDao = reviewDao;
		this.reviewDetailDao = reviewDetailDao;
		this.candidateToQuestionDao = candidateToQuestionDao;
		this.conn = dataSource.getConnection();
		this.conn.setAutoCommit(false);
	}


	@Override
	public Boolean candidateService(List<CandidateAssign> candidateAssigns, Review review,
			List<ReviewDetail> reviewDetail, List<CandidateToQuestion> candidateToQuestion) throws SQLException {
		Boolean result = false;

		try {
			// ===== Candidate Assign =====
			for (int i = 0; i < candidateAssigns.size(); i++) {
				final CandidateAssign candidateAssign = candidateAssignDao.insert(candidateAssigns.get(i));
				candidateAssigns.set(i, candidateAssign);
			}

			// ===== Review =====
			review = reviewDao.insert(review);

			// ===== ReviewDetail =====
			for (int i = 0; i < reviewDetail.size(); i++) {
				final ReviewDetail newReviewDetail = reviewDetailDao.insert(reviewDetail.get(i));
				reviewDetail.set(i, newReviewDetail);
			}

			// ===== CandidateToQuestion =====
			for (int i = 0; i < candidateToQuestion.size(); i++) {
				final CandidateToQuestion newCandidateToQuestion = candidateToQuestionDao
						.insert(candidateToQuestion.get(i));
				candidateToQuestion.set(i, newCandidateToQuestion);
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

}
