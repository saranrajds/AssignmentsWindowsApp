package com.saran.librarymanagement.enums;

public enum UserType {
	
	INVALID(0),
	ADMIN(1),
	LIBRARY_ADMIN(2),
	USER(3);
		
	int userType;
	
	private UserType(int i) {
		this.userType = i;
	}
	
	public int getUserType() {
		return userType;
	}
}

enum UserErrorCode {
	
}
