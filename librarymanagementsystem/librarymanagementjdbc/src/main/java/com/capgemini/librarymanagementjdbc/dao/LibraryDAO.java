package com.capgemini.librarymanagementjdbc.dao;

import java.util.List;
import com.capgemini.librarymanagementjdbc.dto.LibraryUsers;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;

public interface LibraryDAO {
	LibraryUsers login(String emailId, String password);

	boolean addUser(LibraryUsers libraryUsers);

	List<LibraryUsers> viewUsers();

	boolean addBook(BookDetails bookDetails);

	BookDetails search(int bookId);

	List<BookDetails> viewBooks();

	List<RequestInfo> viewRequests();

	boolean issueBook(int requestId);

	boolean removeBook(int bookId);

	LibraryUsers userLogin(String emailId, String password);

	boolean changePassword(String emailId, String oldPassword, String newPassword);

	boolean bookRequest(int userId, int bookId);

	boolean bookReturn(int userId, int bookId);

	boolean receiveBook(int requestId);
}
