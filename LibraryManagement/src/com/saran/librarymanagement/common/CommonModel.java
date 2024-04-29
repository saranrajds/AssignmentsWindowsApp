package com.saran.librarymanagement.common;

import java.util.List;

import com.saran.librarymanagement.Repository.BorrowedBookByUser;
import com.saran.librarymanagement.enums.ModuleType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Book;

public class CommonModel {

	private CommonView commonView;
	public CommonModel(CommonView commonView) {
		this.commonView = commonView;
	}

	
	public int getAvailableBooks() {

		List<Book> books = LibraryDatabase.getInstance().getAvailableBooks();

		if (books.size() > 0) {
			commonView.getAvailableBooks(books);
		} else {
			commonView.showAlert("No Books are Avaibels...");
		}
		
		return books.size();
	}
	
	public void retriveBook() {

		if (!LibraryDatabase.getInstance().isBookFileRetrived()) {
			LibraryDatabase.getInstance().setBookFileRetrived(true);
			LibraryDatabase.getInstance().retriveDataFromFile(ModuleType.BOOK.getModuleType());
		}
	}

	public void showBorrowBookList() {
		List<BorrowedBookByUser> borrowedBooks = LibraryDatabase.getInstance().getUserBorrowedBook();
		
		if(borrowedBooks.size() > 0) {
			commonView.showBorrowBook(borrowedBooks);
		}
		else {
			commonView.showAlert("No Borrowed Book are available...");
		}
	}
}
