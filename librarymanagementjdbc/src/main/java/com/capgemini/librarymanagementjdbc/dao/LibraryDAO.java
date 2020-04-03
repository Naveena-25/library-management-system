package com.capgemini.librarymanagementjdbc.dao;

import java.util.List;
import com.capgemini.librarymanagementjdbc.dto.AdminInfo;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;

public interface LibraryDAO 
{
	AdminInfo login(String emailId, String password );
	boolean addUser(AdminInfo info);
	List<AdminInfo> viewUsers();
	boolean addBook(BookDetails bookDetails);
	BookDetails search(int bookId);
	List<BookDetails> viewBooks();
	List<RequestInfo> viewRequests();
	boolean issueBook(int rid);
	boolean removebook(BookDetails bookDetails);
	boolean receivedBook(AdminInfo info, BookDetails bookDetails);
	
	AdminInfo userLogin(String emailId,String password);
	RequestInfo bookRequest(AdminInfo info,BookDetails bookDetails);
	RequestInfo bookReturn(AdminInfo info,BookDetails bookDetails);
}
