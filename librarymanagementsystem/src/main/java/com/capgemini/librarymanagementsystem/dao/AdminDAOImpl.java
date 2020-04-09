package com.capgemini.librarymanagementsystem.dao;


import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystem.db.DataBase;
import com.capgemini.librarymanagementsystem.dto.AdminInfo;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.exception.Exception;

public class AdminDAOImpl implements AdminDAO{
	Date date = new Date();
	Date expectedReturnDate = new Date();
	Calendar calendar = Calendar.getInstance();

	@Override
	public AdminInfo auth(String adminEmail, String password) {
		AdminInfo details = new AdminInfo();
		if(details.getAdminEmail().equals(adminEmail)&& details.getPassword().equals(password)) {
			return details;
		}
		throw new Exception("Invalid Admin Credentials");
	}
	@Override
	public boolean addUser(UserInfo userInfo) {
		for(UserInfo user : DataBase.USER_INFOS) {
			if(user.getUserId()==userInfo.getUserId() || user.getEmail().equalsIgnoreCase(userInfo.getEmail())) {
				throw new Exception("Cannot add user, as user already exists in the DB");
			}
		} DataBase.USER_INFOS.add(userInfo);
		return true;
	}
	@Override
	public List<UserInfo> showUsers() {
		List<UserInfo> userList = new LinkedList<UserInfo>();
		for (UserInfo user : DataBase.USER_INFOS) {
			user.getUserId();
			user.getUserName();
			user.getEmail();
			user.getPassword();
			user.getMobileNumber();
			userList.add(user);
		}
		return userList;
	}

	public BookDetails search(int bookId) {
		for(BookDetails book : DataBase.BOOK_DETAILS) {
			if(book.getBookId()==bookId) {
				return book;
			}
		}
		throw new Exception("invalid Search");
	}

	@Override
	public boolean addBook(BookDetails details) {
		boolean add = true;
		for (BookDetails bookInfo : DataBase.BOOK_DETAILS) {
			if(bookInfo.getBookId()==details.getBookId()) {
				throw new Exception("Book Can't Be Added, As Book is Already Exists");
			}
		}
		DataBase.BOOK_DETAILS.add(details);
		return add;
	}
	@Override
	public List<BookDetails> showBooks() {
		List<BookDetails> books = new LinkedList<BookDetails>();
		for (BookDetails bookDetails : DataBase.BOOK_DETAILS) {
			bookDetails.getBookId();
			bookDetails.getBookName();
			bookDetails.getAuthor();
			bookDetails.getPublisherName();
			books.add(bookDetails);
		}
		return books;
	}

	@Override
	public List<RequestInfo> showRequests() {
		List<RequestInfo> requestList = new LinkedList<RequestInfo>();
		for (RequestInfo requestInfo : DataBase.REQUEST_INFOS) {
			requestInfo.getBookdetails();
			requestInfo.getUserInfo();
			requestInfo.isIssued();
			requestInfo.isReturned();
			requestInfo.getReturnedDate();
			requestList.add(requestInfo);
		}
		return requestList;

	}


	@Override
	public boolean issueBook(UserInfo user, BookDetails bookDetails) {
		boolean isValid = false;
		calendar.add(Calendar.DATE, 15);
		expectedReturnDate = calendar.getTime();

		RequestInfo requestInfo = new RequestInfo();
		int noOfBooksBorrowed = user.getNoOfBooksBorrowed();
		for (RequestInfo info : DataBase.REQUEST_INFOS) {
			if (info.getUserInfo().getUserId() == user.getUserId()) {
				if (info.getBookdetails().getBookId() == bookDetails.getBookId()) {
					requestInfo = info;
					isValid = true;
				}
			}
		}

		if (isValid) {
			for (BookDetails info2 : DataBase.BOOK_DETAILS) {
				if (info2.getBookId() == bookDetails.getBookId()) {
					bookDetails = info2;
				}
			}
			for (UserInfo userInfo2 : DataBase.USER_INFOS) {
				if (userInfo2.getUserId() == user.getUserId()) {
					user = userInfo2;
					noOfBooksBorrowed = user.getNoOfBooksBorrowed();

				}

			}

			if (noOfBooksBorrowed < 3) {
				boolean isRemoved = DataBase.BOOK_DETAILS.remove(bookDetails);
				if (isRemoved) {
					noOfBooksBorrowed++;
					System.out.println(noOfBooksBorrowed);
					user.setNoOfBooksBorrowed(noOfBooksBorrowed);
					requestInfo.setIssued(true);
					requestInfo.setIssueDate(date);
					requestInfo.setReturnDate(expectedReturnDate);
					requestInfo.setIssued(true);
					return true;
				} else {
					throw new Exception("Book can't be borrowed");
				}

			} else {
				throw new Exception("Student Exceeds maximum limit");
			}

		} else {
			throw new Exception("Book data or User data is incorrect");

		}
	}

	@Override
	public boolean removebook(BookDetails bookDetails) {
		boolean isExist=false;
		BookDetails bookInfo1 = null;
		for (BookDetails bookInfo : DataBase.BOOK_DETAILS) {	
			if(bookInfo.getBookId() == bookDetails.getBookId()) 
			{
				bookInfo1 = bookInfo;
				isExist = true;
			}
		} if(isExist) {
			DataBase.BOOK_DETAILS.remove(bookInfo1);
			return true;
		}
		throw new Exception("Unable To Remove The Book");
	}

	@Override
	public boolean receivedBook(UserInfo user, BookDetails bookDetails) {
		boolean isValid = false;
		RequestInfo requestInfo1 = new RequestInfo();
		for (RequestInfo requestInfo : DataBase.REQUEST_INFOS) {
			if (requestInfo.getBookdetails().getBookId() == bookDetails.getBookId()
					&& requestInfo.getUserInfo().getUserId() == user.getUserId() && requestInfo.isReturned() == true) {
				isValid = true;
				requestInfo1 = requestInfo;
			}
		}
		if (isValid) {

			bookDetails.setBookName(requestInfo1.getBookdetails().getBookName());
			bookDetails.setAuthor(requestInfo1.getBookdetails().getAuthor());
			bookDetails.setPublisherName(requestInfo1.getBookdetails().getPublisherName());
			DataBase.BOOK_DETAILS.add(bookDetails);
			DataBase.REQUEST_INFOS.remove(requestInfo1);


			for (UserInfo userInfo2 : DataBase.USER_INFOS) {
				if (userInfo2.getUserId() == user.getUserId()) {
					user = userInfo2;
				}

			}
			int noOfBooksBorrowed = user.getNoOfBooksBorrowed();
			noOfBooksBorrowed--;
			user.setNoOfBooksBorrowed(noOfBooksBorrowed);
			return true;

		}

		throw new Exception("Book is Not Recieved");
	}
}