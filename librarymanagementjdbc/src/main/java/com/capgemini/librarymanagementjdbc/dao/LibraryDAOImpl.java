package com.capgemini.librarymanagementjdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		LibraryUsers adminDetails = new LibraryUsers();

		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("loginCheckAdmin"));) {
			pstmt.setString(1, emailId);
			pstmt.setString(2, password);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					adminDetails.setEmailId(rs.getString("email_id"));
					adminDetails.setPassword(rs.getString("password"));

					return adminDetails;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new LMSException("Invalid Credentials Please Enter Correctly");
	}

	@Override
	public boolean addUser(LibraryUsers info) {
		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("insertUser"));) {
			pstmt.setInt(1, info.getId());
			pstmt.setString(2, info.getUserName());
			pstmt.setString(3, info.getEmailId());
			pstmt.setString(4, info.getPassword());
			pstmt.setString(5, info.getMobileNumber());
			pstmt.setString(6, info.getRole());

			int count = pstmt.executeUpdate();

			if (count != 0) {
				return true;
			}
		} catch (Exception e) {
			throw new LMSException("User Already Exists");
		}
		return false;
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
				userList.add(info);
			}
			return userList;
		} catch (Exception e) {
			throw new LMSException("No Users Present In The Database");
		}
	}

	@Override
	public boolean addBook(BookDetails bookDetails) {
		try (Connection conn = dbConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(dbConnector.getQuery("insertBook"));) {
			pstmt.setInt(1, bookDetails.getBookId());
			pstmt.setString(2, bookDetails.getBookName());
			pstmt.setString(3, bookDetails.getAuthor());
			pstmt.setString(4, bookDetails.getPublisher());
			pstmt.setBoolean(5, bookDetails.isAvailable());
			int count = pstmt.executeUpdate();

			if (count != 0) {
				return true;
			}
		} catch (Exception e) {
			throw new LMSException("Book Can't be Added, As it is Already Exists in the Database");

		}
		return false;
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new LMSException("Book is Not Found");
	}

	@Override
	public List<BookDetails> viewBooks() {
		List<BookDetails> books = new LinkedList<BookDetails>();

		try (Connection conn = dbConnector.getConnection(); Statement stmt = conn.createStatement();) {
			try (ResultSet resultSet = stmt.executeQuery(dbConnector.getQuery("showBooks"));) {
				while (resultSet.next()) {
					BookDetails details = new BookDetails();
					details.setBookId(resultSet.getInt("book_id"));
					details.setBookName(resultSet.getString("name"));
					details.setAuthor(resultSet.getString("author"));
					details.setPublisher(resultSet.getString("publisher"));
					details.setAvailable(resultSet.getBoolean("isAvailable"));
					books.add(details);
				}
				return books;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new LMSException("NO Books Present in the DataBase");
	}

	@Override
	public List<RequestInfo> viewRequests() {
		List<RequestInfo> requestList = new LinkedList<RequestInfo>();

		try (Connection conn = dbConnector.getConnection(); Statement stmt = conn.createStatement();) {
			try (ResultSet resultSet = stmt.executeQuery(dbConnector.getQuery("showRequests"));) {
				while (resultSet.next()) {
					RequestInfo requestInfo = new RequestInfo();

					requestInfo.setRid(resultSet.getInt("rid"));
					requestInfo.setId(resultSet.getInt("id"));
					requestInfo.setBookId(resultSet.getInt("book_id"));
					requestInfo.setIssueDate(resultSet.getDate("issue_date"));
					requestInfo.setReturnDate(resultSet.getDate("returned_date"));
					requestInfo.setExpectedReturnDate(resultSet.getDate("expectedreturn_date"));
					requestList.add(requestInfo);

				}
				return requestList;
			}
		} catch (SQLException e) {
			throw new LMSException("NO Requests Present in the Request DataBase");
		}
		
	}

	@SuppressWarnings("resource")
	@Override
	public boolean issueBook(int rid) {
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet resultSet = null;
		String query = null;
		int noOfBooksBorrowed = 0;
		try {
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("getRequest");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, rid);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				RequestInfo requestInfo = new RequestInfo();
				requestInfo.setId(resultSet.getInt("id"));
				requestInfo.setBookId(resultSet.getInt("book_id"));
				int requestUserId = requestInfo.getId();
				int requestBookId = resultSet.getInt("book_id");

				if (requestUserId != 0) {
					String query2 = dbConnector.getQuery("getUserBooks");
					pstmt = conn.prepareStatement(query2);
					pstmt.setInt(1, requestUserId);
					resultSet = pstmt.executeQuery();

					if (resultSet.next()) {
						LibraryUsers info2 = new LibraryUsers();
						info2.setNoOfBooksBorrowed(resultSet.getInt("noOfBooksBorrowed"));
						noOfBooksBorrowed = info2.getNoOfBooksBorrowed();

						if (noOfBooksBorrowed < 3) {
							query = dbConnector.getQuery("issueBook");
							pstmt = conn.prepareStatement(query);
							pstmt.setInt(1, rid);

							int updateDate = pstmt.executeUpdate();
							if (updateDate != 0) {
								// Update book availability as false as we are issuing
								query = dbConnector.getQuery("setBookAvailability");
								pstmt = conn.prepareStatement(query);
								pstmt.setInt(1, requestUserId);
								pstmt.executeUpdate();

								// Update User no of books borrowed
								noOfBooksBorrowed++;
								query = dbConnector.getQuery("setNoOfBooksBorrowed");
								pstmt = conn.prepareStatement(query);
								pstmt.setInt(1, noOfBooksBorrowed);
								pstmt.setInt(2, requestUserId);
								pstmt.executeUpdate();

							} // End of if update date!=0

							return true;

						} else {
							throw new LMSException("Number of Books Borrowed are more than 3");
						}

					} else {
						throw new LMSException("user date not found");
					}

				} else {
					throw new LMSException("No Request Found");
				}

			} else {
				throw new LMSException("Book Can't Be issued");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;

		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}

				if (resultSet != null) {
					resultSet.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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

		} catch (SQLException e) {
			e.printStackTrace();

		}
		throw new LMSException("Book Can't Be Removed or Deleted");
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
			e.printStackTrace();

		}
		throw new LMSException("Invalid User Credentials, Please Enter Correct Details");
	}

	@Override
	public RequestInfo bookRequest(int userId, int bookId) {
		RequestInfo requestInfo = new RequestInfo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		boolean isAvail = false;
		String query = null;

		try {
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("checkAvailability");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			resultSet = pstmt.executeQuery();

			// System.out.println("It is not Available for Borrowing");
			while (resultSet.next()) {
				isAvail = resultSet.getBoolean(1);
			}
			if (isAvail) {
				query = dbConnector.getQuery("requestBook");
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, userId);
				pstmt.setInt(2, bookId);
				int count = pstmt.executeUpdate();

				if (count != 0) {
					requestInfo.setId(userId);
					requestInfo.setBookId(bookId);

					return requestInfo;

				} else {
					throw new LMSException("Request Cannot Be Placed");
				}
			} else {
				throw new LMSException("This Book Is Not For Borrowing");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LMSException("Request Can't Be placed");
		} finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = null;
		ResultSet rs = null;
		try {
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("returnBook");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, bookId);

			rs = pstmt.executeQuery();

			if (rs.next() != false) {
				int requestId = rs.getInt("rid");
				System.out.println("Request Id....." + requestId);
				query = dbConnector.getQuery("updateReturnDate");
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, requestId);

				int count = pstmt.executeUpdate();
				if (count != 0) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@SuppressWarnings("resource")
	@Override
	public boolean isBookReceived(int rid) {
		PreparedStatement pstmt = null;
		String query = null;
		ResultSet resultSet = null;
		int noOfDaysDelayed = 0;
		int fine = 0;
		int userId = 0;
		int bookId = 0;
		int result = 0;
		try {
			Connection conn = dbConnector.getConnection();
			query = dbConnector.getQuery("receiveBook");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, rid);
			resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				Date returnedDate = resultSet.getDate("returned_date");
				Date expectedReturnedDate = resultSet.getDate("expectedreturn_date");
				userId = resultSet.getInt("id");
				bookId = resultSet.getInt("book_id");

				if (returnedDate != null) {
					query = dbConnector.getQuery("getfine");
					pstmt = conn.prepareStatement(query);
					pstmt.setDate(1, returnedDate);
					pstmt.setDate(2, expectedReturnedDate);
					pstmt.setInt(3, rid);

					resultSet = pstmt.executeQuery();

					while (resultSet.next()) {
						noOfDaysDelayed = resultSet.getInt(1);
					}

					if (noOfDaysDelayed > 0) {
						fine = noOfDaysDelayed * 5;
						query = dbConnector.getQuery("userFine");
						pstmt = conn.prepareStatement(query);
						pstmt.setDouble(1, fine);
						pstmt.setInt(2, userId);

						result = pstmt.executeUpdate();
						if (result != 0) {
							System.out.println("fine updated   " + fine);
						}
					}
					// Make available in library books
					query = dbConnector.getQuery("setBookAvailability2");
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, bookId);
					result = pstmt.executeUpdate();

					// set No Of Books Borrowed
					query = dbConnector.getQuery("setNoOfBooksBorrowed2");
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, userId);
					result = pstmt.executeUpdate();

					query = dbConnector.getQuery("removeRequest");
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, rid);

					result = pstmt.executeUpdate();
					return true;
				}

			}
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		finally {
			try {

				if (pstmt != null) {
					pstmt.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} 
	}
}
