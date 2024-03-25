package com.saran.librarymanagement.manageusers;

import java.util.List;
import com.saran.librarymanagement.commonvalidation.CommonValidations;
import com.saran.librarymanagement.enums.ModuleType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Book;
import com.saran.librarymanagement.model.User;

public class ManageUserModel {
	
	private ManageUserView manageUserView;
	
	ManageUserModel(ManageUserView manageUserView) {
		this.manageUserView = manageUserView;
	}

	public void addNewUser(User user, boolean isFromNewUserAdd){
		
		if(!CommonValidations.isValidEmail(user.getEmailId()))
		{
			manageUserView.aletMessage("Enter validate Email Id...");
		}
		else { 
			if(isFromNewUserAdd) {
				addNewUser(user);
			}
			else {
				editUser(user);
			}
		}
		manageUserView.checkForUserManagement();
	}
	
	public void addNewUser(User user) {
		
		if(LibraryDatabase.getInstance().insertUser(user))
		{
			manageUserView.aletMessage("User Added Successfully..");
		}
		else {
			manageUserView.aletMessage("User did not added. Please tyr again");
		}
	}
	
	public void editUser(User user) {
		
		if(LibraryDatabase.getInstance().updateUser(user))
		{
			manageUserView.aletMessage(user.getId()+" Book updated Successfully..");
		}
		else {
			manageUserView.aletMessage("User did not updated. Please tyr again");
		}
	}

	public void onDeleteUser(int userId) {
		
		boolean hasUser = LibraryDatabase.getInstance().onDeleteUser(userId);
		
		if (hasUser) {
			manageUserView.aletMessage(userId+" Book Removed Successfully..");
		} else {
			manageUserView.aletMessage("%d Book does not exits");
		}
		manageUserView.checkForUserManagement();
	}

	public void getUsers() {
		
		List<User> users = LibraryDatabase.getInstance().getUsers();
		showSearchBooks(users);
	}
	
	public void showSearchBooks(List<User> users) {
		
		if(users.size() > 0) {
			manageUserView.aletMessage("The Search Result");
			manageUserView.showSearchUsers(users);
		}
		else {
			manageUserView.aletMessage("The Users Found...");
		}
	}
	
	public void retriveUser() {
		
		if(!LibraryDatabase.getInstance().isUserFileRetrived()) {
			LibraryDatabase.getInstance().setUserFileRetrived(true);
			LibraryDatabase.getInstance().retriveDataFromFile(ModuleType.USER.getModuleType());
		}
	}
}
