package com.saran.librarymanagement.enums;

public enum ErrorCode {
	
	INSERTED(11),
	UPDATED(12),
	NOTFOUNT(15),
	NOT_YOUR(16);
	
	private int errorCode;
	
	private ErrorCode(int i) {
		this.errorCode = i;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
}
