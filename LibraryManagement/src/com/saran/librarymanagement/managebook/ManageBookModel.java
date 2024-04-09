package com.saran.librarymanagement.managebook;

import java.util.List;

import com.saran.librarymanagement.enums.BookStatus;
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

		if (isFromNewBookAdd) {
			boolean isCheckBookStatus = LibraryDatabase.getInstance().insertBook(book);

			if (isCheckBookStatus) {
				message = "Book  %s added successfully";
				manageBookView.onBookAdded(book, message);
			} else {
				message = "Book %s already exist";
				manageBookView.onBookExist(book, message);
			}
			manageBookView.checkForAddNewBook();
		} else {

			boolean isCheckBookStatus = LibraryDatabase.getInstance().updateBook(book);

			if (isCheckBookStatus) {
				message = "Book  %s updated successfully";
				manageBookView.onBookAdded(book, message);
			} else {
				message = "Entet Book ( %s ) is invalid...";
				manageBookView.onBookAdded(book, message);
			}
			manageBookView.manageBookOption();
		}
	}

	public void onDeleteBook(int bookId) {

		BookStatus bookStatus = LibraryDatabase.getInstance().onDeleteBook(bookId);
		String userName = LibraryDatabase.getInstance().getUserByBookId(bookId);

		if (bookStatus.getBookStatus() == BookStatus.NOT_AVAILABLE.getBookStatus()) {
			manageBookView.aletMessage(userName + " Already Borrowe the book..");
		} else if (bookStatus.getBookStatus() == BookStatus.NOT_FOUND.getBookStatus()) {
			manageBookView.aletMessage(bookId + " Book Not Found..");
		} else if (bookStatus.getBookStatus() == BookStatus.NOT_DELETED.getBookStatus()) {
			manageBookView.aletMessage(bookId + " Something Wrong Please try again..");
		} else {
			manageBookView.aletMessage(userName + " Book Removed Successfully..");
		}
		
		manageBookView.manageBookOption();
	}

	public void retriveBook() {

		if (!LibraryDatabase.getInstance().isBookFileRetrived()) {
			LibraryDatabase.getInstance().setBookFileRetrived(true);
			LibraryDatabase.getInstance().retriveDataFromFile(ModuleType.BOOK.getModuleType());
		}
	}
}
