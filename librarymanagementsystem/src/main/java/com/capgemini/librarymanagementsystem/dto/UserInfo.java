package com.capgemini.librarymanagementsystem.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserInfo implements Serializable {

	private int userId;
	private String userName;
	private String emailId;
	private String password;
	private String mobileNumber;
	private int noOfBooksBorrowed;
	private double fine;

	public UserInfo() {

	}

	public UserInfo(int userId, String userName, String emailId, String password, String mobileNumber,
			int noOfBooksBorrowed, double fine) {
		this.userId = userId;
		this.userName = userName;
		this.emailId = emailId;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.noOfBooksBorrowed = noOfBooksBorrowed;
		this.fine = fine;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getNoOfBooksBorrowed() {
		return noOfBooksBorrowed;
	}

	public void setNoOfBooksBorrowed(int noOfBooksBorrowed) {
		this.noOfBooksBorrowed = noOfBooksBorrowed;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", userName=" + userName + ", emailId=" + emailId + ", password="
				+ password + ", mobileNumber=" + mobileNumber + ", noOfBooksBorrowed=" + noOfBooksBorrowed + ", fine="
				+ fine + "]";
	}
}
