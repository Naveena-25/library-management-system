package com.capgemini.librarymanagementhibernate.service;

import java.util.List;

import com.capgemini.librarymanagementhibernate.dto.LibraryUsers;
import com.capgemini.librarymanagementhibernate.dto.BookDetails;
import com.capgemini.librarymanagementhibernate.dto.RequestInfo;

public interface LibraryService {
	LibraryUsers login(String emailId, String password);

	boolean addUser(LibraryUsers info);

	List<LibraryUsers> viewUsers();

	boolean addBook(BookDetails bookDetails);

	BookDetails search(int bookId);

	List<BookDetails> viewBooks();

	List<RequestInfo> viewRequests();

	boolean issueBook(int requestId);

	boolean removeBook(int bookId);

	LibraryUsers userLogin(String emailId, String password);
	
	boolean changePassword(int userId,String oldPassword, String newPassword);

	boolean bookRequest(int userId, int bookId);

	boolean bookReturn(int userId, int bookId);

	boolean receiveBook(int requestId);
}
