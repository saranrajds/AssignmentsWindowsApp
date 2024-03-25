package com.saran.enums;

public enum ParticipantRole {

	HR(1), RECEPTION_STAFF(2), CANDIDATE(3);

	int userRoleNumber;

	ParticipantRole(int i) {
		userRoleNumber = i;
	}

	public int getRoleId() {
		return userRoleNumber;
	}
}
