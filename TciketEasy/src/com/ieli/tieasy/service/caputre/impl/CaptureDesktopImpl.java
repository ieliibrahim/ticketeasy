package com.ieli.tieasy.service.caputre.impl;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.transaction.Transactional;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.util.AppUtils;
import com.ieli.tieasy.util.StackTraceHandler;

@Service("iDesktopCaptureService")
@Transactional
public class CaptureDesktopImpl implements ICaptureDesktop {

	final static Logger logger = Logger.getLogger(CaptureDesktopImpl.class);

	@Override
	public void captureDesktop(final JPanel ticketsCarouselPnl) {
		try {

			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			File dir = new File("drafts/");
			if (!dir.exists()) {
				dir.mkdirs();
			}


			File[] tempFileNames = dir.listFiles();
			if(tempFileNames.length == 10) {
				
				File oldestDraft = null;
				Arrays.sort(tempFileNames, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				for (File draftFile : tempFileNames) {
					oldestDraft = draftFile;
					break;
				}
				
				oldestDraft.delete();
			}
			
			File[] fileNames = dir.listFiles();
			Integer lastImgNumber = 1;
			List<Integer> imagesNumbers = new ArrayList<Integer>();
			for (File file : fileNames) {
				imagesNumbers.add(Integer.valueOf(file.getName().replace("image", "").replaceAll(".png", "")));
			}

			if (!imagesNumbers.isEmpty()) {
				lastImgNumber = Collections.max(imagesNumbers) + 1;
			}

			final String imagePath = dir + "/image" + lastImgNumber + ".png";
			ImageIO.write(capture, "png", new File(imagePath));

			final String thumbImagePath = "thumbs" + "/image" + lastImgNumber + "_thumb.png";

			BufferedImage thumNail = AppUtils.imageToThumnail(capture, BufferedImage.TYPE_INT_RGB, 800, 500, "png");
			ImageIO.write(thumNail, "png", new File(thumbImagePath));

		} catch (IOException e) {
			logger.error(StackTraceHandler.getErrString(e));
		} catch (AWTException e) {
			logger.error(StackTraceHandler.getErrString(e));
		}
	}

}
