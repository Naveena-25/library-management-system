Feature: After Admin Login he performs Operations such as addUser, addBook, removeBook, issueBook, receiveBook 

#Scenario Outline: User login with valid credentials 

Background:

Given Admin is on login page
When Admin gives "admin@gmail.com", "Admin1@" 
Then Admin should be logged in


Scenario Outline: User Registration

Given Admin is on Registration page
When Admin gives User Details <name>, <emailId>, <password>, <mobileNumber>, <role>
Then User Registered Successfully

Examples:

|name|emailId|password|mobileNumber|role|
|"Aashrith"|"ashrith@gmail.com"|"Aashrith1@"|"8881174646"|"user"|


Scenario Outline: Adding New Book to Library

Given Admin is on Add Book page
When Admin gives Book Details  <bookName>, <author>, <publisher>
Then Book Added Successfully

Examples:

|bookName|author|publisher|
|"java"|"balaguruswamy"|"java"|


Scenario Outline: Removing a Book

Given Admin is on Delete Book page
When Admin gives Book Id <bookId>
Then Book Removed From Library Successfully

Examples:

|bookId|
|30|


Scenario Outline: Issuing a Book

Given Admin is on Issue Book page
When Admin gives valid Request Id to Issue <rId>
Then Book Issued Successfully

Examples:

|rId|
|39|


Scenario Outline: Receiving a Book

Given Admin is on Receive Book page
When Admin gives valid Request Id to Receive <rId>
Then Book Received Successfully

Examples:

|rId|
|39|