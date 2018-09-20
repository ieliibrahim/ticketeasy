package com.ieli.tieasy.util;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.ieli.tieasy.util.StackTraceHandler;

public class AppUtils {

	final static Logger logger = Logger.getLogger(AppUtils.class);

	public static final String NOW_TIME_PATTERN = "HH:mm:ss";

	private static boolean openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
				return true;
			} catch (Exception e) {
				logger.error(StackTraceHandler.getErrString(e));
			}
		}
		return false;
	}

	public static boolean openWebpage(URL url) {
		try {
			return openWebpage(url.toURI());
		} catch (URISyntaxException e) {
			logger.error(StackTraceHandler.getErrString(e));
		}
		return false;
	}

	public static String getCurrentDateTime() {

		SimpleDateFormat sdf = new SimpleDateFormat(NOW_TIME_PATTERN);
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static String formatTime(FileTime time) {

		SimpleDateFormat sdf = new SimpleDateFormat(NOW_TIME_PATTERN);
		return sdf.format(time.toMillis());
	}

	public static String encodeBasic(String username, String password) {
		Base64 b = new Base64();
		String encoding = b.encodeAsString(new String(username + ":" + password).getBytes());
		return new String("Basic " + encoding);
	}

}
