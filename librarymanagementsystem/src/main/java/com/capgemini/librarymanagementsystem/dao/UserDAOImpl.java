package com.capgemini.librarymanagementsystem.dao;

import java.util.Calendar;
import java.util.Date;

import com.capgemini.librarymanagementsystem.db.DataBase;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.exception.LMSException;

public class UserDAOImpl implements UserDAO {

	Date returnedDate;

	@Override
	public boolean userLogin(String emailId, String password) {
		for (UserInfo userInfo : DataBase.USER_INFOS) {
			if (userInfo.getEmailId().equals(emailId) && userInfo.getPassword().equals(password)) {
				return true;
			}
		}
		throw new LMSException("Invalid user Credentials Please Enter Correctly");
	}

	@Override
	public boolean changePassword(String emailId, String oldPassword, String newPassword) {
		for (UserInfo userInfo : DataBase.USER_INFOS) {
			if ((userInfo.getEmailId().equals(emailId)) && (userInfo.getPassword().equals(oldPassword))) {
				userInfo.setPassword(newPassword);
				return true;
			}
		}

		throw new LMSException("Password Can't be Changed Due To Invalid Credentials");
	}

	@Override
	public BookDetails search(int bookId) {
		for (BookDetails bookInfo : DataBase.BOOK_DETAILS) {
			if (bookInfo.getBookId() == bookId) {
				return bookInfo;
			}
		}

		throw new LMSException("Book is Not Found In The Library");

	}

	@Override
	public boolean bookRequest(int userId, int bookId) {
		RequestInfo requestInfo = new RequestInfo();
		boolean isRequestExists = false;
		for (RequestInfo info : DataBase.REQUEST_INFOS) {
			if (bookId == info.getBookId()) {
				isRequestExists = true;
			}
		}

		if (!isRequestExists) {
			for (UserInfo user : DataBase.USER_INFOS) {
				if (userId == user.getUserId()) {
					for (BookDetails book : DataBase.BOOK_DETAILS) {
						if ((book.getBookId() == bookId) && (book.isAvailable() == true)) {
							requestInfo.setBookId(bookId);
							requestInfo.setUserId(userId);
							requestInfo.setIssued(false);
							DataBase.REQUEST_INFOS.add(requestInfo);
							return true;
						}
					}
				}
			}
		}

		throw new LMSException("Request Can't be place to Admin");
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 20);
		returnedDate = calendar.getTime();

		for (RequestInfo requestInfo : DataBase.REQUEST_INFOS) {

			if ((requestInfo.getBookId() == bookId) && (requestInfo.getUserId() == userId)
					&& (requestInfo.isIssued() == true)) {
				requestInfo.setReturned(true);
				requestInfo.setReturnedDate(returnedDate);
				return true;
			}

		}

		throw new LMSException("Invalid Book Return");
	}

}
