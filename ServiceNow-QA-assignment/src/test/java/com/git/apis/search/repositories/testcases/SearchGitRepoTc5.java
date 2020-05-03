package com.git.apis.search.repositories.testcases;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.api.http.connector.client.HttpClientConnection;
import com.api.http.connector.client.factory.HttpConnectionFactory;
import com.git.apis.global.exception.CustomException;
import com.git.apis.search.repositories.pojo.Item;
import com.git.apis.search.repositories.pojo.SearchResult;
import com.git.apis.search.utils.OperationUtil;
import com.git.apis.search.utils.ConfigUtil;

public class SearchGitRepoTc5 {

	HttpConnectionFactory factory = new HttpConnectionFactory();
	HttpClientConnection client = factory.getHttpConnection("GET");
	String statusCode;
	String BaseURI;
	Logger log = LogManager.getLogger();

	public Map<String, String> searchRepositoriesOwnedByGitHubAndSort(String orgName, String archivalProperty,
			String sortFilter, String orderBy) throws  CustomException {
		Properties prop = ConfigUtil.getProperties();
		BaseURI = ConfigUtil.getBaseURI();
		Map<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put(prop.getProperty("searchcriteria"), prop.getProperty("organization") + ":" + orgName + " "
				+ prop.getProperty("archivalQualifier") + ":" + archivalProperty);
		queryParam.put(prop.getProperty("sortby"), sortFilter);
		queryParam.put(prop.getProperty("orderby"), orderBy);
		queryParam.put(prop.getProperty("numberofrecords"), prop.getProperty("resultCount"));

		client.setBaseURL(BaseURI);
		client.setQueryParam(queryParam);
		client.sendHttpRequest();
		log.info("Base URL is:" + client.getBaseURL());
		Map<String, String> response = new HashMap<String, String>();
		response.put("Status Code", client.getHttpResponseStatusCode());
		response.put("Response body", client.getHttpResponseBody());
		return response;
	}

	public void verifyStatusCode(Map<String, String> response) {
		Assert.assertTrue("Response code do not match with expected value",
				OperationUtil.verifyStatusCode(response.get("Status Code"), "200"));
	}

	public void verifyArchivalProperty(String archivalProperty, Map<String, String> response) {
		
		SearchResult responseObj = (SearchResult) OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);;
		for (Item i : responseObj.getItems()) {
			log.info("Repository's archival value is :" + i.getArchived());
			Assert.assertTrue("Specified archival property is not present:",
					i.getArchived().toString().equals(archivalProperty));
		}
	}

	public void verifyRepositoriesAreOwnedByGithub(String orgName, Map<String, String> response) {
		SearchResult responseObj = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		for (Item i : responseObj.getItems()) {
			log.info("Owner name is:" + i.getOwner().getLogin());
			Assert.assertTrue("Repository is not owned by GitHub", i.getOwner().getLogin().equalsIgnoreCase(orgName));
		}

	}

	public void verifySortingOnStarsAsPerSelectedOrder(String orderBy, Map<String, String> response) {
		Object obj = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		SearchResult responseObj = (SearchResult) obj;
		if (orderBy.equalsIgnoreCase("desc")) {
			int temp = responseObj.getItems().get(0).getStargazersCount();
			for (Item i : responseObj.getItems()) {
				log.info("Desc order is:" + i.getStargazersCount());
				if (temp >= i.getStargazersCount()) {
					temp = i.getStargazersCount();
				} else {
					Assert.fail("Repositories are not sorted in descending order on basis of their forks");
				}
			}
		} else {
			int temp = responseObj.getItems().get(0).getStargazersCount();
			;
			for (Item i : responseObj.getItems()) {
				log.info("Ascending order is:" + i.getStargazersCount());
				if (temp <= i.getStargazersCount()) {
					temp = i.getStargazersCount();
				} else {
					Assert.fail("Repositories are not sorted in ascending order on basis of their forks");
				}
			}
		}
	}
}
