package com.git.apis.search.repositories.stepdefinations;

import java.io.IOException;
import java.util.Map;

import com.git.apis.global.exception.CustomException;
import com.git.apis.search.repositories.testcases.SearchGitRepoTc5;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SearchGitRepoOwnedByGitHUB {

Map<String,String> res;	
SearchGitRepoTc5 searchGitRepoObj=new SearchGitRepoTc5();	

	@Given("User wants to search github repositories owned by {string} and archived property is {string} and records are sorted as per {string} in {string} order")
	public void user_wants_to_search_github_repositories_owned_by_and_archieved_and_records_are_sorted_as_per_in_order(String orgName, String archivalProperty, String sortFilter, String orderBy) throws IOException, CustomException {
		res=searchGitRepoObj.searchRepositoriesOwnedByGitHubAndSort(orgName, archivalProperty,sortFilter,orderBy);
	}
	
	@Then("verify user gets successful response for the search query for github owned repositories")
	public void verify_user_gets_successful_response_for_the_search_query_for_git_hub_owned_repositories() {
		searchGitRepoObj.verifyStatusCode(res);
	}
	
	@Then("verify that {string} is present in login")
	public void verify_that_is_present_in_login(String orgName) {
		searchGitRepoObj.verifyRepositoriesAreOwnedByGithub(orgName, res);
	}
	
	@Then("verify that archival property is {string} as expected")
	public void verify_that_archival_property_is_as_expected(String archivalProperty) {
		searchGitRepoObj.verifyArchivalProperty(archivalProperty, res);
	}
	
	@Then("github owned repositories are sorted on star count and ordered in {string} order as expected")
	public void github_owned_repositories_are_sorted_on_star_count_and_ordered_in_order_as_expected(String orderBy) {
		searchGitRepoObj.verifySortingOnStarsAsPerSelectedOrder(orderBy,res);
	}


	}
