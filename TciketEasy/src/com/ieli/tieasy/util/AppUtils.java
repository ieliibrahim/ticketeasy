package com.ieli.tieasy.util;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class AppUtils {

	final static Logger logger = Logger.getLogger(AppUtils.class);

	public static final String NOW_TIME_PATTERN = "hh:mm:ss";

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

	public static BufferedImage imageToThumnail(BufferedImage image, int imageType, int newWidth, int newHeight,
			String imageExtension) throws IOException {

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

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	public static String encodeBasic(String username, String password) {
		Base64 b = new Base64();
		String encoding = b.encodeAsString(new String(username + ":" + password).getBytes());
		return new String("Basic " + encoding);
	}
}
