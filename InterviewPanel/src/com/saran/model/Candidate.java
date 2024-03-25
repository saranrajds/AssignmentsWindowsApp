package com.saran.model;

public class Candidate {

	private String name;
	private String mobileNumber;
	private String emailId;
	private int interviewStatus;
	private int interviewProgressStatus;
	private int CandidateId;
	private int CandidateSerialNo;
	private int hrId;

	public Candidate() {

	}

	public int getCandidateId() {
		return CandidateId;
	}

	public void setCandidateId(int candidateId) {
		CandidateId = candidateId;
	}

	public int getCandidateSerialNo() {
		return CandidateSerialNo;
	}

	public void setCandidateSerialNo(int candidateSerialNo) {
		CandidateSerialNo = candidateSerialNo;
	}

	public int getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(int interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public int getInterviewProgressStatus() {
		return interviewProgressStatus;
	}

	public void setInterviewProgressStatus(int interviewProgressStatus) {
		this.interviewProgressStatus = interviewProgressStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getHrId() {
		return hrId;
	}

	public void setHrId(int hrId) {
		this.hrId = hrId;
	}

}
