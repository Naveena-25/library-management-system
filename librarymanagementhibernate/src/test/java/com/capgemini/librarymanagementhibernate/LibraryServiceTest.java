package com.capgemini.librarymanagementhibernate;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.librarymanagementhibernate.dto.BookDetails;
import com.capgemini.librarymanagementhibernate.dto.LibraryUsers;
import com.capgemini.librarymanagementhibernate.dto.RequestInfo;
import com.capgemini.librarymanagementhibernate.service.LibraryService;
import com.capgemini.librarymanagementhibernate.service.LibraryServiceImpl;



public class LibraryServiceTest {
	
	LibraryService service = new LibraryServiceImpl();

	@Test
	public void testLogin() {
		LibraryUsers userLogin = service.login("admin@gmail.com", "Admin1@");
		Assertions.assertNotNull(userLogin);
	}

	@Test
	public void testUserLogin() {
		LibraryUsers userLogin = service.userLogin("naveena@gmail.com", "Naveena@2");
		Assertions.assertNotNull(userLogin);
	}

	@Test
	public void testAddUser() {
		LibraryUsers info = new LibraryUsers();
		info.setId(120);
		info.setName("Pravali");
		info.setEmailId("pravali@gmail.com");
		info.setPassword("Pravali1@");
		info.setMobileNumber("9235432123");
		info.setRole("user");
		boolean status = service.addUser(info);
		Assertions.assertTrue(status);
	}

	@Test
	public void testViewUsers() {
		List<LibraryUsers> info = new ArrayList<LibraryUsers>();
		Assertions.assertNotNull(info);
		}

	@Test
	public void testAddBook() {
		BookDetails bookDetails = new BookDetails();
		bookDetails.setBookId(123);
		bookDetails.setBookName("Java");
		bookDetails.setAuthor("Bala Guruswamy");
		bookDetails.setPublisher("McGraw Hill Education");
		bookDetails.setAvailable(true);
		boolean status = service.addBook(bookDetails);
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
		Boolean remove = service.removeBook(123);
		Assertions.assertTrue(remove);
	}

	@Test
	public void testRequestBook() {
		Boolean request = service.bookRequest(100, 100);
		Assertions.assertTrue(request);
	}

	@Test
	public void testViewRequests() {
		List<RequestInfo> info = new ArrayList<RequestInfo>();
		Assertions.assertNotNull(info);
	}

	@Test
	public void testIssueBook() {
		Boolean issue = service.issueBook(15);
		Assertions.assertTrue(issue);
	}

	@Test
	public void testChangePassword() {
		Boolean change = service.changePassword(101, "Pravali1@", "Pravali1@");
		Assertions.assertTrue(change);
	}

	@Test
	public void testReturnBook() {
		Boolean request = service.bookReturn(101, 101);
		Assertions.assertTrue(request);
	}

	@Test
	public void testReceiveBook() {
		Boolean receive = service.receiveBook(14);
		Assertions.assertTrue(receive);
	}
}
