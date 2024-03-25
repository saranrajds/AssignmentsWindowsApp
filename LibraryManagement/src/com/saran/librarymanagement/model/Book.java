package com.saran.librarymanagement.model;

public class Book {

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublication() {
		return publication;
	}
	public void setPublication(String publication) {
		this.publication = publication;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEdition() {
		return edition;
	}
	public void setEdition(String edition) {
		this.edition = edition;
	}
	public int getAvailability() {
		return availability;
	}
	public void setAvailability(int i) {
		this.availability = i;
	}
	public String getJourner() {
		return journer;
	}
	public void setJourner(String journer) {
		this.journer = journer;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	private String name;
	private String author;
	private String publication;
	private int id;
	private String edition;
	private int availability;
	private String journer;
	private int volume;
}
