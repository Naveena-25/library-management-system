package com.capgemini.librarymanagementjdbc.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.capgemini.librarymanagementjdbc.dto.LibraryUsers;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.RequestInfo;
import com.capgemini.librarymanagementjdbc.exception.LMSException;
import com.capgemini.librarymanagementjdbc.factory.Factory;
import com.capgemini.librarymanagementjdbc.service.LibraryService;
import com.capgemini.librarymanagementjdbc.validation.Validation;

public class Controller {

	private static final LibraryService service = Factory.getLibraryService();
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
		BookDetails bookDetails;
		LibraryUsers users;

		int choice = 0;
		int adminchoice = 0;
		int userChoice = 0;

		int id = 0;
		int bookId = 0;
		int requestId = 0;
		boolean isAvailable;
		String name;
		String emailId;
		String password;
		String role;
		String mobileNumber = null;
		do {

			System.out.println("*******Welcome To Library Management System******");
			System.out.println("Press 1 For Admin Login");
			System.out.println("Press 2 For User Login");
			System.out.println("Enter your Choice");
			choice = checkChoice();
			switch (choice) {

			case 1:
				System.out.println("Enter Login Credentials");
				System.out.println("Enter Admin Email Id");
				emailId = checkEmailId();
				System.out.println("Enter The Password");
				password = checkPassword();
				try {
					LibraryUsers admin = service.login(emailId, password);

					if (admin != null) {
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
							System.out.println("Press 0 to Logout");
							System.out.println("--------------------------------------");
							System.out.println("Enter your choice:");
							adminchoice = checkChoice();
							switch (adminchoice) {

							case 1:
								users = new LibraryUsers();
								System.out.println("Enter the Details to add the New User");
								System.out.println("Enter user Id");
								id = checkId();
								scanner.nextLine();
								System.out.println("enter the name");
								name = checkName();
								System.out.println("Enter the email");
								emailId = checkEmailId();
								System.out.println("Enter The Password");
								password = checkPassword();
								System.out.println("Enter the Mobile Number");
								mobileNumber = checkMobileNumber();
								System.out.println("Enter the Role");
								role = scanner.next();

								users.setId(id);
								users.setUserName(name);
								users.setEmailId(emailId);
								users.setPassword(password);
								users.setMobileNumber(mobileNumber);
								users.setRole(role);
								try {
									boolean adduser = service.addUser(users);
									if (adduser) {
										System.out.println("New User added Successfully");
									}
								} catch (LMSException e) {
									System.err.println(e.getMessage());
								}
								break;

							case 2:
								try {
									List<LibraryUsers> userList = service.viewUsers();

									for (LibraryUsers libraryUsers : userList) {
										System.out.println("Show User Data present in database");
										System.out.println("User Id.................." + libraryUsers.getId());
										System.out.println("User Name................" + libraryUsers.getUserName());
										System.out.println("Email Id................." + libraryUsers.getEmailId());
										System.out.println("password................." + libraryUsers.getPassword());
										System.out.println("Mobile Number............" + libraryUsers.getMobileNumber());
										System.out.println("Role....................." + libraryUsers.getRole());
									}
								} catch (LMSException e) {
									System.err.println(e.getMessage());
								}
								break;

							case 3:
								System.out.println("Enter the Id to be searched");
								bookId = checkId();
								try {
									BookDetails searchId = service.search(bookId);
									if (searchId != null) {
										System.out.println("Book has Found Successfully");
										System.out.println("Book Id............." + searchId.getBookId());
										System.out.println("Book Name..........." + searchId.getBookName());
										System.out.println("Author.............." + searchId.getAuthor());
										System.out.println("Publisher..........." + searchId.getPublisher());
										System.out.println("Availability........" + searchId.isAvailable());
									}
								} catch (LMSException e) {
									System.err.println(e.getMessage());
								}
								break;

							case 4:
								bookDetails = new BookDetails();
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
								System.out.println("is Book Available");
								isAvailable = scanner.nextBoolean();

								bookDetails.setBookId(bookId);
								bookDetails.setBookName(name);
								bookDetails.setAuthor(name);
								bookDetails.setPublisher(name);
								bookDetails.setAvailable(isAvailable);
								try {
									boolean addBook = service.addBook(bookDetails);

									if (addBook) {
										System.out.println("Book Added Successfully");
									}
								} catch (LMSException e) {
									System.err.println(e.getMessage());
								}

								break;

							case 5:
								try {
									List<BookDetails> list = service.viewBooks();

									for (BookDetails books : list) {
										System.out.println("List of Books Present in the Library ");
										System.out.println("Book Id................." + books.getBookId());
										System.out.println("Book Name..............." + books.getBookName());
										System.out.println("Author.................." + books.getAuthor());
										System.out.println("Publisher..............." + books.getPublisher());
										System.out.println("Availability............" + books.isAvailable());
										System.out.println("-------------------------------");
									}
								} catch (LMSException e) {
									System.err.println(e.getMessage());
								}
								break;

							case 6:

								System.out.println("Requested Books are:");
								System.out.println("-------------------------------");
								try {
									List<RequestInfo> requestList = service.viewRequests();
									for (RequestInfo requestInfo : requestList) {
										System.out.println("RequestId................" + requestInfo.getRid());
										System.out.println("Book Id.................." + requestInfo.getBookId());
										System.out.println("User Id.................." + requestInfo.getId());
										System.out.println("Book IssuedDate.........." + requestInfo.getIssueDate());
										System.out.println("Expected Return Date....." + requestInfo.getExpectedReturnDate());
										System.out.println("Returned Date............" + requestInfo.getReturnDate());
										System.out.println("-------------------------------");
									}
								} catch (LMSException e) {
									System.err.println(e.getMessage());
								}
								break;

							case 7:
								System.out.println("Enter Request Id");
								requestId = scanner.nextInt();
								try {
									boolean issue = service.issueBook(requestId);
									if (issue) {
										System.out.println("Book Issued to the User");
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
										System.out.println("Book is Removed successfully....");
									}
								} catch (LMSException e) {
									System.err.println(e.getMessage());
								}
								break;

							case 9:
								System.out.println("Receive Returned Book");
								System.out.println("-----------------------");
								System.out.println("Enter Request Id");
								requestId = scanner.nextInt();
								try {
									boolean isReceived = service.isBookReceived(requestId);
									if (isReceived) {
										System.out.println("Book is Received by the Librarian");
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
					}
				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
				break;

			case 2:
				System.out.println("Enter the User EmailId");
				emailId = checkEmailId();
				System.out.println("Enter the Password");
				password = checkPassword();

				try {
					LibraryUsers userLogin = service.userLogin(emailId, password);
					if (userLogin != null) {
						System.out.println("User LoggedIn successfully");
						do {
							System.out.println("Press 1 to Search");
							System.out.println("Press 2 to Show Books ");
							System.out.println("Press 3 to Book Request");
							System.out.println("Press 4 to Return Book");
							System.out.println("Press 0 to Logout");
							userChoice = checkChoice();
							switch (userChoice) {

							case 1:
								System.out.println("Search book by bookId");
								System.out.println("Enter the BookId:");
								int searchId = checkId();
								try {
									BookDetails search = service.search(searchId);
									if (search != null) {
										System.out.println("book has found successfully");
										System.out.println("Book Id............." + search.getBookId());
										System.out.println("Book Name..........." + search.getBookName());
										System.out.println("Author.............." + search.getAuthor());
										System.out.println("Publisher..........." + search.getPublisher());
										System.out.println("Availability........" + search.isAvailable());
									}
								} catch (LMSException e) {
									System.err.println(e.getMessage());
								}
								break;

							case 2:
								try {
									List<BookDetails> list = service.viewBooks();
									for (BookDetails books : list) {
										System.out.println("List of Books Present in the Library ");
										System.out.println("Book Id................." + books.getBookId());
										System.out.println("Book Name..............." + books.getBookName());
										System.out.println("Author.................." + books.getAuthor());
										System.out.println("Publisher..............." + books.getPublisher());
										System.out.println("Availability............" + books.isAvailable());
										System.out.println("-------------------------------");

									}
								} catch (LMSException e) {
									System.err.println(e.getMessage());
								}
								break;

							case 3:
								System.out.println("Enter User Id");
								id = checkId();
								System.out.println("Enter Book Id");
								bookId = checkId();
								try {
									RequestInfo requestInfo = service.bookRequest(id, bookId);
									if (requestInfo != null) {
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
								id = checkId();
								System.out.println("Enter Book Id");
								bookId = checkId();

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
								System.err.println("Invalid Option");
								break;

							}
						} while (userChoice != 0);

					}
				} catch (LMSException e) {
					System.err.println(e.getMessage());
				}
				break;

			default:
				System.err.println("Choice Must Be in Between 1 and 2");
				break;

			}

		} while (true);
	}

}
