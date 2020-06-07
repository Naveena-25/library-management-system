package com.capgemini.librarymanagementspringrest.testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "D:\\librarymanagementsystem\\librarymanagementspringrest\\src\\test\\java\\com\\capgemini\\librarymanagementspringrest\\features", 
					glue = {"com/capgemini/librarymanagementspringrest/stepdefinitions" }, 
					dryRun = false,  
					plugin = { "pretty", "html:target/cucumber" }, 
					monochrome = true)
public class TestRunner {
	
	
}
