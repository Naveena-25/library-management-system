package com.capgemini.librarymanagementhibernate.factory;

import com.capgemini.librarymanagementhibernate.dao.LibraryDAO;
import com.capgemini.librarymanagementhibernate.dao.LibraryDAOimpl;
import com.capgemini.librarymanagementhibernate.service.LibraryService;
import com.capgemini.librarymanagementhibernate.service.LibraryServiceImpl;

public class Factory {
	
	public static LibraryDAO getLibraryDAO() {
		
		return new LibraryDAOimpl();
	}

	public static LibraryService getLibraryService() {
		
		return new LibraryServiceImpl();
	}
}
