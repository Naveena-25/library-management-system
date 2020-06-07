package com.capgemini.librarymanagementhibernate.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.librarymanagementhibernate.exception.LMSException;

public class Validation {

	public boolean validateId(int id) throws LMSException {
		String idRegEx = "[0-9]{3}";
		boolean result = false;

		if (Pattern.matches(idRegEx, String.valueOf(id))) {
			result = true;
		} else {
			throw new LMSException("Please Enter valid Id which Should Contain Exact 3 Digits");
		}
		return result;
	}

	public boolean validateName(String name) throws LMSException {
		String nameRegEx = "^[A-Za-z\\s]+$";
		boolean result = false;

		Pattern pattern = Pattern.compile(nameRegEx);
		Matcher matcher = pattern.matcher(name);
		if (matcher.matches()) {
			result = true;
		} else {
			throw new LMSException("Name Should Contain only Alphabets");
		}
		return result;
	}

	public boolean validateEmail(String emailId) throws LMSException {
		String emailRegEx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		boolean result = false;

		Pattern pattern = Pattern.compile(emailRegEx);
		Matcher matcher = pattern.matcher(emailId);
		if (matcher.matches()) {
			result = true;
		} else {
			throw new LMSException("Please Enter a Valid Email ID");
		}
		return result;
	}

	public boolean validatePassword(String password) throws LMSException {
		String passwordRegEx = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$";
		boolean result = false;

		Pattern pattern = Pattern.compile(passwordRegEx);
		Matcher matcher = pattern.matcher(password);

		if (matcher.matches()) {
			result = true;
		} else {
			throw new LMSException(
					"Enter Valid Passsword with min 6 Characters, One Uppercase and Lowercase and a Symbol ");
		}
		return result;
	}

	public boolean validateMobileNumber(String mobileNumber) throws LMSException {
		String mobileRegEx = "^([6789]{1}\\d{9})$";
		boolean result = false;

		Pattern pattern = Pattern.compile(mobileRegEx);
		Matcher matcher = pattern.matcher(mobileNumber);

		if (matcher.matches()) {
			result = true;
		} else {
			throw new LMSException("Enter 10 Digit Mobile Number, It Should Start With 6,7,8 or 9");
		}
		return result;
	}

}
