package com.saran.hrmanagement;

import com.saran.commonvalidation.CommonValidations;
import com.saran.interivewdb.InterviewDB;
import com.saran.model.Candidate;
import com.saran.model.HR;
import com.saran.reportmanagement.ReportView;

public class HRModel {

	private HRView hrView;
	
	public HRModel(HRView hrView) {
		this.hrView = hrView;
	}
	
	public boolean checkHRExits(String hrEmailId) {
		return InterviewDB.getInstance().checkHRExits(hrEmailId);
	}

	public void addNewHR(HR hr, boolean isFromAddHR) {
		
		if(!CommonValidations.isValidEmail(hr.getEmail()))
		{
			hrView.showErrorAlert("Enter validate Email Id...");
		}
//		else if(!CommonValidations.isValidPhoneNumber(candidate.getMobileNumber())) {
//			hrView.showErrorAlert("Enter validate Mobile Number...");
//		}
		else if(isFromAddHR) {
			if(InterviewDB.getInstance().addNewHR(hr))
			{
				hrView.showSuccessAlert("HR added succesfully...");
			}
			else {
				hrView.showErrorAlert("HR Not Added Try Again...");
			}
		}
		else {
			if(InterviewDB.getInstance().updateNewHR(hr))
			{
				hrView.showSuccessAlert("HR Updated succesfully...");
			}
			else {
				hrView.showErrorAlert("HR Not Updated Try Again...");
			}
		}		
	}

	public void removeHR() {
		
		int hrLen =  new ReportView().getHRs().size();
		if(hrLen > 0) {
			
			String hrEmail = hrView.getHREMail();
			
			if(hrEmail.length() == 0 || hrEmail.isEmpty() || hrEmail.isBlank()) {
				hrView.checkForAddNewHR();
				return;	
			}
			else  
			{				
				if(!InterviewDB.getInstance().removeHR(hrEmail)) {
					hrView.showErrorAlert("Candidate Deleted Successfully...");
				}
				else {
					hrView.showErrorAlert("Candidate Not Deleted Try Again...");
				}
			}
		}
		else {
			hrView.showAlert("No HR Found...");
		}
			
	}
}
