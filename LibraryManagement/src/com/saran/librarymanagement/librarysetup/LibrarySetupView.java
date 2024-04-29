package com.saran.librarymanagement.librarysetup;

import java.util.ArrayList;
import java.util.Scanner;

import com.saran.librarymanagement.borroworreturing.BorrowOrReturingView;
import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.login.LoginView;
import com.saran.librarymanagement.managebook.ManageBookView;
import com.saran.librarymanagement.manageusers.ManageUserView;
import com.saran.librarymanagement.model.Library;
import com.saran.librarymanagement.report.ReportView;
import com.saran.librarymanagement.searchbook.BookSearchView;

public class LibrarySetupView {

	private LibrarySetupModel librarySetupModel;

	public LibrarySetupView() {
		librarySetupModel = new LibrarySetupModel(this);
		librarySetupModel.retriveLibraryData();
	}

	public void init() {
		librarySetupModel.retriveLibraryData();
		librarySetupModel.getLibraryInfo();
		librarySetupModel.startUp();
	}

	public void initialSetup() {

		Scanner scanner = new Scanner(System.in);
		Library libraryInfo = new Library();
		// librarySetupModel.getLibraryInfo();
		System.out.print("Enter Library Name : ");
		libraryInfo.setName(scanner.nextLine());
		System.out.print("Enter the Email Id : ");
		libraryInfo.setEmailId(scanner.next());
		librarySetupModel.createLibrary(libraryInfo);
	}

	public void showAlert(String message) {

		System.out.println(message);
		init();
	}

	public void onSetupCompleted() {

		System.out.println("Library setup completed");
		System.out.println("---------------------------");
		showLibraryName();
		onLoadModules();
	}

	public void showLibraryName() {
		ArrayList<Library> library = librarySetupModel.getLibraryInfo();
		System.out.printf("\nCurrent Library Name - %s", library.get(0).getName());
		System.out.println("\n-----------------------------------------");
		
	}
	
	public void onLoadModules() {

		Scanner scanner = new Scanner(System.in);
		int userType = librarySetupModel.getUserType();
		System.out.println("\n");
		if (userType == UserType.LIBRARY_ADMIN.getUserType()) {
			System.out.println("1. Book Management");
			System.out.println("2. User Management");
		}

		System.out.println("3. Search Book");
		if (userType == UserType.USER.getUserType()) {
			
			System.out.println("4. Borrow / Returning");
		}
		
		System.out.println("5. Report");
		System.out.println("8. Logout");
		System.out.println("9. Exit");
		System.out.print("Enter your Choice:");
		String choice = scanner.next();

		switch (choice) {
		case "1":
			if (userType == UserType.LIBRARY_ADMIN.getUserType())
				new ManageBookView().manageBookOption();
			else
				System.out.println("Please Enter valid choice\n");
			onLoadModules();
			break;
		case "2":
			if (userType == UserType.LIBRARY_ADMIN.getUserType())
				new ManageUserView().manageUserOption();
			else
				System.out.println("Please Enter valid choice\n");
			onLoadModules();
			break;
		case "3":
			new BookSearchView().bookSearchInit();
			onLoadModules();
			break;
		case "4":
			if (userType == UserType.USER.getUserType())  {
				new BorrowOrReturingView().init();
			}
			else {
				System.out.println("Please Enter valid choice\n");
			}
			onLoadModules();
			break;
		case "5":
			new ReportView().reportInit();
			onLoadModules();
			break;
		case "8":
			System.out.println("-- You are logged out successfully -- \n");
			new LoginView().init();
			break;
		case "9":
			System.out.println("Thank You");
			return;
		default:
			System.out.println("Sorry Your Input is not Valid..");
			onLoadModules();
		}
	}

	public void checkForAddNewBook() {

		System.out.println("\nDo you want to Continue? \nType Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("yes")) {
			onSetupCompleted();
		} else if (choice.equalsIgnoreCase("no")) {
			System.out.println("\nThanks");
		} else {
			System.out.println("\nInvalid choice, Please enter valid choice.\n");
			checkForAddNewBook();
		}
	}
}
