package com.saran.librarymanagement.librarysetup;
import java.util.ArrayList;
import java.util.Scanner;

import com.saran.librarymanagement.borroworreturing.BorrowOrReturingView;
import com.saran.librarymanagement.enums.searchType;
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
	}

	public void init() {

		librarySetupModel.startUp();
	}

	public void initialSetup() {

		Scanner scanner = new Scanner(System.in);
		Library libraryInfo = new Library();
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

	public void onSetupCompleted(ArrayList<Library> library) {

		System.out.println("Library setup completed");
//		System.out.printf("\nCurrent Library Name - %s", library.get(0).getName());
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print(
					"\n 1. Book Management"
					+ "\n 2. User Management"
					+ "\n 3. Search Book"
					+ "\n 4. Borrow Or Returning"
					+ "\n 5. Report "
					+ "\n 8. Logout \n 9. Exit \n Enter your Choice:");
			String choice = scanner.next();

			switch (choice) {
			case "1":
//				new ManageBookView().init();
				new ManageBookView().manageBookOption();
				break;
			case "2":
//				new ManageUserView().init();
				new ManageUserView().manageUserOption();
				break;
			case "3":
				new BookSearchView().bookSearchInit();
				break;
			case "4":
				new BorrowOrReturingView().init();
				break;
			case "5":
				new ReportView().reportInit();
				break;
			case "8":
				System.out.println("-- You are logged out successfully -- \n");
				new LoginView().init();
				break;
			case "9":
				System.out.println("Thank You");
				return;
			default:
				System.out.println("Please Enter valid choice\n");
			}
		}
	}
}
