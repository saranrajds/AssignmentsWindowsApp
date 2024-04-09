package com.saran.librarymanagement.librarydatatabase;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saran.librarymanagement.enums.BookStatus;
import com.saran.librarymanagement.enums.ErrorCode;
import com.saran.librarymanagement.enums.ModuleType;
import com.saran.librarymanagement.enums.UserBookStatus;
import com.saran.librarymanagement.enums.UserOperationCode;
import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.enums.searchType;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.Credentials;
import com.saran.librarymanagement.model.Library;
import com.saran.librarymanagement.model.User;
import com.saran.librarymanagement.model.UserBorrowedBooks;

public class LibraryDatabase {

	private final String FILE_PATH = "src/com/saran/librarymanagement/librarydatatabase/LibraryDatabaseFiles/";
	private final String BOOK_FILE_NAME = "book", CREDENTIAL_FILE_NAME = "credential", USER_FILE_NAME = "user",
			LIBRARY_FILE_NAME = "library";

	private static boolean isUserFileRetrived = false, isBookFileRetrived = false;
	private static LibraryDatabase libtatyDatabase;
	private int bookId = 1, userId = 1, currentUserId = -1;
	private int currentUserType = UserType.INVALID.getUserType();

	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<Book> bookList = new ArrayList<Book>();
	private ArrayList<Credentials> credentials = new ArrayList<Credentials>();
	private ArrayList<UserBorrowedBooks> borrowedBooks = new ArrayList<UserBorrowedBooks>();
	private ArrayList<Library> library = new ArrayList<Library>();

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy- HH:mm");
	LocalDateTime currentDateTime = LocalDateTime.now();

	public int getCurrentUserId() {
		return currentUserId;
	}

	public int getCurrentUserType() {
		return currentUserType;
	}

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

		if (pUser.getUserType() == UserType.LIBRARY_ADMIN.getUserType()) {
			long count = userList.stream().filter(user -> user.getUserType() == UserType.LIBRARY_ADMIN.getUserType())
					.count();

			if (count > 0)
				return false;
		}

		boolean hasUser = userList.stream().anyMatch(
				user -> user.getEmailId().equals(pUser.getEmailId()) && user.getUserType() == pUser.getUserType());

		if (!hasUser) {
			Credentials credential = new Credentials();
			pUser.setId(userId++);
			pUser.setActive(true);
			pUser.setCreatedDate(currentDateTime.format(formatter));
			pUser.setModifyDate(currentDateTime.format(formatter));
			userList.add(pUser);
			credential.setEmailId(pUser.getEmailId());
			credential.setPassword(pUser.getPassword());
			credentials.add(credential);
			uploadData(USER_FILE_NAME, userList);
			uploadData(CREDENTIAL_FILE_NAME, credentials);
			return true;
		}
		return false;
	}

	public boolean updateUser(User pUser) {
		boolean hasUser = userList.stream().anyMatch(user -> user.getEmailId().equals(pUser.getEmailId()));

		if (hasUser) {

			int count = 0;
			for (User user : userList) {
				if (user.getEmailId().equals(pUser.getEmailId())) {
					user.setName(pUser.getName());
					pUser.setModifyDate(currentDateTime.format(formatter));
					user.setPassword(pUser.getPassword());
//					user.setUserType(pUser.getUserType());
//					user.setEmailId(pUser.getEmailId());
					credentials.get(count).setPassword(pUser.getPassword());
					credentials.get(count).setEmailId(pUser.getEmailId());
					uploadData(USER_FILE_NAME, userList);
					uploadData(CREDENTIAL_FILE_NAME, credentials);
					return true;
				}
				count++;
			}
		}
		return false;
	}

	public ArrayList<Library> getLibrary() {
		return library;
	}

	public boolean inserLibrary(Library libraryInfo) {

		Library libraryTemp = new Library();
		libraryTemp.setId(1);
		libraryTemp.setEmailId(libraryInfo.getEmailId());
		libraryTemp.setName(libraryInfo.getName());
//		libraryTemp.setLibraryAdminId(currentUserId);
		library.add(libraryTemp);
		uploadData(LIBRARY_FILE_NAME, library);
		return true;
	}

	public boolean insertBook(Book pBook) {

		boolean hasBook = bookList.stream().anyMatch(book -> book.getVolume().equals(pBook.getVolume())
				&& book.getPublication().equals(pBook.getPublication()));

		if (!hasBook) {
			pBook.setId(bookId++);
			pBook.setAvailability(BookStatus.AVAILABLE.getBookStatus());
			pBook.setCreatedDate(currentDateTime.format(formatter));
			pBook.setModifyDate(currentDateTime.format(formatter));
			bookList.add(pBook);
			uploadData(BOOK_FILE_NAME, bookList);
			return true;
		}
		return false;
	}

	public boolean updateBook(Book pBook) {

		boolean hasBook = bookList.stream().anyMatch(book -> book.getId() == pBook.getId());

		if (hasBook) {

			for (Book book : bookList) {

				if (book.getId() == pBook.getId()) {
					book.setName(pBook.getName());
					book.setAuthor(pBook.getAuthor());
					pBook.setModifyDate(currentDateTime.format(formatter));
					uploadData(BOOK_FILE_NAME, bookList);
					return true;
				}
			}
		}
		return false;
	}

	public BookStatus onDeleteBook(int bookId2) {

		boolean[] isRemoved = { false };

		boolean hasBook = bookList.stream().anyMatch(book -> book.getId() == bookId2);

		if (!hasBook) {
			return BookStatus.NOT_FOUND;
		}

		hasBook = bookList.stream().anyMatch(book -> book.getId() == bookId2
				&& book.getAvailability() == BookStatus.NOT_AVAILABLE.getBookStatus());

		if (hasBook) {
			return BookStatus.NOT_AVAILABLE;
		}

		List<Book> _book = bookList.stream().filter(book -> book.getId() == bookId2).map(book -> {
			book.setDeleted(true);
			isRemoved[0] = true;
			return book;
		}).toList();

		if (!isRemoved[0]) {
			return BookStatus.NOT_DELETED;
		} else {
			return BookStatus.DELETED;
		}
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

	public int onDeleteUser(String userEmailId) {

		boolean hasUser = userList.stream().anyMatch(book -> book.getEmailId().equals(userEmailId));
		boolean isRemoved = false;

		if (hasUser) {

			List<User> user = userList.stream().filter(tempUser -> tempUser.getEmailId().equals(userEmailId)).toList();
			List<Book> _brdBooks = getUserBorrowedBook(user.get(0).getId());

			if (_brdBooks.size() > 0) {
				return UserOperationCode.USER_BORROED_BOOK.getUserOperationCode();
			} else {

				userList.stream().filter(tempUser -> tempUser.getEmailId().equals(userEmailId)).map(tempUser -> {
					tempUser.setActive(false);
					return tempUser;
				});

				uploadData(USER_FILE_NAME, userList);
				return UserOperationCode.DELETED.getUserOperationCode();
			}

		}
	
		return UserOperationCode.USER_NOT_FOUND.getUserOperationCode();
		
	}

	public List<Book> getUserBorrowedBook(int userId) {

		List<Book> _brdBooks = borrowedBooks.stream()
				.flatMap(brdBooks -> bookList.stream().filter(
						tempBooks -> brdBooks.getUserId() == userId && brdBooks.getBookId() == tempBooks.getId())
						.map(result -> {
							return result;
						}))
				.toList();
		return _brdBooks;
	}
	
	public List<User> getNeedDeletedUserId(String userEmailId) {
		
		List<User> user = userList.stream().filter(tempUser -> tempUser.getEmailId().equals(userEmailId)).toList();
		return user;
	}

	public int isValidCredential(Credentials credential) {

		if (credential.getEmailId().equals("123") && credential.getPassword().equals("123")) {
			currentUserType = UserType.ADMIN.getUserType();
			currentUserId = 1;
			System.out.println("userCount "+userId);
			return currentUserType;
		}

		boolean isValidUser = credentials.stream().anyMatch(user -> user.getEmailId().equals(credential.getEmailId())
				&& user.getPassword().equals(credential.getPassword()));

		if (!isValidUser) {
			return UserType.INVALID.getUserType();
		}

		List<User> user = userList.stream().filter(_userList -> _userList.getEmailId().equals(credential.getEmailId())
				&& _userList.getPassword().equals(credential.getPassword())).toList();
		currentUserId = user.size() > 0 ? user.get(0).getId() : -1;
		currentUserType = user.size() > 0 ? user.get(0).getUserType() : UserType.INVALID.getUserType();
		return currentUserType;
	}

	public String getUserByBookId(int bookId) {
		String userName;

		List<User> user = borrowedBooks.stream()
				.flatMap(books -> userList.stream().filter(users -> books.getUserId() == users.getId()).map(users -> {
					return users;
				})).collect(Collectors.toList());

		userName = user.size() > 0 ? user.get(0).getName() : "";
		System.out.println("userName" + userName);
		return userName;
	}

	public int onAssignBook(int bookId) {

		int bookStatus = bookIsValid(bookId);

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
			
			if (book.getId() == bookId2
					&& book.getAvailability() == BookStatus.AVAILABLE.getBookStatus())
				return BookStatus.AVAILABLE.getBookStatus();

			if (book.getId() == bookId2
					&& book.getAvailability() == BookStatus.NOT_AVAILABLE.getBookStatus()) {
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

		if (bookList.stream().anyMatch(book -> book.getId() == bookId2
				&& book.getAvailability() == BookStatus.NOT_AVAILABLE.getBookStatus())) {
			return BookStatus.NOT_AVAILABLE.getBookStatus();
		} else if (bookList.stream().anyMatch(book -> book.getId() == bookId2
				&& book.getAvailability() == BookStatus.DIMAGED.getBookStatus())) {
			return BookStatus.DIMAGED.getBookStatus();
		} else if (bookList.stream().anyMatch(book -> book.getId() == bookId2
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

//				String jsonData = objectMapper.writeValueAsString(arrayList);
				objectMapper.writeValue(new File(filename), arrayList);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private boolean createFile(String filename) {

		File file = new File(filename);

		try {
			if (file.exists() && !file.delete()) {
				return false;
			}

			if (file.createNewFile()) {
				return true;
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void retriveDataFromFile(int moduleType) {

		String filename = FILE_PATH;
		String filenameForCrediantial = FILE_PATH;

		if (moduleType == ModuleType.USER.getModuleType()) {
			filename += "user.json";
			filenameForCrediantial += "credential.json";
		} else if (moduleType == ModuleType.LIBRARIY.getModuleType()) {
			filename += "library.json";
		} else {
			filename += "book.json";
		}

		ObjectMapper objectMapper = new ObjectMapper();

		try {

			File file = new File(filename);
			if (file.exists() && file.length() > 0) {

				if (moduleType == ModuleType.USER.getModuleType()) {

					ArrayList<User> userTemp = objectMapper.readValue(new File(filename),
							new TypeReference<ArrayList<User>>() {
							});

					ArrayList<Credentials> credential = objectMapper.readValue(new File(filenameForCrediantial),
							new TypeReference<ArrayList<Credentials>>() {
							});

					userList = userTemp;
					credentials = credential;
					userId = userList.size() + 1;
				} else if (moduleType == ModuleType.LIBRARIY.getModuleType()) {

					ArrayList<Library> libraries = objectMapper.readValue(new File(filename),
							new TypeReference<ArrayList<Library>>() {
							});

					library = libraries;
				} else {
					ArrayList<Book> hrsTemp = objectMapper.readValue(new File(filename),
							new TypeReference<ArrayList<Book>>() {
							});
					bookList = hrsTemp;
					bookId = bookList.size() + 1;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Library> getLibraryInfo() {
		return library;
	}

	public List<User> getParticularUserList(int userType) {

		return userList.stream().filter(user -> user.getUserType() == userType).toList();
	}

	public void resetLoginSession() {
		this.currentUserId = -1;
		this.currentUserType = UserType.INVALID.getUserType();

	}

	public List<Book> getAvailableBooks() {
		return bookList
				.stream()
				.filter(book -> book.getAvailability() == BookStatus.AVAILABLE.getBookStatus()
						&& !book.isDeleted())
				.toList();
	}

	public List<User> getCurrentUser() {
		
		return userList.stream().filter(user -> user.getId() == currentUserId).toList();
	}

	public List<Book> getBook(int bookId) {
		return bookList.stream().filter(book -> book.getId() == bookId).toList();
	}

	public void retriveBooks() {
		
	}
}
