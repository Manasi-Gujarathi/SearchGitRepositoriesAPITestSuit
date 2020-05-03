package com.git.apis.search.repositories.stepdefinations;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import com.git.apis.global.exception.CustomException;
import com.git.apis.search.repositories.testcases.SearchGitRepoTc3;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

	public class SearchGitRepoForSpecificUser {
	SearchGitRepoTc3 searchGitRepoObj=new SearchGitRepoTc3();
	Map<String, String> res;
		
	@Given("User wants to search github repositories for specific user with name {string} and created after date {string}")
	public void user_wants_to_search_github_repositories_for_specific_user_with_name_and_created_after_date(String userName, String date) throws IOException, CustomException {
	res=searchGitRepoObj.searchRepositoryWithGivenUsernameAndDate(userName, date);
	}
	
	@Then("verify user gets successful response for the search query for username")
	public void verify_user_gets_successful_response_for_the_search_query_for_username() {
		searchGitRepoObj.verifyStatusCode(res);
	}
	
	@Then("verify that given username {string} is present as creator for the searched repositories")
	public void verify_that_given_username_is_present_as_creator_for_the_searched_repositories(String userName) {
		searchGitRepoObj.verifyUserNameAsCreator(userName,res);
	}
	
	@Then("verify that created date for repositories is after given date as {string}")
	public void verify_that_created_date_for_repositories_is_after_given_date_as(String date) throws ParseException {
		searchGitRepoObj.verifyDateFilter(date,res);
	}
	
	

}
