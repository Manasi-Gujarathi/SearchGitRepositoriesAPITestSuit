package com.api.http.connector.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.git.apis.global.exception.CustomException;

public abstract class HttpClientConnection {
	private HttpResponse response;
	private HttpRequest request;
	protected String baseURL;
	protected String method;
	protected HttpsURLConnection conn;

	public abstract HttpResponse sendHttpRequest() throws CustomException;

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public String getHttpResponseBody() {
		if (response == null) {
			return null;
		}
		return response.getResponseBody();
	}

	public Map<String, String> getHttpResponseHeader() {
		if (response == null) {
			return null;
		}
		return response.getResponseHeader();
	}

	public String getHttpResponseStatusCode() {
		if (response == null) {
			return null;
		}
		return response.getStatusCode();
	}

	protected void initiateConnection(String baseURI) throws CustomException {
		URL url;
		HttpsURLConnection connection = null;
		try {
			baseURL = baseURI.replace(" ", "%20");
			url = new URL(baseURL);
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Connection", "keep-alive");
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setUseCaches(false);
			if (this.getMethod() != null)
				connection.setRequestMethod(method);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw new CustomException("URL_INVALID", "URL is malformed", e);
			
		}catch (ProtocolException e) {
			// TODO: handle exception
			throw new CustomException("CONNECTION_ERROR", "Error in HTTPs connection", e);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			throw new CustomException("CONNECTION_ERROR", "Error occured while connecting server via HTTPs", e);
		}catch (Exception e) {
			throw new CustomException("INTERNAL_ERROR", "Exception occured while establishing connection to server", e);
		}

		this.setConn(connection);

	}

	public HttpResponse getResponse() {
		return response;
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public String getMethod() {
		return method;
	}

	public HttpsURLConnection getConn() {
		return conn;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	protected void setMethod(String method) {
		this.method = method;

	}

	protected void setConn(HttpsURLConnection conn) {
		this.conn = conn;
	}

	public void createHttpRequest(byte[] requestBody, Map<String, String> requestHeader,
			Map<String, String> queryParams) {
		this.request = new HttpRequest(requestBody, requestHeader, queryParams);
	}

	public void addRequestBody(byte[] requestBody) {
		if (this.request == null) {
			this.request = new HttpRequest(requestBody, null, null);
		} else
			this.request.setRequestBody(requestBody);
	}

	public void addRequestHeader(String headerElement, String headerValue) {
		if (this.request == null) {
			Map<String, String> header = new HashMap<String, String>();
			header.put(headerElement, headerValue);
			this.request = new HttpRequest(null, header, null);
		} else
			this.request.getRequestHeader().put(headerElement, headerValue);
	}

	public void setRequestHeader(Map<String, String> requestHeader) {
		if (this.request == null) {

			this.request = new HttpRequest(null, requestHeader, null);
		} else
			this.request.setRequestHeader(requestHeader);
	}

	public void addQueryParam(String key, String value) {
		if (this.request == null) {
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put(key, value);
			this.request = new HttpRequest(null, null, queryParams);
		} else
			this.request.getQueryParams().put(key, value);
	}

	public void setQueryParam(Map<String, String> queryParams) {
		if (this.request == null) {

			this.request = new HttpRequest(null, null, queryParams);
		} else
			this.request.setQueryParams(queryParams);
	}

}

class HttpResponse {

	protected String responseBody;
	protected Map<String, String> responseHeader;
	protected String statusCode;

	protected HttpResponse(String statusCode, Map<String, String> responseHeader, String responseBody) {
		this.responseBody = responseBody;
		this.responseHeader = responseHeader;
		this.statusCode = statusCode;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public Map<String, String> getResponseHeader() {
		return responseHeader;
	}

	public String getStatusCode() {
		return statusCode;
	}
}

class HttpRequest {

	protected byte[] requestBody;
	protected Map<String, String> requestHeader;
	protected Map<String, String> queryParams;

	public HttpRequest(byte[] requestBody, Map<String, String> requestHeader, Map<String, String> queryParams) {
		super();
		this.requestBody = requestBody;
		this.requestHeader = requestHeader;
		this.queryParams = queryParams;
	}

	public byte[] getRequestBody() {
		return requestBody;
	}

	public Map<String, String> getRequestHeader() {
		return requestHeader;
	}

	public Map<String, String> getQueryParams() {
		return queryParams;
	}

	public void setRequestBody(byte[] requestBody) {
		this.requestBody = requestBody;
	}

	public void setRequestHeader(Map<String, String> requestHeader) {
		this.requestHeader = requestHeader;
	}

	public void setQueryParams(Map<String, String> queryParams) {
		this.queryParams = queryParams;
	}

}