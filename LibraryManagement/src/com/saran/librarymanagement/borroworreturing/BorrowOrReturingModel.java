package com.saran.librarymanagement.borroworreturing;

import java.util.List;

import com.saran.librarymanagement.enums.BookStatus;
import com.saran.librarymanagement.enums.ErrorCode;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;

public class BorrowOrReturingModel {

	BorrowOrReturingView borrowOrReturingView;
	public BorrowOrReturingModel(BorrowOrReturingView borrowOrReturingView) {
		this.borrowOrReturingView = borrowOrReturingView;
	}
	
	public void onAssignBook(int bookId) {
		int errorCode = LibraryDatabase.getInstance().onAssignBook(bookId);
		showErrorCode(errorCode, bookId, "Assign");
	}

	public void returningBook(int bookId) {
		int errorCode = LibraryDatabase.getInstance().returningBook(bookId);
		
		if(errorCode == BookStatus.AVAILABLE.getBookStatus()) {
			borrowOrReturingView.showAlert("No One is borrow the Book..");
		}
		else
			showErrorCode(errorCode, bookId, "Return");
	}
	
	private void showErrorCode(int errorCode, int bookId, String processStatus) {
		
		List<User> user = LibraryDatabase.getInstance().getCurrentUser();
		List<Book> book = LibraryDatabase.getInstance().getBook(bookId);
		String userName = user.size() > 0 ? user.get(0).getName() : "";
		String bookName = book.size() > 0 ? book.get(0).getName() : "";
		System.out.println("\n---------------------------------\n");
		
		if (errorCode == ErrorCode.INSERTED.getErrorCode()) {
			borrowOrReturingView.showAlert("Status INSERTED Successfully..");
		} 
		else if(errorCode == ErrorCode.UPDATED.getErrorCode() && processStatus.equals("Return")) {
			borrowOrReturingView.showAlert("The Book ("+ bookName +") Successfully "+processStatus);
		} 
		else if(errorCode == ErrorCode.UPDATED.getErrorCode()) {
			borrowOrReturingView.showAlert("The Book ("+ bookName +") Successfully "+processStatus+" to "+userName);
		} 
		else if(errorCode == ErrorCode.NOTFOUNT.getErrorCode()) {
			borrowOrReturingView.showAlert("Book ("+ bookName +") NOT FOUNT..");
		} 
		else if(errorCode == BookStatus.DIMAGED.getBookStatus()) {
			borrowOrReturingView.showAlert("Book ("+ bookName +") DIMAGED...");
		} 
		else {
			borrowOrReturingView.showAlert("Book is already Borrowed...");
		}
		System.out.println("---------------------------------\n");
	}
}
