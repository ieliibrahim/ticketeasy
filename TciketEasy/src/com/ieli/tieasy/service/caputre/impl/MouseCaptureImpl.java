package com.ieli.tieasy.service.caputre.impl;

import javax.swing.JPanel;

import org.jnativehook.mouse.NativeMouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.service.caputre.IMouseCapture;

@Service("iMouseCaptureService")
public class MouseCaptureImpl implements IMouseCapture {

	@Autowired
	private ICaptureDesktop iDesktopCaptureService;

	private JPanel ticketsCarouselPnl;
	
	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {

		if (arg0.getButton() != NativeMouseEvent.BUTTON2) {
			iDesktopCaptureService.captureDesktop(ticketsCarouselPnl);
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

}
