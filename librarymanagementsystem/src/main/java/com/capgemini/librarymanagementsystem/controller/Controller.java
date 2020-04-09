package com.capgemini.librarymanagementsystem.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.factory.Factory;
import com.capgemini.librarymanagementsystem.service.AdminService;
import com.capgemini.librarymanagementsystem.service.UserService;
import com.capgemini.librarymanagementsystem.validation.Validation;

public class Controller {

	public static void main(String[] args) {
		AdminService service = Factory.getServiceAdmin();
		UserService userService = Factory.getUserService();
		try(Scanner scanner = new Scanner(System.in);) {
			boolean flag = false;

			Validation validate = new Validation();
			BookDetails book ;
			UserInfo user;

			int userId = 0;
			int bookId = 0;
			String name = null;
			String emailId = null;
			String password = null;
			String mobileNumber = null;

			int choice = 0;
			int adminchoice = 0; 
			int userChoice = 0;
			do {

					System.out.println("********Welcome To Library Management System*************");
					System.out.println("Press 1 For Admin Login");
					System.out.println("Press 2 For User Login");
					System.out.println("Enter Your Choice");
					do {
						try {
							choice = scanner.nextInt();
							flag = true;
						} catch (InputMismatchException e) {
							System.err.println("Choice Must Be In Digits");
							flag = false;
							scanner.next();
						}

					} while (!flag);
					switch(choice) {

					case 1:

						System.out.println("Enter Login Credentials of Admin");
						do {
							try {
								System.out.println("enter Admin Email ID");
								emailId= scanner.next();
								flag = true;
							} catch (Exception e) {
								System.err.println(e.getMessage());
								flag = false;
							}
						} while (!flag);
						do {
							try {
								System.out.println("enter password");
								password= scanner.next();
								flag = true;
							} catch (Exception e) {
								System.err.println(e.getMessage());
								flag = false;
							}
						} while (!flag);
						try {
							service.auth(emailId, password);
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

								do {
									try {
										System.out.println("Enter your choice:");
										adminchoice = scanner.nextInt();
										flag = true;

									} catch (InputMismatchException e) {
										System.err.println("Enter Only Digits");
										flag = false;
										scanner.next();

									} catch (Exception e) {
										System.err.println(e.getMessage());
										flag = false;
									}
								} while (!flag);

								switch (adminchoice) {
								case 1:
									user = new UserInfo();
									System.out.println("Enter the details to Register New User");
									//Do while For User Id
									do {
										try {
											System.out.println("Enter user Id");
											userId = scanner.nextInt();
											validate.validateId(userId);
											flag = true;
										}catch (InputMismatchException e) {
											System.err.println("Id Should Contain Only Digits");
											flag = false;
											scanner.next();
										} catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									//Do While Loop For Name
									do {
										try {
											System.out.println("Enter the User Name");
											name = scanner.nextLine();
											validate.validateName(name);
											flag = true;	
										} catch(Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									//Do while Loop For Email
									do {
										try {

											System.out.println("Enter the Email Id ");
											emailId = scanner.next();
											validate.validateEmail(emailId);
											flag = true;
										}catch (Exception e) {
											System.err.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									//User Password Validation Do While loop
									do {
										try {
											System.out.println("Enter The Password");
											password = scanner.next();
											validate.validatePassword(password);
											flag = true;
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									//Mobile Validation Do While Loop
									do {
										try {
											System.out.println("Enter the Mobile Number");
											mobileNumber = scanner.next();
											validate.validateMobileNumber(mobileNumber);
											flag = true;

										} catch (Exception e) {
											System.err.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									user.setUserId(userId);
									user.setUserName(name);
									user.setEmail(emailId);
									user.setPassword(password);
									user.setMobileNumber(mobileNumber);
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
									System.out.println("Enter the Book Id to be searched");
									do {
										try {
											bookId=scanner.nextInt();
											validate.validateId(bookId);
											flag = true;
										}catch (InputMismatchException e) {
											System.err.println("Id Should Contain Only Digits");
											flag = false;
											scanner.next();
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									try {
										BookDetails search = service.search(bookId);

										System.out.println("book has found successfully");
										System.out.println(search.getBookId());
									}
									catch (Exception e) {
										System.err.println("book is not found");
									}
									break;

								case 4:
									System.out.println("Enter Book details");
									book = new BookDetails();
									do {
										try {
											System.out.println("Enter the Book Id");
											bookId= scanner.nextInt();
											validate.validateId(bookId);
											flag = true;
										}catch (InputMismatchException e) {
											System.err.println("Id Should Contain Only Digits");
											flag = false;
											scanner.next();
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									//validation for Book Name
									do {
										try {
											System.out.println("Enter the Book Name");
											name= scanner.nextLine();
											validate.validateName(name);
											flag = true;
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									do {
										try {
											System.out.println("Enter the Author Name");
											name = scanner.next();
											validate.validateName(name);
											flag = true;
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									do {
										try {
											System.out.println("Enter the Publisher Name");
											name = scanner.next();
											validate.validateName(name);
											flag = true;
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									book.setBookId(bookId);
									book.setBookName(name);
									book.setAuthor(name);
									book.setPublisherName(name);
									try {
										boolean result1 = service.addBook(book);

										if(result1) {
											System.out.println("Book Added Successfully");
										}
									}catch (Exception e) {
										System.err.println(e.getMessage());
									}

									break;
								case 5:
									try {
										List<BookDetails> list = service.showBooks();

										for (BookDetails books : list) {
											System.out.println("Books present in database..");
											System.out.println("bookId....."+books.getBookId());
											System.out.println("bookName......."+books.getBookName());
											System.out.println("bookAuthor......"+books.getAuthor());
											System.out.println("Book Publisher......"+books.getPublisherName());
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
											System.out.println("-------------------------------");
										}
									} catch (Exception e) {
										System.err.println(e.getMessage());
									}

									break;
								case 7:
									book = new BookDetails();
									user = new UserInfo();
									do {
										try {
											System.out.println("enter Book Id");
											bookId = scanner.nextInt();
											validate.validateId(bookId);
											flag = true;
										}catch (InputMismatchException e) {
											System.err.println("Id Should Contain Exact 5 Digits and starting Digit Should Be 1");
											flag = false;
											scanner.next();
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);

									do {
										try {
											System.out.println("enter User Id");
											userId = scanner.nextInt();
											validate.validateId(userId);
											flag = true;
										}catch (InputMismatchException e) {
											System.err.println("Id Should Contain Exact 5 Digits and starting Digit Should Be 1");
											flag = false;
											scanner.next();
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);

									book.setBookId(bookId);
									user.setUserId(userId);

									try {
										boolean isIssued = service.issueBook(user, book);
										if (isIssued) {
											System.out.println("Book Issued");
										} 
									} catch (Exception e) {
										System.err.println("Invalid data request book cannot be issued");
									}
									break;
								case 8:
									book = new BookDetails();
									do {
										try {
											System.out.println("Enter the BookId to be Removed");
											bookId= scanner.nextInt();
											validate.validateId(bookId);
											flag = true;
										}catch (InputMismatchException e) {
											System.err.println("Id Should Contain Only Digits");
											flag = false;
											scanner.next();
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
									book.setBookId(bookId);
									try {
										boolean remove = service.removebook(book);
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
									do {
										try {
											System.out.println("Enter User Id");
											userId = scanner.nextInt();
											validate.validateId(userId);
											flag = true;
										}catch (InputMismatchException e) {
											System.err.println("Id Should Contain Only Digits");
											flag = false;
											scanner.next();
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);

									do {
										try {
											System.out.println("Enter Book Id");
											bookId = scanner.nextInt();
											validate.validateId(bookId);
											flag = true;
										}catch (InputMismatchException e) {
											System.err.println("Id Should Contain Only Digits");
											flag = false;
											scanner.next();
										}catch (Exception e) {
											System.out.println(e.getMessage());
											flag = false;
										}
									}while(!flag);
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
							System.out.println("Exception due to invalid credentials");
						}
						break;
					case 2:
						do {
							try {
								System.out.println("Enter the User Email Id");
								emailId = scanner.next();
								validate.validateEmail(emailId);
								flag = true;
							}catch (Exception e) {
								System.out.println(e.getMessage());
								flag = false;
							}
						}while(!flag);
						do {
							try {
								System.out.println("Enter the Password");
								password = scanner.next();
								validate.validatePassword(password);
								flag = true;
							}catch (Exception e) {
								System.out.println(e.getMessage());
								flag = false;
							}
						} while(!flag);
						try {
						userService.userLogin(emailId, password);
						System.out.println("User LoggedIn successfully");
						do {

							System.out.println("Press 1 to search");
							System.out.println("Press 2 to Book Request");
							System.out.println("Press 3 to Return Book");
							System.out.println("Press 0 to Exit");
							do {
								try {
									System.out.println("Enter Your Choice");
									userChoice = scanner.nextInt();
									flag = true;

								} catch (InputMismatchException e) {
									System.err.println("Enter Only Digits");
									flag = false;
									scanner.next();

								} catch (Exception e) {
									System.err.println(e.getMessage());
									flag = false;
								}
							} while (!flag);
							
							switch(userChoice) {
							case 1: 
								System.out.println("Search Book by BookId");
								do {
									try {
										System.out.println("Enter the BookId:");
										bookId = scanner.nextInt();
										validate.validateId(bookId);
										flag = true;

									} catch (InputMismatchException e) {
										System.err.println("Id Should Contain Only Digits");
										flag = false;
										scanner.next();

									} catch (Exception e) {
										System.err.println(e.getMessage());
										flag = false;
									}
								} while (!flag);
								try {
									BookDetails bookSearch = userService.search(bookId);
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
								do {
									try {
										System.out.println("Enter Book Id");
										bookId = scanner.nextInt();
										validate.validateId(bookId);
										flag = true;
									} catch (InputMismatchException e) {
										System.err.println("Id Should Contain Only Digits");
										flag = false;
										scanner.next();
									} catch (Exception e) {
										System.err.println(e.getMessage());
										flag = false;
									}
								} while (!flag);

								do {
									try {
										System.out.println("Enter User Id");
										userId = scanner.nextInt();
										validate.validateId(userId);
										flag = true;

									} catch (InputMismatchException e) {
										System.err.println("Id Should Contain Only Digits");
										flag = false;
										scanner.next();

									} catch (Exception e) {
										System.err.println(e.getMessage());
										flag = false;
									}
								} while (!flag);
								book.setBookId(bookId);
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
								do {
									try {
										System.out.println("Enter User Id");
										userId  = scanner.nextInt();
										validate.validateId(userId);
										flag = true;
									} catch (InputMismatchException e) {
										System.err.println("Id Should Contain Only Digits");
										flag = false;
										scanner.next();
									} catch (Exception e) {
										System.err.println(e.getMessage());
										flag = false;
									}
								} while (!flag);

								do {
									try {
										System.out.println("Enter Book Id");
										bookId = scanner.nextInt();
										validate.validateId(bookId);
										flag = true;
									} catch (InputMismatchException e) {
										System.err.println("Id Should Contain Only Digits");
										flag = false;
										scanner.next();
									} catch (Exception e) {
										System.err.println(e.getMessage());
										flag = false;
									}
								} while (!flag);
								user.setUserId(userId);
								book.setBookId(bookId);

								try {
									RequestInfo requestInfo = userService.bookReturn(user, book);
									System.out.println("Book is Returning to Admin");
									System.out.println("User Id ------"+requestInfo.getUserInfo().getUserId());
									System.out.println("Book Id ------"+requestInfo.getBookdetails().getBookId());
									System.out.println("Is Returning --"+requestInfo.isReturned());

								} catch (Exception e) {
									System.err.println(e.getMessage());
								}
								break;
							case 0:
								break;
							default:
								System.out.println("invalid option");
							 break;
							} //End of Switch i.e.,User Choice
						} while(userChoice!=0);
						} catch (Exception e) {
							System.out.println("User cannot log in");
						}

					} //End of Main Switch
					
				
			}while(true);//End of Main Do While Loop
		}catch (Exception e) {
			e.printStackTrace();
		}

	}//End of Main Method
}//End of  Controller Class