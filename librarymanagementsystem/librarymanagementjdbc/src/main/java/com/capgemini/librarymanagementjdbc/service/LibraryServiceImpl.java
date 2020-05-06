package com.capgemini.librarymanagementjdbc.service;

import java.util.List;

import com.capgemini.librarymanagementjdbc.dao.LibraryDAO;
import com.capgemini.librarymanagementjdbc.dto.LibraryUsers;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;
import com.capgemini.librarymanagementjdbc.factory.Factory;

public class LibraryServiceImpl implements LibraryService {
	private LibraryDAO dao = Factory.getLibraryDAO();

	@Override
	public LibraryUsers login(String emailId, String password) {

		return dao.login(emailId, password);
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
	public LibraryUsers userLogin(String emailId, String password) {

		return dao.userLogin(emailId, password);
	}

	@Override
	public boolean changePassword(String emailId, String oldPassword, String newPassword) {

		return dao.changePassword(emailId, oldPassword, newPassword);
	}

	@Override
	public boolean bookRequest(int userId, int bookId) {

		return dao.bookRequest(userId, bookId);
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {

		return dao.bookReturn(userId, bookId);
	}

	@Override
	public boolean receiveBook(int requestId) {

		return dao.receiveBook(requestId);
	}

}