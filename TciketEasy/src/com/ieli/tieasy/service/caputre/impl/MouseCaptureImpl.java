package com.ieli.tieasy.service.caputre.impl;

import org.jnativehook.mouse.NativeMouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.service.caputre.IMouseCapture;

@Service("iMouseCaptureService")
public class MouseCaptureImpl implements IMouseCapture {

	private Integer ticketId;
	private Integer userId;

	@Autowired
	private ICaptureDesktop iDesktopCaptureService;

	@Override
	public void nativeMouseClicked(NativeMouseEvent arg0) {

		if (arg0.getButton() != NativeMouseEvent.BUTTON2) {
			iDesktopCaptureService.captureDesktop(this.userId, this.ticketId);
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
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}

	@Override
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
