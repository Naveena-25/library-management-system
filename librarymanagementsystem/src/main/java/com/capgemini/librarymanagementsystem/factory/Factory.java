package com.capgemini.librarymanagementsystem.factory;

import com.capgemini.librarymanagementsystem.dao.AdminDAO;
import com.capgemini.librarymanagementsystem.dao.AdminDAOImpl;
import com.capgemini.librarymanagementsystem.dao.UserDAOImpl;
import com.capgemini.librarymanagementsystem.dao.UserDAO;
import com.capgemini.librarymanagementsystem.service.AdminService;
import com.capgemini.librarymanagementsystem.service.AdminServiceImpl;
import com.capgemini.librarymanagementsystem.service.UserService;
import com.capgemini.librarymanagementsystem.service.UserServiceImpl;

public class Factory {

	public static AdminDAO getAdminDAO() {

		return new AdminDAOImpl();
	}

	public static AdminService getAdminService() {

		return new AdminServiceImpl();
	}

	public static UserDAO getUserDAO() {

		return new UserDAOImpl();
	}

	public static UserService getUserService() {

		return new UserServiceImpl();
	}
}
