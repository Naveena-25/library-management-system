package com.capgemini.librarymanagementsystem.db;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.librarymanagementsystem.dto.AdminInfo;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.RequestInfo;
import com.capgemini.librarymanagementsystem.dto.UserInfo;

public class DataBase {

	public static final List<AdminInfo> ADMIN_INFOS = new ArrayList<AdminInfo>();
	public static final List<UserInfo> USER_INFOS = new ArrayList<UserInfo>();
	public static final List<BookDetails> BOOK_DETAILS = new ArrayList<BookDetails>();
	public static final List<RequestInfo> REQUEST_INFOS = new ArrayList<RequestInfo>();

	public static void addToDataBase() {
		ADMIN_INFOS.add(new AdminInfo("admin@gmail.com", "Admin1@"));

		USER_INFOS.add(new UserInfo(101, "naveena", "naveena@gmail.com", "Naveena@25", "7865454321", 0, 0));
		USER_INFOS.add(new UserInfo(102, "priya", "priya@gmail.com", "Priya1@", "9771234641", 3, 5));
		USER_INFOS.add(new UserInfo(103, "sri", "sri@gmail.com", "Srika1@", "9234554641", 1, 5));

		BOOK_DETAILS.add(new BookDetails(101, "Java", "bala guruswamy", "Tata McGraw-Hill", true));
		BOOK_DETAILS.add(new BookDetails(102, "EveryOne Has A Story", "Savi Sharma", "Westland Publisher", false));
		BOOK_DETAILS.add(new BookDetails(103, "Maha Bharat", "Vyasa", "Bharatiya Vidya Bhavan", true));
		BOOK_DETAILS.add(new BookDetails(104, "C", "Bala Guruswamy", "Tata McGraw-Hill", true));
		BOOK_DETAILS.add(new BookDetails(105, "Three Mistakes Of Life", "Chetan Bhagat", "Pengiun", true));

	}

}
