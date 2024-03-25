package com.saran.librarymanagement.enums;

public enum UserBookStatus {
	
	BORROW(1),
	RETURN(2);

	private int userBookStatus;
	
	private UserBookStatus(int i) {
		this.userBookStatus = i;
	}
	
	public int getUserBookStatus() {
		return userBookStatus;
	}
}
