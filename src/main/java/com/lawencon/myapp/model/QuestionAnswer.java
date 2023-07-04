package com.lawencon.myapp.model;

public class QuestionAnswer extends BaseModel {

	private User reviewer;
	private CandidateAssign candidateAssign;
	private Question question;
	private String essayAnswer;
	private QuestionOption questionOption;

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getEssayAnswer() {
		return essayAnswer;
	}

	public void setEssayAnswer(String essayAnswer) {
		this.essayAnswer = essayAnswer;
	}

	public QuestionOption getQuestionOption() {
		return questionOption;
	}

	public void setQuestionOption(QuestionOption questionOption) {
		this.questionOption = questionOption;
	}

	public CandidateAssign getCandidateAssign() {
		return candidateAssign;
	}

	public void setCandidateAssign(CandidateAssign candidateAssign) {
		this.candidateAssign = candidateAssign;
	}


}
