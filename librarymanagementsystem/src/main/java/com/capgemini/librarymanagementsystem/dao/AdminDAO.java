package com.capgemini.librarymanagementsystem.dao;

import java.util.List;

import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;

public interface AdminDAO {

	boolean authentication(String adminEmail, String password);

	boolean addUser(UserInfo userInfo);

	List<UserInfo> showUsers();

	BookDetails search(int bookId);

	boolean addBook(BookDetails bookDetails);

	List<BookDetails> showBooks();

	List<RequestInfo> showRequests();

	boolean issueBook(int userId, int bookId);

	boolean removeBook(int bookId);

	boolean receiveBook(int userId, int bookId);

}
