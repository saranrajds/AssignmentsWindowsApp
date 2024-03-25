package com.saran.librarymanagement;

import com.saran.librarymanagement.login.LoginView;

public class LibraryManagement {

	private static LibraryManagement libraryManagement;

	private String appName = "Library Management.";
	private String appVersion = "0.1.0.";

	private static LibraryManagement getInstance() {

		if (libraryManagement == null) {
			libraryManagement = new LibraryManagement();
		}

		return libraryManagement;
	}

	public String getAppName() {
		return appName;
	}

	public String getAppVersion() {
		return appVersion;
	}
	
	private void start() {
		LoginView loginView = new LoginView();
		loginView.init();
	}
	
	public static void main(String[] args) {
		
		LibraryManagement.getInstance().start();
	}
}