package com.fashionworld.login.validation;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PhoneNumberValidator {
	
	public static boolean validatePhoneNumber(String phoneNumber) {
        // Define the regular expression pattern
        String pattern = "^[6-9]\\d{9}$";
        
        // Create a Pattern object
        Pattern regexPattern = Pattern.compile(pattern);
        
        // Create a Matcher object to perform matching
        Matcher matcher = regexPattern.matcher(phoneNumber);
        
        // Check if the phone number matches the pattern
        return matcher.matches();
    }
    
    public static void main(String[] args) {
        String phoneNumber1 = "6123456789";
        String phoneNumber2 = "5123456789";
        String phoneNumber3 = "91234567890";
        
        System.out.println(phoneNumber1 + ": " + validatePhoneNumber(phoneNumber1));
        System.out.println(phoneNumber2 + ": " + validatePhoneNumber(phoneNumber2));
        System.out.println(phoneNumber3 + ": " + validatePhoneNumber(phoneNumber3));
    }
}


