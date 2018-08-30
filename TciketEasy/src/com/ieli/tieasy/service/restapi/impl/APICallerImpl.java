package com.ieli.tieasy.service.restapi.impl;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ieli.tieasy.model.api.TokenResponse;
import com.ieli.tieasy.service.restapi.IAPICaller;
import com.ieli.tieasy.util.ImportUtil;
import com.ieli.tieasy.util.StackTraceHandler;
import com.ieli.tieasy.util.StaticData;

@Service("iAPICallerService")
public class APICallerImpl implements IAPICaller {

	final static Logger logger = Logger.getLogger(APICallerImpl.class);

	@Override
	public TokenResponse getReponseToken() {

		TokenResponse tokenReponse = null;
		try {
			HttpClient httpClient = new HttpClient();

			String basicAuth = "Basic c3ByaW5nLXNlY3VyaXR5LW9hdXRoMi1yZWFkLXdyaXRlLWNsaWVudDpzcHJpbmctc2VjdXJpdHktb2F1dGgyLXJlYWQtd3JpdGUtY2xpZW50LXBhc3N3b3JkMTIzNA==";

			httpClient = new HttpClient();
			PostMethod httpPost = new PostMethod(StaticData.OAUTH_API_PATH);
			httpPost.addRequestHeader("Authorization", basicAuth);

			httpPost.addParameter("grant_type", "password");
			httpPost.addParameter("username", "admin");
			httpPost.addParameter("password", "admin1234");
			httpPost.addParameter("client_id", "spring-security-oauth2-read-write-client");

			int returnCodeRefresh = httpClient.executeMethod(httpPost);

			if (returnCodeRefresh != 200) {
				logger.error("Failed : HTTP error code : " + returnCodeRefresh + "\n HTTP error message: "
						+ httpPost.getResponseBodyAsString());
				return tokenReponse;

			}

			String response = ImportUtil.readInputStreamToString(httpPost.getResponseBodyAsStream());
			tokenReponse = new Gson().fromJson(response, TokenResponse.class);

		} catch (MalformedURLException e) {
			logger.error(StackTraceHandler.getErrString(e));

		} catch (IOException e) {
			logger.error(StackTraceHandler.getErrString(e));
		}

		return tokenReponse;
	}

	@Override
	public String submitTicket(String token) {

		String response = null;
		try {
			HttpClient httpClient = new HttpClient();
			GetMethod getMethod = new GetMethod(StaticData.SUBMIT_TICKET_API_PATH);

			getMethod.addRequestHeader("Authorization", "Bearer " + token);

			int returnCode = httpClient.executeMethod(getMethod);

			if (returnCode != 200) {
				System.err.println("Failed : HTTP error code : " + returnCode + "\n HTTP error message: "
						+ getMethod.getResponseBodyAsString());
			}

			response = ImportUtil.readInputStreamToString(getMethod.getResponseBodyAsStream());

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

}
