package com.saran.librarymanagement.borroworreturing;

import com.saran.librarymanagement.enums.BookStatus;
import com.saran.librarymanagement.enums.ErrorCode;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;

public class BorrowOrReturingModel {

	BorrowOrReturingView borrowOrReturingView;
	public BorrowOrReturingModel(BorrowOrReturingView borrowOrReturingView) {
		this.borrowOrReturingView = borrowOrReturingView;
	}
	
	public void onAssignBook(int bookId) {
		int errorCode = LibraryDatabase.getInstance().onAssignBook(bookId);
		showErrorCode(errorCode);
	}

	public void returningBook(int bookId) {
		int errorCode = LibraryDatabase.getInstance().returningBook(bookId);
		
		if(errorCode == BookStatus.AVAILABLE.getBookStatus()) {
			borrowOrReturingView.showAlert("No One is borrow the Book..");
		}
		else
			showErrorCode(errorCode);
	}
	
	private void showErrorCode(int errorCode) {
		
		if (errorCode == ErrorCode.INSERTED.getErrorCode()) {
			borrowOrReturingView.showAlert("Status INSERTED Successfully..");
		} 
		else if(errorCode == ErrorCode.UPDATED.getErrorCode()) {
			borrowOrReturingView.showAlert("Status UPDATED Successfully..");
		} 
		else if(errorCode == ErrorCode.NOTFOUNT.getErrorCode()) {
			borrowOrReturingView.showAlert("Book NOT FOUNT..");
		} 
		else if(errorCode == BookStatus.DIMAGED.getBookStatus()) {
			borrowOrReturingView.showAlert("Book DIMAGED...");
		} 
		else {
			borrowOrReturingView.showAlert("Book is already Borrowed...");
		}
	}
}
