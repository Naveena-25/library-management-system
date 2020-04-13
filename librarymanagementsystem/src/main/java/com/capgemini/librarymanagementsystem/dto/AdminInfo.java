package com.capgemini.librarymanagementsystem.dto;

import java.io.Serializable;

public class AdminInfo implements Serializable {

	private String emailId = "admin@gmail.com";
	private String password = "Admin@1";

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

}
