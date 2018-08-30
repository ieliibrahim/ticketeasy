package com.ieli.tieasy.service.caputre;

import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.JPanel;

import org.jnativehook.keyboard.NativeKeyListener;

import com.ieli.tieasy.ui.TEMainFrame;

public interface IKeyboardCapture extends NativeKeyListener {

	void setTrayIcon(TrayIcon trayIcon);

	void setTray(SystemTray tray);

	void setTeMainFrame(TEMainFrame teMainFrame);

	void setiMouseCaptureService(IMouseCapture iMouseCaptureService);

	void setiKeyboardCaptureService(IKeyboardCapture iKeyboardCaptureService);

	void setTicketsCarouselPnl(JPanel ticketsCarouselPnl);

}
