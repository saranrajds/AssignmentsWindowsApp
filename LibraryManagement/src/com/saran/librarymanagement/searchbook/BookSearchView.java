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

		System.out.println("\n--------------Books-------------------");
		System.out.printf("Id \tName \t\tBook Author \tVolume");				
				
		for (Book book : bookList) {
			System.out.println(book.getId()+"\t"+ book.getName() +"\t\t"+ book.getAuthor()+"\t"+book.getVolume());
		}
		System.out.println("-------------------------------------------\n");
	}

	public void showSearchUser(List<User> userList) {

		System.out.println("\n--------------Users-------------------");			
		System.out.printf("Id \tName \t\tEmail \tCreated Date ");		
		
		for (User user : userList) {
			System.out.println(user.getId() +"\t"+ user.getName() +"\t"+ user.getEmailId() +"\t"+user.getCreatedDate());
		}
		System.out.println("-------------------------------------------\n");
	}

	public void bookSearchInit() {

		System.out.println(
						"-------Search Book Using------- "
						+ "\n1 -> Book Name "
						+ "\n2 -> Book Author ");
		System.out.print("\n Enter your choice (100 or 200) : ");

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
		checkForSearch();
	}
	
	public void checkForSearch() {

		System.out.println("\nDo you want to Continue to Search? \nType Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("yes")) {
			bookSearchInit();
		} else if (choice.equalsIgnoreCase("no")) {
			return;
		} else {
			System.out.println("\nInvalid choice, \nPlease enter valid choice.\n");
			checkForSearch();
		}
	}
	
}
