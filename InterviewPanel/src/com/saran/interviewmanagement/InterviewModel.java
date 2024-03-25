package com.saran.interviewmanagement;

import com.saran.enums.ErrorCode;
import com.saran.enums.InterviewProgressStatus;
import com.saran.enums.UserValidation;
import com.saran.interivewdb.InterviewDB;
import com.saran.model.Candidate;

public class InterviewModel {

	InterviewView interviewView;

	public InterviewModel(InterviewView interviewView) {
		this.interviewView = interviewView;
		;
	}

	public void isValidate(String hrName, String candidateName) {

		int hrErrorCode = InterviewDB.getInstance().checkHRNameIsValid(hrName);
		int cndErrorCode = InterviewDB.getInstance().checkCandidateIsValid(candidateName);

		if (cndErrorCode == InterviewProgressStatus.ALREADYATTEND.getInterviewProgressStatus()) {
			interviewView.showAlert("Candidate Already Attended the Interview");
		} else if (hrErrorCode == ErrorCode.INTERVIEWGOINGON.getErrorCode()) {
			interviewView.showAlert("Somebody is Interview Going On...");
		} else if (hrErrorCode == ErrorCode.NOTMATCHING.getErrorCode()) {
			interviewView.showAlert("No HR is Available...");
		} else if (cndErrorCode == UserValidation.NOVALID.getUserValidation()) {
			interviewView.showAlert("Candidate Not Valid");
		} else {
			InterviewDB.getInstance().updateInterview(hrName, candidateName);
			interviewView.showAlert("Interview Status updated successfully...");
		}

//		return hrErrorCode == ErrorCode.SUCCESS.getErrorCode() && hasCandidate;
	}

	public void onGetCurrentInterviewStatus() {

		Candidate candidate = InterviewDB.getInstance().onGetCurrentInterviewStatus();

		if (candidate != null) {
			String hrName = InterviewDB.getInstance().getHrNameById(candidate.getHrId());
			interviewView.onShowCurrentInterviewStatus(candidate, hrName);
		} else {
			interviewView.showAlert("No Interview is Going...");
		}
	}

	public void updateInterviewStatus(int currentCandidateId) {

		boolean isStatusUpdated = InterviewDB.getInstance().updateInterviewStatus(currentCandidateId);
		interviewView.showAlert("Interview Status updated successfully...");
	}
}
