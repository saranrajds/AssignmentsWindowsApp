package com.saran;

import com.saran.loginmanagement.*;

public class InterviewPanelManagement {

	private static InterviewPanelManagement interviewPanelManagement;
	private String appName = "Interview Panel Application";
	private String appVersion = "0.0.1";

	private static InterviewPanelManagement instance() {

		if (interviewPanelManagement == null)
			interviewPanelManagement = new InterviewPanelManagement();

		return interviewPanelManagement;
	}

	private void init() {
		
		LoginView loginView = new LoginView();
		loginView.init();
	}

	public static void main(String[] args) {

		InterviewPanelManagement.instance().init();
	}
}
