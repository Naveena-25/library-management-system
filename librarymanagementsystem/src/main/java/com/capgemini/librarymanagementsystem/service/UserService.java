package com.capgemini.librarymanagementsystem.service;

import com.capgemini.librarymanagementsystem.dto.BookDetails;

public interface UserService {

	boolean userLogin(String emailId, String password);

	boolean changePassword(String emailId, String oldPassword, String newPassword);

	BookDetails search(int bookId);

	boolean bookRequest(int userId, int bookId);

	boolean bookReturn(int userId, int bookId);

}
