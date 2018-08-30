package com.ieli.tieasy.service.caputre.impl;

import java.awt.Component;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ieli.tieasy.model.Draft;
import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.service.caputre.IKeyboardCapture;
import com.ieli.tieasy.service.caputre.IMouseCapture;
import com.ieli.tieasy.ui.SingleDraftPanel;
import com.ieli.tieasy.ui.TEMainFrame;
import com.ieli.tieasy.ui.TimingPanel;
import com.ieli.tieasy.util.AppUtils;

@Service("iKeyboardCaptureService")
public class KeyboardCaptureImpl implements IKeyboardCapture {

	private TrayIcon trayIcon;
	private

	SystemTray tray;
	private TEMainFrame teMainFrame;
	private IMouseCapture iMouseCaptureService;
	private IKeyboardCapture iKeyboardCaptureService;
	private JPanel ticketsCarouselPnl;

	@Autowired
	private ICaptureDesktop iDesktopCaptureService;

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {

		if (e.getKeyCode() == NativeKeyEvent.VC_ENTER) {
			iDesktopCaptureService.captureDesktop(ticketsCarouselPnl);
		} else if ((e.getKeyCode() == NativeKeyEvent.VC_H) && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0)) {

			tray.remove(trayIcon);
			teMainFrame.setVisible(true);
			teMainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			GlobalScreen.removeNativeMouseListener(iMouseCaptureService);
			GlobalScreen.removeNativeKeyListener(iKeyboardCaptureService);

			try {
				getDrafst(teMainFrame);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	private void getDrafst(JFrame teMainFrame) throws IOException {

		ticketsCarouselPnl.removeAll();
		File draftsDir = new File("drafts/");
		File[] draftFiles = draftsDir.listFiles();

		for (File draftFile : draftFiles) {

			Draft draft = new Draft();
			BasicFileAttributes attr = Files.readAttributes(draftFile.toPath(), BasicFileAttributes.class);
			draft.setDraftDateTime(AppUtils.formatTime(attr.creationTime()));

			String imagePath = "drafts/" + draftFile.getName();
			draft.setImagePath(imagePath);

			String thumbImagePath = "thumbs/" + draftFile.getName().replaceAll(".png", "_thumb.png");
			draft.setImageThumb(thumbImagePath);

			final SingleDraftPanel singleDraftPanel = new SingleDraftPanel(imagePath,
					new ImageIcon(thumbImagePath).getImage(), ticketsCarouselPnl, teMainFrame);
			singleDraftPanel.setDraft(draft);
			ticketsCarouselPnl.add(singleDraftPanel);

		}

		String newest = "";
		Arrays.sort(draftFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		for (File draftFile : draftFiles) {
			BasicFileAttributes attr = Files.readAttributes(draftFile.toPath(), BasicFileAttributes.class);
			newest = AppUtils.formatTime(attr.lastModifiedTime());
			break;
		}

		String oldest = "";
		Arrays.sort(draftFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
		for (File draftFile : draftFiles) {
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

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {

	}

	@Override
	public void setTrayIcon(TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}

	@Override
	public void setTray(SystemTray tray) {
		this.tray = tray;
	}

	@Override
	public void setTeMainFrame(TEMainFrame teMainFrame) {
		this.teMainFrame = teMainFrame;
	}

	@Override
	public void setiMouseCaptureService(IMouseCapture iMouseCaptureService) {
		this.iMouseCaptureService = iMouseCaptureService;
	}

	@Override
	public void setiKeyboardCaptureService(IKeyboardCapture iKeyboardCaptureService) {
		this.iKeyboardCaptureService = iKeyboardCaptureService;
	}

	@Override
	public void setTicketsCarouselPnl(JPanel ticketsCarouselPnl) {
		this.ticketsCarouselPnl = ticketsCarouselPnl;
	}

}
