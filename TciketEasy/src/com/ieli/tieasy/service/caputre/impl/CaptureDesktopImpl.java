package com.ieli.tieasy.service.caputre.impl;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ieli.tieasy.dao.drafts.IDraftsDao;
import com.ieli.tieasy.model.Draft;
import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.util.AppUtils;
import com.ieli.tieasy.util.StackTraceHandler;

@Service("iDesktopCaptureService")
@Transactional
public class CaptureDesktopImpl implements ICaptureDesktop {

	final static Logger logger = Logger.getLogger(CaptureDesktopImpl.class);

	@Autowired
	private IDraftsDao iDraftsDao;

	@Override
	public void captureDesktop(Integer userId, Integer ticketId) {
		try {

			Integer lastImgNumber = iDraftsDao.getLastDraftNumber(ticketId);

			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			File dir = new File("drafts/" + userId + "/" + ticketId);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String imagePath = dir + "/image" + lastImgNumber + ".png";
			ImageIO.write(capture, "png", new File(imagePath));

			String thumbImagePath = dir + "/image" + lastImgNumber + "_thumb.png";

			BufferedImage thumNail = AppUtils.imageToThumnail(capture, BufferedImage.TYPE_INT_RGB, 300, 300, "png");
			ImageIO.write(thumNail, "png", new File(thumbImagePath));

			Draft draft = new Draft();
			draft.setDraftDateTime(AppUtils.getCurrentDateTime());
			draft.setEnabled(1);
			draft.setImagePath(imagePath);
			draft.setImageThumb(thumbImagePath);
			draft.setNumber(lastImgNumber);
			draft.setTicketId(ticketId);
			iDraftsDao.saveDraft(draft);

		} catch (IOException e) {
			logger.error(StackTraceHandler.getErrString(e));
		} catch (AWTException e) {
			logger.error(StackTraceHandler.getErrString(e));
		}
	}

}
