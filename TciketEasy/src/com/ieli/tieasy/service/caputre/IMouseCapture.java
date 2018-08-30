package com.ieli.tieasy.service.caputre;

import javax.swing.JPanel;

import org.jnativehook.mouse.NativeMouseInputListener;

public interface IMouseCapture extends NativeMouseInputListener {

	void setTicketsCarouselPnl(JPanel ticketsCarouselPnl);
}
