package com.saran.librarymanagement.manageusers;

import java.util.List;

import com.saran.librarymanagement.Repository.BorrowedBookByUser;
import com.saran.librarymanagement.commonvalidation.CommonValidations;
import com.saran.librarymanagement.enums.ModuleType;
import com.saran.librarymanagement.enums.UserOperationCode;
import com.saran.librarymanagement.enums.UserType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;

public class ManageUserModel {

	private ManageUserView manageUserView;

	ManageUserModel(ManageUserView manageUserView) {
		this.manageUserView = manageUserView;
	}

	public void addNewUser(User user, boolean isFromNewUserAdd) {

		if (!CommonValidations.isValidEmail(user.getEmailId())) {
			manageUserView.aletMessage("Enter validate Email Id...");
			this.manageUserView.manageUserOption();
		} else {
			if (isFromNewUserAdd) {
				addNewUser(user);
				if (user.getUserType() != UserType.LIBRARY_ADMIN.getUserType())
					this.manageUserView.checkForAddNewUser();
			} else {
				editUser(user);
				this.manageUserView.manageUserOption();
			}
		}
//		manageUserView.checkForUserManagement();
	}

	public void addNewUser(User user) {

		if (LibraryDatabase.getInstance().insertUser(user)) {
			manageUserView.aletMessage(user.getEmailId() +" - User Added Successfully..");
		} else {
			if (user.getUserType() == UserType.LIBRARY_ADMIN.getUserType())
				manageUserView.aletMessage("Librariant alreay added..");
			else
				manageUserView.aletMessage("Someone Used this ("+ user.getEmailId()+") Email Addredd Please change ir..");
		}
	}

	public void editUser(User user) {

		if (LibraryDatabase.getInstance().updateUser(user)) {
			manageUserView.aletMessage(user.getName() + " User updated Successfully..");
		} else {
			manageUserView.aletMessage("Please check User Details");
		}
	}

	public void onDeleteUser(String userEmailId) {

		int hasUser = LibraryDatabase.getInstance().onDeleteUser(userEmailId);

		if (hasUser == UserOperationCode.DELETED.getUserOperationCode()) {
			manageUserView.aletMessage(userEmailId + " User Removed Successfully..");
		} 
		else if(hasUser == UserOperationCode.USER_BORROED_BOOK.getUserOperationCode()) {	
			List<BorrowedBookByUser> books = LibraryDatabase.getInstance().getUserBorrowedBook();
			manageUserView.showBorrowedBooks(books, userEmailId);
		}
		else {
			manageUserView.aletMessage(userEmailId +" User does not exits");
		}
//		manageUserView.checkForUserManagement();
	}

	public void getUsers() {

		List<User> users = LibraryDatabase.getInstance().getUsers();
		showSearchBooks(users);
	}

	public void showSearchBooks(List<User> users) {

		if (users.size() > 0) {
			manageUserView.aletMessage("The Search Result");
			manageUserView.showSearchUsers(users);
		} else {
			manageUserView.aletMessage("The Users Found...");
		}
	}

	public void retriveUser() {

		if (!LibraryDatabase.getInstance().isUserFileRetrived()) {
			LibraryDatabase.getInstance().setUserFileRetrived(true);
			LibraryDatabase.getInstance().retriveDataFromFile(ModuleType.USER.getModuleType());
		}
	}

	public int getCurrentUserType() {
		return LibraryDatabase.getInstance().getCurrentUserType();
	}

	public int getUserType() {
		return LibraryDatabase.getInstance().getCurrentUserType();
	}
}
