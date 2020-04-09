package com.capgemini.librarymanagementsystem.dao;

import java.util.Date;

import com.capgemini.librarymanagementsystem.db.DataBase;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.exception.Exception;

public class UserDAOImpl implements UserDAO{
	Date returnedDate = new Date();

	@Override
	public UserInfo userLogin(String email, String password) {
		for(UserInfo info : DataBase.USER_INFOS) {
			if(info.getEmail().equals(email)&& info.getPassword().equals(password)) {
				return info;
			}
		}
		throw new Exception("Invalid user credentials");
	}

	@Override
	public BookDetails search(int bookId) {
		for(BookDetails bookInfo : DataBase.BOOK_DETAILS) {
			if(bookInfo.getBookId()==bookId) {
				return bookInfo;
			}
		}

		throw new Exception("No Book is Found");

	}

	@Override
	public RequestInfo bookRequest(UserInfo user,BookDetails bookDetails) {
		boolean flag = false, isRequestExists = false;
		RequestInfo requestInfo = new RequestInfo();
		UserInfo userInfo = new UserInfo();
		BookDetails bookInfo = new BookDetails();

		for (RequestInfo requestInfo2 : DataBase.REQUEST_INFOS) {
			if (bookInfo.getBookId() == requestInfo2.getBookdetails().getBookId()) {
				isRequestExists = true;

			}

		}


		if (!isRequestExists) {
			for (UserInfo userInfo1 : DataBase.USER_INFOS) {
				if (user.getUserId() == userInfo1.getUserId()) {
					for (BookDetails bookInfo1 : DataBase.BOOK_DETAILS) {
						if (bookInfo1.getBookId() == bookDetails.getBookId()) {		
							userInfo = userInfo1;
							bookInfo = bookInfo1;
							flag = true; 
							break;
						}
					}
				}

			}
		}
		if (flag == true) {
			requestInfo.setBookdetails(bookInfo);
			requestInfo.setUserInfo(userInfo);
			DataBase.REQUEST_INFOS.add(requestInfo);
			return requestInfo;
		}
		throw new Exception("Invalid request or you cannot request that book");
	}

	@Override
	public RequestInfo bookReturn(UserInfo userdetails,BookDetails bookdetails) {

		for (RequestInfo requestInfo : DataBase.REQUEST_INFOS) {

			if (requestInfo.getBookdetails().getBookId() == bookdetails.getBookId() &&
					requestInfo.getUserInfo().getUserId() == userdetails.getUserId() &&
					requestInfo.isIssued() == true) {
				requestInfo.setReturned(true);
				requestInfo.setReturnedDate(returnedDate);
				return requestInfo;
			}

		}

		throw new Exception("Invalid Book return");
	}

}
