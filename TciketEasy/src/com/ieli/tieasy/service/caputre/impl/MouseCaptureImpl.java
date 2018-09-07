package com.ieli.tieasy.service.caputre.impl;

import javax.swing.JPanel;

import org.jnativehook.mouse.NativeMouseEvent;

import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.service.caputre.IMouseCapture;
import com.ieli.tieasy.ui.TEMainFrame;

public class MouseCaptureImpl implements IMouseCapture {

	private ICaptureDesktop iDesktopCaptureService = new CaptureDesktopImpl();

	private JPanel ticketsCarouselPnl;

	private TEMainFrame teMainFrame;

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {

		if (arg0.getButton() != NativeMouseEvent.BUTTON2) {
			iDesktopCaptureService.captureDesktop(ticketsCarouselPnl, teMainFrame);
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

}
