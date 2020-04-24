package com.capgemini.librarymanagementsystem.service;

import com.capgemini.librarymanagementsystem.dao.UserDAO;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.factory.Factory;

public class UserServiceImpl implements UserService {

	private UserDAO dao = Factory.getUserDAO();

	@Override
	public boolean userLogin(String emailId, String password) {

		return dao.userLogin(emailId, password);
	}

	@Override
	public boolean changePassword(String emailId, String oldPassword, String newPassword) {
		
		return dao.changePassword(emailId, oldPassword, newPassword);
	}

	@Override
	public BookDetails search(int bookId) {

		return dao.search(bookId);
	}

	@Override
	public boolean bookRequest(int userId, int bookId) {

		return dao.bookRequest(userId, bookId);
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {

		return dao.bookReturn(userId, bookId);
	}

}
