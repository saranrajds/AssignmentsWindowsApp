package com.saran.librarymanagement.common;

import java.util.List;

import com.saran.librarymanagement.Repository.BorrowedBookByUser;
import com.saran.librarymanagement.enums.ModuleType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
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
	
	public void retriveBook() {

		commonModel.retriveBook();
	}

	public void showBorrowBookList() {
		commonModel.showBorrowBookList();
	}

	public void showBorrowBook(List<BorrowedBookByUser> borrowedBooks) {		
		
		System.out.println("\n----------------------------------------");
		System.out.println(" User Borrowed Books");
		System.out.println("\n-----------------------------------------");
		System.out.println("Id \t Name \t\t Author \t\t Valumn \t UserName");
		System.out.println("\n-----------------------------------------");
		for(BorrowedBookByUser book: borrowedBooks) 
		{
			System.out.println(book.getBookId() +"\t"+ book.getBookName()+"\t\t"+ book.getAuthorName()+"\t\t"+ book.getVolumn() +"\t\t"+ book.getUserName());
		}
		System.out.println("\n------------------------------------------\n");
	}
}
