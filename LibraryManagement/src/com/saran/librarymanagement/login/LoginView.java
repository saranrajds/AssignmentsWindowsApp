package com.saran.librarymanagement.login;

import java.util.Scanner;
import com.saran.librarymanagement.librarysetup.LibrarySetupView;
import com.saran.librarymanagement.model.Credentials;

public class LoginView {

	private LoginModel loginModel;

	public LoginView() {
		loginModel = new LoginModel(this);
	}

	public void init() {

		Scanner scanner = new Scanner(System.in);
		Credentials credential = new Credentials();
		System.out.print("Enter UserName : ");
		credential.setUserName(scanner.next());
		System.out.print("Enter Password : ");
		credential.setPassword(scanner.next());
		loginModel.isValidate(credential);
	}

	public void alertMessage(String message) {
		System.out.println(message);
		init();
	}

	public void onSuccessAlert(String message) {
		System.out.println(message);
		LibrarySetupView librarySetupView = new LibrarySetupView();
		librarySetupView.init();	
	}
}
