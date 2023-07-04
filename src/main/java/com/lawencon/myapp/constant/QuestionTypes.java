package com.lawencon.myapp.constant;

public enum QuestionTypes {

	OPTION("PG","Pilihan Ganda"),ESSAY("ES","Essay");
	
	public final String questionTypeCode;
	public final String questionTypeName;
	
	
	QuestionTypes(String questionTypeCode, String questionTypeName) {
		this.questionTypeCode = questionTypeCode;
		this.questionTypeName = questionTypeName;
	}
	
}
