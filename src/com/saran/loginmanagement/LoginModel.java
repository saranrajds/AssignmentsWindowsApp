package com.saran.loginmanagement;

import com.saran.loginmanagement.*;

public class LoginModel {

	private LoginView loginView;

	public LoginModel(LoginView loginView) {
		this.loginView = loginView;
	}

	public void isValidate(String userName, String password) {

		if (isValidUserName(userName)) {
			if (!isValidPassword(password)) {
				loginView.alertMessage("password is Wrong");
			} else {

				loginView.onSuccessAlert("Login Sucess");
			}
		} else {
			loginView.alertMessage("UserName Wrong");
		}
	}

	private boolean isValidUserName(String userName) {
		return userName.equals("123");
	}

	private boolean isValidPassword(String password) {
		return password.equals("123");
	}
}
