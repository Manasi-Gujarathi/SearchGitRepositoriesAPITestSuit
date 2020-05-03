package com.git.apis.search.repositories.testcases;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
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

public class SearchGitRepoTc3 {

	HttpConnectionFactory factory = new HttpConnectionFactory();
	HttpClientConnection client = factory.getHttpConnection("GET");
	String statusCode;
	String BaseURI;
	Logger log = LogManager.getLogger();

	public Map<String, String> searchRepositoryWithGivenUsernameAndDate(String userName, String date)
			throws  CustomException {
		Properties prop = ConfigUtil.getProperties();

		BaseURI = ConfigUtil.getBaseURI();
		Map<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put(prop.getProperty("searchcriteria"), prop.getProperty("userQualifier") + ":" + userName + " "
				+ prop.getProperty("createdDate") + ":>" + date);
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

	public void verifyUserNameAsCreator(String userName, Map<String, String> response) {
		SearchResult responseObj  = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		for (Item i : responseObj.getItems()) {
			log.info("Full name is:" + i.getOwner().getLogin());
			Assert.assertTrue("Given name is not present as login for repository",
					StringUtils.containsIgnoreCase(i.getOwner().getLogin(), userName));
		}

	}

	public void verifyDateFilter(String date, Map<String, String> response) {
		Object obj = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		SearchResult responseObj = (SearchResult) obj;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Date expectedDate;
		try {
			expectedDate = dateFormat.parse(date);
			log.info("Expected date is:" + expectedDate.toString());
			for (Item i : responseObj.getItems()) {
				Date actualdate = dateFormat.parse(i.getCreatedAt());
				log.info("Actual date is:" + actualdate.toString());
				Assert.assertTrue("Created date is not after specified date", actualdate.after(expectedDate));
			}
		} catch (ParseException e) {
			log.error("Parsing exception for dateformat:" + e.getStackTrace());
		}
	}

	public void verifySortingasperSelectedOrder(String orderBy, HashMap<String, String> response) {
		Object obj = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		SearchResult responseObj = (SearchResult) obj;
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
