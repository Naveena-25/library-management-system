package com.capgemini.librarymanagementhibernate.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "request")
public class RequestInfo implements Serializable{

	@Id
	@Column
	@GeneratedValue
	private int rId;
	@Column
	private int id;
	@Column(name = "bookId",unique = true)
	private int bookId;
	@Column
	private Date issueDate;
	@Column
	private Date expectedReturnDate;
	@Column
	private Date returnedDate;
}
