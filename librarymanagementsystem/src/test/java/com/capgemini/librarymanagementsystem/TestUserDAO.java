package com.capgemini.librarymanagementsystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.capgemini.librarymanagementsystem.dao.UserDAO;
import com.capgemini.librarymanagementsystem.dao.UserDAOImpl;

public class TestUserDAO {

	UserDAO dao1 = new UserDAOImpl();
	@Test
	public void testChangePassword() {
		Boolean change = dao1.changePassword("pravalika@gmail.com", "Pravalika1@", "pravs1@");
		Assertions.assertTrue(change);
	}
	@Test
	public void testRequestBook() {
	Boolean request = dao1.bookRequest(101, 112);
	Assertions.assertTrue(request);
	}
	
	@Test
	public void testReturnBook() {
	Boolean request = dao1.bookReturn(101, 112);
	Assertions.assertTrue(request);
	}
}
