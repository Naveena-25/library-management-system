package com.capgemini.librarymanagementsystem.db;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.librarymanagementsystem.dto.AdminInfo;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;

public class DataBase {

	public static final List<AdminInfo> ADMINDETAILS = new LinkedList<AdminInfo>();
	
	public static final List<UserInfo> USER_INFOS = new LinkedList<UserInfo>();
	public static final List<BookDetails> BOOK_DETAILS = new LinkedList<BookDetails>();
	public static final List<RequestInfo> REQUEST_INFOS = new LinkedList<RequestInfo>();

}
