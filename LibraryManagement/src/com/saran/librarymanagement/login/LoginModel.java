package com.saran.librarymanagement.login;

import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Credentials;

class LoginModel {

	private LoginView loginView;
	public LoginModel(LoginView loginView) {
		this.loginView = loginView;
	}
	
	public void isValidate(Credentials credential) {
		int loginStatus = LibraryDatabase.getInstance().isValidCredential(credential);
		showLoginStatus(loginStatus);
	}
	
	public void showLoginStatus(int loginStatus) {
		
		if(loginStatus == UserType.ADMIN.getUserType()){
			loginView.onSuccessAlert("Login Sucess");	
		}
		else if(loginStatus == UserType.USER.getUserType()) {
			loginView.onSuccessAlert("Login Sucess");
		}
		else {
			loginView.alertMessage("UserName or password Wrong");
		}
	} 
	
//	private boolean isValidUserName(String userName) {
//		return userName.equals("123");
//	}
//	
//	private boolean isValidPassword(String password) {
//		return password.equals("123");
//	}

}
