package com.saran.enums;

public enum ErrorCode {
	
	NOTMATCHING(1),
	INTERVIEWGOINGON(2),
	SUCCESS(3);
	
	int errorCode;
	
	ErrorCode(int i) {
		errorCode = i;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
}

