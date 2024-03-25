package com.saran.librarymanagement.searchbook;

import java.util.List;

import com.saran.librarymanagement.enums.SearchReportType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;

public class BookSearchModel {

	private BookSearchView searchBookView;
	
	public BookSearchModel(BookSearchView searchBookView) {
		this.searchBookView = searchBookView;
	}
	
	public void searchBook(String bookSearchValue, int seachType) {

		List<Book> bookList = LibraryDatabase.getInstance().getBook(bookSearchValue, seachType);
		showSearch(bookList);
	}
	
	public void searchBook(int searchTyp) {
		
		if(SearchReportType.Book.getSearchReportType() == searchTyp) {
			List<Book> bookList = LibraryDatabase.getInstance().getBooks();
			showSearch(bookList);
		}
		else {
			List<User> userList = LibraryDatabase.getInstance().getUsers();
			showSearchUser(userList);
		}
	}

	public void showSearchUser(List<User> userList) {
		
		if(userList.size() > 0) {
			searchBookView.showAlert("The Search Result");
			searchBookView.showSearchUser(userList);
		}
		else {
			searchBookView.showAlert("The Result Found...");
		}
	}
	
	public void showSearch(List<Book> bookList) {
		
		if(bookList.size() > 0) {
			searchBookView.showAlert("The Search Result");
			searchBookView.showSearchBooks(bookList);
		}
		else {
			searchBookView.showAlert("No Data Found...");
		}
	}
}
