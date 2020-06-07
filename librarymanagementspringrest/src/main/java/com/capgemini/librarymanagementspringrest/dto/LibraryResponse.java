package com.capgemini.librarymanagementspringrest.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LibraryResponse implements Serializable {

	private String message;
	private boolean error;
	private String role;
	private LibraryUsers libraryUsers;
	private List<LibraryUsers> userList;
	private BookDetails bookDetails;
	private List<BookDetails> bookList;
	private RequestInfo requestInfo;
	private List<RequestInfo> requestList;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public LibraryUsers getLibraryUsers() {
		return libraryUsers;
	}

	public void setLibraryUsers(LibraryUsers libraryUsers) {
		this.libraryUsers = libraryUsers;
	}

	public List<LibraryUsers> getUserList() {
		return userList;
	}

	public void setUserList(List<LibraryUsers> userList) {
		this.userList = userList;
	}

	public BookDetails getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(BookDetails bookDetails) {
		this.bookDetails = bookDetails;
	}

	public List<BookDetails> getBookList() {
		return bookList;
	}

	public void setBookList(List<BookDetails> bookList) {
		this.bookList = bookList;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public List<RequestInfo> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<RequestInfo> requestList) {
		this.requestList = requestList;
	}
}
