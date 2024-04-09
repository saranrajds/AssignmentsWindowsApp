package com.saran.librarymanagement.managebook;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.saran.librarymanagement.enums.SearchReportType;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.searchbook.BookSearchView;

public class ManageBookView {

	private ManageBookModel manageBookModel;

	public ManageBookView() {
		manageBookModel = new ManageBookModel(this);
	}

	public void manageBookOption() {

		Scanner scanner = new Scanner(System.in);
		manageBookModel.retriveBook();
		
		System.out.println("\nManege Book Option");
		System.out.println("------------------");
		System.out.print("1 -> Add Book");
		System.out.print("\n2 -> Edit Book");
		System.out.print("\n3 -> Delete Book");
		System.out.print("\n9 -> Back");
		System.out.print("\nEnter UR Choich : ");
		String bookChoich = scanner.next();
		boolean isFromBook = true;
		
		switch (bookChoich) {
		case "1":
			init(true);
			break;
		case "2":
			new BookSearchView().init(SearchReportType.Book.getSearchReportType());
			init(false);
			break;
		case "3":
			new BookSearchView().init(SearchReportType.Book.getSearchReportType());
			onDeleteBook();
			break;
		case "9":
			return;
		default:
			System.out.println("Enter correct option");
			break;
		}
	}

	private void onDeleteBook() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Book Id : ");
		try {
			int bookId = scanner.nextInt();
			manageBookModel.onDeleteBook(bookId);
		} catch (InputMismatchException e) {
			System.out.println("Please enter correct choice");
			checkForAddNewBook();
		}
	}

	public void init(boolean isFromNewBookAdd) {

		System.out.print("\nEnter book details: ");
		System.out.println("\n-----------------------");
		Scanner scanner = new Scanner(System.in);
		Book book = new Book();

		if (!isFromNewBookAdd) {
			System.out.print("\nEnter book Id :");
			book.setId(scanner.nextInt());
			scanner.nextLine();
		}
		
		System.out.print("\nEnter book name:");
		book.setName(scanner.nextLine());
		System.out.print("\nEnter book author:");
		book.setAuthor(scanner.nextLine());
		System.out.print("\nEnter book Volume:");
		book.setVolume(scanner.nextLine());
		System.out.print("\nEnter book Publication:");
		book.setPublication(scanner.nextLine());
		manageBookModel.addNewBook(book, isFromNewBookAdd);
		
	}

	public void aletMessage(String message) {
		System.out.println(message);
	}

	public void onBookAdded(Book book, String message) {
		System.out.printf("\n------- " + message + " -------\n", book.getName());
	}

	public void onBookExist(Book book, String message) {

		String bookInfo = "( " +book.getName() +", "+book.getVolume() +" )";
		System.out.printf("\n-------  "+ message +"  -------\n", bookInfo);
	}

	public void checkForAddNewBook() {

		System.out.println("\nDo you want to add more books? \nType Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("yes")) {
			init(true);
		} else if (choice.equalsIgnoreCase("no")) {
			manageBookOption();
//			System.out.println("\n Thanks for adding books");
		} else {
			System.out.println("\nInvalid choice, Please enter valid choice.\n");
			checkForAddNewBook();
		}
	}
}
