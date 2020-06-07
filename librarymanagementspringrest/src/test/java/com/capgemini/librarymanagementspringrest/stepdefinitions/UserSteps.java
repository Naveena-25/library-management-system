package com.capgemini.librarymanagementspringrest.stepdefinitions;

import org.junit.jupiter.api.Assertions;

import com.capgemini.librarymanagementspringrest.dao.LibraryDAO;
import com.capgemini.librarymanagementspringrest.dao.LibraryDAOImpl;
import com.capgemini.librarymanagementspringrest.dto.LibraryUsers;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class UserSteps {
	LibraryDAO dao = new LibraryDAOImpl();
	LibraryUsers users;
	boolean status;

@Given("^User is on login page$")
public void user_is_on_login_page() throws Throwable {
    users = new LibraryUsers();
}

@When("^user gave \"([^\"]*)\", \"([^\"]*)\"$")
public void user_gave(String arg1, String arg2) throws Throwable {
    users = dao.userLogin(arg1, arg2);
    }

@Then("^user should be logged in$")
public void user_should_be_logged_in() throws Throwable {
	Assertions.assertNotNull(users);
}

@Given("^User is on requested page$")
public void user_is_on_requested_page() throws Throwable {
  
}

@When("^User gave request (\\d+),(\\d+)$")
public void user_gave_request(int arg1, int arg2) throws Throwable {
	status = dao.bookRequest(arg1, arg2);
}

@Then("^request placed successfully$")
public void request_placed_successfully() throws Throwable {
	Assertions.assertTrue(status);  
}

@Given("^User is on return page$")
public void user_is_on_return_page() throws Throwable {
   
}

@When("^User gave return (\\d+),(\\d+)$")
public void user_gave_return(int arg1, int arg2) throws Throwable {
	status = dao.bookReturn(arg1, arg2);
}

@Then("^book return to admin successfully$")
public void book_return_to_admin_successfully() throws Throwable {
   Assertions.assertTrue(status);
}

}
