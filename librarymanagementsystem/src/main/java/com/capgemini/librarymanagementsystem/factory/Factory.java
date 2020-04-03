package com.capgemini.librarymanagementsystem.factory;

import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.dao.AdminDAOImpl;
import com.capgemini.librarymanagementsystem.dao.UserDAOImpl;
import com.capgemini.librarymanagementsystem.dto.AdminInfo;
import com.capgemini.librarymanagementsystem.dto.BookDetails;
import com.capgemini.librarymanagementsystem.dto.UserInfo;
import com.capgemini.librarymanagementsystem.dao.UserDAO;
import com.capgemini.librarymanagementsystem.service.AdminService;
import com.capgemini.librarymanagementsystem.service.AdminServiceImple;
import com.capgemini.librarymanagementsystem.service.UserService;
import com.capgemini.librarymanagementsystem.service.UserServiceImpl;

public class Factory {
	
	public static AdminInfo getAdminDetails() {
		return new AdminInfo();
	}

	public static BookDetails getBookDetails() {
		return new BookDetails();
	}

	public static UserInfo getUser() {
		return new UserInfo();
	}
	public static AdminDAO getAdminDAO() {
	return new AdminDAOImpl();	
	}
	public static AdminService getServiceAdmin() {
		return new AdminServiceImple();
	}
	public static UserDAO getUserDAOInterface() {
		return new UserDAOImpl();	
		}
		public static UserService getUserService() {
			return new UserServiceImpl();
		}
}
