package com.saran.librarymanagement.model;

import java.util.List;

public class Library {

	private String name;
	private int id;
	private String emailId;
	private String address;
	private long phoneNo;
	private List<User> adminUsers;
	private int libraryAdminId;

	public void setLibraryAdminId(int libraryAdminId) {
		this.libraryAdminId = libraryAdminId;
	}

	public int getLibraryAdminId() {
		return libraryAdminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public List<User> getAdminUsers() {
		return adminUsers;
	}

	public void setAdminUsers(List<User> adminUsers) {
		this.adminUsers = adminUsers;
	}
}
