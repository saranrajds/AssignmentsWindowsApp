module Library_Management {
	exports com.saran.librarymanagement.model;
	requires com.fasterxml.jackson.databind;
	opens com.saran.librarymanagement.enums;
}