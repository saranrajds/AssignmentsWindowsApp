package com.saran;

import java.io.IOException;

import com.saran.interivewdb.InterviewDB;
import com.saran.loginmanagement.LoginView;

public class InterviewPanelManagement {

	private static InterviewPanelManagement interviewPanelManagement;
	private String appName = "Interview Panel Application";
	private String appVersion = "0.1.0";

	private static InterviewPanelManagement instance() {

		if (interviewPanelManagement == null)
			interviewPanelManagement = new InterviewPanelManagement();

		return interviewPanelManagement;
	}

	private void init() throws IOException {

		LoginView loginView = new LoginView();
		loginView.init();
	}

	public static void main(String[] args) throws IOException {

		InterviewPanelManagement.instance().init();
	}
}
