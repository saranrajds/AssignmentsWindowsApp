package com.saran.enums;

public enum InterviewStatus {

	WAITINHG(1), CONFIRMED(2), REJECTED(3);

	int interviewStatus;

	InterviewStatus(int i) {
		this.interviewStatus = i;
	}

	int getInterviewStatus() {
		return interviewStatus;
	}
}
