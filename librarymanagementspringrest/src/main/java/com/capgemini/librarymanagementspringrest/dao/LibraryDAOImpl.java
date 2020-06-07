package com.capgemini.librarymanagementspringrest.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.capgemini.librarymanagementspringrest.dto.BookDetails;
import com.capgemini.librarymanagementspringrest.dto.LibraryUsers;
import com.capgemini.librarymanagementspringrest.dto.RequestInfo;
import com.capgemini.librarymanagementspringrest.exception.LMSException;

@Repository
public class LibraryDAOImpl implements LibraryDAO {

	@PersistenceUnit
	private EntityManagerFactory factory;

	@Override
	public LibraryUsers userLogin(String emailId, String password) {
		EntityManager manager = null;
		manager = factory.createEntityManager();
		String jpql = " from  LibraryUsers where emailId = :emailId and password =:password";
		TypedQuery<LibraryUsers> query = manager.createQuery(jpql, LibraryUsers.class);
		query.setParameter("emailId", emailId);
		query.setParameter("password", password);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			throw new LMSException("Invalid Login Credentials, Please Enter Correctly");

		} finally {
			manager.close();
		}
	}

	@Override
	public boolean addUser(LibraryUsers libraryUsers) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			manager.persist(libraryUsers);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("User Already Exists Or User Can't Be added");
		} finally {
			manager.close();
		}
	}

	@Override
	public List<LibraryUsers> viewUsers() {
		EntityManager manager = null;
		manager = factory.createEntityManager();
		String jpql = "select m from LibraryUsers m";
		TypedQuery<LibraryUsers> query = manager.createQuery(jpql, LibraryUsers.class);
		List<LibraryUsers> userList = query.getResultList();
		manager.close();
		return userList;

	}

	@Override
	public boolean addBook(BookDetails bookDetails) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
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
		}
	}

	@Override
	public BookDetails search(int bookId) {
		EntityManager manager = null;
		manager = factory.createEntityManager();
		BookDetails search = manager.find(BookDetails.class, bookId);
		manager.close();
		return search;
	}

	@Override
	public List<BookDetails> viewBooks() {
		EntityManager manager = null;
		manager = factory.createEntityManager();
		String jpql = "select m from BookDetails m";
		TypedQuery<BookDetails> query = manager.createQuery(jpql, BookDetails.class);
		List<BookDetails> bookList = query.getResultList();
		manager.close();
		return bookList;

	}

	@Override
	public BookDetails updateBook(BookDetails bookDetails) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();

			BookDetails book = manager.find(BookDetails.class, bookDetails.getBookId());
			if (bookDetails.getBookName() != null) {
				book.setBookName(bookDetails.getBookName());
			}
			if (bookDetails.getAuthor() != null) {
				book.setAuthor(bookDetails.getAuthor());
			}
			if (bookDetails.getPublisher() != null) {
				book.setPublisher(bookDetails.getPublisher());
			}
			if (!(bookDetails.isAvailable() == book.isAvailable())) {
				book.setAvailable(bookDetails.isAvailable());
			}
			transaction.commit();
			return book;
		} catch (Exception e) {
			return null;
		} finally {
			manager.close();
		}

	}

	@Override
	public List<RequestInfo> viewRequests() {
		EntityManager manager = null;
		manager = factory.createEntityManager();
		String jpql = "select m from RequestInfo m";
		TypedQuery<RequestInfo> query = manager.createQuery(jpql, RequestInfo.class);
		List<RequestInfo> requestList = query.getResultList();
		manager.close();
		return requestList;
	}

	@Override
	public boolean issueBook(int requestId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		RequestInfo info = new RequestInfo();
		BookDetails book = new BookDetails();
		LibraryUsers user = new LibraryUsers();

		int noOfBooksBorrowed = 0;
		int reqBookId = 0;
		int reqUserId = 0;

		Date date = new Date();
		Date expectedReturnDate = null;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 15);
		expectedReturnDate = calendar.getTime();

		try {
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
					noOfBooksBorrowed = user.getNoOfBooksBorrowed();
					++noOfBooksBorrowed;

					user.setNoOfBooksBorrowed(noOfBooksBorrowed);
					transaction.commit();

					transaction.begin();
					book = manager.find(BookDetails.class, reqBookId);
					book.setAvailable(false);
					transaction.commit();
				} else {
					throw new LMSException("This Book Is Already Issued");
				}

			} else {
				throw new LMSException("Invalid Request, Request ID Not Found");
			}

		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
		}
		return true;
	}

	@Override
	public boolean receiveBook(int requestId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		RequestInfo requestInfo = new RequestInfo();
		BookDetails bookDetails = new BookDetails();
		LibraryUsers libraryUsers = new LibraryUsers();

		int noOfBooksBorrowed = 0;
		int reqBookId = 0;
		int reqUserId = 0;
		double fine = 0;

		Date expectedReturnDate = null;
		Date returnedDate = null;

		try {
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
					noOfBooksBorrowed = libraryUsers.getNoOfBooksBorrowed();
					--noOfBooksBorrowed;
					libraryUsers.setNoOfBooksBorrowed(noOfBooksBorrowed);
					if (NoOfDaysDelayed > 0) {
						fine = libraryUsers.getFine();
						fine = fine + (NoOfDaysDelayed * 5);
						libraryUsers.setFine(fine);
					}
					transaction.commit();

					transaction.begin();
					bookDetails = manager.find(BookDetails.class, reqBookId);
					bookDetails.setAvailable(true);
					transaction.commit();

					transaction.begin();
					bookDetails = manager.find(BookDetails.class, reqBookId);
					manager.remove(requestInfo);
					transaction.commit();

				} else {
					throw new LMSException("Book is Not Returned By the User");
				}

			} else {
				throw new LMSException("Invalid Request, Request ID Not Found");
			}
		} catch (LMSException e) {
			throw new LMSException(e.getMessage());
		} finally {
			manager.close();
		}
		return true;
	}

	@Override
	public boolean removeBook(int bookId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;
		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();
			transaction.begin();
			BookDetails bookDetails = manager.find(BookDetails.class, bookId);
			if (bookDetails != null) {
				manager.remove(bookDetails);
				transaction.commit();
				return true;
			} else {
				throw new LMSException("Book with The Given ID is not Present in the Lbrary");

			}
		} catch (Exception e) {
			transaction.rollback();
			throw new LMSException("Book Can't Be Removed or Deleted from Library");
		} finally {
			manager.close();
		}

	}

	@Override
	public boolean bookRequest(int userId, int bookId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		RequestInfo info = new RequestInfo();
		BookDetails bookDetails = new BookDetails();
		LibraryUsers libraryUsers = new LibraryUsers();

		String jpql = null;
		int noOfRequests = 0;

		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			libraryUsers = manager.find(LibraryUsers.class, userId);
			if (libraryUsers != null) {

				jpql = "select count(*) from RequestInfo r where r.id=:userId";
				Query query = manager.createQuery(jpql);
				query.setParameter("userId", userId);
				noOfRequests = ((Number) query.getSingleResult()).intValue();

				if (noOfRequests < 3) {
					bookDetails = manager.find(BookDetails.class, bookId);
					if (bookDetails != null) {
						jpql = "select r from RequestInfo r";
						TypedQuery<RequestInfo> query1 = manager.createQuery(jpql, RequestInfo.class);
						List<RequestInfo> list = query1.getResultList();
						for (RequestInfo requestInfo : list) {
							if (requestInfo.getBookId() == bookId) {
								throw new LMSException("This Book Request is Already Placed");
							}
						}
						if (bookDetails.isAvailable()) {
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
	public List<RequestInfo> getAllReqBook() {
		EntityManager manager = null;
		manager = factory.createEntityManager();
		String jpql = "select m from RequestInfo where m.issueDate = null";
		TypedQuery<RequestInfo> query = manager.createQuery(jpql, RequestInfo.class);
		List<RequestInfo> requestList = query.getResultList();
		manager.close();
		return requestList;
	}

	@Override
	public List<RequestInfo> getAllReturnedBook() {
		EntityManager manager = null;
		manager = factory.createEntityManager();
		String jpql = "select m from RequestInfo where m.returnedDate != null";
		TypedQuery<RequestInfo> query = manager.createQuery(jpql, RequestInfo.class);
		List<RequestInfo> requestlist = query.getResultList();
		manager.close();
		return requestlist;
	}

	@Override
	public List<RequestInfo> userBooks(int userId) {

		EntityManager manager = null;
		String jpql = null;
		try {
			manager = factory.createEntityManager();
			jpql = "select info from RequestInfo info where info.id =: userId";
			TypedQuery<RequestInfo> query = manager.createQuery(jpql, RequestInfo.class);
			query.setParameter("userId", userId);
			List<RequestInfo> requestList = query.getResultList();
			if ((requestList != null) && (!requestList.isEmpty())) {
				return requestList;
			} else {
				throw new LMSException("No Books Borrowed By the User");
			}
		} catch (LMSException e) {
			throw new LMSException(e.getMessage());
		}
	}

	@Override
	public boolean bookReturn(int userId, int bookId) {
		EntityManager manager = null;
		EntityTransaction transaction = null;

		RequestInfo info = new RequestInfo();
		String jpql = null;
		int requestId = 0;

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 20);
		Date returnedDate = calendar.getTime();

		try {
			manager = factory.createEntityManager();
			transaction = manager.getTransaction();

			jpql = "select r from RequestInfo r ";
			TypedQuery<RequestInfo> query = manager.createQuery(jpql, RequestInfo.class);
			List<RequestInfo> list = query.getResultList();

			for (RequestInfo requestInfo : list) {
				if ((requestInfo.getBookId() == bookId) && (requestInfo.getId() == userId)) {
					if (requestInfo.getIssueDate() != null) {
						if (requestInfo.getReturnedDate() != null) {
							throw new LMSException("Book is Already Returned By The User");
						} else {
							requestId = requestInfo.getrId();
						}
					}
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
		}
		return true;
	}
}
