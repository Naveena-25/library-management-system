package com.capgemini.librarymanagementjdbc.dto;

import java.io.Serializable;
import java.util.Date;

public class RequestInfo implements Serializable{
	private BookDetails bookdetails;
	private AdminInfo info;
	private int rid;
	private int id;
	private int bookId;
	private Date issueDate;
	private Date returnDate;
	private boolean isIssued;
	private boolean isReturned;
	public BookDetails getBookdetails() {
		return bookdetails;
	}
	public void setBookdetails(BookDetails bookdetails) {
		this.bookdetails = bookdetails;
	}
	public AdminInfo getInfo() {
		return info;
	}
	public void setInfo(AdminInfo info) {
		this.info = info;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public boolean isIssued() {
		return isIssued;
	}
	public void setIssued(boolean isIssued) {
		this.isIssued = isIssued;
	}
	public boolean isReturned() {
		return isReturned;
	}
	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}

}
