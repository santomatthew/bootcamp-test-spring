package com.lawencon.myapp.model;

public class Review extends BaseModel {

	private ReviewStatus reviewStatus;
	private ProgressStatus progressStatus;
	private User Candidate;
	private User Reviewer;


	public User getCandidate() {
		return Candidate;
	}

	public void setCandidate(User candidate) {
		Candidate = candidate;
	}

	public User getReviewer() {
		return Reviewer;
	}

	public void setReviewer(User reviewer) {
		Reviewer = reviewer;
	}

	public ReviewStatus getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(ReviewStatus reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public ProgressStatus getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(ProgressStatus progressStatus) {
		this.progressStatus = progressStatus;
	}




}
