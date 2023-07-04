package com.lawencon.myapp.model;

import java.util.List;

public class Question extends BaseModel {

	private User reviewer;
	private String question;
	private String questionCode;
	private QuestionType questionType;
	private QuestionTopic questionTopic;
	private QuestionPackage questionPackage;
	private List<QuestionOption> questionOption;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public QuestionTopic getQuestionTopic() {
		return questionTopic;
	}

	public void setQuestionTopic(QuestionTopic questionTopic) {
		this.questionTopic = questionTopic;
	}

	public QuestionPackage getQuestionPackage() {
		return questionPackage;
	}

	public void setQuestionPackage(QuestionPackage questionPackage) {
		this.questionPackage = questionPackage;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public List<QuestionOption> getQuestionOption() {
		return questionOption;
	}

	public void setQuestionOption(List<QuestionOption> questionOption) {
		this.questionOption = questionOption;
	}

}
