package com.saran.librarymanagement.report;

import java.util.List;

import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;

public class ReportModel {

	private ReportView reportView;
	
	public ReportModel(ReportView reportView) {
		this.reportView = reportView;
	}

	public void getBookList() {
		
		List<Book> books = LibraryDatabase.getInstance().getBooks();

		if (books.size() > 0) {
			reportView.showBooksList(books);
		} else {
			reportView.showAlert("No Data Found");
		}
	}
	
	public void getUserList() {
		
		List<User> users = LibraryDatabase.getInstance().getUsers();
		
		if (users.size() > 0) {
			reportView.showUserList(users);
		} else {
			reportView.showAlert("No Data Found");
		}
	}

	public void getParticularUserList(int userType) {
		
		List<User> users = LibraryDatabase.getInstance().getParticularUserList(userType);
		
		if (users.size() > 0) {
			reportView.getParticularUserList(users, userType);
		} else {
			reportView.showAlert("No Data Found");
		}
	}
	
	public int getUserType() {
		return LibraryDatabase.getInstance().getCurrentUserType();
	}
}
