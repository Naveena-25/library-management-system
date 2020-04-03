package com.capgemini.librarymanagementjdbc.service;

import java.util.List;


import com.capgemini.librarymanagementjdbc.dao.LibraryDAO;
import com.capgemini.librarymanagementjdbc.dto.AdminInfo;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;
import com.capgemini.librarymanagementjdbc.factory.Factory;

public class LibraryServiceImpl implements LibraryService{
	private LibraryDAO dao = Factory.getLibraryDAO();

	@Override
	public AdminInfo login(String emailId, String password) {
		// TODO Auto-generated method stub
		return dao.login(emailId, password);
	}

	@Override
	public boolean addUser(AdminInfo info) {
		// TODO Auto-generated method stub
		return dao.addUser(info);
	}

	@Override
	public List<AdminInfo> viewUsers() {
		// TODO Auto-generated method stub
		return dao.viewUsers();
	}

	@Override
	public boolean addBook(BookDetails bookDetails) {
		// TODO Auto-generated method stub
		return dao.addBook(bookDetails);
	}

	@Override
	public BookDetails search(int bookId) {
		// TODO Auto-generated method stub
		return dao.search(bookId);
	}

	@Override
	public List<BookDetails> viewBooks() {
		// TODO Auto-generated method stub
		return dao.viewBooks();
	}

	@Override
	public List<RequestInfo> viewRequests() {
		// TODO Auto-generated method stub
		return dao.viewRequests();
	}

	@Override
	public boolean issueBook(int rid) {
		// TODO Auto-generated method stub
		return dao.issueBook(rid);
	}

	@Override
	public boolean removebook(BookDetails bookDetails) {
		// TODO Auto-generated method stub
		return dao.addBook(bookDetails);
	}

	@Override
	public boolean receivedBook(AdminInfo info, BookDetails bookDetails) {
		// TODO Auto-generated method stub
		return dao.receivedBook(info, bookDetails);
	}

	@Override
	public AdminInfo userLogin(String emailId, String password) {
		// TODO Auto-generated method stub
		return dao.userLogin(emailId, password);
	}

	@Override
	public RequestInfo bookRequest(AdminInfo info,BookDetails bookDetails) {
		// TODO Auto-generated method stub
		return dao.bookRequest(info, bookDetails);
	}

	@Override
	public RequestInfo bookReturn(AdminInfo info, BookDetails bookDetails) {
		// TODO Auto-generated method stub
		return dao.bookReturn(info, bookDetails);
	}


	
	
}