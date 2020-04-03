package com.capgemini.librarymanagementsystem.service;

import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;

public interface UserService {
	UserInfo userLogin(String email,String password);
	BookDetails search(int bookId);
	RequestInfo bookRequest(UserInfo userDetails, BookDetails bookDetails);
	RequestInfo bookReturn(UserInfo userdetails, BookDetails bookdetails);
}
