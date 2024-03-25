package com.saran.librarymanagement.managebook;

import java.util.List;

import com.saran.librarymanagement.enums.ModuleType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Book;

class ManageBookModel {
	
	private ManageBookView manageBookView;
	
	ManageBookModel(ManageBookView manageBookView) {
		this.manageBookView = manageBookView;
	}

	public void addNewBook(Book book, boolean isFromNewBookAdd) {
		
		String message = "";
		
		if(isFromNewBookAdd) {
			boolean isCheckBookStatus = LibraryDatabase.getInstance().insertBook(book);
			
			if(isCheckBookStatus) {
				message ="Book  %s added successfully";
				manageBookView.onBookAdded(book, message);
			}
			else  {
				manageBookView.onBookExist(book);
			}
			manageBookView.checkForAddNewBook();
		}
		else {

			boolean isCheckBookStatus = LibraryDatabase.getInstance().updateBook(book);
			
			if(isCheckBookStatus) {
				message ="Book  %s updated successfully";
				manageBookView.onBookAdded(book, message);
			}
			else  {
				message ="Entet Book ( %s ) is invalid...";
				manageBookView.onBookAdded(book, message);
			}
			manageBookView.manageBookOption();
		}
	}

	public void onDeleteBook(int bookId) {
		boolean hasBook = LibraryDatabase.getInstance().onDeleteBook(bookId);
		
		if (hasBook) {
			manageBookView.aletMessage(bookId+" Book Removed Successfully..");
		} else {
			manageBookView.aletMessage(bookId+" Book does not exits");
		}
		manageBookView.manageBookOption();
	}
	
	public void retriveBook() {
		
		if(!LibraryDatabase.getInstance().isBookFileRetrived()) {
			LibraryDatabase.getInstance().setBookFileRetrived(true);
			LibraryDatabase.getInstance().retriveDataFromFile(ModuleType.BOOK.getModuleType());
		}
	}
}
