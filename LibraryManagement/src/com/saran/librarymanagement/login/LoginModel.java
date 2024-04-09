package com.saran.librarymanagement.login;

import com.saran.librarymanagement.enums.ModuleType;
import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Credentials;

class LoginModel {

	private LoginView loginView;
	public LoginModel(LoginView loginView) {
		this.loginView = loginView;
	}
	
	public void isValidateUser(Credentials credential) {
		int loginStatus = LibraryDatabase.getInstance().isValidCredential(credential);
		showLoginStatus(loginStatus, credential);
	}
	
	public void showLoginStatus(int loginStatus, Credentials credential) {
		
		if(loginStatus == UserType.ADMIN.getUserType()){
			loginView.onSuccessAlertByAdmin("Login Success for Admin ( "+ credential.getEmailId() +" )");	
		}
		else if(loginStatus == UserType.LIBRARY_ADMIN.getUserType()) {
			retriveBooks();
			loginView.onSuccessAlertByLibraryAdmin("Login Sucess for Librariant ( "+ credential.getEmailId() +" )");			
		}
		else if(loginStatus == UserType.USER.getUserType()) {
			retriveBooks();
			loginView.onSuccessAlertByUser("Login Sucess for User ( "+ credential.getEmailId() +" )");			
		}
		else {
			loginView.alertMessage("UserName or password Wrong");
		}
	}

	public void retriveBooks() {
		LibraryDatabase.getInstance().retriveDataFromFile(ModuleType.BOOK.getModuleType());
	}
	
	public void ritriveUsers() {
		LibraryDatabase.getInstance().retriveDataFromFile(ModuleType.USER.getModuleType());
	} 
	
	public void ritriveLibraryInfo() {
		LibraryDatabase.getInstance().retriveDataFromFile(ModuleType.LIBRARIY.getModuleType());	
	} 
//	private boolean isValidUserName(String userName) {
//		return userName.equals("123");
//	}
//	
//	private boolean isValidPassword(String password) {
//		return password.equals("123");
//	}

	public void resetLoginSession() {
		LibraryDatabase.getInstance().resetLoginSession();
	}

}
