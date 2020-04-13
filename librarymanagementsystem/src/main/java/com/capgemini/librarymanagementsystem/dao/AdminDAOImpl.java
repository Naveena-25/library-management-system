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
import com.capgemini.librarymanagementsystem.exception.LMSException;

public class AdminDAOImpl implements AdminDAO {
	Date date = new Date();
	Date expectedReturnDate = new Date();
	Calendar calendar = Calendar.getInstance();

	@Override
	public boolean authentication(String adminEmail, String password) {
		AdminInfo adminInfo = new AdminInfo();
		if (adminInfo.getEmailId().equals(adminEmail) && adminInfo.getPassword().equals(password)) {
			return true;
		}
		throw new LMSException("Invalid Admin Credentials");
	}

	@Override
	public boolean addUser(UserInfo userInfo) {
		for (UserInfo user : DataBase.USER_INFOS) {
			if (user.getUserId() == userInfo.getUserId() || user.getEmailId().equalsIgnoreCase(userInfo.getEmailId())) {
				throw new LMSException("Cannot add New User, as User Already Exists");
			}
		}
		DataBase.USER_INFOS.add(userInfo);
		return true;
	}

	@Override
	public List<UserInfo> showUsers() {
		List<UserInfo> userList = new LinkedList<UserInfo>();
		for (UserInfo user : DataBase.USER_INFOS) {
			user.getUserId();
			user.getUserName();
			user.getEmailId();
			user.getPassword();
			user.getMobileNumber();
			userList.add(user);
		}
		return userList;

	}

	@Override
	public BookDetails search(int bookId) {
		for (BookDetails book : DataBase.BOOK_DETAILS) {
			if (book.getBookId() == bookId) {
				return book;
			}
		}
		throw new LMSException("Book is Not Found In The Library");
	}

	@Override
	public boolean addBook(BookDetails details) {
		boolean add = true;
		for (BookDetails bookInfo : DataBase.BOOK_DETAILS) {
			if (bookInfo.getBookId() == details.getBookId()) {
				throw new LMSException("Book Can't Be Added, As Book is Already Exists");
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
		List<RequestInfo> requestsList = new LinkedList<RequestInfo>();

		for (RequestInfo requestInfo : DataBase.REQUEST_INFOS) {
			requestInfo.getBookId();
			requestInfo.getUserId();
			requestInfo.isIssued();
			requestInfo.isReturned();
			requestsList.add(requestInfo);
		}
		return requestsList;
	}

	@Override
	public boolean issueBook(int userId, int bookId) {

		UserInfo user = new UserInfo();
		BookDetails book = new BookDetails();
		RequestInfo requestInfo = new RequestInfo();
		int noOfBooksBorrowed = 0;
		boolean isValidRequest = false;
		calendar.add(Calendar.DATE, 15);
		expectedReturnDate = calendar.getTime();

		for (RequestInfo info : DataBase.REQUEST_INFOS) {
			if (info.getUserId() == userId) {
				if (info.getBookId() == bookId) {
					isValidRequest = true;
					requestInfo = info;
				}
			}
		}

		if (isValidRequest) {
			for (BookDetails bookDetails : DataBase.BOOK_DETAILS) {
				if (bookDetails.getBookId() == bookId) {
					book = bookDetails;
				}
			}

			for (UserInfo userInfo : DataBase.USER_INFOS) {
				if (userInfo.getUserId() == userId) {
					user = userInfo;
					noOfBooksBorrowed = user.getNoOfBooksBorrowed();
				}
			}

			if (noOfBooksBorrowed < 3) {
				book.setAvailable(false);
				noOfBooksBorrowed++;
				user.setNoOfBooksBorrowed(noOfBooksBorrowed);
				requestInfo.setIssued(true);
				requestInfo.setIssueDate(date);
				requestInfo.setExpectedReturnDate(expectedReturnDate);
				return true;
			} else {
				throw new LMSException("Student Exceeds maximum Borrowing limit");
			}

		} else {
			throw new LMSException("Book Can't Be Issued");
		}
	}

	@Override
	public boolean removeBook(int bookId) {
		for (BookDetails bookDetails : DataBase.BOOK_DETAILS) {
			if (bookDetails.getBookId() == bookId) {
				DataBase.BOOK_DETAILS.remove(bookDetails);
				return true;
			}
		}
		throw new LMSException("Unable To Remove The Book");
	}

	@Override
	public boolean receiveBook(int userId, int bookId) {
		boolean isValidReceive = false;
		int noOfBooksBorrowed;

		RequestInfo requestInfo = new RequestInfo();

		for (RequestInfo requestInfo1 : DataBase.REQUEST_INFOS) {
			if ((requestInfo1.getBookId() == bookId) && (requestInfo1.getUserId() == userId)
					&& (requestInfo1.isReturned() == true)) {
				isValidReceive = true;
				requestInfo = requestInfo1;
			}
		}

		if (isValidReceive) {
			for (BookDetails bookDetails : DataBase.BOOK_DETAILS) {
				if (bookDetails.getBookId() == bookId) {
					bookDetails.setAvailable(true);
					break;
				}
			}

			for (UserInfo userInfo : DataBase.USER_INFOS) {
				if (userInfo.getUserId() == userId) {
					noOfBooksBorrowed = userInfo.getNoOfBooksBorrowed();
					noOfBooksBorrowed--;
					userInfo.setNoOfBooksBorrowed(noOfBooksBorrowed);
					break;
				}
			}

			DataBase.REQUEST_INFOS.remove(requestInfo);
			return true;
		}

		throw new LMSException("Book is Not Received ");
	}
}