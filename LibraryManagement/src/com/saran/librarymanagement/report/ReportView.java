package com.saran.librarymanagement.report;

import java.util.List;
import java.util.Scanner;

import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;

public class ReportView {

	private ReportModel reportModel;
	
	public ReportView() {
		reportModel = new ReportModel(this);
	}
	
	public void reportInit() {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("\n------------Reports----------");		
		System.out.println("1 -> Get Books List");
		System.out.println("2 -> Get Users List");
		System.out.println("9 -> Back");
		System.out.print("\nEnter Your Choice : ");
		String choice = scanner.next();
		
		switch (choice) {
		case "1":
			getBooksList();
			reportInit();
			break;
		case "2":
			getUserList();
			reportInit();
			break;
		case "9":
			return;
		default:
			System.out.println("Enter correct choice...");
			reportInit();
			break;
		}
	}

	private void getUserList() {
		reportModel.getUserList();
//		checkForReport();
	}
	
	public void getParticularUserList(int userType) {
		reportModel.getParticularUserList(userType);	
	}

	private void getBooksList() {
		reportModel.getBookList();
	}

	public void showBooksList(List<Book> books) {

		System.out.println("\n------------------- Books ----------------------\n");
		System.out.println("Id \tName \t\tBook Author \tBook Status  \tCreated Date ");
		System.out.println("-----------------------------------------------------");
		for(Book book: books) {
			System.out.println(book.getId() +"\t"+ book.getName() +"\t"+ book.getAuthor() +"\t"+ book.getAvailability()+"\t"+book.getModifyDate());
		}
		System.out.println("-----------------------------------------------------");
	}
	
	public void showUserList(List<User> users) {
		
		System.out.println("\n------------------- Users ----------------------\n");
		System.out.println("Id\tName \tEmail \t\tStatus \tCreated Date ");
		System.out.println("-----------------------------------------------------");
		for(User user: users) {
			System.out.println(user.getId() +"\t"+ user.getName() +"\t"+ user.getEmailId() +"\t"+ user.isActive()+"\t"+user.getModifyDate());
		}
		System.out.println("-----------------------------------------------------");
//		checkForReport();
	}
	
	public void getParticularUserList(List<User> users, int userType) {
		
		System.out.println("\n------------------- Librarients ----------------------\n");
		System.out.println("Id\tName \tEmail \t\tStatus \tCreated Date ");
		System.out.println("-----------------------------------------------------");
		for(User user: users) {
			System.out.println(user.getId() +"\t"+ user.getName() +"\t"+ user.getEmailId() +"\t"+ user.isActive()+"\t"+user.getModifyDate());
		}
		System.out.println("-----------------------------------------------------");
	}
	
	public void showAlert(String message) {
		System.out.println("\n-----------------------");
		System.out.println(message);
	}
	
	public void checkForReport() {

		System.out.println("\nDo you want to see report? \nType Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("yes")) {
			reportInit();
		} else if (choice.equalsIgnoreCase("no")) {
			return;
//			System.out.println("\n Thanks for");
		} else {
			System.out.println("\nInvalid choice, Please enter valid choice.\n");
			checkForReport();
		}
	}
}
