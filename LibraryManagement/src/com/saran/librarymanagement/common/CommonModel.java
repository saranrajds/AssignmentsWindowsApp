package com.saran.librarymanagement.common;

import java.util.List;

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
			commonView.showAlert("No Data Found");
		}
		
		return books.size();
	}
}
