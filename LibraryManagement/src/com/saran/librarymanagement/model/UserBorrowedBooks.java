package com.saran.librarymanagement.model;

import com.saran.librarymanagement.enums.UserBookStatus;

public class UserBorrowedBooks {

	private int bookId;
	private int userId;
	private UserBookStatus bookStatus; 
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public UserBookStatus getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(UserBookStatus bookStatus) {
		this.bookStatus = bookStatus;
	}
}
