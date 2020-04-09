package com.capgemini.librarymanagementjdbc.controller;


import java.util.List;
import java.util.Scanner;

import com.capgemini.librarymanagementjdbc.dto.AdminInfo;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;
import com.capgemini.librarymanagementjdbc.exception.LMSException;
import com.capgemini.librarymanagementjdbc.factory.Factory;
import com.capgemini.librarymanagementjdbc.service.LibraryService;

public class LibraryController {

	public static void main(String[] args) {
		LibraryService service = Factory.getLibraryService();
		try(Scanner scanner = new Scanner(System.in);) {
			BookDetails book;
			AdminInfo info ;
			int choice = 0;
			int adminchoice = 0;
			int userChoice = 0;

			int id;
			int bookId;
			int rid;
			String name;
			String emailId;
			String password;
			String role;
			do
			{

					System.out.println("*******Library Management System******");
					System.out.println("Press 1 For Admin Login");
					System.out.println("Press 2 For User Login");
					System.out.println("Enter your Choice");
					choice = scanner.nextInt();
					switch(choice) {
					case 1:
						System.out.println("Enter Login Credentials");
						System.out.println("Enter Admin Email Id");
						emailId = scanner.next();
						System.out.println("Enter The Password");
						password= scanner.next();
						try {
							AdminInfo admin = service.login(emailId, password);

							if(admin != null) {
								System.out.println("Admin logged in Successfully");
								do {
									System.out.println("press 1 to Register New User");
									System.out.println("Press 2 to Show List of Users");
									System.out.println("press 3 to Search Book");
									System.out.println("press 4 to Add Book");
									System.out.println("press 5 to Show Books");
									System.out.println("Press 6 to Show Requests");
									System.out.println("press 7 to Issue the book");
									System.out.println("press 8 to Remove Book");
									System.out.println("Press 9 to Receive Book");
									System.out.println("Press 0 to Exit");
									System.out.println("--------------------------------------");
									System.out.println("Enter your choice:");
									adminchoice = scanner.nextInt();
									switch (adminchoice) {
									case 1:
										info = new AdminInfo();
										System.out.println("Enter the Details to add the New User");
										System.out.println("Enter user Id");
										id = scanner.nextInt();
										System.out.println("enter the name");
										name = scanner.next();
										System.out.println("Enter the email");
										emailId = scanner.next();
										System.out.println("Enter The Password");
										password = scanner.next();
										System.out.println("Enter the Mobile Number");
										String userContact = scanner.next();
										System.out.println("Enter the Role");
										role = scanner.next();

										info.setId(id);
										info.setUserName(name);
										info.setEmailId(emailId);
										info.setPassword(password);
										info.setMobileNumber(userContact);
										info.setRole(role);
										try {
											boolean adduser = service.addUser(info);
											if(adduser) {
												System.out.println("New User added Successfully");
											}
										}catch (LMSException e) {
											System.err.println(e.getMessage());
										}
										break;
									case 2:

										List<AdminInfo> userList = service.viewUsers();
										try {
											for (AdminInfo infos : userList) {
												System.out.println("Show User Data present in database");
												System.out.println("User Id....."+infos.getId());
												System.out.println("User Name......."+infos.getUserName());
												System.out.println("Email Id......"+infos.getEmailId());
												System.out.println("password......."+infos.getPassword());
												System.out.println("User mobile Number....."+infos.getMobileNumber());
												System.out.println("Role......."+infos.getRole());
											} 
										} catch (LMSException e) {
											System.err.println(e.getMessage());
										}
										break;
									case 3:
										System.out.println("Enter the Id to be searched");	
										bookId=scanner.nextInt();
										try {
											BookDetails searchId = service.search(bookId);
											if(searchId != null) 
												System.out.println("book has found successfully");
										}
										catch (LMSException e) {
											System.err.println(e.getMessage());
										}
										break;

									case 4:
										book = new BookDetails();
										System.out.println("Enter Book details");
										System.out.println("Enter the Book Id");
										bookId= scanner.nextInt();
										System.out.println("Enter the Book Name");
										name= scanner.next();
										System.out.println("Enter the Author Name");
										name = scanner.next();
										System.out.println("Enter the Publisher Name");
										name = scanner.next();
										book.setBookId(bookId);
										book.setBookName(name);
										book.setAuthor(name);
										book.setPublisher(name);
										try {
											boolean addBook = service.addBook(book);

											if(addBook) {
												System.out.println("Book Added Successfully");
											}
										}catch (LMSException e) {
											System.err.println(e.getMessage());
										}

										break;
									case 5:
										try {
											List<BookDetails> list = service.viewBooks();

											for (BookDetails books : list) {
												System.out.println("Books present in database..");
												System.out.println("bookId....."+books.getBookId());
												System.out.println("bookName......."+books.getBookName());
												System.out.println("bookAuthor......"+books.getAuthor());
												System.out.println("bookPublisher......"+books.getPublisher());
												System.out.println("-------------------------------");
											}
										}catch (LMSException e) {
											System.err.println("No Books Present in library");
										}

										break;
									case 6:
										try {
											System.out.println("Requests for Books are :");
											System.out.println("-------------------------------");

											List<RequestInfo> requestList = service.viewRequests();
											for (RequestInfo requestInfo : requestList) {
												System.out.println("RequestId-----"+requestInfo.getRid());
												System.out.println("Book id ---------- " + requestInfo.getBookId());
												System.out.println("User id----------- " + requestInfo.getId());
												System.out.println("Book IssuedDate-----" + requestInfo.getIssueDate());
												System.out.println("Expected Return Date--" + requestInfo.getExpectedReturnDate());

												System.out.println("-------------------------------");
											}
										} catch (LMSException e) {
											System.err.println(e.getMessage());
										}

										break;
									case 7:
										System.out.println("Enter Request Id");
										rid = scanner.nextInt();
										try {
											boolean issue = service.issueBook(rid);
											if (issue) {
												System.out.println("Book Issued to the User");
											}
										} catch (LMSException e) {
											System.err.println(e.getMessage());
										}
										break;


									case 8:
										System.out.println("Enter the BookId to be Removed");
										bookId= scanner.nextInt();
										try {
											boolean remove = service.removeBook(bookId);
											if(remove) {
												System.out.println("Book is Removed successfully....");
											}
										}catch (LMSException e)  {
											System.err.println(e.getMessage());
										}
										break;	
									case 9 :

										System.out.println("Receive Returned Book");
										System.out.println("-----------------------");
										System.out.println("Enter Request Id");
										rid = scanner.nextInt();
										try {
											boolean isReceived = service.isBookReceived(rid);
											if(isReceived) {
												System.out.println("Book is Received by te Librarian");
											}
										} catch (LMSException e) {
											System.err.println(e.getMessage());
										}
										break;
									case 0:
										break;
									default:
										System.out.println("invalid option");
									 break;
									}

								}while(adminchoice != 0);
							}

						}
						catch (LMSException e) {
							System.err.println(e.getMessage());	
						}

						break;
					case 2:

						System.out.println("Enter the User Name");
						emailId = scanner.next();
						System.out.println("Enter the Password");
						password = scanner.next();

						try {
							AdminInfo userLogin = service.userLogin(emailId, password);
							if(userLogin != null) {
								System.out.println("User LoggedIn successfully");
								do {
									System.out.println("Press 1 to search");
									System.out.println("Press 2 to Show Books ");
									System.out.println("Press 3 to Book Request");
									System.out.println("Press 4 to Return Book");
									System.out.println("Press 0 to Exit");
									userChoice = scanner.nextInt();
									switch(userChoice) {
									case 1: 
										System.out.println("Search book by bookId");
										System.out.println("Enter the BookId:");
										int searchId=scanner.nextInt();
										try
										{

											BookDetails bookSearch = service.search(searchId);
											if(bookSearch != null) {
												System.out.println("Book has Found Successfully");
												System.out.println(bookSearch.getBookId());
												System.out.println(bookSearch.getBookName());
												System.out.println(bookSearch.getAuthor());
											}
										}catch (LMSException e) {
											System.err.println(e.getMessage());
										}
										break;
									case 2:
										try {
											List<BookDetails> list = service.viewBooks();

											for (BookDetails books : list) {
												System.out.println("Books present in database..");
												System.out.println("bookId....."+books.getBookId());
												System.out.println("bookName......."+books.getBookName());
												System.out.println("bookAuthor......"+books.getAuthor());
												System.out.println("bookPublisher......"+books.getPublisher());
												System.out.println("-------------------------------");
											}
										}catch (LMSException e) {
											System.err.println("No Books Present in library");
										}
										break;
									case 3:
										System.out.println("Enter User Id");
										id = scanner.nextInt();							
										System.out.println("Enter Book Id");
										bookId = scanner.nextInt();
										try 
										{
											RequestInfo requestInfo = service.bookRequest(id, bookId);
											if (requestInfo != null)
											{
												System.out.println("Request Successfully Placed to Admin");
											}
										} catch (LMSException e) {

											System.err.println(e.getMessage());
										}
										break;
									case 4:

										System.out.println("Returning a book:");
										System.out.println("------------------");
										System.out.println("Enter User Id");
										id = scanner.nextInt();
										System.out.println("Enter Book Id");
										bookId = scanner.nextInt();

										try {
											boolean isReturn = service.bookReturn(id, bookId);
											if (isReturn) {
												System.out.println("Returning Request placed to Admin");
											}
										} catch (LMSException e) {
											System.err.println(e.getMessage());
										}
										break;
									case 0:
										break;
									default:
										System.out.println("invalid option");
									 break;

									}// End of Switch case
								} while(userChoice!=0);

							} //if condition
						}
						catch (LMSException e) {
							System.err.println(e.getMessage());
						}
					} //End of  Main Switch Case
			} while(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}// End of Class