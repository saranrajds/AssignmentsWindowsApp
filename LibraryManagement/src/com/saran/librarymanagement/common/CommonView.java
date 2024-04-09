package com.saran.librarymanagement.common;

import java.util.List;

import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;

public class CommonView {

	private CommonModel commonModel;
	
	public CommonView() {
		
		this.commonModel = new CommonModel(this);
	}
	
	public int getAvailableBooks() {
		int booksLen = commonModel.getAvailableBooks();
		return booksLen;
	}
	
	public void getAvailableBooks(List<Book> books) {

		System.out.println("\n------------------- Books ----------------------\n");
		System.out.println("Id \tName \t\tBook Author \tBook Status  \tCreated Date ");
		System.out.println("-----------------------------------------------------");
		for(Book book: books) {
			System.out.println(book.getId() +"\t"+ book.getName() +"\t"+ book.getAuthor() +"\t"+ book.getAvailability()+"\t"+book.getModifyDate());
		}
		System.out.println("-----------------------------------------------------");
	}
	
	public void showAlert(String message) {
		System.out.println("\n-----------------------");
		System.out.println(message);
	}
}
