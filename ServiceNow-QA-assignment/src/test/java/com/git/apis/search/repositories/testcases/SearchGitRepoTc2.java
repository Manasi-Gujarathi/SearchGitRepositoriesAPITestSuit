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

public class SearchGitRepoTc2 {
	HttpConnectionFactory factory = new HttpConnectionFactory();
	HttpClientConnection client = factory.getHttpConnection("GET");
	String statusCode;
	String BaseURI;
	Logger log = LogManager.getLogger();

	public Map<String, String> searchRepositoryByKeyword(String keyword, String sortFilter, String orderBy)
			throws  CustomException {
		Properties prop = ConfigUtil.getProperties();
		BaseURI = ConfigUtil.getBaseURI();

		Map<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put(prop.getProperty("searchcriteria"), keyword + prop.getProperty("repositoryname"));
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

	public void verifyKeywordInRepoName(String keyword, Map<String, String> response) {
		SearchResult responseObj  = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		for (Item i : responseObj.getItems()) {
			log.info("Repository name is:" + i.getName());
			Assert.assertTrue("Keyword is not present in repository name",
					StringUtils.containsIgnoreCase(i.getName(), keyword));
		}

	}

	public void verifySortingasperSelectedOrder(String orderBy, Map<String, String> response) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		Object obj = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		SearchResult responseObj = (SearchResult) obj;
		try {
			if (orderBy.equalsIgnoreCase("desc")) {
				String temp = responseObj.getItems().get(0).getUpdatedAt();
				Date tempDate;
				tempDate = dateFormat.parse(temp);
				for (Item i : responseObj.getItems()) {
					log.info("Desc order is:" + i.getUpdatedAt());
					if (tempDate.compareTo(dateFormat.parse(i.getUpdatedAt())) >= 0) {
						tempDate = dateFormat.parse(i.getUpdatedAt());
					} else {
						Assert.fail("Repositories are not sorted in descending order on basis of their updated date");
					}
				}
			} else {
				String temp = responseObj.getItems().get(0).getUpdatedAt();
				Date tempDate = dateFormat.parse(temp);
				for (Item i : responseObj.getItems()) {
					log.info("Ascending order is:" + i.getUpdatedAt());
					if (tempDate.compareTo(dateFormat.parse(i.getUpdatedAt())) <= 0) {
						tempDate = dateFormat.parse(i.getUpdatedAt());
					} else {
						Assert.fail("Repositories are not sorted in ascending order on basis of their updated date");
					}
				}
			}
		} catch (ParseException e) {
			log.error("Dateformat exception :" + e.getStackTrace());
		}
	}
}
