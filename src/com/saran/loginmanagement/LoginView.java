package com.saran.loginmanagement;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.saran.hrmanagement.HRView;
import com.saran.interviewmanagement.InterviewView;
import com.saran.managecandidate.ManageCandidateView;
import com.saran.reportmanagement.ReportView;

public class LoginView {

	private LoginModel loginModel;

	public LoginView() {
		this.loginModel = new LoginModel(this);
	}

	public void init() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter UserName : ");
		String userName = scanner.next();
		System.out.print("Enter Password : ");
		String password = scanner.next();
		loginModel.isValidate(userName, password);
	}

	public void alertMessage(String message) {

		System.out.println(message);
		init();
	}

	public void onSuccessAlert(String message) {

		System.out.println(message);
		showMenus();
	}

	public void showMenus() {

		System.out.println("Interview Panel Module");
		Scanner scanner = new Scanner(System.in);
		boolean isShowMenu = true;

		while (isShowMenu) {

			System.out.println("-----------------------");
			System.out.println("1 -> Manage Candidate " + "\n2 -> Manage HR " + "\n3 -> Interview Management");
			System.out.println("4 -> Report");
			System.out.println("9 -> Exit");
			System.out.print("Enter Your Choice : ");

			try {
				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					new ManageCandidateView().init();
					break;
				case 2:
					new HRView().init();
					break;
				case 3:
					new InterviewView().init();
					break;
				case 4:
					new ReportView().initReport();
					break;
				case 9:
					System.out.println("Thank You");
					isShowMenu = false;
					break;
				default:
					System.out.println("Please Enter valid choice");
				}
			} catch (InputMismatchException e) {
				showAlert("Please enter a valid integer choice...");
				showMenus();
			}
		}
	}

	private void showAlert(String message) {
		System.out.println(message);
	}

}
