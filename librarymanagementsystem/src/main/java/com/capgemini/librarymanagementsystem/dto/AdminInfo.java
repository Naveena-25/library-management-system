package com.capgemini.librarymanagementsystem.dto;

import java.io.Serializable;

public class AdminInfo implements Serializable{

	private String adminEmail="admin@gmail.com";
	private String password="admin123";

	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
