package com.lawencon.myapp.model;

import java.time.LocalDateTime;

public class CandidateAssign extends BaseModel{

	private User candidate;
	private QuestionType questionType;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	public User getCandidate() {
		return candidate;
	}
	public void setCandidate(User candidate) {
		this.candidate = candidate;
	}
	public QuestionType getQuestionType() {
		return questionType;
	}
	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	
}
