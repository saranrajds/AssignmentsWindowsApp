package com.saran.librarymanagement.librarysetup;

import java.util.ArrayList;
import com.saran.librarymanagement.commonvalidation.CommonValidations;
import com.saran.librarymanagement.enums.ModuleType;
import com.saran.librarymanagement.librarydatatabase.LibraryDatabase;
import com.saran.librarymanagement.model.Library;

class LibrarySetupModel {

	private LibrarySetupView librarySetupView;
	ArrayList<Library> library;
	
	public LibrarySetupModel(LibrarySetupView librarySetupView) {
		
		this.librarySetupView = librarySetupView;
//		library = LibraryDatabase.getInstance().getLibrary();
	}

	public void createLibrary(Library libraryInfo) {

		if(!CommonValidations.isValidEmail(libraryInfo.getEmailId())) {
			librarySetupView.showAlert("Email is In-Valid...");
			return;
		}
		else if(libraryInfo.getName().length() < 3 || libraryInfo.getName().isEmpty())
		{
			librarySetupView.showAlert("Name is In-Valid...");
			return;
		}
		
		LibraryDatabase.getInstance().inserLibrary(libraryInfo);
		librarySetupView.onSetupCompleted();
	}

	public void startUp() {
				
		if(library.size() == 0|| library.get(0).getId() == 0) {
			librarySetupView.initialSetup();
		}
		else {
			librarySetupView.onSetupCompleted();
		}	
	}

	public ArrayList<Library> getLibraryInfo() {		
		library = LibraryDatabase.getInstance().getLibraryInfo();
		return library;
	}
	
	public int getUserType() {
		return LibraryDatabase.getInstance().getCurrentUserType();
	}

	public void retriveLibraryData() {
		LibraryDatabase.getInstance().retriveDataFromFile(ModuleType.LIBRARIY.getModuleType());
	}
}
