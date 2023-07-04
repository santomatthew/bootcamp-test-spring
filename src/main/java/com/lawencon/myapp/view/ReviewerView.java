package com.lawencon.myapp.view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lawencon.myapp.constant.QuestionTypes;
import com.lawencon.myapp.constant.ReviewStat;
import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.QuestionAnswer;
import com.lawencon.myapp.model.QuestionOption;
import com.lawencon.myapp.model.QuestionPackage;
import com.lawencon.myapp.model.QuestionTopic;
import com.lawencon.myapp.model.QuestionType;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;
import com.lawencon.myapp.model.ReviewStatus;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.QuestionPackageService;
import com.lawencon.myapp.service.QuestionService;
import com.lawencon.myapp.service.QuestionTopicService;
import com.lawencon.myapp.service.QuestionTypeService;
import com.lawencon.myapp.service.ReviewService;
import com.lawencon.myapp.util.ScannerUtil;

public class ReviewerView {

	private final User reviewer;
	private final ReviewService reviewService;
	private final QuestionTypeService questionTypeService;
	private final QuestionTopicService questionTopicService;
	private final QuestionPackageService questionPackageService;
	private final QuestionService questionService;
	final List<Question> questions = new ArrayList<>();

	public ReviewerView(User user, ReviewService reviewService, QuestionTypeService questionTypeService,
			QuestionTopicService questionTopicService, QuestionPackageService questionPackageService,
			QuestionService questionService) {
		this.reviewService = reviewService;
		this.reviewer = user;
		this.questionTypeService = questionTypeService;
		this.questionTopicService = questionTopicService;
		this.questionPackageService = questionPackageService;
		this.questionService = questionService;
	}

	public void show() throws SQLException {
		System.out.println("===== Reviewer Menu =====");
		System.out.println("1. Buat Question baru");
		System.out.println("2. Buat Topic baru");
		System.out.println("3. Review Candidate");
		System.out.println("4. List Review by status");
		System.out.println("5. Logout");
		final int option = ScannerUtil.scannerInt("Pilih menu : ", 1, 5);
		menu(option);
	}

	private void menu(int option) throws SQLException {
		if (option == 1) {
			showCreateQuestion();
		} else if (option == 2) {
			showCreateTopic();
		} else if (option == 3) {
			showReviewCandidate();
		} else if (option == 4) {
			showReviewByStatus();
		}
	}

	// Create new Question
	private void showCreateQuestion() throws SQLException {
		final LocalDateTime timeNow = LocalDateTime.now();
		final List<QuestionType> questionTypes = questionTypeService.getQuestionTypes();
		for(int i=0;i<questionTypes.size();i++) {
			System.out.println((i+1)+ ". "+ questionTypes.get(i).getQuestionType());
		}
		
		final int chosenType = ScannerUtil.scannerInt("Pilih tipe : ", 1, questionTypes.size());
		
		final String inputQuestion = ScannerUtil.scannerStr("Masukkan question : ");
		final String inputCode = ScannerUtil.scannerStr("Masukkan code question :");
		final Question question = new Question();
		question.setQuestionCode(inputCode);
		question.setQuestion(inputQuestion);
		question.setQuestionType(questionTypes.get(chosenType-1));
		question.setReviewer(reviewer);
		question.setCreatedBy(reviewer.getId());
		question.setCreatedAt(timeNow);
		question.setIsActive(true);
		question.setVer(1);
		
		if(questionTypes.get(chosenType-1).getTypeCode().equals(QuestionTypes.OPTION.questionTypeCode)) {
			final int optionQty = ScannerUtil.scannerNoMaximum("Mau berapa Pilgan : ", 2);
			final List<QuestionOption> questionOptions = new ArrayList<>();
			System.out.println("===== Create Option =====");
			for(int i=0;i<optionQty;i++) {
				final String optLabel = ScannerUtil.scannerStr("Masukkan opsi ke "+(i+1)+" : ");
				final Boolean optAnswer = ScannerUtil.scannerBool("Pilih kunci jawaban (1:True || 2:False): ");
				
				final QuestionOption questionOption = new QuestionOption();
				questionOption.setOptLabel(optLabel);
				questionOption.setCorrect(optAnswer);
				questionOption.setCreatedBy(reviewer.getId());
				questionOption.setCreatedAt(timeNow);
				questionOption.setIsActive(true);
				questionOption.setVer(1);
				
				questionOptions.add(questionOption);
			}
			
			question.setQuestionOption(questionOptions);
		}
		
		System.out.println("===== Question Topic =====");
		final List<QuestionTopic> questionTopics = questionTopicService.getAll();
		for(int j=0;j<questionTopics.size();j++) {
			System.out.println((j+1)+". "+ questionTopics.get(j).getQuestionTopic());
		}
		
		final int chosenTopic = ScannerUtil.scannerInt("Pilih topic : ", 1, questionTopics.size());
		question.setQuestionTopic(questionTopics.get(chosenTopic-1));
		
		System.out.println("===== Question Package =====");
		final List<QuestionPackage>questionPackages = questionPackageService.getAll();
		for(int j=0;j<questionPackages.size();j++) {
			System.out.println((j+1)+". "+ questionPackages.get(j).getPackageName());
		}
		
		final int chosenPackage = ScannerUtil.scannerInt("Pilih package : ", 1, questionPackages.size());
		question.setQuestionPackage(questionPackages.get(chosenPackage-1));

		questions.add(question);
		
		final Boolean askCreateQuestion = ScannerUtil.scannerBool("Mau buat soal lagi ? (1: Ya || 2 Tidak)");
		if(askCreateQuestion) {
			showCreateQuestion();
		}
		else {
			final Boolean result = questionService.createQuestion(questions);
			if(result) {
				System.out.println("Berhasil membuat question");
				
			}
			else {
				System.out.println("Gagal membuat soal");
			}
			
			show();
		}
		
		
	}

	// Create Topic
	private void showCreateTopic() throws SQLException {
		final LocalDateTime timeNow = LocalDateTime.now();
		final String newTopic = ScannerUtil.scannerStr("Masukkan nama topic : ");
		final String codeTopic = ScannerUtil.scannerStr("Masukkan code topic : ");
		QuestionTopic questionTopic = new QuestionTopic();
		questionTopic.setQuestionTopic(newTopic);
		questionTopic.setTopicCode(codeTopic);
		questionTopic.setReviewer(reviewer);
		questionTopic.setCreatedAt(timeNow);
		questionTopic.setCreatedBy(reviewer.getId());
		questionTopic.setIsActive(true);
		questionTopic.setVer(1);
		questionTopic = questionTopicService.createQuestionTopic(questionTopic);
		if (questionTopic != null) {
			System.out.println("Sukses membuat topic baru");
		} else {
			System.out.println("Gagal membuat topic");
		}
		show();
	}

	// Review Candidate
	private void showReviewCandidate() throws SQLException {
		final ReviewStat[] allReviewStatus = ReviewStat.values();
		final List<Review> ownedReviews = reviewService.getOwnedReview(reviewer.getId(), allReviewStatus[0].statusCode);

		if (ownedReviews.size() > 0) {
			System.out.println("=== List Nama Kandidat ===");
			for (int i = 0; i < ownedReviews.size(); i++) {
				final User candidate = reviewService.getCandidate(ownedReviews.get(i).getCandidate().getId());
				final String reviewStatusName = reviewService
						.getReviewStatusName(ownedReviews.get(i).getReviewStatus().getId());
				System.out.println(
						(i + 1) + ". " + candidate.getProfile().getFullName() + " || Status : " + reviewStatusName);
			}

			final int chooseCandidate = ScannerUtil.scannerInt("Pilih kandidat : ", 1, ownedReviews.size());
			final Review chosenReview = ownedReviews.get(chooseCandidate - 1);
			final List<CandidateToQuestion> candidateToQuestions = reviewService
					.getCandidateQuestion(chosenReview.getCandidate().getId());
			final List<Question> essayQuestions = new ArrayList<>();
			CandidateAssign candidateAssign = null;
			for (int i = 0; i < candidateToQuestions.size(); i++) {
				final Question question = reviewService
						.getQuestionByCandidateToQuestion(candidateToQuestions.get(i).getQuestion().getId());
				if (question.getQuestionType().getQuestionType().equals(QuestionTypes.ESSAY.questionTypeCode)) {
					essayQuestions.add(question);
					candidateAssign = candidateToQuestions.get(i).getCandidateAssign();
				}
			}

			if (candidateAssign != null) {
				final List<QuestionAnswer> questionAnswers = reviewService.getQuestionAnswer(candidateAssign);
				for (int i = 0; i < essayQuestions.size(); i++) {
					System.out.println("========================");
					System.out.println("Pertanyaan ke " + (i + 1));
					System.out.println(essayQuestions.get(i).getQuestion());
					System.out.println("Jawaban : " + questionAnswers.get(i).getEssayAnswer());

				}

				final float grade = ScannerUtil.scannerFloatNoMaximum("Masukkan grade : ", 0);
				final String notes = ScannerUtil.scannerStr("Masukkan notes : ");
				final ReviewDetail reviewDetail = new ReviewDetail();
				reviewDetail.setReview(chosenReview);
				reviewDetail.setGrade(grade);
				reviewDetail.setNotes(notes);
				reviewDetail.setCandidateAssign(candidateAssign);
				final Boolean result = reviewService.updateReviewDetail(reviewDetail);
				if (result) {
					System.out.println("Grade dan Notes berhasil ditambahkan");
				} else {
					System.out.println("Gagal menambahkan grade dan Notes");
				}
			}

			final ReviewDetail reviewDetail = new ReviewDetail();
			reviewDetail.setReview(chosenReview);
			final List<ReviewDetail> reviewDetails = reviewService.getReviewDetails(reviewDetail);

			for (int i = 0; i < reviewDetails.size(); i++) {
				System.out.println("Grade pertanyaan ke " + (i + 1) + " : " + reviewDetails.get(i).getGrade());
			}
			updateReviewStatus(chosenReview);
		} else {
			System.out.println("Anda belum memiliki kandidat");
			show();
		}
	}

	// Update review status
	private void updateReviewStatus(Review review) throws SQLException {
		final LocalDateTime timeNow = LocalDateTime.now();
		final List<ReviewStatus> reviewStatus = reviewService.getAllReviewStatusExceptNeedsReview();
		for (int i = 0; i < reviewStatus.size(); i++) {
			System.out.println((i + 1) + ". " + reviewStatus.get(i).getStatusName());
		}

		final int chosenStatus = ScannerUtil.scannerInt("Pilih status untuk di update pada review : ", 1,
				reviewStatus.size());
		final ReviewStatus newReviewStatus = reviewStatus.get(chosenStatus - 1);
		review.setUpdatedAt(timeNow);
		review.setUpdatedBy(reviewer.getId());
		review.setReviewStatus(newReviewStatus);
		final Boolean result = reviewService.updateReview(review);

		if (result) {
			System.out.println("Berhasil mengupdate status");
		} else {
			System.out.println("Gagal mengupdate status");
		}

		show();
	}

	// Review by status
	private void showReviewByStatus() throws SQLException {
		final ReviewStat[] allReviewStatus = ReviewStat.values();
		for (int i = 0; i < allReviewStatus.length; i++) {
			System.out.println((i + 1) + ". " + allReviewStatus[i].statusName);
		}
		final int chooseStatus = ScannerUtil.scannerInt("Pilih status : ", 1, allReviewStatus.length);

		final List<Review> reviews = reviewService.getReviewByStatus(reviewer.getId(),
				allReviewStatus[chooseStatus - 1].statusCode);

		if (reviews.size() > 0) {
			System.out.println("========================");
			for (int i = 0; i < reviews.size(); i++) {
				final User candidate = reviewService.getCandidate(reviews.get(i).getCandidate().getId());
				final String reviewStatusName = reviewService
						.getReviewStatusName(reviews.get(i).getReviewStatus().getId());
				System.out.println(
						(i + 1) + ". " + candidate.getProfile().getFullName() + " || Status : " + reviewStatusName);
			}
			System.out.println("========================");
		} else {
			System.out.println(
					"Anda tidak memiliki review dengan status : " + allReviewStatus[chooseStatus - 1].statusName);
		}

		show();

	}

}
