package com.ieli.tieasy.util;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class AppUtils {

	final static Logger logger = Logger.getLogger(AppUtils.class);

	public static final String NOW_TIME_PATTERN = "yyyy-MM-dd hh:mm";

	private static boolean openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
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

	public static BufferedImage imageToThumnail(BufferedImage image, int imageType, int newWidth, int newHeight,
			String imageExtension) throws IOException {

		// Make sure the aspect ratio is maintained, so the image is not
		// distorted
		double thumbRatio = (double) newWidth / (double) newHeight;
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		double aspectRatio = (double) imageWidth / (double) imageHeight;

		if (thumbRatio < aspectRatio) {
			newHeight = (int) (newWidth / aspectRatio);
		} else {
			newWidth = (int) (newHeight * aspectRatio);
		}

		// Draw the scaled image
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, imageType);
		Graphics2D graphics2D = newImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(newImage, imageExtension, baos);

		return newImage;
	}
}
