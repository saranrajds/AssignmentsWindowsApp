package com.saran.librarymanagement.manageusers;

import java.util.List;
import java.util.Scanner;
import com.saran.librarymanagement.enums.SearchReportType;
import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.login.LoginView;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;
import com.saran.librarymanagement.searchbook.BookSearchView;

public class ManageUserView {

	private ManageUserModel manageUserModel;

	public ManageUserView() {
		this.manageUserModel = new ManageUserModel(this);
	}

	public void init(boolean isFromNewUserAdd) {

		System.out.println("\nEnter the following details");
		User user = new User();
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter Email : ");
		user.setEmailId(scanner.next());
		scanner.nextLine();
		System.out.print("\nEnter UserName : ");
		user.setName(scanner.nextLine());
		System.out.print("Enter Password : ");
		user.setPassword(scanner.next());
		
		if (manageUserModel.getCurrentUserType() == UserType.ADMIN.getUserType()) {
			user.setUserType(UserType.LIBRARY_ADMIN.getUserType());
		} else if (manageUserModel.getCurrentUserType() == UserType.LIBRARY_ADMIN.getUserType()) {
			user.setUserType(UserType.USER.getUserType());
		} else {
			user.setUserType(UserType.INVALID.getUserType());
		}

		System.out.println("manageUserModel.getCurrentUserType() " + manageUserModel.getCurrentUserType());
		System.out.println(user.getUserType());
//		if(isFromNewUserAdd)
		manageUserModel.addNewUser(user, isFromNewUserAdd);
	}

	public void onUserAdded(User user) {
		System.out.printf("User %s Added Successfully...\n", user.getName());
		checkForAddNewUser();
	}

	public void onUserExist(User user) {
		System.out.printf("User %s already exist...\n", user.getName());
		checkForAddNewUser();
	}

	public void checkForAddNewUser() {

		System.out.print("Do you want to add more users? \nType Yes/No : ");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();

		if (choice.equalsIgnoreCase("yes")) {
			init(true);
		} else if (choice.equalsIgnoreCase("no")) {
			manageUserOption();
//			System.out.println("\n Thanks for adding users");
		} else {
			System.out.println("\nInvalid choice, Please enter valid choice.\n");
			checkForAddNewUser();
		}
	}

	public void getBooks() {
//		manageUserModel.getUsers();
	}

	public void manageUserOption() {

		Scanner scanner = new Scanner(System.in);
		manageUserModel.retriveUser();

		System.out.println("------------------");
		System.out.println("Manege User Option");
		System.out.println("------------------\n");
		System.out.print("1 -> Add User");
		System.out.print("\n2 -> Edit User");
		System.out.print("\n3 -> Delete User");

//		if (manageUserModel.getUserType() != UserType.ADMIN.getUserType())
			System.out.print("\n8 -> Back");
//		else {
//			System.out.print("\n9 -> Exit");
//		}

		System.out.print("\nEnter UR Choich : ");

		String userChoich = scanner.next();

		switch (userChoich) {
		case "1":
			init(true);
			break;
		case "2":
			new BookSearchView().init(SearchReportType.User.getSearchReportType());
			init(false);
			break;
		case "3":
			new BookSearchView().init(SearchReportType.User.getSearchReportType());
			onDeleteUser();
			break;
		case "8":
//			System.out.println(manageUserModel.getUserType() + " " + UserType.ADMIN.getUserType());
//			if (manageUserModel.getUserType() != UserType.LIBRARY_ADMIN.getUserType()) {
////				checkForUserManagement();
//				System.out.println("Enter correct option");
//			} else {
//				return;
//			}
			return;
		default:
			System.out.println("Enter correct option");
			manageUserOption();
		}
	}

	private void onDeleteUser() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("\n-----------------------------------");
		System.out.print("\nEnter User Email Id : ");
		String userEmailId = scanner.next();
		manageUserModel.onDeleteUser(userEmailId);
		manageUserOption();
	}

	public void aletMessage(String message) {
		System.out.println("\n-----------------------------");
		System.out.println(message);
		System.out.println("-----------------------------\n");
	}

	public void showSearchUsers(List<User> users) {

	}
//	
//	public void checkForUserManagement() {
//
//		System.out.println("\nDo you want to Continue to book Management? \nType Yes/No");
//		Scanner scanner = new Scanner(System.in);
//		String choice = scanner.next();
//		if (choice.equalsIgnoreCase("yes")) {
////			manageUserOption();
//			init(true);
//		} else if (choice.equalsIgnoreCase("no")) {
//			return;
//		} else {
//			System.out.println("\nInvalid choice, \nPlease enter valid choice.\n");
//			checkForUserManagement();
//		}
//	}

	public void showBorrowedBooks(List<Book> books, String userEmailId) {
		
		System.out.println("\n----------------------------------------");
		System.out.println("The "+ userEmailId +" User Borrowed Books");
		System.out.println("\n-----------------------------------------");
		System.out.println("Id \t Name \t\t Author");
		System.out.println("\n-----------------------------------------");
		for(Book book: books) 
		{
			System.out.println(book.getId() +"\t"+ book.getName()+"\t\t"+ book.getAuthor());
		}
		System.out.println("Submit all borrowed books before deleting...");
		System.out.println("\n------------------------------------------\n");
	}
}
