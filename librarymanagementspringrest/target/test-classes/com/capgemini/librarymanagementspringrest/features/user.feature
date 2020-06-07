Feature: User operations

Scenario Outline: User Should be able to login

Given User is on login page
When user gave <emailId>, <password>
Then user should be logged in

Examples:
|emailId|password|
|"srika@gmail.com"|"Srika1@"|

Scenario Outline: User should be able to request book

Given User is on requested page
When User gave request <id>,<bookId>
Then request placed successfully

Examples:
|id|bookId|
|4|12|

Scenario Outline: User should be able to return book

Given User is on return page
When User gave return <id>,<bookId>
Then book return to admin successfully

Examples:
|id|bookId|
|4|12|