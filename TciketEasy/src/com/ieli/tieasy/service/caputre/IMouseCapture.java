package com.ieli.tieasy.service.caputre;

import org.jnativehook.mouse.NativeMouseInputListener;

public interface IMouseCapture extends NativeMouseInputListener {

	void setTicketId(Integer ticketId);
	
	void setUserId(Integer userId);
	
}
