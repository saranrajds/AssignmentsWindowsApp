package com.saran.hrmanagement;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import com.saran.model.HR;
import com.saran.reportmanagement.ReportView;

public class HRView {

	private HRModel hrModel;

	public HRView() {
		this.hrModel = new HRModel(this);
	}

	public void init() {


		hrModel.retriveHR();
		Scanner scanner = new Scanner(System.in);

		showAlert("HR Management");
		System.out.print("1 -> Add HR \n2 -> Update HR \n3 -> Remove HR \n9 -> Exit \nEnter Your Choich :");

		try {
			int choich = scanner.nextInt();

			switch (choich) {
			case 1:
				addHR(true);
				break;
			case 2:
				onUpdateHR();
				break;
			case 3:
				removeHR();
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

	private void onUpdateHR() {

		int hrLen1 = getHR();
		if (hrLen1 > 0)
			addHR(false);
		else
			System.out.println("No Data Found...");
	}

	private boolean checkIdIsExits(String hrEmailId) {
		return hrModel.checkHRExits(hrEmailId);
	}

	private void removeHR() {
		hrModel.removeHR();
	}

	public String getHREMail() {

		String hrEmailId = "";
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter HR EmailId : ");
		hrEmailId = scanner.next();

		if (!checkIdIsExits(hrEmailId)) {
			System.out.println("Enter HR is not Present...");
			hrEmailId = "";
		}
		return hrEmailId;
	}

	private void addHR(boolean isFromAddHR) {

		Scanner scanner = new Scanner(System.in);
		HR hr = new HR();
		int hrId = -1;
		String hrEmail = isFromAddHR ? "" : getHREMail();

		if (!isFromAddHR && (hrEmail.length() == 0 || hrEmail.isEmpty() || hrEmail.isBlank())) {
			checkForAddNewHR();
			return;
		}

		System.out.print("Enter HR Name : ");
		hr.setName(scanner.nextLine());

		if (isFromAddHR) {
			System.out.print("Enter HR EMail : ");
			hr.setEmail(scanner.next());
		} else {
			System.out.println("Enter HR EMail : " + hrEmail);
			hr.setEmail(hrEmail);
		}

		hr.setId(hrId);
		hrModel.addNewHR(hr, isFromAddHR);
	}

	private int getHR() {
		List<HR> hrs = new ReportView().getHRs();
		return hrs.size();
	}

	public void showSuccessAlert(String message) {
		System.out.println(message);
		checkForAddNewHR();
	}

	public void showErrorAlert(String message) {
		System.out.println(message);
		checkForAddNewHR();
	}

	public void checkForAddNewHR() {

		System.out.println("Do you want to add more HR? \nType Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();

		if (choice.equalsIgnoreCase("yes")) {
			init();
		} else if (choice.equalsIgnoreCase("no")) {
			System.out.println("\nThanks for adding HR");
		} else {
			System.out.println("\nInvalid choice, Please enter valid choice.\n");
			checkForAddNewHR();
		}
	}

	public void showAlert(String message) {
		System.out.println("-----------------------------");
		System.out.println(message);
	}
}
