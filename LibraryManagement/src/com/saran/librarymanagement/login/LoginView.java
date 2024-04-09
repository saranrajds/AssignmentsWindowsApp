package com.saran.librarymanagement.login;

import java.util.Scanner;

import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.librarysetup.LibrarySetupView;
import com.saran.librarymanagement.manageusers.ManageUserView;
import com.saran.librarymanagement.model.Credentials;
import com.saran.librarymanagement.report.ReportModel;
import com.saran.librarymanagement.report.ReportView;

public class LoginView {

	private LoginModel loginModel;
	private LibrarySetupView librarySetupView;

	public LoginView() {
		loginModel = new LoginModel(this);
		loginModel.ritriveUsers();		
	}

	public void init() {
		loginModel.resetLoginSession();
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nPlease login to proceed.");
		System.out.println("--------------------------");
		Credentials credential = new Credentials();
		System.out.print("Enter EmailId : ");
		credential.setEmailId(scanner.next());
		System.out.print("Enter Password : ");
		credential.setPassword(scanner.next());
		loginModel.isValidateUser(credential);
	}

	LibrarySetupView getLibraryInstance() {

		if (librarySetupView == null)
			librarySetupView = new LibrarySetupView();

		return librarySetupView;
	}

	public void alertMessage(String message) {
		alertDisplayInfo(message);
		init();
	}

	public void alertDisplayInfo(String message) {

		System.out.println("------------------------------------\n");
		System.out.println(message);
		System.out.println("------------------------------------\n");
	}

	public void onSuccessAlertByAdmin(String message) {

		alertDisplayInfo(message);
		adminOption();
	}
	
	public void adminOption() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Select Option");
		System.out.println("--------------------");
		System.out.println("1 -> User Management");
		System.out.println("2 -> User Report");
		System.out.println("8 -> Exit");
		System.out.print("Enter Choice : ");
		String choice = scanner.next();
		
		if(choice.equals("1")) {
			new ManageUserView().manageUserOption();//isFromAdmin
//			init();
			adminOption();
		}
		else if (choice.equals("2")) {
			new ReportView().getParticularUserList(UserType.LIBRARY_ADMIN.getUserType());
			System.out.println("\n------------------------\n");
			adminOption();
		}
		else if (choice.equals("8")) {
			init();
		}
		else {
			System.out.println("\nEnter Correct Option...");
			System.out.println("\n------------------------\n");
			adminOption();
		}
	}

	public void onSuccessAlertByUser(String message) {

		alertDisplayInfo(message);
		getLibraryInstance().showLibraryName();
		getLibraryInstance().onLoadModules();
	}

	public void onSuccessAlertByLibraryAdmin(String message) {
		alertDisplayInfo(message);
		getLibraryInstance().init();
	}
}
