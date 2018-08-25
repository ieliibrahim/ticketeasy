package com.ieli.tieasy.service.caputre;

import org.jnativehook.keyboard.NativeKeyListener;

public interface IKeyboardCapture extends NativeKeyListener {

	void setTicketId(Integer ticketId);

	void setUserId(Integer userId);

}
