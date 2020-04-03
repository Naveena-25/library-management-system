package com.capgemini.librarymanagementsystem.controller;

import java.util.List;
import java.util.Scanner;

import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.factory.Factory;
import com.capgemini.librarymanagementsystem.service.AdminService;
import com.capgemini.librarymanagementsystem.service.UserService;

public class ControllerMain {

	public static void main(String[] args) {
		AdminService service = Factory.getServiceAdmin();
		UserService userService = Factory.getUserService();
		try(Scanner scanner = new Scanner(System.in);) {
			//Validation validate = new Validation();
			BookDetails book ;
			UserInfo user ;
			int choice;
			int adminchoice;
			int userChoice;
			do
			{
				System.out.println("Press 1 For Admin Login");
				System.out.println("Press 2 For User Login");
				System.out.println("----------------------------------------");
				choice = scanner.nextInt();
				switch(choice) {
				case 1:	
					System.out.println("Enter Login Credentials");
					System.out.println("enter Admin name");
					String adminEmail= scanner.next();
					System.out.println("enter password");
					String password= scanner.next();
					try {

						service.auth(adminEmail, password);
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
								user = new UserInfo();
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
								long userContact = scanner.nextInt();

								user.setUserId(userId);
								user.setUserName(userName);
								user.setEmail(userEmail);
								user.setPassword(userPassword);
								user.setMobileNumber(userContact);
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
								user = new UserInfo();
								List<UserInfo> userList = service.showUsers();

								for (UserInfo userInfo : userList) {
									System.out.println("Show user list present in database");
									System.out.println("User Id....."+userInfo.getUserId());
									System.out.println("User Name......."+userInfo.getUserName());
									System.out.println("Email Id......"+userInfo.getEmail());
									System.out.println("User mobile Number....."+userInfo.getMobileNumber());
								}
								break;
							case 3:
								System.out.println("Enter the Id to be searched");	
								int searchId=scanner.nextInt();
								try {
									BookDetails search = service.search(searchId);

									System.out.println("book has found successfully");
									System.out.println(search.getAuthor());
								}
								catch (Exception e) {
									System.err.println("book is not found");
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
								book.setPublisherName(publisherName);
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
									List<BookDetails> list = service.showBooks();

									for (BookDetails b : list) {
										System.out.println("Books present in database..");
										System.out.println("bookId....."+b.getBookId());
										System.out.println("bookName......."+b.getBookName());
										System.out.println("bookAuthor......"+b.getAuthor());
										System.out.println("bookPublisher......"+b.getPublisherName());
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

									List<RequestInfo> requestInfos = service.showRequests();
									for (RequestInfo info : requestInfos) {

										System.out.println("Book id ---------- " + info.getBookdetails().getBookId());
										System.out.println("Book Name -------- " + info.getBookdetails().getBookName());
										System.out.println("User id----------- " + info.getUserInfo().getUserId());
										System.out.println("User Name -------- " + info.getUserInfo().getUserName());
										System.out.println("Book Issued ------" + info.isIssued());
										System.out.println("Book Returned------" + info.isReturned());
										System.out.println("Book IssuedDate-----" + info.getIssueDate());
										System.out.println("Expected Return Date--" + info.getReturnDate());
										System.out.println("Returned Date......"+info.getReturnedDate());

										System.out.println("-------------------------------");
									}
								} catch (Exception e) {
									System.err.println(e.getMessage());
								}

								break;
							case 7:
								book = new BookDetails();
								System.out.println("enter Book Id");
								int bId = scanner.nextInt();
								user = new UserInfo();
								System.out.println("enter User Id");
								int uId = scanner.nextInt();
								book.setBookId(bId);
								user.setUserId(uId);

								try
								{
									boolean isIssued = service.issueBook(user, book);
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
								user = new UserInfo();
								System.out.println("Receive Returned Book");
								System.out.println("-----------------------");
								System.out.println("Enter User Id");
								userId = scanner.nextInt();
								System.out.println("Enter Book Id");
								bookId = scanner.nextInt();

								book.setBookId(bookId);
								user.setUserId(userId);
								try {
									boolean isReceive = service.receivedBook(user, book);
									if (isReceive) {
										System.out.println(" Received Returned book");
									} 
								} catch (Exception e) {
									System.err.println(e.getMessage());
								}
								break;
							case 0:
								break;
								
							default:
								System.out.println("Invalid Option");
								break;
							}
						}while(adminchoice != 0);

					}catch (Exception e) {

						System.out.println("Invalid Admin Details");
					}
					break;
				case 2:
					System.out.println("Enter the User Name");
					String userName = scanner.next();
					System.out.println("Enter the Password");
					String userpassword = scanner.next();
					try {
						userService.userLogin(userName, userpassword);
						System.out.println("User LoggedIn successfully");
						do {
							//System.out.println("Press 1 to login");
							System.out.println("Press 1 to search");
							System.out.println("Press 2 to Book Request");
							System.out.println("Press 3 to Return Book");
							System.out.println("Press 0 to Exit");
							System.out.println("Enter choice");
							userChoice = scanner.nextInt();
							switch(userChoice) {
							case 1: 
								System.out.println("Search book by bookId");
								System.out.println("Enter the BookId:");
								int searchId=scanner.nextInt();
								try
								{
									BookDetails bookSearch = userService.search(searchId);
									System.out.println("book has found successfully");
									System.out.println(bookSearch.getBookId());
									System.out.println(bookSearch.getBookName());
									System.out.println(bookSearch.getAuthor());
								}catch (Exception e) {
									System.err.println("Book is Not Found");
								}
								break;

							case 2:
								book = new BookDetails();

								user = new UserInfo();
								System.out.println("Enter book id");
								int bookId = scanner.nextInt();

								book.setBookId(bookId);

								System.out.println("Enter user id");
								int userId = scanner.nextInt();							
								user.setUserId(userId);
								try {
									RequestInfo request = userService.bookRequest(user, book);
									System.out.println("Request placed to admin");
									System.out.println("User Id-----" + request.getUserInfo().getUserId());
									System.out.println("Book Id-----" + request.getBookdetails().getBookId());
								} catch (Exception e) {

									System.err.println(e.getMessage());
								}
								break;
							case 3:

								user = new UserInfo();
								book = new BookDetails();
								System.out.println("Returning a book:");
								System.out.println("------------------");
								System.out.println("Enter User Id");
								int user1  = scanner.nextInt();
								System.out.println("Enter Book Id");
								int book1 = scanner.nextInt();
								user.setUserId(user1);
								book.setBookId(book1);

								try {
									RequestInfo requestInfo = userService.bookReturn(user, book);
									System.out.println("Book is Returning to Admin");
									System.out.println("User Id ------"+requestInfo.getUserInfo().getUserId());
									System.out.println("Book Id ------"+requestInfo.getBookdetails().getBookId());
									System.out.println("Is Returning --"+requestInfo.isReturned());
									System.out.println("Returning date--" + requestInfo.getReturnedDate());

								} catch (Exception e) {
									System.err.println(e.getMessage());
								}
								break;
							case 0:
								break;
							default:
								System.out.println("Invalid Option ");
								break;
							}
						} while(userChoice!=0);//Do While Loop for userChoice

					} catch (Exception e) {
						System.err.println("User Can't Log In");
					}

				} //End of Switch Choice
			} while(true);

		} catch (Exception e) {
			e.printStackTrace();
		}//scanner close

	}//End of Main Method
}// End of Class