package com.capgemini.librarymanagementjdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementjdbc.core.DBConnector;
import com.capgemini.librarymanagementjdbc.dto.LibraryUsers;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;
import com.capgemini.librarymanagementjdbc.exception.LMSException;

public class LibraryDAOImpl implements LibraryDAO {
	DBConnector dbConnector = new DBConnector();

	@Override
	public LibraryUsers login(String emailId, String password) {
		LibraryUsers libraryUsers = new LibraryUsers();

		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("loginCheckAdmin"));) {
			pstmt.setString(1, emailId);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					libraryUsers.setEmailId(rs.getString("email_id"));
					libraryUsers.setPassword(rs.getString("password"));

					return libraryUsers;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new LMSException("Invalid Login Credentials, Please Enter Correctly");
	}

	@Override
	public boolean addUser(LibraryUsers info) {
		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("addUser"));) {
			pstmt.setInt(1, info.getId());
			pstmt.setString(2, info.getUserName());
			pstmt.setString(3, info.getEmailId());
			pstmt.setString(4, info.getPassword());
			pstmt.setString(5, info.getMobileNumber());
			pstmt.setString(6, info.getRole());

			pstmt.executeUpdate();

		} catch (Exception e) {
			throw new LMSException("Can't Add New User, as User Already Exists");
		}
		return true;
	}

	@Override
	public List<LibraryUsers> viewUsers() {

		List<LibraryUsers> userList = new LinkedList<LibraryUsers>();
		try (Connection conn = dbConnector.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery(dbConnector.getQuery("showUsers"))) {
			while (resultSet.next()) {
				LibraryUsers info = new LibraryUsers();
				info.setId(resultSet.getInt("id"));
				info.setUserName(resultSet.getString("name"));
				info.setEmailId(resultSet.getString("email_Id"));
				info.setPassword(resultSet.getString("password"));
				info.setMobileNumber(resultSet.getString("mobile"));
				info.setRole(resultSet.getString("role"));
				info.setFine(resultSet.getDouble("fine"));
				info.setNoOfBooksBorrowed(resultSet.getInt("noOfBooksBorrowed"));
				
				userList.add(info);
			}
			if (userList.isEmpty()) {
				throw new LMSException("No Users Present in the Library");
			} else {
				return userList;
			}
		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		}

	}

	@Override
	public boolean addBook(BookDetails bookDetails) {
		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("addBook"));) {
			pstmt.setInt(1, bookDetails.getBookId());
			pstmt.setString(2, bookDetails.getBookName());
			pstmt.setString(3, bookDetails.getAuthor());
			pstmt.setString(4, bookDetails.getPublisher());
			pstmt.setBoolean(5, bookDetails.isAvailable());
			pstmt.executeUpdate();
		} catch (Exception e) {
			throw new LMSException("Book Can't be Added, As it is Already Exists in the Library");
		}
		return true;
	}

	@Override
	public BookDetails search(int bookId) {
		BookDetails book = null;
		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("searchBook"));) {
			pstmt.setInt(1, bookId);
			try (ResultSet resultSet = pstmt.executeQuery();) {
				if (resultSet.next()) {
					book = new BookDetails();
					book.setBookId(resultSet.getInt("book_Id"));
					book.setBookName(resultSet.getString("name"));
					book.setAuthor(resultSet.getString("author"));
					book.setPublisher(resultSet.getString("publisher"));

					return book;
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
		throw new LMSException("Book is Not Found in the Library with the Given Book Id");
	}

	@Override
	public List<BookDetails> viewBooks() {
		List<BookDetails> books = new LinkedList<BookDetails>();

		try (Connection conn = dbConnector.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery(dbConnector.getQuery("showBooks"));) {
			while (resultSet.next()) {
				BookDetails bookDetails = new BookDetails();
				bookDetails.setBookId(resultSet.getInt("book_id"));
				bookDetails.setBookName(resultSet.getString("name"));
				bookDetails.setAuthor(resultSet.getString("author"));
				bookDetails.setPublisher(resultSet.getString("publisher"));
				bookDetails.setAvailable(resultSet.getBoolean("isAvailable"));
				books.add(bookDetails);
			}

			if (books.isEmpty()) {
				throw new LMSException("No Books Present in the Library");
			} else {
				return books;
			}
		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		}

	}

	@Override
	public List<RequestInfo> viewRequests() {
		List<RequestInfo> requestList = new LinkedList<RequestInfo>();

		try (Connection conn = dbConnector.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery(dbConnector.getQuery("showRequests"));) {
			while (resultSet.next()) {
				RequestInfo requestInfo = new RequestInfo();
				requestInfo.setRequestId(resultSet.getInt("rid"));
				requestInfo.setId(resultSet.getInt("id"));
				requestInfo.setBookId(resultSet.getInt("book_id"));
				requestInfo.setIssueDate(resultSet.getDate("issue_date"));
				requestInfo.setReturnDate(resultSet.getDate("returned_date"));
				requestInfo.setExpectedReturnDate(resultSet.getDate("expectedreturn_date"));
				requestList.add(requestInfo);
			}
			if (requestList.isEmpty()) {
				throw new LMSException("No Requests Present in the Library");
			} else {
				return requestList;
			}
		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		}

	}

	@Override
	public boolean issueBook(int rid) {

		try (Connection conn = dbConnector.getConnection();
				PreparedStatement getReqPstmt = conn.prepareStatement(dbConnector.getQuery("getRequest"));
				PreparedStatement getUserPstmt = conn.prepareStatement(dbConnector.getQuery("getUser"));
				PreparedStatement issuePstmt = conn.prepareStatement(dbConnector.getQuery("issueBook"));
				PreparedStatement setAvailPstmt = conn.prepareStatement(dbConnector.getQuery("setBookAvailability"));
				PreparedStatement NoofBooksPstmt = conn
						.prepareStatement(dbConnector.getQuery("setNoOfBooksBorrowed"));) {
			getReqPstmt.setInt(1, rid);
			try (ResultSet getReqSet = getReqPstmt.executeQuery();) {
				if (getReqSet.next()) {
					int requestUserId = getReqSet.getInt("id");
					int requestBookId = getReqSet.getInt("book_id");

					if (requestUserId != 0) {
						getUserPstmt.setInt(1, requestUserId);
						try (ResultSet getUserSet = getUserPstmt.executeQuery();) {

							if (getUserSet.next()) {
								LibraryUsers users = new LibraryUsers();
								users.setNoOfBooksBorrowed(getUserSet.getInt("noOfBooksBorrowed"));
								int noOfBooksBorrowed = users.getNoOfBooksBorrowed();
								issuePstmt.setInt(1, rid);

								int updateDate = issuePstmt.executeUpdate();
								if (updateDate != 0) {
									// Update Book Availability to false while Issuing the Book
									setAvailPstmt.setInt(1, requestBookId);
									setAvailPstmt.executeUpdate();

									// Update No. of Books Borrowed By the User
									noOfBooksBorrowed++;
									NoofBooksPstmt.setInt(1, noOfBooksBorrowed);
									NoofBooksPstmt.setInt(2, requestUserId);
									NoofBooksPstmt.executeUpdate();
								} else {
									throw new LMSException("This Book is Already Issued to the User");
								}
							}
						}

					} else {
						throw new LMSException("Request not Found with Matching BookId (or) UserId ");
					}
				}
			}
		} catch (Exception e) {
			throw new LMSException(e.getMessage());

		}
		return true;
	}

	@Override
	public boolean removeBook(int bookId) {
		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("removeBook"));) {
			pstmt.setInt(1, bookId);
			int result = pstmt.executeUpdate();
			if (result != 0) {
				return true;
			}

		} catch (Exception e) {
			throw new LMSException(e.getMessage());

		}
		throw new LMSException("Book Can't Be Removed or Deleted Because it is not Present in the Library");
	}

	@Override
	public LibraryUsers userLogin(String emailId, String password) {
		LibraryUsers info = new LibraryUsers();
		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("loginCheckUser"));) {
			pstmt.setString(1, emailId);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					info.setEmailId(rs.getString("email_id"));
					info.setPassword(rs.getString("password"));
					return info;
				}
			}
		} catch (Exception e) {
			throw new LMSException(e.getMessage());

		}
		throw new LMSException("Invalid Login Credentials, Please Enter Correctly");
	}

	@Override
	public boolean changePassword(String emailId, String oldPassword, String newPassword) {

		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("changePassword"));) {
			pstmt.setString(1, newPassword);
			pstmt.setString(2, emailId);
			pstmt.setString(3, oldPassword);
			int count = pstmt.executeUpdate();
			if (count != 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		throw new LMSException("Unable to Update the Password due to Invalid Credentials");
	}

	@Override
	public boolean bookRequest(int userId, int bookId) {
		boolean isAvailable = false;
		int reqestedBookId = 0;
		int validBookId = 0;
		int noOfRequests = 0;

		try (Connection conn = dbConnector.getConnection();
				Statement isRequestExist = conn.createStatement();
				PreparedStatement countReqPstmt = conn.prepareStatement(dbConnector.getQuery("countRequests"));
				PreparedStatement checkAvailPstmt = conn.prepareStatement(dbConnector.getQuery("checkAvailability"));
				PreparedStatement reqPstmt = conn.prepareStatement(dbConnector.getQuery("requestBook"));) {

			try (ResultSet resultSet = isRequestExist.executeQuery(dbConnector.getQuery("showRequests"))) {
				while (resultSet.next()) {
					reqestedBookId = resultSet.getInt("book_id");
					if (reqestedBookId == bookId) {
						throw new LMSException("Book is Already Requested, So Request Can't be Placed");
					}
				}

			}
			countReqPstmt.setInt(1, userId);

			try (ResultSet countResultSet = countReqPstmt.executeQuery()) {
				if (countResultSet.next()) {
					noOfRequests = countResultSet.getInt(1);
				}

				if (noOfRequests < 3) {
					checkAvailPstmt.setInt(1, bookId);

					try (ResultSet availResultSet = checkAvailPstmt.executeQuery();) {
						while (availResultSet.next()) {
							validBookId = availResultSet.getInt("book_id");
							isAvailable = availResultSet.getBoolean("isAvailable");
						}

						if (validBookId != 0) {
							if (isAvailable) {
								reqPstmt.setInt(1, userId);
								reqPstmt.setInt(2, bookId);
								reqPstmt.executeUpdate();

								RequestInfo requestInfo = new RequestInfo();
								requestInfo.setId(userId);
								requestInfo.setBookId(bookId);

								return true;
							} else {
								throw new LMSException("Book is Not Available For Borrowing");
							}
						} else {
							throw new LMSException("The Book You are Requested is Not Present in the Library");
						}
					}
				} else {
					throw new LMSException("User Can't Place More Than 3 Requests");
				}
			}

		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		}
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {

		try (Connection conn = dbConnector.getConnection();
				PreparedStatement getReqPstmt = conn.prepareStatement(dbConnector.getQuery("returnBook"));
				PreparedStatement updateDatePstmt = conn.prepareStatement(dbConnector.getQuery("updateReturnDate"));) {
			getReqPstmt.setInt(1, userId);
			getReqPstmt.setInt(2, bookId);

			try (ResultSet resultSet = getReqPstmt.executeQuery();) {

				if (resultSet.next()) {
					int requestId = resultSet.getInt("rid");
					updateDatePstmt.setInt(1, requestId);

					updateDatePstmt.executeUpdate();

				} else {
					throw new LMSException("Return Request not Found with the Request Id");
				}
			}

		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		}
		return true;
	}

	@Override
	public boolean receiveBook(int requestId) {

		int noOfDaysDelayed = 0;
		int fine = 0;
		int userId = 0;
		int bookId = 0;
		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("receiveBook"));
				PreparedStatement getFinePstmt = conn.prepareStatement(dbConnector.getQuery("getfine"));
				PreparedStatement setFinePstmt = conn.prepareStatement(dbConnector.getQuery("userFine"));
				PreparedStatement setAvailPstmt = conn.prepareStatement(dbConnector.getQuery("setBookAvailability2"));
				PreparedStatement setNoofBooksPstmt = conn
						.prepareStatement(dbConnector.getQuery("setNoOfBooksBorrowed2"));
				PreparedStatement removeReqPstmt = conn.prepareStatement(dbConnector.getQuery("removeRequest"));) {

			pstmt.setInt(1, requestId);
			try (ResultSet resultSet = pstmt.executeQuery();) {

				while (resultSet.next()) {
					Date returnedDate = resultSet.getDate("returned_date");
					Date expectedReturnedDate = resultSet.getDate("expectedreturn_date");
					userId = resultSet.getInt("id");
					bookId = resultSet.getInt("book_id");

					if (returnedDate != null) {

						getFinePstmt.setDate(1, returnedDate);
						getFinePstmt.setDate(2, expectedReturnedDate);
						getFinePstmt.setInt(3, requestId);

						try (ResultSet fineResultSet = getFinePstmt.executeQuery();) {

							while (fineResultSet.next()) {
								noOfDaysDelayed = fineResultSet.getInt(1);
							}
						}
						if (noOfDaysDelayed > 0) {
							fine = noOfDaysDelayed * 5;

							setFinePstmt.setDouble(1, fine);
							setFinePstmt.setInt(2, userId);
							setFinePstmt.executeUpdate();

						}
						// set Availability of Book to true After Receiving it From the User
						setAvailPstmt.setInt(1, bookId);
						setAvailPstmt.executeUpdate();

						// set no Of Books Borrowed After Receiving
						setNoofBooksPstmt.setInt(1, userId);
						setNoofBooksPstmt.executeUpdate();

						// Delete the Request After Receiving it from the User
						removeReqPstmt.setInt(1, requestId);
						removeReqPstmt.executeUpdate();
						return true;
					} else {
						throw new LMSException("Book Can't be Received, as it is not Returned by the User");
					}
				}

			}

			throw new LMSException("Request Id is not Valid");

		} catch (Exception e) {
			throw new LMSException(e.getMessage());
		}

	}

}