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
import com.capgemini.librarymanagementjdbc.dto.AdminInfo;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;
import com.capgemini.librarymanagementjdbc.exception.LMSException;

public class LibraryDAOImpl implements LibraryDAO
{
	DBConnector dbConnector = new DBConnector();
	Connection conn = null;
	PreparedStatement pstmt = null;
	@Override
	public AdminInfo login(String emailId, String password)
	{
		String query = null;
		AdminInfo adminDetails = new AdminInfo();
		try 
		{
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("loginCheckAdmin");

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emailId);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next())
			{
				adminDetails.setEmailId(rs.getString("email_id"));
				adminDetails.setPassword(rs.getString("password"));

				pstmt = null;
				return adminDetails;
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();

		} 
		finally 
		{
			try 
			{
				if (conn != null) 
				{
					conn.close();
					conn = null;
				}
				if (pstmt != null) 
				{

					pstmt.close();						
					pstmt = null;
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;

	}

	@Override
	public boolean addUser(AdminInfo info) {
		String query = null;
		try {
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("addUserQuery");

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, info.getId());
			pstmt.setString(2, info.getUserName());
			pstmt.setString(3,info.getEmailId());
			pstmt.setString(4, info.getPassword());
			pstmt.setString(5, info.getMobileNumber());
			pstmt.setString(6, info.getRole());

			int count = pstmt.executeUpdate();

			if (count != 0) {
				return true;
			}
		} 
		catch (Exception e) {
			throw new LMSException("User Already Exists");
		} finally 
		{
			try {
				if (conn != null) 
				{
					conn.close();
					conn = null;
				}
				if (pstmt != null) 
				{
					pstmt.close();
					pstmt = null;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;


	}
	@Override
	public List<AdminInfo> viewUsers() 
	{

		List<AdminInfo> userList = new LinkedList<AdminInfo>();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			conn = dbConnector.getConnection();
			String query = dbConnector.getQuery("showUsersQuery");
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(query);
			while(resultSet.next()) {
				AdminInfo info = new AdminInfo();
				info.setId(resultSet.getInt("id"));
				info.setUserName(resultSet.getString("name"));
				info.setEmailId(resultSet.getString("email_Id"));
				info.setPassword(resultSet.getString("password"));
				info.setMobileNumber(resultSet.getString("mobile"));
				info.setRole(resultSet.getString("role"));
				userList.add(info);
			}

			return userList;
		}catch (Exception e) {
			throw new LMSException("No Users Present In The Database");
		}
	}

	@Override
	public boolean addBook(BookDetails bookDetails) 
	{
		String query = null;
		try {
			conn = dbConnector.getConnection();

			query = dbConnector.getQuery("addBookQuery");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookDetails.getBookId());
			pstmt.setString(2, bookDetails.getBookName());
			pstmt.setString(3, bookDetails.getAuthor());
			pstmt.setString(4, bookDetails.getPublisher());
			pstmt.setString(5, bookDetails.isAvailable());
			int count = pstmt.executeUpdate();

			if (count != 0) {
				return true;
			}
		} catch (Exception e) {
			throw new LMSException("Book Can't be Added, As it is Already Exists in the Database");

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		return false;
	}


	@Override
	public BookDetails search(int bookId) {
		ResultSet resultSet = null;
		BookDetails book = null;
		try {
			conn = dbConnector.getConnection();
			String query = dbConnector.getQuery("searchBookQuery");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			resultSet = pstmt.executeQuery();
			if(resultSet.next()) {
				book = new BookDetails();
				book.setBookId(resultSet.getInt("book_Id"));
				book.setBookName(resultSet.getString("name"));
				book.setAuthor(resultSet.getString("author"));
				book.setPublisher(resultSet.getString("publisher"));
			}
			return book;
		} catch (Exception e) {
			throw new LMSException("Book is Not Found");
		}
		finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	@Override
	public List<BookDetails> viewBooks() {
		List<BookDetails> books = new LinkedList<BookDetails>();
		Statement stmt = null;
		ResultSet resultSet = null;
		try {
			conn = dbConnector.getConnection();
			String query = dbConnector.getQuery("showBooksQuery");
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(query);
			while(resultSet.next()) {
				BookDetails details = new BookDetails();
				details.setBookId(resultSet.getInt("book_id"));
				details.setBookName(resultSet.getString("name"));
				details.setAuthor(resultSet.getString("author"));
				details.setPublisher(resultSet.getString("publisher"));
				books.add(details);
			}
			return books;
		}catch (Exception e) {
			throw new LMSException("NO Books Present in the DataBase");
		}
	}

	@Override
	public List<RequestInfo> viewRequests() 
	{
		List<RequestInfo> requestList = new LinkedList<RequestInfo>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;

		try {
			conn = dbConnector.getConnection();
			String query = dbConnector.getQuery("showRequestQuery");
			stmt = conn.createStatement();
			resultSet = stmt.executeQuery(query);

			while(resultSet.next())
			{
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
		} catch (Exception e) {
			e.printStackTrace();
			throw new LMSException("NO Requests Present in the Request DataBase");

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	@SuppressWarnings("resource")
	@Override
	public boolean issueBook(int rid) {
		ResultSet resultSet = null; // resultSet1, resultSet2, resultSet3, resultSet4, resultSet5;
		//String query = null;
		try {
			conn = dbConnector.getConnection();
			String query1 = dbConnector.getQuery("getRequest");
			pstmt = conn.prepareStatement(query1);
			pstmt.setInt(1, rid);
			resultSet = pstmt.executeQuery();
			if (resultSet.next()) {
				RequestInfo info = new RequestInfo();
				info.setId(resultSet.getInt("id"));
				info.setBookId(resultSet.getInt("book_id"));
				// System.out.println("Requested user"+info.getUserId());
				int requestUserId = info.getId();
				System.out.println("Requested user " + info.getId());
				// System.out.println(requestUserId);
				int requestBookId = resultSet.getInt("book_id");
				System.out.println("Requested book " + requestBookId);

				// In Admin setting the no Of Books Borrowed
				if (requestUserId != 0)
				{
					//conn = dbConnector.getConnection();
					String query2 = dbConnector.getQuery("getUserBooks");
					pstmt = conn.prepareStatement(query2);
					pstmt.setInt(1, requestUserId);
					resultSet = pstmt.executeQuery();

					if (resultSet.next()) 
					{
						AdminInfo info2 = new AdminInfo();
						info2.setNoOfBooksBorrowed(resultSet.getInt("noOfBooksBorrowed"));
						int noOfBooksBorrowed = info2.getNoOfBooksBorrowed();
						System.out.println("no of books Before issue	" + noOfBooksBorrowed);
						if (noOfBooksBorrowed < 3) 
						{
							//conn = dbConnector.getConnection();
							String query3 = dbConnector.getQuery("issueBookQuery");
							pstmt = conn.prepareStatement(query3);

							pstmt.setInt(1, rid);

							int updateDate = pstmt.executeUpdate();
							if (updateDate != 0) 
							{
								// Update book availability as false as we are issuing
								//conn = dbConnector.getConnection();
								String query4 = dbConnector.getQuery("setBookAvailability");
								pstmt = conn.prepareStatement(query4);
								pstmt.setInt(1, requestUserId);
								pstmt.executeUpdate();


								// Update User no of books borrowed
								noOfBooksBorrowed++;
								String query5 = dbConnector.getQuery("setNoOfBooksBorrowed");
								pstmt = conn.prepareStatement(query5);
								pstmt.setInt(1, noOfBooksBorrowed);
								pstmt.setInt(2, requestUserId);
								pstmt.executeUpdate();

							} // End of if update date!=0

							return true;

						} else {
							throw new LMSException("Number of Books Borrowed are more than 3");// End of if No of books borrowed <3
						}

					} else {
						return false;// End Of getting User Data
					}

				} else {
					return false;
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
		String query= null;
		try {
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("removeBookQuery");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookId);
			int result = pstmt.executeUpdate();
			if (result != 0) { 
				return true;
			} 

		} catch (Exception e) {
			throw new LMSException("Book Can't Be Removed or Deleted");

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}

		}
		return false;
	}

	@Override
	public AdminInfo userLogin(String emailId, String password)
	{
		String query = null;
		AdminInfo info = new AdminInfo();
		try 
		{
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("loginCheckUser");

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emailId);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next())
			{
				info.setEmailId(rs.getString("email_id"));
				info.setPassword(rs.getString("password"));

				pstmt = null;
				return info;
			}

		} catch (Exception e) {
			throw new LMSException("Invalid User Credentials, Please Enter Correct Details");
		} 
		finally {
			try {
				if (conn != null) {
					conn.close();
					conn = null;
				}
				if (pstmt != null) {
					pstmt.close();						
					pstmt = null;
				}
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public RequestInfo bookRequest(int id , int bookId) {
		RequestInfo requestInfo = new RequestInfo();
		try {
			conn = dbConnector.getConnection();
			String query = dbConnector.getQuery("requestBookQuery");

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, bookId);
			int count = pstmt.executeUpdate();

			if (count != 0) 
			{
				requestInfo.setId(id);
				requestInfo.setBookId(bookId);

				return requestInfo;
			}	
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new LMSException("Request Can't Be placed");

		} 
		finally 
		{
			try {
				if (conn != null) 
				{
					conn.close();
					conn = null;
				}
				if (pstmt != null) 
				{
					pstmt.close();
					pstmt = null;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}

		}
		return null;
	}
	@Override
	public boolean bookReturn(int userId,int bookId) {
		String query = null;
		ResultSet rs = null;
		try {
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("bookReturn");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, bookId);

			rs = pstmt.executeQuery();

			if (rs.next() != false) {
				int requestId = rs.getInt("rid");
				System.out.println("Request Id....." + requestId);

				//conn = dbConnector.getConnection();
				query = dbConnector.getQuery("updateReturnDate");
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, requestId);

				int count = pstmt.executeUpdate();
				if (count != 0) {
					return true;
				} else {
					return false;
				}
			}else {
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
			conn = dbConnector.getConnection();
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

					while(resultSet.next()) {
						noOfDaysDelayed = resultSet.getInt(1);
					}
					//System.out.println("No Of Days Delayed " + noOfDaysDelayed);

					if (noOfDaysDelayed > 0) {
						fine = noOfDaysDelayed * 5;
						query = dbConnector.getQuery("userFine");
						pstmt = conn.prepareStatement(query);
						pstmt.setDouble(1, fine);
						pstmt.setInt(2, userId);

						result = pstmt.executeUpdate();
						if (result != 0) {
							return true;
							//System.out.println("fine updated" + fine);
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

			} // End Of While Loop
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

		}// End Of Book Receive
	}
}
