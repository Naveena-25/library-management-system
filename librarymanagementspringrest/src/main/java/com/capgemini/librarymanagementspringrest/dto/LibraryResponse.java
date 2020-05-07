package com.capgemini.librarymanagementspringrest.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class LibraryResponse implements Serializable {
	private String message;
	private boolean error;
	private LibraryUsers libraryUsers;
	private List<LibraryUsers> userList;
	private BookDetails bookDetails;
	private List<BookDetails> bookList;
	private RequestInfo requestInfo;
	private List<RequestInfo> requestList;
}
