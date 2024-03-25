package com.saran.librarymanagement.searchbook;

import java.util.List;
import java.util.Scanner;

import com.saran.librarymanagement.enums.searchType;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;

public class BookSearchView {

	private BookSearchModel searchBookModel;

	public BookSearchView() {

		this.searchBookModel = new BookSearchModel(this);
	}

	public void init(String bookAuthorName, int seachType) {
		searchBookModel.searchBook(bookAuthorName, seachType);
	}

	public void init(int searchTyp) {
		searchBookModel.searchBook(searchTyp);
	}

	public void showAlert(String message) {
		System.out.println(message);
		System.out.println("---------------");
	}

	public void showSearchBooks(List<Book> bookList) {

		for (Book book : bookList) {
			System.out.printf("Book Id : %d, Book Name : %s, Book Author : %s \n", book.getId(), book.getName(),
					book.getAuthor());
		}
	}

	public void showSearchUser(List<User> userList) {

		for (User user : userList) {
			System.out.printf("User Id : %d, User Name : %s, User Email : %s \n", user.getId(), user.getName(),
					user.getEmailId());
		}
	}

	public void bookSearchInit() {

		System.out.println(
				"-------Search Book Using------- \n1 -> Book Name \n2 -> Book Author \n Enter your choice (100 or 200) : ");

		Scanner scanner = new Scanner(System.in);
		int searchChoice = scanner.nextInt();
		scanner.nextLine();

		if (searchType.NAME.getSeachType() == searchChoice) {
			
			System.out.print("Enter Search Book Name : ");
			String bookName = scanner.nextLine();
			init(bookName, searchType.NAME.getSeachType());
		} else if (searchType.AUTHOR.getSeachType() == searchChoice) {
			
			System.out.print("Enter Search Book Author Name : ");
			String bookAuthorName = scanner.nextLine();
			init(bookAuthorName, searchType.AUTHOR.getSeachType());
		} else {
			
			System.out.println("Invalid search choice.");
		}
	}
}
