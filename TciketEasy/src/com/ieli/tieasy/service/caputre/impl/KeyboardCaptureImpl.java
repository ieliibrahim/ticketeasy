package com.ieli.tieasy.service.caputre.impl;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ieli.tieasy.model.Draft;
import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.service.caputre.IKeyboardCapture;
import com.ieli.tieasy.service.caputre.IMouseCapture;
import com.ieli.tieasy.service.drafts.IDraftsService;
import com.ieli.tieasy.ui.SingleDraftPanel;
import com.ieli.tieasy.ui.TEMainFrame;

@Service("iKeyboardCaptureService")
public class KeyboardCaptureImpl implements IKeyboardCapture {

	private Integer ticketId;
	private Integer userId;

	private TrayIcon trayIcon;
	private SystemTray tray;
	private TEMainFrame teMainFrame;
	private IMouseCapture iMouseCaptureService;
	private IKeyboardCapture iKeyboardCaptureService;
	private IDraftsService iDraftsService;
	private JPanel ticketsCarouselPnl;

	@Autowired
	private ICaptureDesktop iDesktopCaptureService;

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {

		if (e.getKeyCode() == NativeKeyEvent.VC_ENTER) {
			iDesktopCaptureService.captureDesktop(this.userId, this.ticketId);
		} else if ((e.getKeyCode() == NativeKeyEvent.VC_K) && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0)) {

			tray.remove(trayIcon);
			teMainFrame.setVisible(true);
			teMainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			GlobalScreen.removeNativeMouseListener(iMouseCaptureService);
			GlobalScreen.removeNativeKeyListener(iKeyboardCaptureService);
			List<Draft> drafts = iDraftsService.getTicketDrafts(ticketId);
			getTicketDrafts(drafts, ticketsCarouselPnl);
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {

	}

	@Override
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	private void getTicketDrafts(List<Draft> drafts, JPanel ticketsCarouselPnl) {
		int draftIndex = 0;
		for (Draft draft : drafts) {

			SingleDraftPanel singleDraftPanel = new SingleDraftPanel(new ImageIcon(draft.getImageThumb()).getImage());
			ticketsCarouselPnl.add(singleDraftPanel, "cell " + draftIndex + " 0");
			draftIndex += 3;
			singleDraftPanel.updateUI();
		}

		ticketsCarouselPnl.updateUI();
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
	public void setiDraftsService(IDraftsService iDraftsService) {
		this.iDraftsService = iDraftsService;
	}

	@Override
	public void setTicketsCarouselPnl(JPanel ticketsCarouselPnl) {
		this.ticketsCarouselPnl = ticketsCarouselPnl;
	}

}
