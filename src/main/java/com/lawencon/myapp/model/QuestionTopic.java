package com.lawencon.myapp.model;

public class QuestionTopic extends BaseModel {

	private User reviewer;
	private String topicCode;
	private String questionTopic;


	public String getTopicCode() {
		return topicCode;
	}

	public void setTopicCode(String topicCode) {
		this.topicCode = topicCode;
	}

	public String getQuestionTopic() {
		return questionTopic;
	}

	public void setQuestionTopic(String questionTopic) {
		this.questionTopic = questionTopic;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

}
