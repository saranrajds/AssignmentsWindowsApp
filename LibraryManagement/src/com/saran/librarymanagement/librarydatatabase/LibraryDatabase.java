package com.saran.librarymanagement.librarydatatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saran.librarymanagement.enums.BookStatus;
import com.saran.librarymanagement.enums.ErrorCode;
import com.saran.librarymanagement.enums.ModuleType;
import com.saran.librarymanagement.enums.UserBookStatus;
import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.enums.searchType;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.Credentials;
import com.saran.librarymanagement.model.Library;
import com.saran.librarymanagement.model.User;
import com.saran.librarymanagement.model.UserBorrowedBooks;

public class LibraryDatabase {

	private final String FILE_PATH = "src/com/saran/librarymanagement/librarydatatabase/LibraryDatabaseFiles/";
	private final String BOOK_FILE_NAME = "book", USER_FILE_NAME = "user", LIBRARY_FILE_NAME = "Library";
	
	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<Book> bookList = new ArrayList<Book>();
	private ArrayList<Credentials> credentials = new ArrayList<Credentials>();
	private ArrayList<UserBorrowedBooks> borrowedBooks = new ArrayList<UserBorrowedBooks>();
	private static LibraryDatabase libtatyDatabase;
	private ArrayList<Library> library = new ArrayList<Library>();
	private int bookId = 1, userId = 1;
	private int currentUserId = UserType.INVALID.getUserType();
	private static boolean isUserFileRetrived = false, isBookFileRetrived = false;
	
	public static boolean isUserFileRetrived() {
		return isUserFileRetrived;
	}

	public static void setUserFileRetrived(boolean isUserFileRetrived) {
		LibraryDatabase.isUserFileRetrived = isUserFileRetrived;
	}

	public static boolean isBookFileRetrived() {
		return isBookFileRetrived;
	}

	public static void setBookFileRetrived(boolean isBookFileRetrived) {
		LibraryDatabase.isBookFileRetrived = isBookFileRetrived;
	}

	public static LibraryDatabase getInstance() {

		if (LibraryDatabase.libtatyDatabase == null)
			LibraryDatabase.libtatyDatabase = new LibraryDatabase();

		return libtatyDatabase;
	}

	public boolean insertUser(User pUser) {

		boolean hasUser = userList.stream().anyMatch(
				user -> user.getName().equals(pUser.getName()) && user.getEmailId().equals(pUser.getEmailId()));

		if (!hasUser) {
			Credentials credential = new Credentials();
			pUser.setId(userId++);
			userList.add(pUser);
			credential.setUserName(pUser.getName());
			credential.setPassword(pUser.getPassword());
			credentials.add(credential);
			uploadData(USER_FILE_NAME, userList);
			return true;
		}
		return false;
	}

	public boolean updateUser(User pUser) {
		boolean hasUser = userList.stream().anyMatch(user -> user.getId() == pUser.getId());

		if (hasUser) {

			for (User user : userList) {
				if (user.getId() == pUser.getId()) {
					user.setName(pUser.getName());
					user.setEmailId(pUser.getEmailId());
					uploadData(USER_FILE_NAME, userList);
					return true;
				}
			}
		}
		return false;
	}

	public ArrayList<Library> getLibrary() {
		return library;
	}

	public ArrayList<Library> inserLibrary(Library libraryInfo) {
		Library libraryTemp = new Library();
		libraryTemp.setId(1);
		libraryTemp.setEmailId(libraryInfo.getEmailId());
		libraryTemp.setName(libraryInfo.getName());
		uploadData(LIBRARY_FILE_NAME, library);
		return library;
	}

	public boolean insertBook(Book pBook) {

		boolean hasBook = bookList.stream().anyMatch(book -> book.getName().equals(pBook.getName()));
		
		if (!hasBook) {
			pBook.setId(bookId++);
			pBook.setAvailability(BookStatus.AVAILABLE.getBookStatus());
			bookList.add(pBook);
			uploadData(BOOK_FILE_NAME, bookList);
			return true;
		}
		return false;
	}

	public boolean updateBook(Book pBook) {

//		boolean hasBook = bookList.stream().anyMatch(book -> book.getName().equals(pBook.getName()) && book.getAuthor().equals(pBook.getAuthor()));
		boolean hasBook = bookList.stream().anyMatch(book -> book.getId() == pBook.getId());

		if (hasBook) {

			for (Book book : bookList) {
				if (book.getId() == pBook.getId()) {
					book.setName(pBook.getName());
					book.setAuthor(pBook.getAuthor());
					uploadData(BOOK_FILE_NAME, bookList);
					return true;
				}
			}
		}
		return false;
	}

	public List<Book> getBooks() {
		return bookList;
	}

	public List<User> getUsers() {
		return userList;
	}

	public List<Book> getBook(String bookSearchValue, int seachType) {

		List<Book> searchBooks = new ArrayList<Book>();
		
		if (searchType.NAME.getSeachType() == seachType)
			searchBooks = bookList.stream().filter(book -> book.getName().equals(bookSearchValue)).toList();
		else
			searchBooks = bookList.stream().filter(book -> book.getAuthor().equals(bookSearchValue)).toList();

		return searchBooks;
	}

	public boolean onDeleteBook(int bookId2) {

//		List<Book> _book = bookList.stream().filter(book -> book.getId() == bookId2).toList();
		boolean hasBook = bookList.stream().anyMatch(book -> book.getId() == bookId2);
		boolean isRemoved = false;

		if (hasBook) {
			ArrayList<Book> book_ = new ArrayList<Book>();
			for (Book book : bookList) {

				if (book.getId() != bookId2)
					book_.add(book);
				else
					isRemoved = true;
			}
			bookList = book_;
		}
		uploadData(BOOK_FILE_NAME, bookList);
		return isRemoved;
	}

	public boolean onDeleteUser(int userId) {

		List<User> _user = userList.stream().filter(book -> book.getId() == userId).toList();
		boolean isRemoved = false;

		if (_user.size() > 0) {
//			userList = userList.stream().filter(book -> book.getId() != userId).toList();
			ArrayList<User> users_ = new ArrayList<User>();
			for (User user : userList) {

				if (user.getId() != userId)
					users_.add(user);
				else
					isRemoved = true;
			}

			userList = users_;
		}
		uploadData(USER_FILE_NAME, userList);
		return isRemoved;
	}

	public int isValidCredential(Credentials credential) {

		boolean hasUser = credentials.stream().anyMatch(user -> user.getUserName().equals(credential.getUserName())
				&& user.getPassword().equals(credential.getPassword()));

		if (hasUser) {

			currentUserId = UserType.USER.getUserType();
			return UserType.USER.getUserType();
		} else if (credential.getUserName().equals("123") && credential.getPassword().equals("123")) {

			currentUserId = UserType.ADMIN.getUserType();
			return UserType.ADMIN.getUserType();
		}
		currentUserId = UserType.INVALID.getUserType();
		return UserType.INVALID.getUserType();
	}

	public int onAssignBook(int bookId) {

		int bookStatus = bookIsValid(bookId);

		System.out.println("bookIsValidStatus "+bookStatus);
		
		if (bookStatus == BookStatus.AVAILABLE.getBookStatus()) {

			UserBorrowedBooks borrowedBook = new UserBorrowedBooks();
			borrowedBook.setBookId(bookId);
			borrowedBook.setUserId(currentUserId);
			borrowedBook.setBookStatus(UserBookStatus.BORROW.getUserBookStatus());
			borrowedBooks.add(borrowedBook);
			changeBookStatus(bookId);
			return ErrorCode.UPDATED.getErrorCode();
		}
		return bookStatus;
	}
	
	public int returningBook(int bookId2) {
		
		for (Book book : bookList) {
			System.out.println(book.getId()  +" "+bookId2);
			
			if(book.getId() == bookId2 && book.getAvailability() == BookStatus.AVAILABLE.getBookStatus())
				return BookStatus.AVAILABLE.getBookStatus();
						
			if (book.getId() == bookId2 && book.getAvailability() == BookStatus.NOT_AVAILABLE.getBookStatus()) {
				book.setAvailability(BookStatus.AVAILABLE.getBookStatus());
				uploadData(BOOK_FILE_NAME, bookList);
				return ErrorCode.UPDATED.getErrorCode();
			}
		}
		
		return ErrorCode.NOTFOUNT.getErrorCode();		
	}

	private void changeBookStatus(int bookId) {

		for (Book book : bookList) {

			if (book.getId() == bookId) {
				book.setAvailability(BookStatus.NOT_AVAILABLE.getBookStatus());
				uploadData(BOOK_FILE_NAME, bookList);
				break;
			}
		}
	}

	private int bookIsValid(int bookId2) {

		if(bookList.stream().anyMatch(
				book -> book.getId() == bookId2 
				&& book.getAvailability() == BookStatus.NOT_AVAILABLE.getBookStatus()))
		{
			return BookStatus.NOT_AVAILABLE.getBookStatus();
		}
		else if (bookList.stream().anyMatch(
				book -> book.getId() == bookId2 
				&& book.getAvailability() == BookStatus.DIMAGED.getBookStatus())) {
			return BookStatus.DIMAGED.getBookStatus();
		}
		else if (bookList.stream().anyMatch(
				book -> book.getId() == bookId2
				&& book.getAvailability() == BookStatus.AVAILABLE.getBookStatus())) {
			return BookStatus.AVAILABLE.getBookStatus();

		}
		
		return ErrorCode.NOTFOUNT.getErrorCode();		
	}
	
	public <T> void uploadData(String pFilename, ArrayList<T> arrayList) {

		String filename = FILE_PATH + pFilename + ".json";
		ObjectMapper objectMapper = new ObjectMapper();
		boolean fileValid = createFile(pFilename);

		try {
			if (fileValid) {
				
				String jsonData = objectMapper.writeValueAsString(arrayList);
				objectMapper.writeValue(new File(filename), arrayList);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
	}
	
	private boolean createFile(String filename) {

		File file = new File(filename);

		try {
			if (file.exists()) {
				if (file.delete()) {
//					System.out.println("Existing file deleted.");
				} else {
//					System.out.println("Failed to delete the existing file.");
					return false;
				}
			}

			if (file.createNewFile()) {
//				System.out.println("File created: " + file.getName());
				return true;
			} else {
//				System.out.println("Failed to create the file.");
			}
			return false;
		} catch (IOException e) {
//			System.out.println("An error occurred.");
			e.printStackTrace();
			return false;
		}
	}
	
	public void retriveDataFromFile(int moduleType) {

		String filename = FILE_PATH;

		if (moduleType == ModuleType.USER.getModuleType()) {
			filename += "user.json";
		}
		else {
			filename += "book.json";
		}

		ObjectMapper objectMapper = new ObjectMapper();

		try {

			File file = new File(filename);
			if (file.exists()) {

				if (moduleType == ModuleType.USER.getModuleType()) {
					ArrayList<User> userTemp = objectMapper.readValue(new File(filename),
							new TypeReference<ArrayList<User>>() {
							});
					userList = userTemp;
					userId = userList.size()+1;
				}
				else {
					ArrayList<Book> hrsTemp = objectMapper.readValue(new File(filename),
							new TypeReference<ArrayList<Book>>() {
					});
					bookList = hrsTemp;
					bookId = bookList.size()+1;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
