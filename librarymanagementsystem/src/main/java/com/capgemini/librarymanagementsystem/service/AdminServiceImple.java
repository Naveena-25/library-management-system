package com.capgemini.librarymanagementsystem.service;

import java.util.List;

import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.dto.AdminInfo;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.factory.Factory;

public class AdminServiceImple implements AdminService{
	private AdminDAO dao = Factory.getAdminDAO();

	@Override
	public AdminInfo auth(String adminEmail, String password) {

		return dao.auth(adminEmail, password);
	}

	@Override
	public boolean addUser(UserInfo userInfo) {

		return dao.addUser(userInfo);
	}

	@Override
	public List<UserInfo> showUsers() {
		return dao.showUsers();
	}

	@Override
	public BookDetails search(int bookId) {

		return dao.search(bookId);
	}

	@Override
	public boolean addBook(BookDetails details) {

		return dao.addBook(details);
	}

	@Override
	public List<BookDetails> showBooks() {

		return dao.showBooks();
	}

	@Override
	public List<RequestInfo> showRequests() {

		return dao.showRequests();
	}

	@Override
	public boolean issueBook(UserInfo user, BookDetails bookDetails) {


		return dao.issueBook(user, bookDetails);
	}

	@Override
	public boolean removebook(BookDetails bookDetails) {

		return dao.removebook(bookDetails);
	}

	@Override
	public boolean receivedBook(UserInfo user, BookDetails bookDetails) {

		return dao.receivedBook(user, bookDetails);
	}




}
