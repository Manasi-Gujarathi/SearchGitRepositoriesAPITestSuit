package com.api.http.connector.client.factory;

import com.api.http.connector.client.HttpClientConnection;
import com.api.http.connector.client.HttpGetClientConnection;

public class HttpConnectionFactory {

	public HttpClientConnection getHttpConnection(String method) {

		if (method == null || method == "") {
			return null;
		}
		if (method == "GET") {
			return new HttpGetClientConnection();
		}
		return null;
	}
}
