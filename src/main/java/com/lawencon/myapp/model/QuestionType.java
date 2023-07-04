package com.lawencon.myapp.model;

public class QuestionType extends BaseModel {

	private User superadmin;
	private String typeCode;
	private String questionType;

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public User getSuperadmin() {
		return superadmin;
	}

	public void setSuperadmin(User superadmin) {
		this.superadmin = superadmin;
	}


}
