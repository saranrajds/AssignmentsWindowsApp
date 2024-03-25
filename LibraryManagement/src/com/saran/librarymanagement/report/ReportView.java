package com.saran.librarymanagement.report;

import java.util.List;
import java.util.Scanner;

import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;

public class ReportView {

	private ReportModel reportModel;
	
	public ReportView() {
		reportModel = new ReportModel(this);
	}
	
	public void reportInit() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Reports");
		
		System.out.println("1 -> Get Books List");
		System.out.println("2 -> Get Users List");
		System.out.println("9 -> Back");
		
		String choice = scanner.next();
		
		switch (choice) {
		case "1":
			getBooksList();
			break;
		case "2":
			getUserList();
			break;
		case "9":
			return;
		default:
			System.out.println("Enter correct choice...");
			break;
		}
	}

	private void getUserList() {
		reportModel.getUserList();	
	}

	private void getBooksList() {
		reportModel.getBookList();
	}

	public void showBooksList(List<Book> books) {

		for(Book book: books) {
			System.out.printf("Book Id : %d, Book Name : %s, Book Author : %s, Book Status : %s \n", 
					book.getId(), book.getName(), book.getAuthor(), book.getAvailability());
		}
		checkForReport();
	}
	
	public void showUserList(List<User> users) {
		
		for(User user: users) {
			System.out.printf("User Id : %d, User Name : %s, User Email : %s \n", 
					user.getId(), user.getName(), user.getEmailId());
		}
		checkForReport();
	}
	
	public void showAlert(String message) {
		System.out.println(message);
	}
	
	public void checkForReport() {

		System.out.println("\nDo you want to see report? \nType Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("yes")) {
			reportInit();
		} else if (choice.equalsIgnoreCase("no")) {
			System.out.println("\n Thanks for");
		} else {
			System.out.println("\nInvalid choice, Please enter valid choice.\n");
			checkForReport();
		}
	}
}
