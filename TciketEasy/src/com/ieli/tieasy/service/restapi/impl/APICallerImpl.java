package com.ieli.tieasy.service.restapi.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.ieli.tieasy.model.api.Incident;
import com.ieli.tieasy.model.api.IncidentInput;
import com.ieli.tieasy.model.api.Upload;
import com.ieli.tieasy.service.restapi.IAPICaller;
import com.ieli.tieasy.service.util.IPropertyService;
import com.ieli.tieasy.service.util.impl.PropertyServiceImpl;
import com.ieli.tieasy.util.AppUtils;
import com.ieli.tieasy.util.ImportUtil;
import com.ieli.tieasy.util.StackTraceHandler;

public class APICallerImpl implements IAPICaller {

	final static Logger logger = Logger.getLogger(APICallerImpl.class);

	private IPropertyService iPropertyService = new PropertyServiceImpl();

	private IncidentInput incidentInput;

	public APICallerImpl() {
		incidentInput = iPropertyService.getIncidentInput("api.properties");
	}

	@Override
	public Incident createIncident(String title, String description) {
		Incident incident = null;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();

			String basicAuth = AppUtils.encodeBasic(incidentInput.getUsername(), incidentInput.getPassword());

			HttpPost httpPost = new HttpPost(incidentInput.getCreateIncidentPath());

			Header authHeader = new BasicHeader("Authorization", basicAuth);
			httpPost.addHeader(authHeader);
			StringEntity requestEntity = new StringEntity("{\"caller_id\":\"" + incidentInput.getEmail()
					+ "\",\"short_description\":\"" + title + "\",\"description\":\"" + description + "\"}");
			requestEntity.setChunked(true);
			requestEntity.setContentEncoding("UTF-8");

			Header acceptHeader = new BasicHeader("Accept", "application/json");
			Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
			httpPost.addHeader(acceptHeader);
			httpPost.addHeader(contentTypeHeader);

			httpPost.setEntity(requestEntity);

			HttpResponse res = httpClient.execute(httpPost);

			if (res.getStatusLine().getStatusCode() != 201) {
				logger.error("Failed : HTTP error code : " + res.getStatusLine().getStatusCode()
						+ "\n HTTP error message: " + res.getStatusLine().getReasonPhrase());
				return incident;

			}

			String response = ImportUtil.readInputStreamToString(res.getEntity().getContent());
			incident = new Gson().fromJson(response, Incident.class);

		} catch (MalformedURLException e) {
			logger.error(StackTraceHandler.getErrString(e));

		} catch (IOException e) {
			logger.error(StackTraceHandler.getErrString(e));
		}

		return incident;
	}

	@Override
	public Upload uploadFile(File file, String sysId) {

		Upload upload = null;
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;

		try {

			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(incidentInput.getUsername(),
					incidentInput.getPassword());
			provider.setCredentials(AuthScope.ANY, credentials);
			httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

			HttpPost post = new HttpPost(incidentInput.getUploadFilePath());

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
			StringBody tableNameParam = new StringBody("incident", ContentType.MULTIPART_FORM_DATA);
			StringBody sysIdParam = new StringBody(sysId, ContentType.MULTIPART_FORM_DATA);

			builder.addPart("table_name", tableNameParam);
			builder.addPart("table_sys_id", sysIdParam);
			builder.addPart(file.getName(), fileBody);
			HttpEntity entity = builder.build();
			post.setEntity(entity);

			response = httpClient.execute(post);

			if (response.getStatusLine().getStatusCode() != 201) {
				logger.error("Failed : HTTP error code : " + response.getStatusLine().getStatusCode()
						+ "\n HTTP error message: " + EntityUtils.toString(response.getEntity()));
				return upload;

			}

			String res = ImportUtil.readInputStreamToString(response.getEntity().getContent());
			upload = new Gson().fromJson(res, Upload.class);

		} catch (MalformedURLException e1) {
			logger.error(StackTraceHandler.getErrString(e1));

		} catch (IOException e2) {
			logger.error(StackTraceHandler.getErrString(e2));
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error(StackTraceHandler.getErrString(e));
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error(StackTraceHandler.getErrString(e));
				}
			}
		}

		return upload;

	}
}
