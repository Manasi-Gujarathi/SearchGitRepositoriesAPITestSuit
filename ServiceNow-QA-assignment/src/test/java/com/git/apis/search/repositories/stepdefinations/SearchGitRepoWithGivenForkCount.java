package com.git.apis.search.repositories.stepdefinations;

import java.io.IOException;
import java.util.HashMap;

import com.git.apis.global.exception.CustomException;
import com.git.apis.search.repositories.testcases.SearchGitRepoTc1;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SearchGitRepoWithGivenForkCount {

HashMap<String,String> res;	
SearchGitRepoTc1 searchGitRepoObj=new SearchGitRepoTc1();	

	@Given("User wants to search github repositories with search criteria where fork count is greater than {string} and records are sorted as per {string} in {string} order")
	public void user_wants_to_search_github_repositories_with_search_criteria_where_fork_count_is_greater_than_and_records_are_sorted_as_per_in_order(String forkCount, String sortCriteria, String orderBy)  throws IOException, CustomException {
	res=(HashMap<String, String>) searchGitRepoObj.searchRepositoryforForkCount(forkCount,sortCriteria,orderBy);
	}
	
	
	@Then("verify that fork count is greater than {string}")
	public void verify_that_fork_count_is_greater_than(String forkCount) {
		searchGitRepoObj.verifyForkCount(forkCount,res);
	}
	
	@Then("repositories are sorted with most stared and ordered in {string} order as expected")
	public void repositories_are_sorted_with_most_stared_and_ordered_in_order_as_expected(String orderBy) {
		searchGitRepoObj.verifySortingasperSelectedOrder(orderBy,res);
	}
	
	@Then("verify user gets successful response for the search criteria on forks")
	public void verify_user_gets_successfull_response_for_the_search_query() {
		searchGitRepoObj.verifyStatusCode(res);
	}
	}
