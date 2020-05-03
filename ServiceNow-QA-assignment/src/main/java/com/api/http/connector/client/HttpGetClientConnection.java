package com.api.http.connector.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.git.apis.global.exception.CustomException;

public class HttpGetClientConnection extends HttpClientConnection {
	static Logger log = LogManager.getLogger();

	@Override
	public HttpResponse sendHttpRequest() throws CustomException {
		
		// set request method
		this.setMethod("GET");
		
		// set query params
		if (this.getRequest().queryParams != null) {
			String queryParams = "";

			for (Map.Entry<String, String> entry : this.getRequest().getQueryParams().entrySet()) {
				queryParams = queryParams + "&" + entry.getKey() + "=" + entry.getValue();
			}
			this.initiateConnection(this.getBaseURL() + "?" + queryParams);

		} else {
			this.initiateConnection(this.getBaseURL());
		}
		// TODO Auto-generated method stub
		this.getConn().setRequestProperty("Accept-Encoding", "application/json");

		// Set request header
		if (this.getRequest().requestHeader != null) {
			for (Map.Entry<String, String> entry : this.getRequest().getRequestHeader().entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}


		try {
			int respCode = this.getConn().getResponseCode();
			String responseJson = "";
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				responseJson = responseJson + output;
			}

			Map<String, String> responseHeader = new HashMap<String, String>();

			for (Map.Entry<String, List<String>> entry : getConn().getHeaderFields().entrySet()) {
				responseHeader.put(entry.getKey(), entry.getValue().get(0));
			}
			log.info("Output from Server ....\n" + responseJson);
			this.setResponse(new HttpResponse(Integer.toString(respCode), responseHeader, responseJson));

		} catch (IOException e) {
			
			throw new CustomException("CONNECTION_ERROR", "Error occured while connecting server via HTTPs", e);
		}catch (Exception e) {
			throw new CustomException("INTERNAL_ERROR", "Exception occured while establishing connection to server", e);
		}finally {
			this.getConn().disconnect();
		}
		return this.getResponse();
	}

}
