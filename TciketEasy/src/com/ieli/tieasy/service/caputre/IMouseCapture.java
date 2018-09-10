package com.ieli.tieasy.service.caputre;

import javax.swing.JPanel;

import org.jnativehook.mouse.NativeMouseInputListener;

import com.ieli.tieasy.ui.TEMainFrame;
import com.ieli.tieasy.ui.TrayPopupMenu;

public interface IMouseCapture extends NativeMouseInputListener {

	void setTicketsCarouselPnl(JPanel ticketsCarouselPnl);
	
	void setTeMainFrame(TEMainFrame teMainFrame);

	void setTrayPopupMenu(TrayPopupMenu trayPopupMenu);
}
