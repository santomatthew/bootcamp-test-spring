package com.lawencon.myapp.view;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lawencon.myapp.model.CandidateAssign;
import com.lawencon.myapp.model.CandidateInformation;
import com.lawencon.myapp.model.CandidateToQuestion;
import com.lawencon.myapp.model.File;
import com.lawencon.myapp.model.FileType;
import com.lawencon.myapp.model.ProgressStatus;
import com.lawencon.myapp.model.Question;
import com.lawencon.myapp.model.QuestionAnswer;
import com.lawencon.myapp.model.QuestionOption;
import com.lawencon.myapp.model.Review;
import com.lawencon.myapp.model.ReviewDetail;
import com.lawencon.myapp.model.ReviewStatus;
import com.lawencon.myapp.model.User;
import com.lawencon.myapp.service.AnswerService;
import com.lawencon.myapp.service.CandidateToQuestionService;
import com.lawencon.myapp.service.FileTypeService;
import com.lawencon.myapp.service.QuestionService;
import com.lawencon.myapp.util.ScannerUtil;

public class CandidateView {

	private final User candidate;
	private final AnswerService answerService;
	private final FileTypeService fileTypeService;
	private final QuestionService questionService;
	private final CandidateToQuestionService candidateToQuestionService;

	List<CandidateInformation> candidateInformations = new ArrayList<>();
	List<QuestionAnswer> questionAnswers = new ArrayList<>();
	List<CandidateToQuestion> candidateToQuestions = new ArrayList<>();
	List<Boolean> optionAnswers = new ArrayList<>();

	CandidateView(User candidate, AnswerService answerService, FileTypeService fileTypeService,
			QuestionService questionService, CandidateToQuestionService candidateToQuestionService) {
		this.candidate = candidate;
		this.answerService = answerService;
		this.fileTypeService = fileTypeService;
		this.questionService = questionService;
		this.candidateToQuestionService = candidateToQuestionService;
	}

	public void show() throws SQLException {
		final List<CandidateToQuestion> candidateToquestions = candidateToQuestionService.getCandidateToQuestionByCandidateId(candidate.getId());
		
		if(candidateToquestions.size()>0) {
		System.out.println("----- Upload File -----");
		final List<FileType> fileTypes = fileTypeService.getAllFileTypes();
		final LocalDateTime timeNow = LocalDateTime.now();
		for(int i=0;i<fileTypes.size();i++) {
			
			final String typeName = fileTypes.get(i).getTypeName();
			final String inputName = ScannerUtil.scannerStr("Masukkan "+ typeName+ " : ");
			final String inputExt = ScannerUtil.scannerStr("Masukkan Extension "+ typeName+ " : ");
			final CandidateInformation newCandidateInformation = new CandidateInformation();
			newCandidateInformation.setCandidate(candidate);
			newCandidateInformation.setFileType(fileTypes.get(i));
			
			final File newFile = new File();
			newFile.setFileName(inputName);
			newFile.setExt(inputExt);
			newFile.setCreatedBy(candidate.getId());
			newFile.setCreatedAt(timeNow);
			newFile.setIsActive(true);
			newFile.setVer(1);
			newCandidateInformation.setFile(newFile);
			
			newCandidateInformation.setCreatedBy(candidate.getId());
			newCandidateInformation.setCreatedAt(timeNow);
			newCandidateInformation.setIsActive(true);
			newCandidateInformation.setVer(1);
			candidateInformations.add(newCandidateInformation);
		}
		
		
		final List<Question> questions = new ArrayList<>();
		
		
			for(int i=0;i<candidateToquestions.size();i++) {
				final Question question = questionService.getQuestionByCandidateToQuestion(candidateToquestions.get(i).getQuestion().getId());
				questions.add(i, question);
			}
			
			//Update Review
			Review updatedReview = new Review();
			updatedReview.setCandidate(candidate);
			
			//Progress Status
			final List<ProgressStatus> allProgressStatus = answerService.getProgressStatus();
			final ProgressStatus progressStatus = new ProgressStatus();
			progressStatus.setId(allProgressStatus.get(0).getId());
			updatedReview.setProgressStatus(progressStatus);
			updatedReview.setUpdatedAt(timeNow);
			updatedReview.setUpdatedBy(candidate.getId());
			updatedReview = answerService.updateCandidateReview(updatedReview);
			CandidateAssign candAssign = null;

			
			for(int i=0;i<questions.size();i++) {
				final QuestionAnswer newQuestionAnswer = new QuestionAnswer();
				final Long candidateAssignId = answerService.candidateAssignId(questions.get(i).getId(), candidate.getId());
				final CandidateAssign candidateAssign = new CandidateAssign();
				candidateAssign.setId(candidateAssignId);
				newQuestionAnswer.setCandidateAssign(candidateAssign);
				newQuestionAnswer.setQuestion(questions.get(i));
				newQuestionAnswer.setCreatedBy(candidate.getId());
				newQuestionAnswer.setCreatedAt(timeNow);
				newQuestionAnswer.setIsActive(true);
				newQuestionAnswer.setVer(1);
				//Print Question
				System.out.println((i+1)+". "+questions.get(i).getQuestion());
				
				if(questions.get(i).getQuestionType().getId() == 1) {
					final List<QuestionOption> options = answerService.getOptions(questions.get(i).getId());
					for(int j=0;j<options.size();j++) {
						System.out.print("("+(j+1)+") "+ options.get(j).getOptLabel());
					}
					System.out.println();
					final int takeOption = ScannerUtil.scannerInt("Pilih jawaban : ", 1, options.size());
					newQuestionAnswer.setQuestionOption(options.get(takeOption-1));
					newQuestionAnswer.setEssayAnswer(null);
					optionAnswers.add(options.get(takeOption-1).getCorrect());
					
					candAssign = candidateAssign;
					final CandidateToQuestion candidateToQuestion = answerService.getCandidateToQuestionByQuestionId(questions.get(i).getId());
					candidateToQuestions.add(candidateToQuestion);
					
					
				}
				else if(questions.get(i).getQuestionType().getId() == 2) {
					final String essayAnswer = ScannerUtil.scannerStr("Jawab : ");
					newQuestionAnswer.setEssayAnswer(essayAnswer);
					newQuestionAnswer.setQuestionOption(null);
				}
				questionAnswers.add(newQuestionAnswer);
			}

			final Boolean result = answerService.insertFileAndAnswer(candidateInformations, questionAnswers);

			if(result) {
				System.out.println("Upload file dan jawaban berhasil");
				System.out.println("Silahkan menunggu hasil review");
				
				final CandidateAssign checkCandAssign = candAssign;
				
				if(checkCandAssign != null) {
					//Auto generate Score on Review Detail on Question Option
					final ReviewDetail reviewDetail = new ReviewDetail();
					final Float gradeBySystem = answerService.getGrade(optionAnswers);
					reviewDetail.setGrade(gradeBySystem);
					reviewDetail.setNotes("Grade generated by System");
					reviewDetail.setReview(updatedReview);
					reviewDetail.setCandidateAssign(candAssign);
					answerService.updateReviewDetail(reviewDetail);
				}

				//Progress Status and Review Status
				updatedReview.setProgressStatus(null);
				final ProgressStatus newProgressStatus = new ProgressStatus();
				newProgressStatus.setId(allProgressStatus.get(1).getId());
				updatedReview.setProgressStatus(newProgressStatus);
				
				updatedReview.setReviewStatus(null);
				final List<ReviewStatus> allReviewStatus = answerService.getReviewStatus();
				final ReviewStatus reviewStatus = new ReviewStatus();
				reviewStatus.setId(allReviewStatus.get(0).getId());
				updatedReview.setReviewStatus(reviewStatus);
				answerService.updateCandidateReview(updatedReview);
				final User updatedCandidate = candidate;
				updatedCandidate.setUpdatedAt(timeNow);
				updatedCandidate.setUpdatedBy(candidate.getId());
				updatedCandidate.setIsActive(false);
				answerService.setIsActive(updatedCandidate);
			}
			else {
				System.out.println("Gagal Upload file dan jawaban");
			}
			
			optionAnswers =new ArrayList<>();
			candidateInformations= new ArrayList<>();
			questionAnswers = new ArrayList<>();
		}
		else {
			System.out.println("Kamu belum memiliki soal");
		}
		
	
	}

}
