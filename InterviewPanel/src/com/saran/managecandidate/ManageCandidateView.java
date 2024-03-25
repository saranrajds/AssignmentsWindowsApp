package com.saran.managecandidate;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.saran.commonvalidation.CommonValidations;
import com.saran.model.Candidate;
import com.saran.reportmanagement.ReportView;

public class ManageCandidateView {

	private ManageCandidateMadel manageCandidateMadel;

	public ManageCandidateView() {
		this.manageCandidateMadel = new ManageCandidateMadel(this);
	}

	public void init() {
		
		
		manageCandidateMadel.retriveCandidate();
		Scanner scanner = new Scanner(System.in);
		System.out.print(
				"1 -> Add Candidate \n2 -> Update Candidate \n3 -> Remove Candidate \n9 -> Exit \nEnter Your Choich :");

		try {
			int choich = scanner.nextInt();

			switch (choich) {
			case 1:
				addCandidate(true);
				break;
			case 2:
				updateCandidate();
				break;
			case 3:
				int condidateLen2 = getCandidates();
				if (condidateLen2 > 0)
					removeCandidate();
				else
					System.out.println("No Data Found...");
				break;
			case 9:
				return;
			default:
				System.out.println("Choich is In-Correct...");
				break;
			}
		} catch (InputMismatchException e) {
			showAlert("Please enter a valid integer choice...");
			init();
		}
	}

	private void showAlert(String message) {
		System.out.println(message);
	}

	private void updateCandidate() {

		int condidateLen1 = getCandidates();
		if (condidateLen1 > 0)
			addCandidate(false);
		else
			System.out.println("No Data Found...");
	}

	private void removeCandidate() {

		String candidateEmail = getCandidateEmail();
		if (!CommonValidations.isValidEmail(candidateEmail) || candidateEmail.isEmpty() || candidateEmail.isBlank()) {
			checkForAddNewCandidate();
			return;
		}
		manageCandidateMadel.removeCandidate(candidateEmail);
	}

	private int getCandidates() {
		List<Candidate> candidates = new ReportView().getCandidates();
		return candidates.size();
	}

	private String getCandidateEmail() {

		String candidateEmail = "";
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter Email Id : ");
		candidateEmail = scanner.next();

		if (!checkIdIsExits(candidateEmail)) {
			System.out.println("Enter Candidate is not Present...");
			candidateEmail = "";
		}

		return candidateEmail;
	}

	private void addCandidate(boolean isFromAddCandidate){

		Scanner scanner = new Scanner(System.in);
		Candidate candidate = new Candidate();
		int candidateId = -1;
		String candidateEMail = isFromAddCandidate ? "" : getCandidateEmail();

		if (!isFromAddCandidate && candidateEMail.length() == 0) {
			checkForAddNewCandidate();
			return;
		}

		System.out.print("Enter Participant Name : ");
		candidate.setName(scanner.nextLine());

		if (!isFromAddCandidate) {
			System.out.print("Enter Participant Email Id : " + candidateEMail);
			candidate.setEmailId(candidateEMail);
			System.out.println();
		} else {
			System.out.print("Enter Participant Email Id : ");
			candidate.setEmailId(scanner.nextLine());
		}

		System.out.print("Enter Mobile Number : ");
		candidate.setMobileNumber(scanner.next());
		candidate.setCandidateId(candidateId);
		candidate.setInterviewProgressStatus(1);
		candidate.setInterviewStatus(1);
		manageCandidateMadel.addNewCandidate(candidate, isFromAddCandidate);
	}

	private boolean checkIdIsExits(String candidateEmail) {

		return manageCandidateMadel.checkCandidateExits(candidateEmail);
	}

	public void showSuccessAlert(String message) {

		System.out.println("---------------------");
		System.out.println(message);
		checkForAddNewCandidate();
	}

	public void showErrorAlert(String message) {

		System.out.println("---------------------");
		System.out.println(message);
		checkForAddNewCandidate();
	}

	private void checkForAddNewCandidate() {

		System.out.println("\nDo you want to add more books? \nType Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("yes")) {
			init();
		} else if (choice.equalsIgnoreCase("no")) {
			System.out.println("\n Thanks for adding Candidate");
		} else {
			System.out.println("\nInvalid choice, \nPlease enter valid choice.\n");
			checkForAddNewCandidate();
		}
	}
}
