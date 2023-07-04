package com.lawencon.myapp.view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.lawencon.myapp.constant.UserRole;
import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.File;
import com.lawencon.myapp.model.Profile;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.QuestionPackage;
import com.lawencon.myapp.model.QuestionType;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;
import com.lawencon.myapp.model.Role;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.CandidateService;
import com.lawencon.myapp.service.QuestionPackageService;
import com.lawencon.myapp.service.QuestionService;
import com.lawencon.myapp.service.QuestionTypeService;
import com.lawencon.myapp.service.UserService;
import com.lawencon.myapp.util.DateUtil;
import com.lawencon.myapp.util.ScannerUtil;

public class HumanResourceView {

	private final User hr;
	
	List<Question> chosenQuestions = new ArrayList<>();
	List<CandidateAssign> candidateAssigns = new ArrayList<>();
	List<CandidateToQuestion> candidateToQuestions = new ArrayList<>();
	List<ReviewDetail> reviewDetails = new ArrayList<>();
	
	Set<Long> chosenQuestionTypes = new HashSet<>();
	
	private final UserService userService;
	private final CandidateService candidateService;
	private final QuestionTypeService questionTypeService;
	private final QuestionService questionService;
	private final QuestionPackageService questionPackageService;

	public HumanResourceView(User user, UserService userService, QuestionTypeService questionTypeService,
		QuestionService questionService, CandidateService candidateService,QuestionPackageService questionPackageService) {
		this.hr = user;
		this.userService = userService;
		this.questionTypeService = questionTypeService;
		this.questionService = questionService;
		this.candidateService = candidateService;
		this.questionPackageService = questionPackageService;
	}

	public void show() throws SQLException {
		System.out.println("===== Welcome to HR Menu =====");

		System.out.println("1. Assign Candidate");
		System.out.println("2. Create Package Question");
		System.out.println("3. Create Candidate Account");
		System.out.println("4. Logout");

		final int optionMenu = ScannerUtil.scannerInt("Pilih menu : ", 1, 4);
		option(optionMenu);

	}

	private void option(int chosenOption) throws SQLException  {
		if (chosenOption == 1) {
			showAssignCandidate();
		}
		else if(chosenOption == 2) {
			 showCreatePackage();
		}
		else if(chosenOption == 3) {
			showCreateCandidate();
		}
		
	}

	// Assign Candidate
	private void showAssignCandidate() throws SQLException {
		final List<User>candidates = userService.getByRoleCode(UserRole.CANDIDATE.roleCode);
		System.out.println("=== List Candidate ===");
		for (int i = 0; i < candidates.size(); i++) {
			System.out.println((i + 1) + ". " + candidates.get(i).getProfile().getFullName());
		}

		final int chosenCandidate = ScannerUtil.scannerInt("Pilih candidate : ", 1, candidates.size());
		final User candidate = candidates.get(chosenCandidate - 1);

		System.out.println("=== List Reviewer ===");
		final List<User> reviewers = userService.getByRoleCode(UserRole.REVIEWER.roleCode);
		for (int i = 0; i < reviewers.size(); i++) {
			System.out.println((i + 1) + ". " + reviewers.get(i).getProfile().getFullName());
		}

		final int chosenReviewer = ScannerUtil.scannerInt("Pilih Reviewer : ", 1, candidates.size());
		final User reviewer = reviewers.get(chosenReviewer - 1);

		final Review newReview = new Review();
		newReview.setCandidate(candidate);
		newReview.setReviewer(reviewer);
		newReview.setCreatedBy(hr.getId());
		final LocalDateTime timeNow = LocalDateTime.now();
		newReview.setCreatedAt(timeNow);
		newReview.setIsActive(true);
		newReview.setVer(1);
		final String startDate = ScannerUtil.scannerStr("Masukkan start time (Contoh : 2023-06-23 09:00:00) :");
		final String endDate = ScannerUtil.scannerStr("Masukkan end time (Contoh : 2023-06-23 09:00:00) :");
		final LocalDateTime startDateConverter = DateUtil.parseStringToDate(startDate);
		final LocalDateTime endDateConverter = DateUtil.parseStringToDate(endDate);
		showQuestionType(candidate.getId(),newReview, startDateConverter,endDateConverter);

	}

	private void showQuestionType(Long candidateId,Review newReview , LocalDateTime startDateConverter, LocalDateTime endDateConverter) throws SQLException {
		System.out.println("=== Question Type ===");
		final List<QuestionType> questionTypes = questionTypeService.getQuestionTypes();
		
		for (int i = 0; i < questionTypes.size(); i++) {
			System.out.println((i + 1) + ". " + questionTypes.get(i).getQuestionType());
		}

		final int chosenQuestionType = ScannerUtil.scannerInt("Pilih Question Type : ", 1, questionTypes.size());
		chosenQuestionTypes.add(questionTypes.get(chosenQuestionType - 1).getId());
		final String questionTypeCode = questionTypes.get(chosenQuestionType - 1).getTypeCode();

		

		final List<Question> questions = questionService.getByCode(questionTypeCode);
		for (int i = 0; i < questions.size(); i++) {
			System.out.println((i + 1) + ". " + questions.get(i).getQuestion());
		}
		final int chooseQuestion = ScannerUtil.scannerInt("Pilih Question  : ", 1, questions.size());
		final Question chosenQuestion = questions.get(chooseQuestion - 1);
		chosenQuestions.add(chosenQuestion);

		final int option = ScannerUtil.scannerInt("Apakah mau pilih soal lagi? (1 Untuk Ya, 2 Untuk Tidak) : ", 1, 2);
		if (option == 1) {
			showQuestionType(candidateId,newReview,startDateConverter,endDateConverter);
			
		} else if (option == 2) {
			final LocalDateTime timeNow = LocalDateTime.now();
			//Loop Candidate Assign
			for(Long chosenQuestionTypeId : chosenQuestionTypes) {
				final CandidateAssign newCandidateAssign = new CandidateAssign();
				final User candidate = new User();
				candidate.setId(candidateId);
				newCandidateAssign.setCandidate(candidate);
				
				//Question Type
				final QuestionType questionType = new QuestionType();
				questionType.setId(chosenQuestionTypeId);
				newCandidateAssign.setQuestionType(questionType);
				
				newCandidateAssign.setStartTime(startDateConverter);
				newCandidateAssign.setEndTime(endDateConverter);
				newCandidateAssign.setIsActive(true);
				newCandidateAssign.setVer(1);
				newCandidateAssign.setCreatedBy(hr.getId());
				System.out.println(hr.getId());
				
				newCandidateAssign.setCreatedAt(timeNow);
				candidateAssigns.add(newCandidateAssign);
			}
			
			
			//Loop Review Detail
			for(int i=0;i<candidateAssigns.size();i++) {
				final ReviewDetail newReviewDetail = new ReviewDetail();
				newReviewDetail.setCandidateAssign(candidateAssigns.get(i));
				newReviewDetail.setReview(newReview);
				newReviewDetail.setCreatedBy(hr.getId());
				
				newReviewDetail.setCreatedAt(timeNow);
				newReviewDetail.setIsActive(true);
				newReviewDetail.setVer(1);
				reviewDetails.add(newReviewDetail);
			}
			
			//Loop Candidate To Question
			for(int i=0;i<candidateAssigns.size();i++) {
				for(int j=0;j<chosenQuestions.size();j++) {
					if(candidateAssigns.get(i).getQuestionType().getId() == chosenQuestions.get(j).getQuestionType().getId()) {
						final CandidateToQuestion newCandidateToQuestion= new CandidateToQuestion();
						
						newCandidateToQuestion.setQuestion(chosenQuestions.get(j));
						
						final User candidate = new User();
						candidate.setId(candidateId);
						newCandidateToQuestion.setCandidate(candidate);
					
						newCandidateToQuestion.setHr(hr);					
						newCandidateToQuestion.setCandidateAssign(candidateAssigns.get(i));
						newCandidateToQuestion.setCreatedBy(hr.getId());
						newCandidateToQuestion.setCreatedAt(timeNow);
						newCandidateToQuestion.setIsActive(true);
						newCandidateToQuestion.setVer(1);
						candidateToQuestions.add(newCandidateToQuestion);
						
					}
				}
			}
			
			final Boolean candidateServ = candidateService.candidateService(candidateAssigns, newReview, reviewDetails, candidateToQuestions);
			
			if(candidateServ) {
				System.out.println("Assign sukses");
			}
			else {
				System.out.println("Assign gagal");
			}
			
			chosenQuestions= new ArrayList<>();
			candidateAssigns = new ArrayList<>();
			candidateToQuestions = new ArrayList<>();
			reviewDetails = new ArrayList<>();
			
		}
		show();
	}


	//Create package Soal
	private void showCreatePackage() throws SQLException {
		final String newPackage = ScannerUtil.scannerStr("Masukkan nama package : ");
		final String codePackage = ScannerUtil.scannerStr("Masukkan code package : ");
		final QuestionPackage questionPackage = questionPackageService.createNew(newPackage, codePackage, hr);
		if(questionPackage!=null) {
			System.out.println("Sukses membuat package baru");
		}
		else {
			System.out.println("Gagal membuat package");
		}
		show();
	}
	
	//Create Candidate
	private void showCreateCandidate() throws SQLException {
		final LocalDateTime timeNow = LocalDateTime.now();
		System.out.println("===== Create Account (Candidate) =====");
		final String inputEmail = ScannerUtil.scannerStr("Masukkan email : ");
		final String inputPassword = ScannerUtil.scannerStr("Masukkan password : ");
		final String fullName = ScannerUtil.scannerStr("Masukkan nama lengkap : ");
		final String address = ScannerUtil.scannerStr("Masukkan alamat : ");
		final String phone = ScannerUtil.scannerStr("Masukkan no telepon : ");
		final String photoName  = ScannerUtil.scannerStr("Masukkan nama foto : ");
		final String photoExt  = ScannerUtil.scannerStr("Masukkan ext foto : ");
		
		final Role role = userService.getByCode(UserRole.CANDIDATE.roleCode);
		
		final File newFile = new File();
		newFile.setExt(photoExt);
		newFile.setFileName(photoName);
		newFile.setCreatedBy(hr.getId());
		newFile.setCreatedAt(timeNow);
		newFile.setIsActive(true);
		newFile.setVer(1);
		
		final Profile newProfile = new Profile();
		newProfile.setPhoto(newFile);
		newProfile.setFullName(fullName);
		newProfile.setAddress(address);
		newProfile.setPhoneNo(phone);
		newProfile.setCreatedBy(hr.getId());
		newProfile.setCreatedAt(timeNow);
		newProfile.setIsActive(true);
		newProfile.setVer(1);
		
		User newUser = new User();
		newUser.setUserEmail(inputEmail);
		newUser.setUserPassword(inputPassword);
		newUser.setRole(role);
		newUser.setProfile(newProfile);
		newUser.setCreatedBy(hr.getId());
		newUser.setCreatedAt(timeNow);
		newUser.setIsActive(true);
		newUser.setVer(1);
		
		newUser = userService.insert(newUser, newProfile, newFile);
		
		if(newUser!=null) {
			System.out.println("Berhasil membuat akun "+ role.getRoleName() + " baru");
		}
		else {
			System.out.println("Gagal membuat akun");
		}
	
		show();
	}
	
}
