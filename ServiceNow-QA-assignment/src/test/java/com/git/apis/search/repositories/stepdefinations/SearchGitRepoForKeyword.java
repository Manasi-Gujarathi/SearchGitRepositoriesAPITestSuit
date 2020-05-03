package com.git.apis.search.repositories.stepdefinations;

import java.io.IOException;
import java.util.Map;

import com.git.apis.global.exception.CustomException;
import com.git.apis.search.repositories.testcases.SearchGitRepoTc2;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SearchGitRepoForKeyword {
	
SearchGitRepoTc2 searchGitRepoObj=new SearchGitRepoTc2();
Map<String, String> res;
	
	@Given("User wants to search github repositories having given search keyword {string}in name and records are sorted as per {string} in {string} order")
		public void user_wants_to_search_github_repositories_having_given_search_keyword_in_name_and_records_are_sorted_as_per_in_order(String keyWord, String sortCriteria, String orderBy) throws IOException, CustomException {
		res=searchGitRepoObj.searchRepositoryByKeyword(keyWord,sortCriteria,orderBy);
	}

	@Then("verify that search keyword {string} is present in repositories name")
	public void verify_that_search_keyword_is_present_in_repositories_name(String keyword) {
		searchGitRepoObj.verifyKeywordInRepoName(keyword,res);
	}

	@Then("verify user gets successful response for the search query on keyword")
	public void verify_user_gets_successfull_response_for_the_search_query() {
		searchGitRepoObj.verifyStatusCode(res);
	}
	
	@Then("repositories are sorted with most stared and ordered in {string} order as expected for search keyword")
	public void repositories_are_sorted_with_most_stared_and_ordered_in_order_as_expected_for_search_keyword(String orderBy) {
		searchGitRepoObj.verifySortingasperSelectedOrder(orderBy,res);
	}
}
