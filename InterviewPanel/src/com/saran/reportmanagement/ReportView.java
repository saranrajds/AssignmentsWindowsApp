package com.saran.reportmanagement;

import java.util.List;
import java.util.Scanner;

import com.saran.enums.InterviewProgressStatus;
import com.saran.model.Candidate;
import com.saran.model.HR;

public class ReportView {

	private ReportModel reportModel;

	public ReportView() {
		this.reportModel = new ReportModel(this);
	}
	
	public void initReport() {
		
		Scanner scanner = new Scanner(System.in);
		boolean isReport = true;
		while (isReport) {
			
			System.out.print("1 -> HR Report \n2 -> Candidate Report \n3 -> Search HR \n4 -> Search Candidate \n9 -> Exit \nEnter Your Choich :");
			int choich = scanner.nextInt();
			
			switch (choich) {
				case 1:
					getHRList();
					break;
				case 2:
					getCandidateList();
					break;
				case 3:
					getHRByName();
					break;
				case 4:
					getCandidatesByName();
					break;
				case 9:
					isReport = false;
					break;
				default:
					showErrorMessage("Enter correct option...");
					break;
			}
		}
		
	}

	private void getHRByName() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter HR Name : ");
		String hrName = scanner.nextLine();
		reportModel.getHRByName(hrName);
	}
	
	private void getCandidatesByName() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Candidate Name : ");
		String candidateName = scanner.nextLine();
		reportModel.getCandidatesByName(candidateName);
	}
	
	private void getCandidateList() {
		
		List<Candidate> candidates = reportModel.getCandidates();
//		showCandidateResult(candidates);
	}

	public void getHRList() {
		List<HR> hrs = reportModel.getHR();
//		showHRResult(hrs);
	}
	
	public List<Candidate> getCandidates() {
		List<Candidate> candidates = reportModel.getCandidates();
		return candidates;
	}

	public void showCandidateResult(List<Candidate> candidates) {

		if (candidates.size() > 0) {
			
			System.out.println("-----------Candidate Details------------");
			System.out.printf("%s %10s %10s %10s %s\n", "Id", "Name", "Progress Status", "Email_ID", "Mobile No");
			for (Candidate candidate : candidates) {
			
				String status = InterviewProgressStatus.getByValue(candidate.getInterviewProgressStatus());
						
				System.out.printf("%d %10s %10s %10s %s\n", candidate.getCandidateId(), candidate.getName(),
						status, candidate.getEmailId(), candidate.getMobileNumber());
			}
			System.out.println("--------------------------------");
		}
		else {
			showErrorMessage("No Data Fount");
		}
	}

	public List<HR> getHRs() {
		List<HR> hrs = reportModel.getHR();
		return hrs;
	}

	public void showHRResult(List<HR> hrs) {
		
		if (hrs.size() > 0) {
			System.out.println("-----------HR Details------------");
			System.out.printf("%s %10s %10s\n", "Id", "Name","EMail");
			for (HR hr: hrs) {
				System.out.printf("%d %10s %10s\n", hr.getId(), hr.getName(), hr.getEmail());
			}
			System.out.println("--------------------------------");
		}
		else {
			showErrorMessage("No Data Fount");
		}
	}
	
	private void showErrorMessage(String message) {
//		System.out.println("----------------------------------");
		System.out.println("------------"+ message +"------------");
		System.out.println("----------------------------------");
	}
}
