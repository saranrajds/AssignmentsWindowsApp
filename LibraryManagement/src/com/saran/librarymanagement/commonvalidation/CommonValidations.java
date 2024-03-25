package com.saran.librarymanagement.commonvalidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidations {

    public static boolean isValidEmail(String email) {
    	
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static boolean isValidPhoneNumber(String phoneNumber) {

    	String regex = "^\\d{10}$";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(phoneNumber);
    	return matcher.matches();
    }
}
