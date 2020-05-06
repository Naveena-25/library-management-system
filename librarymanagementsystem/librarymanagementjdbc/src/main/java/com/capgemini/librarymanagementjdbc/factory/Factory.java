package com.capgemini.librarymanagementjdbc.factory;

import com.capgemini.librarymanagementjdbc.dao.LibraryDAO;
import com.capgemini.librarymanagementjdbc.dao.LibraryDAOImpl;
import com.capgemini.librarymanagementjdbc.service.LibraryService;
import com.capgemini.librarymanagementjdbc.service.LibraryServiceImpl;

public class Factory {
	public static LibraryDAO getLibraryDAO() {
		return new LibraryDAOImpl();	
		}
		public static LibraryService getLibraryService() {
			return new LibraryServiceImpl();
		}
}
