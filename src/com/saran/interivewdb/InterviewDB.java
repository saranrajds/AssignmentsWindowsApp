
package com.saran.interivewdb;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.saran.enums.ErrorCode;
import com.saran.enums.InterviewProgressStatus;
import com.saran.enums.InterviewStatus;
import com.saran.enums.UserValidation;
import com.saran.model.Candidate;
import com.saran.model.HR;

public class InterviewDB {

	private static InterviewDB interivewDB;
	private List<Candidate> candidates = new ArrayList<Candidate>();
	private int candidatePrimaryID = 1;
	private List<HR> hrs = new ArrayList<HR>();
	private int hrPrimaryID = 1;

	public static InterviewDB getInstance() {

		if (interivewDB == null)
			interivewDB = new InterviewDB();
		return interivewDB;
	}

	public boolean addNewCandidate(Candidate candidate) {

		if (candidate.getCandidateId() == -1) {
			candidate.setCandidateId(candidatePrimaryID++);
			candidate.setInterviewStatus(InterviewProgressStatus.NOTSTART.getInterviewProgressStatus());
			candidates.add(candidate);
			return true;
		} else {
			return true;
		}
	}

	public boolean updateNewCandidate(Candidate pCandidate) {

		boolean isUpdated = false;

		for (Candidate candidate : candidates) {

			if (candidate.getEmailId().equals(pCandidate.getEmailId())) {
				candidate.setName(pCandidate.getName());
				candidate.setMobileNumber(pCandidate.getMobileNumber());
//				candidate.setEmailId(pCandidate.getEmailId());
				isUpdated = true;
			}
		}

		return isUpdated;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public boolean checkCandidateExits(String candidateEmail) {
		return candidates.stream().anyMatch(candidate -> candidate.getEmailId().equals(candidateEmail));
	}

	public boolean removeCandidate(String candidateEmail) {

		List<Candidate> tempCandidate = new ArrayList<Candidate>();
		for (Candidate candidate : candidates) {
			System.out.println("1 "+ candidateEmail+ " ---  "+candidate.getEmailId());
			if (!candidate.getEmailId().equals(candidateEmail)) {
				System.out.println("2 "+ candidateEmail+ " ---  "+candidate.getEmailId());
				tempCandidate.add(candidate);
			}
		}

		candidates = tempCandidate;
		return candidates.stream().anyMatch(cnd -> cnd.getEmailId().equals(candidateEmail));
	}

	public boolean checkHRExits(String hrEmailId) {
		return hrs.stream().anyMatch(hr -> hr.getEmail().equals(hrEmailId));
	}

	public boolean updateNewHR(HR pHR) {

		boolean isUpdated = false;

		for (HR hr : hrs) {

			if (hr.getEmail().equals(pHR.getEmail())) {
				hr.setName(pHR.getName());
				isUpdated = true;
				break;
			}
		}

		return isUpdated;
	}

	public boolean addNewHR(HR pHR) {

		System.out.println("hrs -> " + hrs);
		if (hrs.stream().anyMatch(hr -> hr.getName().equals(pHR.getName())))
			return false;

		if (pHR.getId() == -1) {
			pHR.setId(hrPrimaryID++);
			hrs.add(pHR);
			;
		}
		return true;
	}

	public boolean removeHR(String hrEmail) {

		List<HR> temHr = new ArrayList<HR>();
		for (HR hr : hrs) {

			if (hr.getEmail().equals(temHr))
				temHr.add(hr);
		}

		hrs = temHr;
		return hrs.stream().anyMatch(hr -> hr.getEmail().equals(temHr));
	}

	public List<HR> getHRList() {
		return hrs;
	}

	public List<HR> getHRByName(String hrName) {
		return hrs.stream().filter(hr -> hr.getName().equals(hrName)).toList();
	}

	public List<Candidate> getCandidatesByName(String candidateName) {
		return candidates.stream().filter(candidate -> candidate.getName().equals(candidateName)).toList();
	}

	public int checkHRNameIsValid(String hrName) {

		if (!hrs.stream().anyMatch(hr -> hr.getName().equals(hrName))) {
			return ErrorCode.NOTMATCHING.getErrorCode();
		}
		
		int hrId = hrs.stream().filter(hr -> hr.getName().equals(hrName)).toList().get(0).getId();
		
		if (candidates
				.stream()
				.anyMatch(hr -> 
						hr.getInterviewProgressStatus() == InterviewProgressStatus.INPROGRESS.getInterviewProgressStatus()
						)
				) {
			return ErrorCode.INTERVIEWGOINGON.getErrorCode();
		}

		return ErrorCode.SUCCESS.getErrorCode();
	}

	public int checkCandidateIsValid(String candidateName) {
		
		if (candidates.stream().anyMatch(hr -> hr.getName().equals(candidateName))) {
			
			int status = InterviewProgressStatus.NOTSTART.getInterviewProgressStatus();
					
			if(candidates.stream().anyMatch(cnd -> cnd.getName().equals(candidateName) && cnd.getInterviewProgressStatus() == status)){
				return UserValidation.VALID.getUserValidation();
			}
			return InterviewProgressStatus.ALREADYATTEND.getInterviewProgressStatus();
		}
		
		return UserValidation.NOVALID.getUserValidation();
	}

	public void updateInterview(String hrName, String candidateName) {
		
		int hrId = hrs.stream().filter(hr -> hr.getName().equals(hrName)).toList().get(0).getId();
		
		List<Candidate> updatedCandidate = candidates.stream().map(candidate -> {
													if(candidate.getName().equals(candidateName))
													{
														candidate.setInterviewProgressStatus(InterviewProgressStatus.INPROGRESS.getInterviewProgressStatus());
														candidate.setHrId(hrId);
													}
													return candidate;
												})
												.collect(Collectors.toList());
		candidates = updatedCandidate;
		
	}

	public Candidate onGetCurrentInterviewStatus() {
		
		int statusId = InterviewProgressStatus.INPROGRESS.getInterviewProgressStatus();
		Candidate candidate = null;
		if(candidates.stream().anyMatch(cnd -> cnd.getInterviewProgressStatus() == statusId))
			candidate = candidates.stream().filter(cnd -> cnd.getInterviewProgressStatus() == statusId).findFirst().get();
		
		return candidate;
	}

	public String getHrNameById(int hrId) {
		return  hrs.stream().filter(hr -> hr.getId() == hrId).toList().get(0).getName();
	}

	public boolean updateInterviewStatus(int currentCandidateId) {

		List<Candidate> updatedCandidate = candidates.stream().map(candidate -> {
													if(candidate.getCandidateId() == currentCandidateId)
													{
														candidate.setInterviewProgressStatus(InterviewProgressStatus.COMPLETED.getInterviewProgressStatus());
													}
													return candidate;
												})
												.collect(Collectors.toList());
		candidates = updatedCandidate;
		return true;
	}
}
