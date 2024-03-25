package com.saran.librarymanagement.enums;

public enum BookStatus {

	AVAILABLE(1),
	NOT_AVAILABLE(2),
	DIMAGED(3);
	
	private int bookStatus;
	
	private BookStatus(int i) {
		this.bookStatus = i;		
	}
	
	public int getBookStatus() {
		return bookStatus;
	}
}
