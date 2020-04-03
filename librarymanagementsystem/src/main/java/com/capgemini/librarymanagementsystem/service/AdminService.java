package com.capgemini.librarymanagementsystem.service;

import java.util.List;


import com.capgemini.librarymanagementsystem.dto.AdminInfo;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;

public interface AdminService {
	AdminInfo auth(String adminEmail, String password );
	boolean addUser(UserInfo userInfo);
	List<UserInfo> showUsers();
	BookDetails search(int bookId);
	boolean addBook(BookDetails details);
	List<BookDetails> showBooks();
	List<RequestInfo> showRequests();
	boolean issueBook(UserInfo user, BookDetails details);
	boolean removebook(BookDetails details);
	boolean receivedBook(UserInfo user, BookDetails details);
	
}
