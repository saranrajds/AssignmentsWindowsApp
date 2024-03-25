package com.saran.loginmanagement;

import com.saran.enums.UserType;
import com.saran.interivewdb.InterviewDB;
import com.saran.model.Credentials;

public class LoginModel {

	private LoginView loginView;

	public LoginModel(LoginView loginView) {
		this.loginView = loginView;
	}

	public void isValidate(Credentials credential) {

		int loginStatus = InterviewDB.getInstance().isValidCredential(credential);
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
//		return userName.equals("1");
//	}

//	private boolean isValidPassword(String password) {
//		return password.equals("1");
//	}
}
