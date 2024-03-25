package com.saran.enums;

public enum ModuleType {
	
	HR(1),
	CANDIDATE(2);
	
	int moduleType;
	
	private ModuleType(int i) {
		moduleType = i;
	}
	
	public int getModuleType() {
		return moduleType;
	}
}
