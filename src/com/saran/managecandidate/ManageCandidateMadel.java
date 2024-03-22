package com.saran.managecandidate;

import com.saran.commonvalidation.CommonValidations;
import com.saran.interivewdb.InterviewDB;
import com.saran.model.Candidate;

public class ManageCandidateMadel {

	private ManageCandidateView manageCandidateView;
	public ManageCandidateMadel(ManageCandidateView manageCandidateView) {
		this.manageCandidateView = manageCandidateView;
	}
	
	public void addNewCandidate(Candidate candidate, boolean isFromAddCandidate) {
		
		if(!CommonValidations.isValidEmail(candidate.getEmailId()))
		{
			manageCandidateView.showErrorAlert("Enter validate Email Id...");
		}
		else if(!CommonValidations.isValidPhoneNumber(candidate.getMobileNumber())) {
			manageCandidateView.showErrorAlert("Enter validate Mobile Number...");
		}		
		else if(isFromAddCandidate) {
			if(InterviewDB.getInstance().addNewCandidate(candidate))
			{
				manageCandidateView.showSuccessAlert("Candidate added succesfully...");
			}
			else {
				manageCandidateView.showErrorAlert("Candidate Not Added Try Agsin...");
			}
		}
		else {
			if(InterviewDB.getInstance().updateNewCandidate(candidate))
			{
				manageCandidateView.showSuccessAlert("Candidate Updated succesfully...");
			}
			else {
				manageCandidateView.showErrorAlert("Candidate Not Updated Try Agsin...");
			}
		}
	}

	public boolean checkCandidateExits(String candidateEmail) {
		return InterviewDB.getInstance().checkCandidateExits(candidateEmail);
	}

	public void removeCandidate(String candidateEmail) {
		
		if(!InterviewDB.getInstance().removeCandidate(candidateEmail)) {
			manageCandidateView.showErrorAlert("Candidate Deleted Successfully...");
		}
		else {
			manageCandidateView.showErrorAlert("Candidate Not Deleted Try Again...");
		}
	}

}
