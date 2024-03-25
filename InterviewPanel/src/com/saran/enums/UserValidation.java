package com.saran.enums;

public enum UserValidation {

	VALID(10), NOVALID(20);

	int userValidation;

	private UserValidation(int userValidation) {
		this.userValidation = userValidation;
	}

	public int getUserValidation() {
		return userValidation;
	}
}
