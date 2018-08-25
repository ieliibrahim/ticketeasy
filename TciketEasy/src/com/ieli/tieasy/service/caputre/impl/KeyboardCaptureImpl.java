package com.ieli.tieasy.service.caputre.impl;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.service.caputre.IKeyboardCapture;

@Service("iKeyboardCaptureService")
public class KeyboardCaptureImpl implements IKeyboardCapture {

	private Integer ticketId;
	private Integer userId;

	@Autowired
	private ICaptureDesktop iDesktopCaptureService;

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {

		if (e.getKeyCode() == NativeKeyEvent.VC_ENTER) {
			iDesktopCaptureService.captureDesktop(this.userId, this.ticketId);
		} else if ((e.getKeyCode() == NativeKeyEvent.VC_K) && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0)) {
			System.out.println("Open system");
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

}
