package com.saran.librarymanagement.enums;

public enum UserOperationCode {

	INSERTED(0),
	UPDATED(1),
	DELETED(2),
	USER_BORROED_BOOK(3),
	USER_NOT_FOUND(4);
	
	int userOperationCode;
	private UserOperationCode(int userOperationCode) {
		
		this.userOperationCode = userOperationCode;
	}
	
	public int getUserOperationCode() {
		return userOperationCode;
	}
}
