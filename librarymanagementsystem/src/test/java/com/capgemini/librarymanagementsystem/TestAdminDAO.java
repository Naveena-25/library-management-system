package com.capgemini.librarymanagementsystem;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.dao.AdminDAOImpl;
import com.capgemini.librarymanagementsystem.dto.AdminInfo;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.UserInfo;


public class TestAdminDAO {
	
	AdminDAO dao = new AdminDAOImpl();
	
	@Test
	public void testAddUser() {
	UserInfo info = new UserInfo();
	info.setUserId(101);
	info.setUserName("Pravalika");
	info.setEmailId("pravalika@gmail.com");
	info.setPassword("Pravalika1@");
	info.setMobileNumber("9865432123");
	info.setNoOfBooksBorrowed(1);
	info.setFine(0);
	boolean status = dao.addUser(info);
	Assertions.assertTrue(status);
	}

	@Test
	public void testViewUsers() {
		List<AdminInfo>info = new ArrayList<AdminInfo>();
		Assertions.assertNotNull(info);
	}
	@Test
	public void testAddBook() {
	BookDetails bookDetails = new BookDetails();
	bookDetails.setBookId(153);
	bookDetails.setBookName("Java");
	bookDetails.setAuthor("Bala Guruswamy");
	bookDetails.setPublisherName("McGraw Hill Education");
	bookDetails.isBookAvailable();
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
	BookDetails bookDetails = dao.search(153);
	Assertions.assertNotNull(bookDetails);
	}
	
	@Test
	public void testRemoveBook() {
	Boolean remove = dao.removeBook(153);
	Assertions.assertTrue(remove);
	}
	
	
	@Test
	public void testIssueBook() {
	Boolean receive = dao.issueBook(101, 112);
	Assertions.assertTrue(receive);
	}
	
	@Test
	public void testReceiveBook() {
	Boolean receive = dao.receiveBook(101, 112);
	Assertions.assertTrue(receive);
	}
	
}
