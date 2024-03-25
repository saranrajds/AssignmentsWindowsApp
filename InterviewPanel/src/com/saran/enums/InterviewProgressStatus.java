package com.saran.enums;

public enum InterviewProgressStatus {

	NOTSTART(1), INPROGRESS(2), COMPLETED(3), CANCELED(4), ALREADYATTEND(4);

	private int interviewProgressStatus;

	InterviewProgressStatus(int i) {
		interviewProgressStatus = i;
	}

	public int getInterviewProgressStatus() {
		return interviewProgressStatus;
	}

	public static String getByValue(int value) {
		for (InterviewProgressStatus status : InterviewProgressStatus.values()) {
			if (status.getInterviewProgressStatus() == value) {
				return status.name();
			}
		}
		throw new IllegalArgumentException("No enum constant with value " + value);
	}
}
