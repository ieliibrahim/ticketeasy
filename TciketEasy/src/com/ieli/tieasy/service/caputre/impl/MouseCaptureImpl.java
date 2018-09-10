package com.ieli.tieasy.service.caputre.impl;

import javax.swing.JPanel;

import org.jnativehook.mouse.NativeMouseEvent;

import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.service.caputre.IMouseCapture;
import com.ieli.tieasy.ui.TEMainFrame;
import com.ieli.tieasy.ui.TrayPopupMenu;

public class MouseCaptureImpl implements IMouseCapture {

	private ICaptureDesktop iDesktopCaptureService = new CaptureDesktopImpl();

	private JPanel ticketsCarouselPnl;

	private TEMainFrame teMainFrame;

	private TrayPopupMenu trayPopupMenu;

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {

		if (arg0.getButton() != NativeMouseEvent.BUTTON2) {
			iDesktopCaptureService.captureDesktop(ticketsCarouselPnl, teMainFrame);
			if (trayPopupMenu != null) {
				if (trayPopupMenu.isVisible()) {
					trayPopupMenu.setInvoker(null);
					trayPopupMenu.setVisible(false);
				}
			}
		}
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent arg0) {

	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent arg0) {

	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent arg0) {

	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent arg0) {

	}

	@Override
	public void setTicketsCarouselPnl(JPanel ticketsCarouselPnl) {
		this.ticketsCarouselPnl = ticketsCarouselPnl;
	}

	@Override
	public void setTeMainFrame(TEMainFrame teMainFrame) {
		this.teMainFrame = teMainFrame;
	}

	@Override
	public void setTrayPopupMenu(TrayPopupMenu trayPopupMenu) {
		this.trayPopupMenu = trayPopupMenu;
	}

}
