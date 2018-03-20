package com.statsh.assessment.stash.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpConnectionManager {

	public static String getJsonResponse(String url, String jsonEntity) throws ClientProtocolException, IOException {
		HttpPost httpPost;
		CloseableHttpResponse response;
		httpPost = new HttpPost(url);
		StringEntity params = new StringEntity(jsonEntity);
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.addHeader("Accept", "application/json");
		httpPost.setEntity(params);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		response = httpClient.execute(httpPost);
		HttpEntity responseEntity = response.getEntity();
		if (responseEntity != null) {
			String responseString = EntityUtils.toString(responseEntity);
			return responseString;
		}
		return null;
	}
}
