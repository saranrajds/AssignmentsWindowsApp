package com.saran.enums;

public enum UserType {

	INVALID(0),
	ADMIN(1),
	USER(2);
	
	int userType;
	
	private UserType(int i) {
		this.userType = i;
	}
	
	public int getUserType() {
		return userType;
	}
}
