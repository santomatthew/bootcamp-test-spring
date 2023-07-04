package com.lawencon.myapp.model;

public class ReviewDetail extends BaseModel {

	private Review review;
	private CandidateAssign candidateAssign;
	private float grade;
	private String notes;

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public CandidateAssign getCandidateAssign() {
		return candidateAssign;
	}

	public void setCandidateAssign(CandidateAssign candidateAssign) {
		this.candidateAssign = candidateAssign;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
