package com.capgemini.librarymanagementsystem.dao;

import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;

public interface UserDAO {
	UserInfo userLogin(String email,String password);
	BookDetails search(int bookId);
	RequestInfo bookRequest(UserInfo userdetails,BookDetails bookdetails);
	RequestInfo bookReturn(UserInfo userdetails,BookDetails bookdetails);
}
