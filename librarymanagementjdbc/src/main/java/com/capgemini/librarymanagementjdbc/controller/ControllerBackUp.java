package com.capgemini.librarymanagementjdbc.controller;

import java.util.List;
import java.util.Scanner;

import com.capgemini.librarymanagementjdbc.dto.AdminInfo;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;
import com.capgemini.librarymanagementjdbc.factory.Factory;
import com.capgemini.librarymanagementjdbc.service.LibraryService;

public class ControllerBackUp {

	public static void main(String[] args) {
		LibraryService service = Factory.getLibraryService();
		try(Scanner scanner = new Scanner(System.in);) {
			BookDetails book;
			AdminInfo user ;
			
			int choice = 0;
			int adminchoice = 0;
			int userChoice = 0;
			do
			{
				
				try {
					System.out.println("Press 1 For Admin Login");
					System.out.println("Press 2 For User Login");
					System.out.println("----------------------------------------");
					choice = scanner.nextInt();
					switch(choice)
					{

					case 1:
						System.out.println("Enter Login Credentials");
						System.out.println("Enter Admin Email Id");
						String adminEmail= scanner.next();
						System.out.println("Enter The Password");
						String password= scanner.next();
						try 
						{
							AdminInfo admin = service.login(adminEmail, password);

							if(admin != null)
							{
								System.out.println("Admin logged in Successfully");
								do {
									System.out.println("press 1 to Add New User");
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
									switch (adminchoice) 
									{
									case 1:
										user = new AdminInfo();
										System.out.println("Enter the details to add the user");
										System.out.println("Enter user Id");
										int userId = scanner.nextInt();
										System.out.println("enter the name");
										String userName = scanner.next();
										System.out.println("Enter the email");
										String userEmail = scanner.next();
										System.out.println("Enter The Password");
										String userPassword = scanner.next();
										System.out.println("Enter the Mobile Number");
										String userContact = scanner.next();
										System.out.println("Enter the Role");
										String role = scanner.next();


										user.setId(userId);
										user.setUserName(userName);
										user.setEmailId(userEmail);
										user.setPassword(userPassword);
										user.setMobileNumber(userContact);
										user.setRole(role);
										try {
											boolean adduser = service.addUser(user);
											if(adduser) {
												System.out.println("New User added successfully");
											}
										}catch (Exception e) {
											System.err.println(e.getMessage());
										}
										break;
									case 2:
										
										List<AdminInfo> userList = service.viewUsers();

										for (AdminInfo info : userList) {
											System.out.println("Show user list present in database");
											System.out.println("User Id....."+info.getId());
											System.out.println("User Name......."+info.getUserName());
											System.out.println("Email Id......"+info.getEmailId());
											System.out.println("password......."+info.getPassword());
											System.out.println("User mobile Number....."+info.getMobileNumber());
										}
										break;
									case 3:
										System.out.println("Enter the Id to be searched");	
										int searchId=scanner.nextInt();
										try {
											BookDetails search = service.search(searchId);
											if(search!= null)
											System.out.println("book has found successfully");
										}
										catch (Exception e) {
											e.printStackTrace();
										}
										break;

									case 4:

										System.out.println("Enter Book details");
										book = new BookDetails();
										System.out.println("Enter the Book Id");
										int bookId= scanner.nextInt();
										System.out.println("Enter the Book Name");
										String bookName= scanner.next();
										System.out.println("Enter the Author Name");
										String author = scanner.next();
										System.out.println("Enter the Publisher Name");
										String publisherName = scanner.next();
										book.setBookId(bookId);
										book.setBookName(bookName);
										book.setAuthor(author);
										book.setPublisher(publisherName);
										try {
											boolean result1 = service.addBook(book);

											if(result1) {
												System.out.println("Book Added Successfully");
											}
										}catch (Exception e) {
											System.out.println(e.getMessage());
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
										}catch (Exception e) {
											System.out.println("No Books Present in library");
										}

										break;
									case 6:
										try {
											System.out.println("Requests for Books are :");
											System.out.println("-------------------------------");

											List<RequestInfo> requestInfos = service.viewRequests();
											for (RequestInfo info : requestInfos) {

												System.out.println("Book id ---------- " + info.getBookId());
												System.out.println("User id----------- " + info.getInfo().getId());
												System.out.println("Book Issued ------" + info.isIssued());
												System.out.println("Book Returned------" + info.isReturned());
												System.out.println("Book IssuedDate-----" + info.getIssueDate());
												System.out.println("Expected Return Date--" + info.getReturnDate());

												System.out.println("-------------------------------");
											}
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}

										break;
									case 7:
										book = new BookDetails();
										user = new AdminInfo();
										RequestInfo info = new RequestInfo();
										System.out.println("enter Request Id");
										int rid = scanner.nextInt();
										System.out.println("enter Book Id");
										int bId = scanner.nextInt();
										System.out.println("enter User Id");
										int uId = scanner.nextInt();
										book.setBookId(bId);
										user.setId(uId);
										info.setRid(rid);
										try
										{
											boolean isIssued = service.issueBook(rid);
											if (isIssued)
											{
												System.out.println("Book Issued");
											}
											else 
											{
												System.out.println("Book cannot be issued");
											}
										} catch (Exception e) 
										{
											System.out.println("Invalid data request book cannot be issued");
											}
										break;
									case 8:
										System.out.println("Enter the BookId to be Removed");
										int Id= scanner.nextInt();
										BookDetails bookInfo = new BookDetails();
										bookInfo.setBookId(Id);

										try {
											boolean remove = service.removebook(bookInfo);
											if(remove) 
											{
												System.out.println("removed successfully....");
											}
										}catch (Exception e)  {
											System.err.println(e.getMessage());
										}
										break;	
									case 9 :
										book = new BookDetails();
										user = new AdminInfo();
										System.out.println("Receive Returned Book");
										System.out.println("-----------------------");
										System.out.println("Enter User Id");
										userId = scanner.nextInt();
										System.out.println("Enter Book Id");
										bookId = scanner.nextInt();

										book.setBookId(bookId);
										user.setId(userId);
										try {
											boolean isReceive = service.receivedBook(user, book);
											if (isReceive) {
												System.out.println(" Received Returned book");
											} 
										} catch (Exception e) {
											System.err.println(e.getMessage());
										}
										break;

									}

								}while(adminchoice != 0);
							}

						}
						catch (Exception e) 
						{
							System.err.println("Invalid Login Details");	
						}
						
						break;
					case 2:
						
						System.out.println("Enter the User Name");
						String userName = scanner.next();
						System.out.println("Enter the Password");
						String userpassword = scanner.next();
						try {
							service.userLogin(userName, userpassword);
							System.out.println("User LoggedIn successfully");
							do {
								//System.out.println("Press 1 to login");
								System.out.println("Press 1 to search");
								System.out.println("Press 2 to Book Request");
								System.out.println("Press 3 to Return Book");
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
										System.out.println("book has found successfully");
										System.out.println(bookSearch.getBookId());
										System.out.println(bookSearch.getBookName());
										System.out.println(bookSearch.getAuthor());
									}catch (Exception e) {
										System.err.println("Book is Not Found");
									}
									break;

								case 2:
									RequestInfo request = new RequestInfo();
									user = new AdminInfo();
									book = new BookDetails();
									System.out.println("Enter id");
									int requestId = scanner.nextInt();
									request.setBookId(requestId);
									System.out.println("Enter book id");
									int bookId = scanner.nextInt();
									request.setBookId(bookId);

									System.out.println("Enter user id");
									int userId = scanner.nextInt();							
									request.setId(userId);
									try {
										RequestInfo requestInfo = service.bookRequest(user, book);
										System.out.println("Request placed to admin");
										System.out.println("User Id-----" + requestInfo.getRid());
										System.out.println("User Id-----" + requestInfo.getId());
										System.out.println("Book Id-----" + requestInfo.getBookId());
										System.out.println("issued........."+requestInfo.isIssued());
									} catch (Exception e) {

										System.err.println(e.getMessage());
									}
									break;
								case 3:
									user = new AdminInfo();
									book = new BookDetails();
									System.out.println("Returning a book:");
									System.out.println("------------------");
									System.out.println("Enter User Id");
									int user1  = scanner.nextInt();
									System.out.println("Enter Book Id");
									int book1 = scanner.nextInt();
									user.setId(user1);
									book.setBookId(book1);

									try {
										RequestInfo requestInfo = service.bookReturn(user, book);
										System.out.println("Book is Returning to Admin");
										System.out.println("User Id ------"+requestInfo.getInfo().getId());
										System.out.println("Book Id ------"+requestInfo.getBookdetails().getBookId());
										System.out.println("Is Returning --"+requestInfo.isReturned());
									//	System.out.println("Returning date--" + requestInfo.getReturnedDate());

									} catch (Exception e) {
										System.err.println(e.getMessage());
									}
									break;

								}
								break;
							} while(userChoice!=0);


						} 
						catch (Exception e)
						{
							System.err.println("User Can't Log In");
						}

					} 
				}catch (Exception e) {
					System.err.println("Error in Choice");
				}
			} while(true);
	}catch (Exception e) {
		e.printStackTrace();
	}

	}
}// End of Class