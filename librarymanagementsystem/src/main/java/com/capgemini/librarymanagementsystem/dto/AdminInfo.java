package com.capgemini.librarymanagementsystem.dto;

import java.io.Serializable;

public class AdminInfo implements Serializable {

	private String emailId;
	private String password;

	public AdminInfo() {

	}

	public AdminInfo(String emailId, String password) {
		this.emailId = emailId;
		this.password = password;
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

	@Override
	public String toString() {
		return "AdminInfo [emailId=" + emailId + ", password=" + password + "]";
	}
}
