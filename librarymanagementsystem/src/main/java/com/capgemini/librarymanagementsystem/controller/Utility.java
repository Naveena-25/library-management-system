package com.capgemini.librarymanagementsystem.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.capgemini.librarymanagementsystem.db.DataBase;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.exception.LMSException;
import com.capgemini.librarymanagementsystem.factory.Factory;
import com.capgemini.librarymanagementsystem.service.AdminService;
import com.capgemini.librarymanagementsystem.service.UserService;
import com.capgemini.librarymanagementsystem.validation.Validation;

public class Utility {

	private static final AdminService service = Factory.getAdminService();
	private static final UserService userService = Factory.getUserService();
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
				System.err.println("Invalid Choice, It Should Contain Only Digits");
				scanner.next();
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
				System.err.println("Id should Contain Only Digits");
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
			} catch (LMSException e) {
				flag = false;
				System.err.println(e.getMessage());
			}
		} while (!flag);
		return mobileNumber;
	}

	public static boolean checkBookAvailability() {
		boolean isAvailable = false;
		boolean flag = false;
		do {
			try {
				isAvailable = scanner.nextBoolean();
				flag = true;
			} catch (InputMismatchException e) {
				System.err.println("Enter Boolean value either True or False");
				flag = false;
				scanner.next();
			}
		} while (!flag);
		return isAvailable;
	}

	public static void libraryUtility() {

		BookDetails book;
		UserInfo user;
		int choice = 0;
		int adminchoice = 0;
		int userChoice = 0;

		int userId = 0;
		int bookId = 0;
		String name = null;
		String author = null;
		String publisher = null;
		String emailId = null;
		String password = null;
		String newPassword = null;
		String mobileNumber = null;
		boolean isAvailable = false;

		DataBase.addToDataBase();

		do {
			System.out.println("********Welcome To Library Management System***********");
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
					do {
						System.out.println("----------------------------------------------");
						System.out.println("press 1 to Add New User");
						System.out.println("Press 2 to View Users");
						System.out.println("press 3 to Search Book");
						System.out.println("press 4 to Add Book");
						System.out.println("press 5 to View Books");
						System.out.println("Press 6 to View Requests");
						System.out.println("press 7 to Issue the book");
						System.out.println("press 8 to Remove Book");
						System.out.println("Press 9 to Receive Book");
						System.out.println("Press 0 to Logout");

						adminchoice = checkChoice();
						switch (adminchoice) {
						case 1:
							user = new UserInfo();
							System.out.println("Enter the Details to Register New User");
							userId = (int) (Math.random() * 1000);
							if (userId <= 100) {
								userId = userId + 100;
							}
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
								boolean isUserAdded = service.addUser(user);
								if (isUserAdded) {
									System.out.println("New User Added Successfully");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 2:
							try {
								List<UserInfo> userList = service.viewUsers();
								System.out.println("Users Present in Database are:");
								System.out.println(String.format("%-10s %-15s %-25s %-15s %-25s %-5s", "USER ID",
										"USER NAME", "EMAIL ID", "MOBILE NUMBER", "NO.OF BOOKS BORROWED", "FINE"));

								for (UserInfo userInfo : userList) {
									System.out.println(String.format("%-10s %-15s %-25s %-15s %-25s %-5s",
											userInfo.getUserId(), userInfo.getUserName(), userInfo.getEmailId(),
											userInfo.getMobileNumber(), userInfo.getNoOfBooksBorrowed(),
											userInfo.getFine()));
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
								System.out.println("Availability................." + search.isBookAvailable());
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 4:
							book = new BookDetails();
							System.out.println("Enter Book details");
							System.out.println("---------------------------");
							bookId = (int) (Math.random() * 1000);
							if (bookId <= 100) {
								bookId = bookId + 100;
							}
							scanner.nextLine();
							System.out.println("Enter the Book Name");
							name = checkName();
							System.out.println("Enter the Author Name");
							author = checkName();
							System.out.println("Enter the Publisher Name");
							publisher = checkName();
							System.out.println("Enter Book Availability");
							isAvailable = checkBookAvailability();

							book.setBookId(bookId);
							book.setBookName(name);
							book.setAuthor(author);
							book.setPublisherName(publisher);
							book.setBookAvailable(isAvailable);
							try {
								boolean isBookAdded = service.addBook(book);
								if (isBookAdded) {
									System.out.println("Book Added Successfully");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 5:
							try {
								List<BookDetails> list = service.viewBooks();
								System.out.println("Books present in Database are:");
								System.out.println("----------------------------------------------------------");
								System.out.println(String.format("%-10s %-35s %-25s %-30s %-25s ", "BOOKID", "BOOKNAME",
										"AUTHOR", "PUBLISHER", "ISBOOKAVAILABLE"));

								for (BookDetails bookDetails : list) {
									System.out.println(String.format("%-10s %-35s %-25s %-30s %-25s",
											bookDetails.getBookId(), bookDetails.getBookName(), bookDetails.getAuthor(),
											bookDetails.getPublisherName(), bookDetails.isBookAvailable()));
								}

							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 6:
							try {
								List<RequestInfo> requestlist = service.viewRequests();
								System.out.println("Requested Books are :");
								System.out.println("---------------------------------------------------------");
								System.out.println(String.format("%-10s %-10s %-15s %-15s %-30s %-30s %-25s", "USER ID",
										"BOOK ID", "IS ISSUED", "IS RETURNED", "ISSUE DATE", "EXPECTED RETURN DATE",
										"RETURN DATE"));

								for (RequestInfo requestInfo : requestlist) {
									System.out.println(String.format("%-10s %-10s %-15s %-15s %-30s %-30s %-25s",
											requestInfo.getUserId(), requestInfo.getBookId(), requestInfo.isIssued(),
											requestInfo.isReturned(), requestInfo.getIssueDate(),
											requestInfo.getExpectedReturnDate(), requestInfo.getReturnedDate()));
								}

							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 7:
							System.out.println("Enter Book Id");
							bookId = checkId();
							System.out.println("Enter User Id");
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
								boolean isRemoved = service.removeBook(bookId);
								if (isRemoved) {
									System.out.println("Book Removed Successfully");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 9:
							System.out.println("Receive Returned Book from the User");
							System.out.println("----------------------------------------");
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
					System.out.println("------------------------------------");
					do {
						System.out.println("Press 1 to Change Password");
						System.out.println("Press 2 to Search");
						System.out.println("Press 3 to View Books");
						System.out.println("Press 4 to Book Request");
						System.out.println("Press 5 to Return Book");
						System.out.println("Press 0 to Logout");
						System.out.println("Enter choice");
						userChoice = checkChoice();
						switch (userChoice) {

						case 1:
							System.out.println("Change Password");
							System.out.println("-------------------------");
							System.out.println("Enter User Email Id");
							emailId = checkEmailId();
							scanner.nextLine();
							System.out.println("Enter Old Password");
							password = checkPassword();
							System.out.println("Enter New Password");
							newPassword = checkPassword();

							try {
								userService.changePassword(emailId, password, newPassword);
								System.out.println("Password Changed Successfully");
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 2:
							System.out.println("Search Book by Book Id");
							System.out.println("-----------------------------");
							System.out.println("Enter Book Id");
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

						case 3:
							try {
								List<BookDetails> list = service.viewBooks();
								System.out.println("Books present in Database are ");
								System.out.println("----------------------------------------");
								System.out.println(String.format("%-10s %-35s %-25s %-30s %-25s ", "BOOKID", "BOOKNAME",
										"AUTHOR", "PUBLISHER", "ISAVAILABLE"));

								for (BookDetails bookDetails : list) {
									System.out.println(String.format("%-10s %-35s %-25s %-30s %-25s",
											bookDetails.getBookId(), bookDetails.getBookName(), bookDetails.getAuthor(),
											bookDetails.getPublisherName(), bookDetails.isBookAvailable()));
								}

							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 4:
							System.out.println("Enter the Details To Request a Book");
							System.out.println("----------------------------------------");
							System.out.println("Enter Book Id");
							bookId = checkId();
							System.out.println("Enter User Id");
							userId = checkId();
							try {
								boolean request = userService.bookRequest(userId, bookId);
								if (request) {
									System.out.println("Request placed to Admin Successfully");
								}
							} catch (LMSException e) {
								System.err.println(e.getMessage());
							}
							break;

						case 5:
							System.out.println("Enter the Details to To Return the Book");
							System.out.println("-----------------------------------------");
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
				System.err.println("Invalid Option, Choice should be In Between 1 and 2");
				break;
			}

		} while (true);

	}
}
