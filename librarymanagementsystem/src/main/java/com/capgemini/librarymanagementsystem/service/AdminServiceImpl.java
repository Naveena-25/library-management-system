package com.capgemini.librarymanagementsystem.service;

import java.util.List;

import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.factory.Factory;

public class AdminServiceImpl implements AdminService {

	private AdminDAO dao = Factory.getAdminDAO();

	@Override
	public boolean authentication(String adminEmail, String password) {

		return dao.authentication(adminEmail, password);
	}

	@Override
	public boolean addUser(UserInfo userInfo) {

		return dao.addUser(userInfo);
	}

	@Override
	public List<UserInfo> viewUsers() {

		return dao.viewUsers();
	}

	@Override
	public BookDetails search(int bookId) {

		return dao.search(bookId);
	}

	@Override
	public boolean addBook(BookDetails bookDetails) {

		return dao.addBook(bookDetails);
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
	public boolean issueBook(int userId, int bookId) {

		return dao.issueBook(userId, bookId);
	}

	@Override
	public boolean removeBook(int bookId) {

		return dao.removeBook(bookId);
	}

	@Override
	public boolean receiveBook(int userId, int bookId) {

		return dao.receiveBook(userId, bookId);
	}

}
