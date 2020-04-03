package com.capgemini.librarymanagementjdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementjdbc.core.DBConnector;
import com.capgemini.librarymanagementjdbc.dto.AdminInfo;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;

public class LibraryDAOImpl implements LibraryDAO
{
	DBConnector dbConnector = new DBConnector();
	Connection conn = null;

	@Override
	public AdminInfo login(String emailId, String password)
	{
		PreparedStatement pstmt = null;
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

		PreparedStatement pstmt = null;
		String query = null;
		try 
		{
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

			if (count != 0) 
			{
				return true;
			}
			else 
			{
				throw new Exception("User Already Exists");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

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
		return false;


	}
	@Override
	public List<AdminInfo> viewUsers() 
	{

		List<AdminInfo> userList = new LinkedList<AdminInfo>();
		Connection conn = null;
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
				userList.add(info);
			}

			return userList;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean addBook(BookDetails bookDetails) 
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String query = null;
		try {
			conn = dbConnector.getConnection();

			query = dbConnector.getQuery("addBookQuery");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookDetails.getBookId());
			pstmt.setString(2, bookDetails.getBookName());
			pstmt.setString(3, bookDetails.getAuthor());
			pstmt.setString(4, bookDetails.getPublisher());
			int count = pstmt.executeUpdate();
			//	System.out.println("no of rows affected...");

			if (count != 0) {
				return true;
			}
			else {
				throw new Exception("Book Already Exists");
			}
		} catch (Exception e) {
			e.printStackTrace();

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
		PreparedStatement pstmt = null;
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
			else {
				throw new Exception("Book is Not Found");

			}

		} catch (Exception e) {
			e.printStackTrace();
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
		return book;
	}
	@Override
	public List<BookDetails> viewBooks() {
		List<BookDetails> books = new LinkedList<BookDetails>();
		Connection conn = null;
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
			e.printStackTrace();
		}
		return null;
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
				requestInfo.setReturnDate(resultSet.getDate("return_date"));
				requestInfo.setIssued(resultSet.getBoolean("issued"));
				requestInfo.setReturned(resultSet.getBoolean("returned"));
				requestList.add(requestInfo);
			}
			return requestList;			
		} catch (Exception e) {
			e.printStackTrace();

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
		return null;
	}

	@Override
	public boolean issueBook(int rid) {
		PreparedStatement pstmt = null;//statement1, statement2, statement3, statement4;
		ResultSet resultSet = null; // resultSet1, resultSet2, resultSet3, resultSet4, resultSet5;
		String query = null;
		try {
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("getRequest");
			pstmt = conn.prepareStatement(query);
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
				if (requestUserId != 0) {
					conn = dbConnector.getConnection();
					query = dbConnector.getQuery("getRequest");
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, requestUserId);
					resultSet = pstmt.executeQuery();

					if (resultSet.next()) {
						AdminInfo info2 = new AdminInfo();
						info2.setNoOfBooksBorrowed(resultSet.getInt("noOfBooksBorrowed"));
						int noOfBooksBorrowed = info2.getNoOfBooksBorrowed();
						System.out.println("no of books Before issue	" + noOfBooksBorrowed);
						if (noOfBooksBorrowed < 3) {
							conn = dbConnector.getConnection();
							query = dbConnector.getQuery("issueBook");
							pstmt = conn.prepareStatement(query);
							pstmt.setInt(1, requestUserId);
							resultSet = pstmt.executeQuery();
							pstmt.setInt(1, rid);
							int updateDate = pstmt.executeUpdate();
							if (updateDate != 0) {
								System.out.println("Dates updated successfully");
								System.out.println();

								// Update book availability as false as we are issuing
								//conn = dbConnector.getConnection();
								query = dbConnector.getQuery("setBookAvailability");
								pstmt = conn.prepareStatement(query);
								pstmt.setInt(1, requestUserId);
								pstmt.executeUpdate();
								pstmt.setInt(1, requestBookId);
								pstmt.executeUpdate();

								// Update User no of books borrowed
								noOfBooksBorrowed++;
								query = dbConnector.getQuery("setNoOfUserBooks");
								pstmt = conn.prepareStatement(query);
								pstmt.setInt(1, noOfBooksBorrowed);
								pstmt.setInt(2, requestUserId);
								pstmt.executeUpdate();

							} // End of if update date!=0

							return true;

						} else {
							return false;// End OF If No of books borrowed <3
						}

					} else {
						return false;// End Of getting User Data
					}

				} else

				{
					System.out.println("");
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

				if (resultSet != null) {
					resultSet.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public boolean removebook(BookDetails bookDetails) {
		PreparedStatement pstmt = null;
		String query= null;
		try {
			conn = dbConnector.getConnection();
			query = dbConnector.getQuery("removeBookQuery");
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bookDetails.getBookId());
			int result = pstmt.executeUpdate();
			if (result != 0) { 
				return true;
			} else {
				throw new Exception("Book Can't be Removed");	
			} 
		} catch (Exception e) {
			e.printStackTrace();

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
	public boolean receivedBook(AdminInfo info, BookDetails bookDetails)
	{
		return false;
	}

	@Override
	public AdminInfo userLogin(String emailId, String password)
	{
		PreparedStatement pstmt = null;
		String query = null;
		AdminInfo adminDetails = new AdminInfo();
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
	public RequestInfo bookRequest(AdminInfo info,BookDetails bookDetails) {
		RequestInfo requestInfo = new RequestInfo();
		PreparedStatement pstmt = null;
		try {
			conn = dbConnector.getConnection();
			String query = dbConnector.getQuery("requestBookQuery");

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, info.getId());
			pstmt.setInt(2, bookDetails.getBookId());
			pstmt.setBoolean(3, requestInfo.isIssued());
			int count = pstmt.executeUpdate();

			if (count != 0) 
			{
				return requestInfo;
			}
			else 
			{
				throw new Exception("Request Can't Be placed");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();

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
	public RequestInfo bookReturn(AdminInfo info, BookDetails bookdetails) {

		return null;
	}



}
