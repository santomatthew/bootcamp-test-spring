package com.lawencon.myapp.model;

public class CandidateToQuestion extends BaseModel {

	private Question question;
	private User candidate;
	private User hr;
	private CandidateAssign candidateAssign;

	public User getCandidate() {
		return candidate;
	}

	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}

	public User getHr() {
		return hr;
	}

	public void setHr(User hr) {
		this.hr = hr;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public CandidateAssign getCandidateAssign() {
		return candidateAssign;
	}

	public void setCandidateAssign(CandidateAssign candidateAssign) {
		this.candidateAssign = candidateAssign;
	}

}
