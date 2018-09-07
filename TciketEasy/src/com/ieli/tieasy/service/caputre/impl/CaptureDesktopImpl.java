package com.ieli.tieasy.service.caputre.impl;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.log4j.Logger;

import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.ui.SingleDraftPanel;
import com.ieli.tieasy.ui.TEMainFrame;
import com.ieli.tieasy.ui.TimingPanel;
import com.ieli.tieasy.util.AppUtils;
import com.ieli.tieasy.util.StackTraceHandler;

public class CaptureDesktopImpl implements ICaptureDesktop {

	final static Logger logger = Logger.getLogger(CaptureDesktopImpl.class);

	@Override
	public void captureDesktop(final JPanel ticketsCarouselPnl, final TEMainFrame teMainFrame) {
		try {

			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			File dir = new File("drafts/");
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File[] tempFileNames = dir.listFiles();
			if (tempFileNames.length == 10) {

				File oldestDraft = null;
				Arrays.sort(tempFileNames, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
				for (File draftFile : tempFileNames) {
					oldestDraft = draftFile;
					break;
				}

				oldestDraft.delete();
				ticketsCarouselPnl.remove(0);
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

			final SingleDraftPanel singleDraftPanel = new SingleDraftPanel(capture, imagePath, ticketsCarouselPnl,
					teMainFrame);
			ticketsCarouselPnl.add(singleDraftPanel);

			updateTimeLine(ticketsCarouselPnl);

		} catch (IOException e) {
			logger.error(StackTraceHandler.getErrString(e));
		} catch (AWTException e) {
			logger.error(StackTraceHandler.getErrString(e));
		}
	}

	private void updateTimeLine(JPanel ticketsCarouselPnl) throws IOException {

		File draftsDir = new File("drafts/");
		File[] newDraftFiles = draftsDir.listFiles();
		File[] oldDraftFiles = draftsDir.listFiles();

		String newest = "";
		Arrays.sort(newDraftFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		for (File draftFile : newDraftFiles) {
			BasicFileAttributes attr = Files.readAttributes(draftFile.toPath(), BasicFileAttributes.class);
			newest = AppUtils.formatTime(attr.lastModifiedTime());
			break;
		}

		String oldest = "";
		Arrays.sort(oldDraftFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
		for (File draftFile : oldDraftFiles) {
			BasicFileAttributes attr = Files.readAttributes(draftFile.toPath(), BasicFileAttributes.class);
			oldest = AppUtils.formatTime(attr.lastModifiedTime());
			break;
		}

		ticketsCarouselPnl.invalidate();
		ticketsCarouselPnl.repaint();
		ticketsCarouselPnl.updateUI();

		JPanel ticketsPnl = (JPanel) ticketsCarouselPnl.getParent();
		ticketsPnl.invalidate();
		ticketsPnl.repaint();
		ticketsPnl.updateUI();

		for (int i = 0; i < ticketsPnl.getComponentCount(); i++) {
			Component comp = ticketsPnl.getComponent(i);
			if (comp instanceof TimingPanel) {
				TimingPanel tp = (TimingPanel) comp;
				for (int m = 0; m < tp.getComponentCount(); m++) {
					Component tpComp = tp.getComponent(m);

					if (tpComp instanceof JLabel) {
						JLabel lbl = (JLabel) tpComp;
						if (lbl.getName().equals("oldtime")) {
							lbl.setText("<html><center>Newest</center><br /><center>" + newest + "</center>");
						}
						if (lbl.getName().equals("newtime")) {
							lbl.setText("<html><center>Oldest</center><br /><center>" + oldest + "</center>");
						}
					}
				}
				tp.setVisible(true);
				tp.invalidate();
				tp.repaint();
				tp.updateUI();
			}
		}

		JPanel mainPanel = (JPanel) ticketsPnl.getParent();
		mainPanel.invalidate();
		mainPanel.repaint();
		mainPanel.updateUI();
	}

}
