package com.capgemini.librarymanagementspringrest.stepdefinitions;

import org.junit.jupiter.api.Assertions;

import com.capgemini.librarymanagementspringrest.dao.LibraryDAO;
import com.capgemini.librarymanagementspringrest.dao.LibraryDAOImpl;
import com.capgemini.librarymanagementspringrest.dto.BookDetails;
import com.capgemini.librarymanagementspringrest.dto.LibraryUsers;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class steps {	
	
	private LibraryDAO dao = new LibraryDAOImpl() ;
	LibraryUsers users;
	BookDetails bookDetails;
	boolean status;
	
	@Given("^Admin is on login page$")
	public void admin_is_on_login_page() throws Throwable {
	   users = new LibraryUsers();
	}

	@When("^Admin gives \"([^\"]*)\", \"([^\"]*)\"$")
	public void admin_gives(String arg1, String arg2) throws Throwable {
	   users = dao.userLogin(arg1, arg2);
	}

	@Then("^Admin should be logged in$")
	public void admin_should_be_logged_in() throws Throwable {
		Assertions.assertNotNull(users);
	}


@Given("^Admin is on Registration page$")
public void admin_is_on_Registration_page() throws Throwable {
   users = new LibraryUsers();
}

	@When("^Admin gives User Details \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
	public void admin_gives_User_Details(String arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
		users.setEmailId(arg1);
		users.setName(arg2);
		users.setPassword(arg3);
		users.setMobileNumber(arg4);
		users.setRole(arg5);

		status = dao.addUser(users);
	    
	}

	@Then("^User Registered Successfully$")
	public void user_Registered_Successfully() throws Throwable {
		Assertions.assertTrue(status);
	}

	@Given("^Admin is on Add Book page$")
	public void admin_is_on_Add_Book_page() throws Throwable {
		bookDetails = new BookDetails();
	}

	@When("^Admin gives Book Details  \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"$")
	public void admin_gives_Book_Details(String arg1, String arg2, String arg3) throws Throwable {
	   bookDetails.setBookName(arg1);
	   bookDetails.setAuthor(arg2);
	   bookDetails.setPublisher(arg3);
	   status = dao.addBook(bookDetails);
	}

	@Then("^Book Added Successfully$")
	public void book_Added_Successfully() throws Throwable {
		Assertions.assertTrue(status);
	}

	@Given("^Admin is on Delete Book page$")
	public void admin_is_on_Delete_Book_page() throws Throwable {
	
	}

	@When("^Admin gives Book Id (\\d+)$")
	public void admin_gives_Book_Id(int arg1) throws Throwable {
		status = dao.removeBook(arg1);
	}

	@Then("^Book Removed From Library Successfully$")
	public void book_Removed_From_Library_Successfully() throws Throwable {
		Assertions.assertTrue(status);
	}

	@Given("^Admin is on Issue Book page$")
	public void admin_is_on_Issue_Book_page() throws Throwable {
		
	}

	@When("^Admin gives valid Request Id to Issue (\\d+)$")
	public void admin_gives_valid_Request_Id_to_Issue(int arg1) throws Throwable {
	    status = dao.issueBook(arg1);
	}

	@Then("^Book Issued Successfully$")
	public void book_Issued_Successfully() throws Throwable {
		Assertions.assertTrue(status);
	}

	@Given("^Admin is on Receive Book page$")
	public void admin_is_on_Receive_Book_page() throws Throwable {

	}

	@When("^Admin gives valid Request Id to Receive (\\d+)$")
	public void admin_gives_valid_Request_Id_to_Receive(int arg1) throws Throwable {
		status = dao.receiveBook(arg1);
	}

	@Then("^Book Received Successfully$")
	public void book_Received_Successfully() throws Throwable {
		Assertions.assertTrue(status);
	}


}
