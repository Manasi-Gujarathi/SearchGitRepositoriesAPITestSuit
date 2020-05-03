package com.git.apis.search.repositories.stepdefinations;

import java.io.IOException;
import java.util.Map;

import com.git.apis.global.exception.CustomException;
import com.git.apis.search.repositories.testcases.SearchGitRepoTc4;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class SearchGitRepoForMultipleLanguages {

Map<String,String> res;	
SearchGitRepoTc4 searchGitRepoObj=new SearchGitRepoTc4();	

	@Given("User wants to search github repositories for language {string} and  {string} and records are sorted as per {string} in {string} order")
	public void user_wants_to_search_github_repositories_for_language_and_and_records_are_sorted_as_per_in_order(String lang1, String lang2, String sortFilter, String orderBy) throws IOException, CustomException {
		res=searchGitRepoObj.searchRepositoryforMultipleLanguage(lang1,lang2,sortFilter,orderBy);
	}
	
	@Then("verify user gets successful response for the search query for multiple languages")
	public void verify_user_gets_successful_response_for_the_search_query_for_multiple_languages() {
		searchGitRepoObj.verifyStatusCode(res);
	}
	
	@Then("verify that given languages {string} or {string} are present in repositories")
	public void verify_that_given_languages_or_are_present_in_repositories(String lang1, String lang2) {
		searchGitRepoObj.verifyLanguagesPresent(lang1, lang2, res);
	}
	
	@Then("repositories are sorted on fork count and ordered in {string} order as expected for languages search")
	public void repositories_are_sorted_on_fork_count_and_ordered_in_order_as_expected_for_languages_search(String orderBy) {
		searchGitRepoObj.verifySortingOnForksAsPerSelectedOrder(orderBy,res);
	}


	}
