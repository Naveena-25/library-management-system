package com.capgemini.librarymanagementhibernate.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "library_users")
		
public class LibraryUsers implements Serializable{
	@Id
	@Column
	private int id;
	@Column
	private String name;
	@Column(unique = true)
	private String emailId;
	@Column
	private String password;
	@Column(unique = true)
	private String mobileNumber;
	@Column
	private String role;
	@Column
	private int noOfBooksBorrowed;
	@Column
	private double fine;
}
