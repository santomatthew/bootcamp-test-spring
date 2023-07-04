package com.lawencon.myapp.constant;

public enum ReviewStat {

	NEEDSREVIEW("NR","Needs Review"),REJECTED("RJ","Rejected"),CONSIDERED("CD","Considered"),RECOMMENDED("RC","Recommended");

	public final String statusCode;
	public final String statusName;
	
	ReviewStat(String statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName= statusName;
	}
	
	
	
}
