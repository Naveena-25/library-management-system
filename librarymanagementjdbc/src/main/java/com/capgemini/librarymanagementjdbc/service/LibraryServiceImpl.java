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
	public boolean issueBook(int rid) {
		return dao.issueBook(rid);
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
	public RequestInfo bookRequest(int userId, int bookId) {

		return dao.bookRequest(userId, bookId);
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {

		return dao.bookReturn(userId, bookId);
	}

	@Override
	public boolean isBookReceived(int rid) {

		return dao.isBookReceived(rid);
	}

}