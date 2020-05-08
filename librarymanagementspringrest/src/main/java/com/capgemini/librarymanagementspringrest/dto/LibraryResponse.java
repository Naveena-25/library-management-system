package com.capgemini.librarymanagementspringrest.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
