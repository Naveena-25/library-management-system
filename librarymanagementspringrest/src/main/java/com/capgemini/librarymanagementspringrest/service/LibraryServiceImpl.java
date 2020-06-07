package com.capgemini.librarymanagementspringrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.librarymanagementspringrest.dao.LibraryDAO;
import com.capgemini.librarymanagementspringrest.dto.BookDetails;
import com.capgemini.librarymanagementspringrest.dto.LibraryUsers;
import com.capgemini.librarymanagementspringrest.dto.RequestInfo;

@Service
public class LibraryServiceImpl implements LibraryService {
	@Autowired
	LibraryDAO dao;

	@Override
	public LibraryUsers userLogin(String emailId, String password) {

		return dao.userLogin(emailId, password);
	}

	@Override
	public boolean addUser(LibraryUsers libraryUsers) {

		return dao.addUser(libraryUsers);
	}

	@Override
	public List<LibraryUsers> viewUsers() {

		return dao.viewUsers();
	}

	@Override
	public boolean addBook(BookDetails bookDetails) {

		return dao.addBook(bookDetails);
	}

	@Override
	public BookDetails search(int bookId) {

		return dao.search(bookId);
	}

	@Override
	public List<BookDetails> viewBooks() {

		return dao.viewBooks();
	}

	@Override
	public BookDetails updateBook(BookDetails bookDetails) {
		
		return dao.updateBook(bookDetails);
	}

	@Override
	public List<RequestInfo> viewRequests() {

		return dao.viewRequests();
	}

	@Override
	public boolean issueBook(int requestId) {

		return dao.issueBook(requestId);
	}

	@Override
	public boolean removeBook(int bookId) {

		return dao.removeBook(bookId);
	}

	@Override
	public boolean bookRequest(int userId, int bookId) {

		return dao.bookRequest(userId, bookId);
	}
	
	@Override
	public List<RequestInfo> userBooks(int userId) {

		return dao.userBooks(userId);
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {

		return dao.bookReturn(userId, bookId);
	}

	@Override
	public boolean receiveBook(int requestId) {

		return dao.receiveBook(requestId);
	}

	@Override
	public List<RequestInfo> getAllReqBook() {
		
		return dao.getAllReqBook();
	}

	@Override
	public List<RequestInfo> getAllReturnedBook() {
		
		return dao.getAllReturnedBook();
	}


}
