package com.capgemini.librarymanagementjdbc.service;

import java.util.List;

import com.capgemini.librarymanagementjdbc.dto.AdminInfo;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;

public interface LibraryService {
	AdminInfo login(String emailId, String password );
	boolean addUser(AdminInfo info);
	List<AdminInfo> viewUsers();
	boolean addBook(BookDetails bookDetails);
	BookDetails search(int bookId);
	List<BookDetails> viewBooks();
	List<RequestInfo> viewRequests();
	boolean issueBook(int rid);
	boolean removeBook(int bookId);
	
	AdminInfo userLogin(String emailId,String password);
	RequestInfo bookRequest(int id , int bookId);
	boolean bookReturn(int userId,int bookId);
	boolean isBookReceived(int rid);
}