package com.saran.librarymanagement.borroworreturing;

import java.util.Scanner;

import com.saran.librarymanagement.common.CommonView;
import com.saran.librarymanagement.enums.BookStatus;
import com.saran.librarymanagement.enums.ErrorCode;
import com.saran.librarymanagement.report.ReportModel;

public class BorrowOrReturingView {

	BorrowOrReturingModel borrowOrReturingModel;

	public BorrowOrReturingView() {

		this.borrowOrReturingModel = new BorrowOrReturingModel(this);
	}

	public void init() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("Borrow / Returing Management");
		System.out.println("-----------------------------");
		System.out.println("1 -> Borrow Book");
		System.out.println("2 -> Retuning Book");
		System.out.println("3 -> View Borrowed Book");
		System.out.println("9 -> Back");

		System.out.print("Enter choice : ");
		String choice = scanner.next();

		if (choice.equals("1")) {
			borrowBook();
		} else if (choice.equals("2")) {
			returningBook();
		} else if (choice.equals("3")) {
			viewBorrowBooks();
		} else if (choice.equals("9")) {

		} else {
			System.out.println("Enter Correct Option...");
			init();
		}
	}

	private void viewBorrowBooks() {

	}

	private void returningBook() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Book ID : ");

		try {
			int bookId = scanner.nextInt();
			borrowOrReturingModel.returningBook(bookId);
		} catch (Exception e) {
			System.out.println("Enter correct Book Id");
		}
	}

	private void borrowBook() {

		int bookLen = new CommonView().getAvailableBooks();

		try {
			if (bookLen <= 0) {
				System.out.println("\n------------Info-----------");
				System.out.println("No Books are Avaibels...");
				System.out.println("-----------------------------\n");
				init();
			} else {
				Scanner scanner = new Scanner(System.in);
				System.out.print("Enter Book ID : ");
				int bookId = scanner.nextInt();
				borrowOrReturingModel.onAssignBook(bookId);
			}

		} catch (Exception e) {
			System.out.println("Enter correct Book Id");
		}
	}

	public void showAlert(String errorCode) {
		System.out.println(errorCode);
		checkForInit();
	}

	private void checkForInit() {

		System.out.print("Do you want to continue Borrow / Returing Management? \nType Yes/No : ");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();

		if (choice.equalsIgnoreCase("yes")) {
			init();
		} else if (choice.equalsIgnoreCase("no")) {
			System.out.println("\n Thanks for adding users");
		} else {
			System.out.println("\nInvalid choice, Please enter valid choice.\n");
			checkForInit();
		}
	}
}
