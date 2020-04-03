package com.capgemini.librarymanagementsystem.service;

import com.capgemini.librarymanagementsystem.dao.UserDAO;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.factory.Factory;


public class UserServiceImpl implements UserService{
	
	private UserDAO dao = Factory.getUserDAOInterface();
	@Override
	public UserInfo userLogin(String email, String password) {
		return dao.userLogin(email, password);
		
	
	}
	@Override
	public BookDetails search(int bookId) {
		return dao.search(bookId);
	}
	@Override
	public RequestInfo bookRequest(UserInfo userDetails, BookDetails bookDetails) {
		return dao.bookRequest(userDetails, bookDetails);
	}
	@Override
	public RequestInfo bookReturn(UserInfo userdetails, BookDetails bookdetails) {
		return dao.bookReturn(userdetails, bookdetails);
	}

}
