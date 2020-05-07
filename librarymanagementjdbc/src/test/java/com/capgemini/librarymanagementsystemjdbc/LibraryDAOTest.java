package com.capgemini.librarymanagementsystemjdbc;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.librarymanagementjdbc.dao.LibraryDAO;
import com.capgemini.librarymanagementjdbc.dao.LibraryDAOImpl;
import com.capgemini.librarymanagementjdbc.dto.BookDetails;
import com.capgemini.librarymanagementjdbc.dto.LibraryUsers;

public class LibraryDAOTest {
	
	LibraryDAO dao = new LibraryDAOImpl();
	

	@Test
	public void testUserLogin() {
		LibraryUsers userLogin = dao.login("admin@gmail.com", "Admin1@");
		Assertions.assertNotNull(userLogin);
	}
	
	@Test
	public void testLogin() {
		LibraryUsers userLogin = dao.userLogin("naveena@gmail.com", "Naveena@2");
		Assertions.assertNotNull(userLogin);
	}
	
	@Test
	public void testAddUser() {
	LibraryUsers info = new LibraryUsers();
	info.setId(110);
	info.setUserName("Pravalika");
	info.setEmailId("pravalika@gmail.com");
	info.setPassword("Pravalika1@");
	info.setMobileNumber("9865432123");
	info.setRole("user");
	boolean status = dao.addUser(info);
	Assertions.assertTrue(status);
	}
	
	@Test
	public void testViewUsers() {
		List<LibraryUsers>info = new ArrayList<LibraryUsers>();
		Assertions.assertNotNull(info);
	}
	@Test
	public void testAddBook() {
	BookDetails bookDetails = new BookDetails();
	bookDetails.setBookId(153);
	bookDetails.setBookName("Java");
	bookDetails.setAuthor("Bala Guruswamy");
	bookDetails.setPublisher("McGraw Hill Education");
	bookDetails.setAvailable(true);
	boolean status = dao.addBook(bookDetails);
	Assertions.assertTrue(status);
	}
	
	@Test
	public void testViewBooks() {
		List<BookDetails> bookDetails = new ArrayList<BookDetails>();
		Assertions.assertNotNull(bookDetails);
	}
	
	@Test
	public void testSearchBook() {
	BookDetails bookDetails = new BookDetails();
	Assertions.assertNotNull(bookDetails);
	}
	
	@Test
	public void testRemoveBook() {
	Boolean remove = dao.removeBook(153);
	Assertions.assertTrue(remove);
	}
	
	@Test
	public void testRequestBook() {
	Boolean request = dao.bookRequest(100, 100);
	Assertions.assertTrue(request);
	}
	
	
	@Test
	public void testIssueBook() {
	Boolean issue = dao.issueBook(15);
	Assertions.assertTrue(issue);
	}
	
	@Test
	public void testChangePassword() {
		Boolean change = dao.changePassword("pravalika@gmail.com", "Pravalika1@", "Pravs1@");
		Assertions.assertTrue(change);
	}
	
	@Test
	public void testReturnBook() {
	Boolean request = dao.bookReturn(100, 100);
	Assertions.assertTrue(request);
	}
	
//	@Test
//	public void testReceiveBook() {
//	Boolean receive = dao.receiveBook(15);
//	Assertions.assertTrue(receive);
//	}
}
