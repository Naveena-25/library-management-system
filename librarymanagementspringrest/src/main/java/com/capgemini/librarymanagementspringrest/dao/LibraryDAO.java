package com.capgemini.librarymanagementspringrest.dao;

import java.util.List;

import com.capgemini.librarymanagementspringrest.dto.BookDetails;
import com.capgemini.librarymanagementspringrest.dto.LibraryUsers;
import com.capgemini.librarymanagementspringrest.dto.RequestInfo;

public interface LibraryDAO {
	
	LibraryUsers userLogin(String emailId, String password);

	boolean addUser(LibraryUsers libraryUsers);

	List<LibraryUsers> viewUsers();

	boolean addBook(BookDetails bookDetails);

	BookDetails search(int bookId);

	List<BookDetails> viewBooks();
	
	BookDetails updateBook(BookDetails bookDetails);

	List<RequestInfo> viewRequests();

	boolean issueBook(int requestId);
	
	List<RequestInfo> getAllReqBook();
	
	List<RequestInfo> getAllReturnedBook();

	boolean removeBook(int bookId);

	boolean bookRequest(int userId, int bookId);
	
	List<RequestInfo> userBooks(int userId);
	
	boolean bookReturn(int userId, int bookId);

	boolean receiveBook(int requestId);

}
