package com.capgemini.librarymanagementspringrest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.librarymanagementspringrest.dto.BookDetails;
import com.capgemini.librarymanagementspringrest.dto.LibraryResponse;
import com.capgemini.librarymanagementspringrest.dto.LibraryUsers;
import com.capgemini.librarymanagementspringrest.dto.RequestInfo;
import com.capgemini.librarymanagementspringrest.service.LibraryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class LibraryRestController {
	@Autowired
	private LibraryService service;

	@PostMapping(path = "/userLogin", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse userLogin(@RequestBody Map<String, String> inputJson) {
		LibraryUsers userLogin = service.userLogin(inputJson.get("emailId"), inputJson.get("password"));
		LibraryResponse response = new LibraryResponse();

		if (userLogin != null) {
			response.setLibraryUsers(userLogin);
			response.setMessage("User Logged in Successfully");
		} else {
			response.setError(true);
			response.setMessage("User Details which was given is invalid, Unable to login");
		}
		return response;
	}

	@PostMapping(path = "/addUser", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse addUser(@RequestBody LibraryUsers libraryUsers) {
		boolean isAdded = service.addUser(libraryUsers);
		LibraryResponse response = new LibraryResponse();
		if (isAdded) {
			response.setMessage("User Added Successfully");
		} else {
			response.setError(true);
			response.setMessage("User Can't Be Registered,As Id Already Exist In the Library");
		}
		return response;
	}

	@GetMapping(path = "/getAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse getAllUsers() {
		List<LibraryUsers> userList = service.viewUsers();
		LibraryResponse response = new LibraryResponse();
		if (userList != null && !userList.isEmpty()) {
			response.setUserList(userList);
		} else {
			response.setError(true);
			response.setMessage("No Users Found in the Library");
		}

		return response;
	}

	@PostMapping(path = "/addBook", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse addBook(@RequestBody BookDetails bookDetails) {
		boolean isAdded = service.addBook(bookDetails);
		LibraryResponse response = new LibraryResponse();
		if (isAdded) {
			response.setMessage("Book Added Successfully");
		} else {
			response.setError(true);
			response.setMessage("Book Can't Be Added,As it is Already Exist In the Library");
		}
		return response;
	}

	@GetMapping(path = "/searchBook/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse searchBook(@PathVariable(name = "bookId") int bookId) {
		BookDetails bookDetails = service.search(bookId);
		LibraryResponse response = new LibraryResponse();
		if (bookDetails != null) {
			response.setBookDetails(bookDetails);
		} else {
			response.setError(true);
			response.setMessage("No Book Found in the Library With the Given Id");
		}
		return response;
	}

	@GetMapping(path = "/getAllBooks", produces = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse getAllBooks() {
		List<BookDetails> bookList = service.viewBooks();
		LibraryResponse response = new LibraryResponse();
		if (bookList != null && !bookList.isEmpty()) {
			response.setBookList(bookList);
		} else {
			response.setError(true);
			response.setMessage("No Books Found In the Library");
		}

		return response;
	}

	@PostMapping(path = "/updateBook", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse updateBook(@RequestBody BookDetails bookDetails) {
		BookDetails isUpdated = service.updateBook(bookDetails);
		LibraryResponse response = new LibraryResponse();
		if (isUpdated != null) {
			response.setMessage("Book Updated Successfully");
			response.setBookDetails(isUpdated);
		} else {
			response.setError(true);
			response.setMessage("Book Can't be Updated");
		}
		return response;
	}

	@DeleteMapping(path = "/deleteBook/{bookId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse deleteBook(@PathVariable Integer bookId) {
		boolean isDeleted = service.removeBook(bookId);
		LibraryResponse response = new LibraryResponse();
		if (isDeleted) {
			response.setMessage("Book Removed Successfully");
		} else {
			response.setError(true);
			response.setMessage("Unable To Remove The Book, As it is Not Present in the Library");
		}
		return response;
	}

	@PostMapping(path = "/requestBook/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse requestBook(@RequestBody BookDetails bookDetails, @PathVariable Integer userId) {
		boolean isRequested = service.bookRequest(userId, bookDetails.getBookId());
		LibraryResponse response = new LibraryResponse();
		if (isRequested) {
			response.setMessage("Book Request Placed Successfully");
		} else {
			response.setError(true);
			response.setMessage("Request Can't Be Placed to Admin");
		}
		return response;
	}

	@GetMapping(path = "/getAllRequests", produces = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse getAllRequests() {
		List<RequestInfo> requestList = service.viewRequests();

		LibraryResponse response = new LibraryResponse();
		if (requestList != null && !requestList.isEmpty()) {
			response.setRequestList(requestList);
		} else {
			response.setError(true);
			response.setMessage("No Books Found In the Library");
		}

		return response;
	}
	
	@GetMapping(path = "/getAllRequestedBooks", produces = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse getAllReqBook() {
		List<RequestInfo> requestList = service.viewRequests();

		LibraryResponse response = new LibraryResponse();
		if (requestList != null && !requestList.isEmpty()) {
			response.setRequestList(requestList);
		} else {
			response.setError(true);
			response.setMessage("No Books Found In the Library");
		}

		return response;
	}
	
	@GetMapping(path = "/userBooks/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse userTakenBooks(@PathVariable int userId) {
		List<RequestInfo> requestInfo = service.userBooks(userId);
		LibraryResponse response = new LibraryResponse();
		if ((requestInfo != null) && (!requestInfo.isEmpty())) {
			response.setMessage("Borrowed Books By the User");
			response.setRequestList(requestInfo);
		} else {
			response.setError(true);
			response.setMessage("No Books Are Borrowed By the User");
		}
		return response;
	}
	
	@GetMapping(path = "/getAllReturnedBooks", produces = MediaType.APPLICATION_JSON_VALUE)
	public LibraryResponse getAllReturnedBook() {
		List<RequestInfo> requestList = service.viewRequests();

		LibraryResponse response = new LibraryResponse();
		if (requestList != null && !requestList.isEmpty()) {
			response.setRequestList(requestList);
		} else {
			response.setError(true);
			response.setMessage("No Books Found In the Library");
		}

		return response;
	}


	@PostMapping(path = "/issueBook", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse issueBook(@RequestBody RequestInfo requestInfo) {
		boolean isIssued = service.issueBook(requestInfo.getrId());
		LibraryResponse response = new LibraryResponse();
		if (isIssued) {
			response.setMessage("Book Issued to the User");
		} else {
			response.setError(true);
			response.setMessage("Book Can't be Issued To the User");
		}
		return response;
	}

	@PostMapping(path = "/returnBook/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse returnBook(@RequestBody RequestInfo requestInfo,@PathVariable  Integer userId) {
		boolean isReturned = service.bookReturn(userId, requestInfo.getBookId());
		LibraryResponse response = new LibraryResponse();
		if (isReturned) {
			response.setMessage("Book Returned By the User Successfully");
		} else {
			response.setError(true);
			response.setMessage("Book Is Not Returned");
		}
		return response;
	}

	@PostMapping(path = "/receiveBook", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public LibraryResponse receiveBook(@RequestBody RequestInfo requestInfo) {
		boolean isReceived = service.receiveBook(requestInfo.getrId());
		LibraryResponse response = new LibraryResponse();
		if (isReceived) {
			response.setMessage("Book Received Successfully");
		} else {
			response.setError(true);
			response.setMessage("Unable to Receive, Invalid Request");
		}
		return response;
	}
}
