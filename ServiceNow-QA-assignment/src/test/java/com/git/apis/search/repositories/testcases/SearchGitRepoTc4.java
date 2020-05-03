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

public class SearchGitRepoTc4 {
	HttpConnectionFactory factory = new HttpConnectionFactory();
	HttpClientConnection client = factory.getHttpConnection("GET");
	String statusCode;
	String BaseURI;
	Logger log = LogManager.getLogger();

	public Map<String, String> searchRepositoryforMultipleLanguage(String lang1, String lang2, String sortFilter,
			String orderBy) throws  CustomException {
		Properties prop = ConfigUtil.getProperties();
		BaseURI = ConfigUtil.getBaseURI();
		Map<String, String> queryParam = new LinkedHashMap<>();
		queryParam.put(prop.getProperty("searchcriteria"), prop.getProperty("languageQualifier") + ":" + lang1 + " "
				+ prop.getProperty("languageQualifier") + ":" + lang2);
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

	public void verifyLanguagesPresent(String lang1, String lang2, Map<String, String> response) {
		SearchResult responseObj = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		for (Item i : responseObj.getItems()) {
			log.info("Language for repository is:" + i.getLanguage().toString());
			Assert.assertTrue("Specified languages are not present:", i.getLanguage().toString().equalsIgnoreCase(lang1)
					|| i.getLanguage().toString().equalsIgnoreCase(lang2));
		}
	}

	public void verifySortingOnForksAsPerSelectedOrder(String orderBy, Map<String, String> response) {
		Object obj = OperationUtil.getPojoClassObject(response.get("Response body"), SearchResult.class);
		SearchResult responseObj = (SearchResult) obj;
		if (orderBy.equalsIgnoreCase("desc")) {
			int temp = responseObj.getItems().get(0).getForks();
			for (Item i : responseObj.getItems()) {
				log.info("Desc order is:" + i.getForks());
				if (temp >= i.getForks()) {
					temp = i.getForks();
				} else {
					Assert.fail("Repositories are not sorted in descending order on basis of their forks");
				}
			}
		} else {
			int temp = responseObj.getItems().get(0).getForks();
			;
			for (Item i : responseObj.getItems()) {
				log.info("Ascending order is:" + i.getForks());
				if (temp >= i.getForks()) {
					temp = i.getForks();
				} else {
					Assert.fail("Repositories are not sorted in ascending order on basis of their forks");
				}
			}
		}
	}
}
