package com.saran.librarymanagement.enums;

public enum searchType {
	NAME(1),
	AUTHOR(2);

	private int searchValue;
	
	searchType(int searchValue) {
		this.searchValue = searchValue;
	}
	
	public int getSeachType() {
        return searchValue;
    }
}
