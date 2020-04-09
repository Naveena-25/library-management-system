package com.capgemini.librarymanagementsystem.validation;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	
	public boolean validateId(int id) throws Exception {
		String idRegEx = "[1]{1}[0-9]{2}";
		boolean result = false;
		if (Pattern.matches(idRegEx, String.valueOf(id))) {
			result = true;
		} else {
			throw new Exception("Id should contains exactly 3 digits, first digit should be 1");
		}
		return result;
	}

	public boolean validateName(String name) throws Exception 
	{
		String nameRegEx = "^[A-Za-z\\s]+[A-Za-z\\s]$";
		boolean result = false;
		Pattern pattern = Pattern.compile(nameRegEx);
		Matcher matcher = pattern.matcher(name);
		if (matcher.matches()) 
		{
			result = true;
		} 
		else 
		{
			throw new Exception("Name should contains only Alphabets");
		}
		return result;
	}

	
	public boolean validateEmail(String email) throws Exception {
		String emailRegEx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		boolean result = false;
		Pattern pattern = Pattern.compile(emailRegEx);
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			result = true;
		} else {
			throw new Exception("Enter The Proper Email ID");
		}
		return result;
	}

	public boolean validatePassword(String password) throws Exception 
	{
		String passwordRegEx = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
		boolean result = false;
		Pattern pattern = Pattern.compile(passwordRegEx);
		Matcher matcher = pattern.matcher(password);
		
		if (matcher.matches()) 
		{
			result = true;
		}
		else
		{
			throw new Exception("Password should contain atleast 8 characters ,one uppercase,one lowercase and one symbol ");
		}
		return result;
	}
	public boolean validateMobileNumber(String mobileNumber) throws Exception{
		String mobileRegEx = "^([6789]{1}\\d{9})$";
		boolean result = false;
		Pattern pattern = Pattern.compile(mobileRegEx);
		Matcher matcher = pattern.matcher(mobileNumber);
		
		if (matcher.matches()) 
		{
			result = true;
		}
		else {
			throw new Exception("Enter 10 Digit Mobile Number, It Should Start With 6,7,8 or 9");
		}
		return result;
	}

}

				