package com.lawencon.myapp.model;

public class QuestionOption extends BaseModel{

	private Question question;
	private String optLabel;
	private Boolean correct;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public String getOptLabel() {
		return optLabel;
	}

	public void setOptLabel(String optLabel) {
		this.optLabel = optLabel;
	}

}
