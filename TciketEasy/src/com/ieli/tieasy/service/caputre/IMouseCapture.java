package com.ieli.tieasy.service.caputre;

import javax.swing.JPanel;

import org.jnativehook.mouse.NativeMouseInputListener;

import com.ieli.tieasy.ui.TEMainFrame;

public interface IMouseCapture extends NativeMouseInputListener {

	void setTicketsCarouselPnl(JPanel ticketsCarouselPnl);
	
	void setTeMainFrame(TEMainFrame teMainFrame);
}
