package com.capgemini.librarymanagementhibernate.service;

import java.util.List;

import com.capgemini.librarymanagementhibernate.dao.LibraryDAO;
import com.capgemini.librarymanagementhibernate.dto.LibraryUsers;
import com.capgemini.librarymanagementhibernate.dto.BookDetails;
import com.capgemini.librarymanagementhibernate.dto.RequestInfo;
import com.capgemini.librarymanagementhibernate.factory.Factory;

public class LibraryServiceImpl implements LibraryService {
	LibraryDAO dao = Factory.getLibraryDAO();

	@Override
	public LibraryUsers login(String emailId, String password) {

		return dao.login(emailId, password);
	}

	@Override
	public boolean addUser(LibraryUsers info) {

		return dao.addUser(info);
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

		return false;
	}

	@Override
	public LibraryUsers userLogin(String emailId, String password) {

		return dao.userLogin(emailId, password);
	}

	@Override
	public boolean changePassword(int userId, String oldPassword, String newPassword) {

		return dao.changePassword(userId, oldPassword, newPassword);
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
	public boolean isBookReceived(int requestId) {

		return dao.isBookReceived(requestId);
	}

}