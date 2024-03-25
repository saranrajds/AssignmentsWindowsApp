package com.saran.librarymanagement.enums;

public enum SearchReportType {
	
	Book(1), 
	User(2);

	private int searchReportType;
	SearchReportType(int i) {
		searchReportType = i;
	}
	
	public int getSearchReportType() {
		return searchReportType;
	}
}
