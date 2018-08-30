package com.ieli.tieasy.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.apache.log4j.Logger;

import com.ieli.tieasy.service.caputre.impl.CaptureDesktopImpl;

public class ImportUtil {

	final static Logger logger = Logger.getLogger(CaptureDesktopImpl.class);

	public static String readInputStreamToString(HttpURLConnection connection) {
		String result = null;
		StringBuffer sb = new StringBuffer();
		InputStream is = null;

		try {
			is = new BufferedInputStream(connection.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String inputLine = "";
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			result = sb.toString();
		} catch (Exception e) {
			result = null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(StackTraceHandler.getErrString(e));
				}
			}
		}

		return result;
	}

	public static String readInputStreamToString(InputStream inputStream) {
		String result = null;
		StringBuffer sb = new StringBuffer();
		InputStream is = null;

		try {
			is = new BufferedInputStream(inputStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String inputLine = "";
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			result = sb.toString();
		} catch (Exception e) {
			result = null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(StackTraceHandler.getErrString(e));
				}
			}
		}

		return result;
	}

}
