package com.saran.interviewmanagement;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.saran.enums.InterviewProgressStatus;
import com.saran.model.Candidate;

public class InterviewView {

	InterviewModel interviewModel;
	private static int currentCandidateId = -1;

	public InterviewView() {

		this.interviewModel = new InterviewModel(this);
	}

	public void init() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("-------------Interview Maganement------------");
		System.out.println("1 -> Start Interview \n2 -> Current Interview Status \n3 -> Update Interview Status");
		System.out.println("9 -> Exit");
		System.out.print("Enter Your Choice : ");

		try {

			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				onStartInterview();
				break;
			case 2:
				onGetCurrentInterviewStatus();
				break;
			case 3:
				updateInterviewStatus();
				break;
			case 9:
				return;
			default:
				showAlert("Enter correct choich...");
				break;
			}
		} catch (InputMismatchException e) {
			System.out.println("Please enter a valid integer choice...");
			init();
		}

	}

	private void updateInterviewStatus() {

		if (currentCandidateId > 0) {
			interviewModel.updateInterviewStatus(currentCandidateId);
		} else {
			showAlert("No Interview is Going...");
		}
		currentCandidateId = -1;
	}

	private void onGetCurrentInterviewStatus() {
		interviewModel.onGetCurrentInterviewStatus();
	}

	private void onStartInterview() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter HR Name : ");
		String hrName = scanner.nextLine();
		System.out.print("\nEnter Candidate Name : ");
		String candidateName = scanner.nextLine();
		interviewModel.isValidate(hrName, candidateName);
	}

	public void showAlert(String message) {

		System.out.println("---------------------");
		System.out.println(message);
		System.out.println("----------------------");
		checkForInterviewManagement();
	}

	public void onShowCurrentInterviewStatus(Candidate candidate, String hrName) {
		currentCandidateId = candidate.getCandidateId();
		System.out.println("------------Current Interview Status---------------");
		System.out.printf("%s %20s %20s", "Candidate Name", "HR Name", "Interview Status");
		System.out.printf("\n%s %30s %20s", candidate.getName(), hrName,
				InterviewProgressStatus.getByValue(candidate.getInterviewProgressStatus()));
		System.out.println("\n---------------------------------------------------");
	}

	private void checkForInterviewManagement() {

		System.out.println("\nDo you want to add more books? \nType Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("yes")) {
			init();
		} else if (choice.equalsIgnoreCase("no")) {
			System.out.println("\n Thanks for adding Candidate");
		} else {
			System.out.println("\nInvalid choice, \nPlease enter valid choice.\n");
			checkForInterviewManagement();
		}
	}
}
