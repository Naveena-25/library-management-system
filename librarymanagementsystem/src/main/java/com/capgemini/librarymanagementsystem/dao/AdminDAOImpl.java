package com.capgemini.librarymanagementsystem.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.capgemini.librarymanagementsystem.db.DataBase;
import com.capgemini.librarymanagementsystem.dto.AdminInfo;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.exception.LMSException;

public class AdminDAOImpl implements AdminDAO {
	Date date = new Date();
	Date expectedReturnDate;
	Date returnedDate;

	@Override
	public boolean authentication(String adminEmail, String password) {
		for (AdminInfo adminInfo : DataBase.ADMIN_INFOS) {
			if (adminInfo.getEmailId().equalsIgnoreCase(adminEmail) && adminInfo.getPassword().equals(password)) {
				return true;
			}
		}
		throw new LMSException("Invalid Login Credentials, Please Enter Correctly");
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
	public List<UserInfo> viewUsers() {
		List<UserInfo> userList = new ArrayList<UserInfo>();
		for (UserInfo user : DataBase.USER_INFOS) {
			user.getUserId();
			user.getUserName();
			user.getEmailId();
			user.getPassword();
			user.getMobileNumber();
			userList.add(user);

		}
		if (userList.isEmpty()) {
			throw new LMSException("No Users Found In The Library");
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
				throw new LMSException("Book Can't Be Added, As Book Id Already Exists in the Library");
			}
		}
		DataBase.BOOK_DETAILS.add(details);
		return add;
	}

	@Override
	public List<BookDetails> viewBooks() {

		List<BookDetails> books = new ArrayList<BookDetails>();
		for (BookDetails bookDetails : DataBase.BOOK_DETAILS) {
			bookDetails.getBookId();
			bookDetails.getBookName();
			bookDetails.getAuthor();
			bookDetails.getPublisherName();
			books.add(bookDetails);
		}
		if (books.isEmpty()) {
			throw new LMSException("No Books Present In The Library with the Given Book Id");
		}
		return books;
	}

	@Override
	public List<RequestInfo> viewRequests() {
		List<RequestInfo> requestsList = new ArrayList<RequestInfo>();

		for (RequestInfo requestInfo : DataBase.REQUEST_INFOS) {
			requestInfo.getBookId();
			requestInfo.getUserId();
			requestInfo.isIssued();
			requestInfo.isReturned();
			requestsList.add(requestInfo);
		}
		if (requestsList.isEmpty()) {
			throw new LMSException("No Requests Present In The Library");
		} else {
			return requestsList;
		}
	}

	@Override
	public boolean issueBook(int userId, int bookId) {

		UserInfo user = new UserInfo();
		BookDetails book = new BookDetails();
		RequestInfo requestInfo = new RequestInfo();
		int noOfBooksBorrowed = 0;
		boolean isValidRequest = false;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 15);
		expectedReturnDate = calendar.getTime();

		for (RequestInfo info : DataBase.REQUEST_INFOS) {
			if (info.getUserId() == userId) {
				if ((info.getBookId() == bookId) && (info.isIssued() == false)) {
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
				book.setBookAvailable(false);
				noOfBooksBorrowed++;
				user.setNoOfBooksBorrowed(noOfBooksBorrowed);
				requestInfo.setIssued(true);
				requestInfo.setIssueDate(date);
				requestInfo.setExpectedReturnDate(expectedReturnDate);
				return true;

			} else {
				DataBase.REQUEST_INFOS.remove(requestInfo);
				throw new LMSException("Student Exceeds maximum Borrowing limit");
			}

		} else {
			throw new LMSException("Book Can't Be Issued, as BookId (or) UserId is Invalid");
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
		throw new LMSException("Unable To Remove The Book from the Library");
	}

	@Override
	public boolean receiveBook(int userId, int bookId) {
		boolean isValidReceive = false;
		int noOfBooksBorrowed;
		double fine;
		RequestInfo requestInfo = new RequestInfo();

		for (RequestInfo requestInfo1 : DataBase.REQUEST_INFOS) {
			if ((requestInfo1.getBookId() == bookId) && (requestInfo1.getUserId() == userId)
					&& (requestInfo1.isReturned() == true)) {
				isValidReceive = true;
				expectedReturnDate = requestInfo1.getExpectedReturnDate();
				returnedDate = requestInfo1.getReturnedDate();
				requestInfo = requestInfo1;
			}
		}

		if (isValidReceive) {
			long expReturnDateInMilliSecs = expectedReturnDate.getTime();
			long returnedDateInMilliSecs = returnedDate.getTime();
			long diffInMilliSecs = returnedDateInMilliSecs - expReturnDateInMilliSecs;
			int NoOfDaysDelayed = (int) (diffInMilliSecs / (24 * 60 * 60 * 1000));
			for (BookDetails bookDetails : DataBase.BOOK_DETAILS) {
				if (bookDetails.getBookId() == bookId) {
					bookDetails.setBookAvailable(true);
					break;
				}
			}

			for (UserInfo userInfo : DataBase.USER_INFOS) {
				if (userInfo.getUserId() == userId) {
					noOfBooksBorrowed = userInfo.getNoOfBooksBorrowed();
					noOfBooksBorrowed--;
					userInfo.setNoOfBooksBorrowed(noOfBooksBorrowed);
					fine = userInfo.getFine();
					if (NoOfDaysDelayed > 0) {
						fine = fine + (NoOfDaysDelayed * 5);
					}
					userInfo.setFine(fine);
					break;
				}
			}

			DataBase.REQUEST_INFOS.remove(requestInfo);
			return true;
		}

		throw new LMSException("Book is Not Received,Due to Invalid userId or BookId");
	}
}