package com.saran.librarymanagement.manageusers;

import java.util.List;
import java.util.Scanner;
import com.saran.librarymanagement.enums.SearchReportType;
import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.model.User;
import com.saran.librarymanagement.searchbook.BookSearchView;

public class ManageUserView {

	private ManageUserModel manageUserModel;

	public ManageUserView() {
		this.manageUserModel = new ManageUserModel(this);
	}

	public void init(boolean isFromNewUserAdd) {
		System.out.println("Enter the following details");
		User user = new User();
		Scanner scanner = new Scanner(System.in);

		if (!isFromNewUserAdd) {
			System.out.print("\nEnter User Id :");
			user.setId(scanner.nextInt());
//			scanner.next();
		}

		System.out.print("Enter UserName : ");
		user.setName(scanner.next());
		System.out.print("Enter Password : ");
		user.setPassword(scanner.next());
		System.out.print("Enter Email : ");
		user.setEmailId(scanner.next());
		user.setUserType(UserType.USER.getUserType());
		
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

	private void checkForAddNewUser() {

		System.out.print("Do you want to add more users? \nType Yes/No : ");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();

		if (choice.equalsIgnoreCase("yes")) {
			init(true);
		} else if (choice.equalsIgnoreCase("no")) {
			System.out.println("\n Thanks for adding users");
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
		
		System.out.println("Manege User Option");
		System.out.println("------------------");
		System.out.print("1 -> Add User");
		System.out.print("\n2 -> Edit User");
		System.out.print("\n3 -> Delete User");
		System.out.print("\n9 -> Back");
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
		case "9":
			return;
		default:
			System.out.println("Enter correct option");
			break;
		}
	}

	private void onDeleteUser() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter User Id : ");
		int userId = scanner.nextInt();
		manageUserModel.onDeleteUser(userId);
	}

	public void aletMessage(String message) {
		System.out.println(message);
	}

	public void showSearchUsers(List<User> users) {

	}
	
	public void checkForUserManagement() {

		System.out.println("\nDo you want to Continue to book Management? \nType Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("yes")) {
			manageUserOption();
		} else if (choice.equalsIgnoreCase("no")) {
			return;
		} else {
			System.out.println("\nInvalid choice, \nPlease enter valid choice.\n");
			checkForUserManagement();
		}
	}
}
