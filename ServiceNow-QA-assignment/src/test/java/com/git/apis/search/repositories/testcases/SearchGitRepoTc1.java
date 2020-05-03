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

public class SearchGitRepoTc1 {
	HttpConnectionFactory factory = new HttpConnectionFactory();
	HttpClientConnection client = factory.getHttpConnection("GET");
	String statusCode;
	String baseURI;
	Logger log = LogManager.getLogger();

	public Map<String, String> searchRepositoryforForkCount(String forkCount, String sortFilter, String orderBy)
			throws CustomException {
		Properties prop = ConfigUtil.getProperties();
		baseURI = ConfigUtil.getBaseURI();

		Map<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put(prop.getProperty("searchcriteria"), prop.getProperty("forkCount") + ":>=" + forkCount);
		queryParam.put(prop.getProperty("sortby"), sortFilter);
		queryParam.put(prop.getProperty("orderby"), orderBy);
		queryParam.put(prop.getProperty("numberofrecords"), prop.getProperty("resultCount"));

		client.setBaseURL(baseURI);
		client.setQueryParam(queryParam);

		client.sendHttpRequest();
		log.info("Base URL is:" + client.getBaseURL());

		Map<String, String> response = new HashMap<String, String>();
		response.put("Status Code", client.getHttpResponseStatusCode());
		response.put("Response body", client.getHttpResponseBody());

		return response;
	}

	public void verifyStatusCode(HashMap<String, String> response) {
		Assert.assertTrue("Response code do not match with expected value",
				OperationUtil.verifyStatusCode(response.get("Status Code"), "200"));
	}

	public void verifyForkCount(String forkCount, HashMap<String, String> response) {
		Object obj = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		SearchResult responseObj = (SearchResult) obj;
		for (Item i : responseObj.getItems()) {
			log.info("Fork count for repository is:" + i.getForks());
			Assert.assertTrue("Fork count is less than expected value", i.getForks() >= Integer.parseInt(forkCount));
		}
	}

	public void verifySortingasperSelectedOrder(String orderBy, HashMap<String, String> response) {
		SearchResult responseObj = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		if (orderBy.equalsIgnoreCase("desc")) {
			int temp = responseObj.getItems().get(0).getStargazersCount();
			for (Item i : responseObj.getItems()) {
				log.info("Desc order is:" + i.getStargazersCount());
				if (temp >= i.getStargazersCount()) {
					temp = i.getStargazersCount();
				} else {
					Assert.fail("Repositories are not sorted in descending order on basis of their stars");
				}
			}
		} else {
			int temp = responseObj.getItems().get(0).getStargazersCount();
			for (Item i : responseObj.getItems()) {
				log.info("Ascending order is:" + i.getStargazersCount());
				if (temp >= i.getStargazersCount()) {
					temp = i.getStargazersCount();
				} else {
					Assert.fail("Repositories are not sorted in ascending order on basis of their stars");
				}
			}
		}
	}
}
