package com.capgemini.librarymanagementsystem.dto;

import java.io.Serializable;
import java.util.Date;

public class RequestInfo implements Serializable{
	private BookDetails bookdetails;
	private UserInfo userInfo;
	private boolean isIssued;
	private boolean isReturned;
	private Date issueDate;
	private Date returnDate;
	private Date returnedDate;
	public BookDetails getBookdetails() {
		return bookdetails;
	}
	public void setBookdetails(BookDetails bookdetails) {
		this.bookdetails = bookdetails;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
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
	public Date getReturnedDate() {
		return returnedDate;
	}
	public void setReturnedDate(Date returnedDate) {
		this.returnedDate = returnedDate;
	}


}
