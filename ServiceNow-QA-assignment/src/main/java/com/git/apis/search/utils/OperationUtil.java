package com.git.apis.search.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.git.apis.global.pojo.GlobalRestResponse;

public class OperationUtil {
	static Logger log = LogManager.getLogger();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends GlobalRestResponse> T getPojoClassObject(String jsonResponse, Class classname) {
		ObjectMapper mapper = new ObjectMapper();
		// JSON from String to Object
		T result = null;
		try {
			result = (T) mapper.readValue(jsonResponse, classname);
		} catch (JsonProcessingException e) {
			log.error(e.getStackTrace());
		}
		return result;
	}

	public static boolean verifyStatusCode(String responseCode, String expectedResponseCode) {
		//.String code = response.get("Status Code");
		log.info("Status code is:" + responseCode);
		return responseCode.equals(expectedResponseCode);
	}
}
