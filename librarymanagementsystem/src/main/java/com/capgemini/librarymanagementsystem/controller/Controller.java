package com.capgemini.librarymanagementsystem.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.exception.LMSException;
import com.capgemini.librarymanagementsystem.factory.Factory;
import com.capgemini.librarymanagementsystem.service.AdminService;
import com.capgemini.librarymanagementsystem.service.UserService;
import com.capgemini.librarymanagementsystem.validation.Validation;

public class Controller {

	public static final AdminService service = Factory.getAdminService();
	public static final UserService userService = Factory.getUserService();
	private static final Validation VALIDATION = new Validation();
	public static Scanner scanner = new Scanner(System.in);

	public static int checkChoice() {
		boolean flag = false;
		int choice = 0;
		do {
			try {
				choice = scanner.nextInt();
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println("Choice Should Contain Only Digits");
				scanner.next();
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return choice;
	}

	public static int checkId() {
		boolean flag = false;
		int id = 0;
		do {
			try {
				id = scanner.nextInt();
				VALIDATION.validateId(id);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println(e.getMessage());
				scanner.next();
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);

		return id;
	}

	public static String checkName() {
		String name = null;
		boolean flag = false;
		do {
			try {
				name = scanner.nextLine();
				VALIDATION.validateName(name);
				flag = true;
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return name;
	}

	public static String checkEmailId() {
		String emailId = null;
		boolean flag = false;
		do {
			try {
				emailId = scanner.next();
				VALIDATION.validateEmail(emailId);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println(e.getMessage());
				scanner.next();
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return emailId;
	}

	public static String checkPassword() {
		String password = null;
		boolean flag = false;
		do {
			try {
				password = scanner.next();
				VALIDATION.validatePassword(password);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println(e.getMessage());
				scanner.next();
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return password;
	}

	public static String checkMobileNumber() {
		String mobileNumber = null;
		boolean flag = false;
		do {
			try {
				mobileNumber = scanner.next();
				VALIDATION.validateMobileNumber(mobileNumber);
				flag = true;
			} catch (InputMismatchException e) {
				flag = false;
				System.err.println(e.getMessage());
				scanner.next();
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return mobileNumber;
	}

	public static void main(String[] args) {

		BookDetails book;
		UserInfo user;
		int choice = 0;
		int adminchoice = 0;
		int userChoice = 0;

		int userId = 0;
		int bookId = 0;
		String name = null;
		String emailId = null;
		String password = null;
		String mobileNumber = null;
		do {
			System.out.println("********Welcome To Library Management System*************");
			System.out.println("Press 1 For Admin Login");
			System.out.println("Press 2 For User Login");
			choice = checkChoice();
			switch (choice) {

			case 1:
				System.out.println("Enter Login Credentials of Admin");
				System.out.println("Enter Email Id");
				emailId = checkEmailId();
				System.out.println("Enter the Password");
				password = checkPassword();
				try {
					service.authentication(emailId, password);

					System.out.println("Admin logged in Successfully");
					System.out.println("---------------------------------");
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
						System.out.println("Press 0 to Logout");

						adminchoice = checkChoice();
						switch (adminchoice) {
						case 1:
							user = new UserInfo();
							System.out.println("Enter the details to Register New User");
							System.out.println("Enter User Id");
							userId = checkId();
							scanner.nextLine();
							System.out.println("Enter the User Name");
							name = checkName();
							System.out.println("Enter the Email Id");
							emailId = checkEmailId();
							System.out.println("Enter The Password");
							password = checkPassword();
							System.out.println("Enter the Mobile Number");
							mobileNumber = checkMobileNumber();
							user.setUserId(userId);
							user.setUserName(name);
							user.setEmailId(emailId);
							user.setPassword(password);
							user.setMobileNumber(mobileNumber);

							try {
								boolean adduser = service.addUser(user);
								if (adduser) {
									System.out.println("New User added successfully");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 2:
							try {
							List<UserInfo> userList = service.showUsers();
							
							for (UserInfo userInfo : userList) {
								System.out.println("Show user list present in database");
								System.out.println("User Id....................." + userInfo.getUserId());
								System.out.println("User Name..................." + userInfo.getUserName());
								System.out.println("Email Id...................." + userInfo.getEmailId());
								System.out.println("Password...................." + userInfo.getPassword());
								System.out.println("User mobile Number.........." + userInfo.getMobileNumber());
								System.out.println("Number of books Borrowed...." + userInfo.getNoOfBooksBorrowed());
							}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 3:
							System.out.println("Enter the Book Id to be searched");
							bookId = checkId();
							try {
								BookDetails search = service.search(bookId);
								System.out.println("Book has Found Successfully");
								System.out.println("The Book Id is..............." + search.getBookId());
								System.out.println("Book Name is................." + search.getBookName());
								System.out.println("Author......................." + search.getAuthor());
								System.out.println("Book Publisher..............." + search.getPublisherName());
								System.out.println("Availability................." + search.isAvailable());
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 4:
							book = new BookDetails();
							System.out.println("Enter Book details");
							System.out.println("Enter the Book Id");
							bookId = checkId();
							scanner.nextLine();
							System.out.println("Enter the Book Name");
							name = checkName();
							System.out.println("Enter the Author Name");
							name = checkName();
							System.out.println("Enter the Publisher Name");
							name = checkName();
							System.out.println("Enter Book Availability");
							boolean isAvailable = scanner.nextBoolean();
							book.setBookId(bookId);
							book.setBookName(name);
							book.setAuthor(name);
							book.setPublisherName(name);
							book.setAvailable(isAvailable);
							try {
								service.addBook(book);
								System.out.println("Book Added Successfully");
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 5:
							try {
							List<BookDetails> list = service.showBooks();						
							for (BookDetails books : list) {
								System.out.println("Books present in Database are ");
								System.out.println("BookId..............." + books.getBookId());
								System.out.println("BookName............." + books.getBookName());
								System.out.println("BookAuthor..........." + books.getAuthor());
								System.out.println("Book Publisher......." + books.getPublisherName());
								System.out.println("Availability........." + books.isAvailable());
								System.out.println("-------------------------------");
							}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 6:
							System.out.println("Requested Books are :");
							try {
							List<RequestInfo> requestlist = service.showRequests();
							for (RequestInfo info : requestlist) {

								System.out.println("Book id .................." + info.getBookId());
								System.out.println("User id.................. " + info.getUserId());
								System.out.println("Issue Date................" + info.getIssueDate());
								System.out.println("Expected Return Date......" + info.getExpectedReturnDate());
								System.out.println("Returned Date............." + info.getReturnedDate());
								System.out.println("Book Issued..............." + info.isIssued());
								System.out.println("Book Returned............." + info.isReturned());
								System.out.println("-------------------------------");
							}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 7:
							System.out.println("enter Book Id");
							bookId = checkId();
							System.out.println("enter User Id");
							userId = checkId();
							try {
								boolean isIssued = service.issueBook(userId, bookId);
								if (isIssued) {
									System.out.println("Book Issued For the User");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 8:
							System.out.println("Enter the BookId to be Removed");
							bookId = checkId();
							try {
								boolean remove = service.removeBook(bookId);
								if (remove) {
									System.out.println("Book Removed Successfully");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 9:
							System.out.println("Receive Returned Book");
							System.out.println("-----------------------");
							System.out.println("Enter User Id");
							userId = checkId();
							System.out.println("Enter Book Id");
							bookId = checkId();

							try {
								boolean isReceive = service.receiveBook(userId, bookId);
								if (isReceive) {
									System.out.println("Received Returned Book");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 0:
							break;

						default:
							System.err.println("Invalid Option");
							break;
						}
					} while (adminchoice != 0);

				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
				break;

			case 2:
				System.out.println("Enter the User Name");
				emailId = checkEmailId();
				System.out.println("Enter the Password");
				password = checkPassword();
				try {
					userService.userLogin(emailId, password);
					System.out.println("User LoggedIn successfully");
					do {

						System.out.println("Press 1 to Search");
						System.out.println("Press 2 to Show Books");
						System.out.println("Press 3 to Book Request");
						System.out.println("Press 4 to Return Book");
						System.out.println("Press 0 to Logout");
						System.out.println("Enter choice");
						userChoice = checkChoice();
						switch (userChoice) {
						case 1:
							System.out.println("Search Book by bookId");
							System.out.println("Enter the BookId ");
							int searchId = checkId();
							try {
								BookDetails search = userService.search(searchId);
								System.out.println("Book has Found Successfully");
								System.out.println("The Book Id is..............." + search.getBookId());
								System.out.println("Book Name is................." + search.getBookName());
								System.out.println("Author......................." + search.getAuthor());
								System.out.println("Book Publisher..............." + search.getPublisherName());

							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 2:
							try {
							List<BookDetails> list = service.showBooks();
						
								for (BookDetails books : list) {
									System.out.println("Books present in database..");
									System.out.println("Book Id..............." + books.getBookId());
									System.out.println("Book Name............." + books.getBookName());
									System.out.println("Author................" + books.getAuthor());
									System.out.println("Book Publisher........" + books.getPublisherName());
									System.out.println("Availability.........." + books.isAvailable());
									System.out.println("-------------------------------");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 3:
							System.out.println("Enter Book Id");
							bookId = checkId();
							System.out.println("Enter User Id");
							userId = checkId();
							try {
								boolean request = userService.bookRequest(userId, bookId);
								if (request) {
									System.out.println("Request placed to Admin");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 4:

							System.out.println("Returning a book:");
							System.out.println("Enter User Id");
							userId = checkId();
							System.out.println("Enter Book Id");
							bookId = checkId();
							try {
								boolean isReturn = userService.bookReturn(userId, bookId);
								if (isReturn) {
									System.out.println("Book is Returning to Admin");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 0:
							break;

						default:
							System.err.println("Invalid Option");
							break;
						}

					} while (userChoice != 0);

				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
				break;
			default:
				System.err.println("Invalid Option");
				break;
			}

		} while (true);

	}
}
