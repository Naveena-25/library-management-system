package com.capgemini.librarymanagementsystem.dao;

import java.util.List;

import com.capgemini.librarymanagementsystem.dto.AdminInfo;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;

public interface AdminDAO {
	AdminInfo auth(String adminEmail, String password );
	boolean addUser(UserInfo userInfo);
	List<UserInfo> showUsers();
	BookDetails search(int bookId);
	boolean addBook(BookDetails bookDetails);
	List<BookDetails> showBooks();
	List<RequestInfo> showRequests();
	boolean issueBook(UserInfo user, BookDetails bookDetails);
	boolean removebook(BookDetails bookDetails);
	boolean receivedBook(UserInfo user, BookDetails bookDetails);
}
