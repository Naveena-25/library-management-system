package com.capgemini.librarymanagementhibernate.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.capgemini.librarymanagementhibernate.dto.LibraryUsers;
import com.capgemini.librarymanagementhibernate.dto.BookDetails;
import com.capgemini.librarymanagementhibernate.dto.RequestInfo;
import com.capgemini.librarymanagementhibernate.exception.LMSException;

public class LibraryDAOimpl implements LibraryDAO {

	EntityManagerFactory factory = null;

	@Override
	public LibraryUsers login(String emailId, String password) {
		EntityManager manager = null;
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		String jpql = "select a from  LibraryUsers a where a.emailId = :emailId and a.password =:password";
		TypedQuery<LibraryUsers> query = manager.createQuery(jpql, LibraryUsers.class);
		query.setParameter("emailId", emailId);
		query.setParameter("password", password);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			throw new LMSException("Invalid Login Credentials, Please Enter Correctly");
		} finally {
			manager.close();
			factory.close();
		}
	}

	@Override
	public boolean addUser(LibraryUsers info) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			manager.persist(info);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("User Already Exists Or User Can't Be added");
		} finally {
			manager.close();
			factory.close();
		}
	}

	@Override
	public List<LibraryUsers> viewUsers() {
		EntityManager manager = null;
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		String jpql = "select m from LibraryUsers m";
		TypedQuery<LibraryUsers> query = manager.createQuery(jpql, LibraryUsers.class);
		List<LibraryUsers> recordlist = query.getResultList();
		manager.close();
		factory.close();
		return recordlist;

	}

	@Override
	public boolean addBook(BookDetails bookDetails) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {

			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			manager.persist(bookDetails);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		} finally {
			manager.close();
			factory.close();
		}
	}

	@Override
	public BookDetails search(int bookId) {
		EntityManager manager = null;
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		BookDetails search = manager.find(BookDetails.class, bookId);

		factory.close();
		manager.close();
		return search;
	}

	@Override
	public List<BookDetails> viewBooks() {
		EntityManager manager = null;
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		String jpql = "select m from BookDetails m";
		TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
		List<BookDetails> listOfBooks = query.getResultList();
		factory.close();
		manager.close();
		return listOfBooks;

	}

	@Override
	public List<RequestInfo> viewRequests() {
		EntityManager manager = null;
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		String jpql = "select m from RequestInfo m";
		TypedQuery<RequestInfo> query = manager.createQuery(jpql, RequestInfo.class);
		List<RequestInfo> listOfRequest = query.getResultList();
		factory.close();
		manager.close();
		return listOfRequest;
	}

	@Override
	public boolean issueBook(int requestId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		RequestInfo info = new RequestInfo();
		BookDetails bookInfo = new BookDetails();
		LibraryUsers user = new LibraryUsers();

		int noOfBooks = 0;
		int reqBookId = 0;
		int reqUserId = 0;

		Date date = new Date();
		Date expectedReturnDate = null;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 15);
		expectedReturnDate = calendar.getTime();

		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			transaction.begin();
			info = manager.find(RequestInfo.class, requestId);

			if (info != null) {
				Date issueDate = info.getIssueDate();
				if (issueDate == null) {
					reqUserId = info.getId();
					reqBookId = info.getBookId();

					info.setIssueDate(date);
					info.setExpectedReturnDate(expectedReturnDate);
					transaction.commit();

					transaction.begin();
					user = manager.find(LibraryUsers.class, reqUserId);
					noOfBooks = user.getNoOfBooksBorrowed();
					++noOfBooks;
					System.out.println("No Of Books Borrowed" + noOfBooks);

					user.setNoOfBooksBorrowed(noOfBooks);
					transaction.commit();

					transaction.begin();
					bookInfo = manager.find(BookDetails.class, reqBookId);
					bookInfo.setAvailable(false);
					transaction.commit();
				} else {
					throw new LMSException("This Book Is Already Issued");
				}

			} else {
				throw new LMSException("Invalid Request Id");
			}

		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
			factory.close();
		}
		return false;
	}

	@Override
	public boolean isBookReceived(int requestId) {
		EntityManagerFactory factory = null;
		EntityManager manager = null;
		EntityTransaction transaction = null;

		RequestInfo requestInfo = new RequestInfo();
		BookDetails bookInfo = new BookDetails();
		LibraryUsers libraryUsers = new LibraryUsers();

		int noOfBooks = 0;
		int reqBookId = 0;
		int reqUserId = 0;
		double fine = 0;

		Date expectedReturnDate = null;
		Date returnedDate = null;

		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			transaction.begin();
			requestInfo = manager.find(RequestInfo.class, requestId);

			if (requestInfo != null) {
				reqBookId = requestInfo.getBookId();
				reqUserId = requestInfo.getId();
				returnedDate = requestInfo.getReturnedDate();
				expectedReturnDate = requestInfo.getExpectedReturnDate();
				transaction.commit();

				if (returnedDate != null) {
					long expReturnDateInMilliSecs = expectedReturnDate.getTime();
					long returnedDateInMilliSecs = returnedDate.getTime();
					long diffInMilliSecs = returnedDateInMilliSecs - expReturnDateInMilliSecs;
					int NoOfDaysDelayed = (int) (diffInMilliSecs / (24 * 60 * 60 * 1000));

					transaction.begin();
					libraryUsers = manager.find(LibraryUsers.class, reqUserId);
					noOfBooks = libraryUsers.getNoOfBooksBorrowed();
					--noOfBooks;
					libraryUsers.setNoOfBooksBorrowed(noOfBooks);
					if (NoOfDaysDelayed > 0) {
						fine = libraryUsers.getFine();
						fine = fine + (NoOfDaysDelayed * 5);
						libraryUsers.setFine(fine);
					}
					transaction.commit();

					transaction.begin();
					bookInfo = manager.find(BookDetails.class, reqBookId);
					bookInfo.setAvailable(true);
					transaction.commit();

					transaction.begin();
					bookInfo = manager.find(BookDetails.class, reqBookId);
					manager.remove(requestInfo);
					transaction.commit();

				} else {
					throw new LMSException("Book is Not returned By the User");
				}

			} else {
				throw new LMSException("Invalid Request Id");
			}
		} catch (LMSException e) {
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
			factory.close();
		}
		return false;
	}

	@Override
	public boolean removeBook(int bookId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			BookDetails book = manager.find(BookDetails.class, bookId);
			manager.remove(book);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Book Can't Be Removed or Deleted from Library");
		} finally {
			manager.close();
			factory.close();
		}

	}

	@Override
	public LibraryUsers userLogin(String emailId, String password) {
		EntityManager manager = null;
		factory = Persistence.createEntityManagerFactory("TestPersistence");
		manager = factory.createEntityManager();
		String jpql = "select a from  LibraryUsers a where a.emailId = :emailId and a.password =:password";
		TypedQuery<LibraryUsers> query = manager.createQuery(jpql, LibraryUsers.class);
		query.setParameter("emailId", emailId);
		query.setParameter("password", password);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			throw new LMSException("Invalid Login Credentials");
		}
	}

	@Override
	public boolean changePassword(int userId, String oldPassword, String newPassword) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		LibraryUsers user = new LibraryUsers();
		String password = null;

		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			transaction.begin();
			user = manager.find(LibraryUsers.class, userId);
			password = user.getPassword();
			if (password.equals(oldPassword)) {

				user.setPassword(newPassword);
				transaction.commit();
			} else {
				throw new LMSException("Invalid Password");
			}
		} catch (LMSException e) {
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
			factory.close();
		}
		return true;
	}

	@Override
	public boolean bookRequest(int userId, int bookId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		RequestInfo info = new RequestInfo();
		BookDetails bookDetails = new BookDetails();
		LibraryUsers user = new LibraryUsers();

		String jpql = null;
		int noOfRequests = 0;

		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			user = manager.find(LibraryUsers.class, userId);
			if (user != null) {

				jpql = "select count(*) from RequestInfo ri where ri.id=:userId";
				Query query = manager.createQuery(jpql);
				query.setParameter("userId", userId);
				noOfRequests = ((Number) query.getSingleResult()).intValue();

				if (noOfRequests < 3) {
					bookDetails = manager.find(BookDetails.class, bookId);
					if (bookDetails != null) {
						jpql = "select ri from RequestInfo ri";
						TypedQuery<RequestInfo> query2 = manager.createQuery(jpql, RequestInfo.class);
						List<RequestInfo> list = query2.getResultList();
						for (RequestInfo requestInfo : list) {
							if (requestInfo.getBookId() == bookId) {
								throw new LMSException("This Book Request is Already Placed");
							}
						}
						if (bookDetails.isAvailable()) {
							System.out.println("tans started");
							transaction.begin();
							info.setId(userId);
							info.setBookId(bookId);
							manager.persist(info);
							transaction.commit();
						} else {
							throw new LMSException("This Book Is Not Available For Borrowing");
						}

					} else {
						throw new LMSException("Book Doesnot Exists with Given Book Id");
					}

				} else {
					throw new LMSException("You 've Already Placed Maximum No Of Requests");
				}
			} else {
				throw new LMSException("User Doesn't Present With Matching UserId");
			}

		} catch (LMSException e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		}
		return true;
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		RequestInfo info = new RequestInfo();
		String jpql = null;
		int requestId = 0;

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DATE, 20);
		Date returnedDate = calendar2.getTime();

		try {
			factory = Persistence.createEntityManagerFactory("TestPersistence");
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			jpql = "select ri from RequestInfo ri ";
			TypedQuery<RequestInfo> query = manager.createQuery(jpql, RequestInfo.class);
			List<RequestInfo> list = query.getResultList();

			for (RequestInfo requestInfo : list) {
				if ((requestInfo.getBookId() == bookId) && (requestInfo.getId() == userId)
						&& (requestInfo.getReturnedDate() != null)) {
					throw new LMSException("Book is Already Returned By The User");
				} else {
					requestId = requestInfo.getRId();
				}
			}
			if (requestId != 0) {
				transaction.begin();
				info = manager.find(RequestInfo.class, requestId);
				info.setReturnedDate(returnedDate);
				transaction.commit();

			} else {
				throw new LMSException("Invalid Book Return as User Id or Book Id Doesn't Match");
			}

		} catch (LMSException e) {
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
			factory.close();
		}

		return true;
	}

}
