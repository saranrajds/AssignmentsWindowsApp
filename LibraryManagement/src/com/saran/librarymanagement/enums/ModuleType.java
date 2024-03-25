package com.saran.librarymanagement.enums;

public enum ModuleType {

	ADMIN(1),
	USER(2),
	BOOK(3),
	CREDENTIALS(4),
	BORROWEDBOOK(5);
	
	int moduleType;
	
	private ModuleType(int i) {
		moduleType = i;
	}
	
	public int getModuleType() {
		return moduleType;
	}
}
