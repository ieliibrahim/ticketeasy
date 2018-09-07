package com.ieli.tieasy.service.caputre.impl;

import java.awt.SystemTray;
import java.awt.TrayIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;

import com.ieli.tieasy.service.caputre.ICaptureDesktop;
import com.ieli.tieasy.service.caputre.IKeyboardCapture;
import com.ieli.tieasy.service.caputre.IMouseCapture;
import com.ieli.tieasy.ui.TEMainFrame;

public class KeyboardCaptureImpl implements IKeyboardCapture {

	private TrayIcon trayIcon;
	private

	SystemTray tray;
	private TEMainFrame teMainFrame;
	private IMouseCapture iMouseCaptureService;
	private IKeyboardCapture iKeyboardCaptureService;
	private JPanel ticketsCarouselPnl;

	private ICaptureDesktop iDesktopCaptureService = new CaptureDesktopImpl();

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {

		if (e.getKeyCode() == NativeKeyEvent.VC_ENTER) {
			iDesktopCaptureService.captureDesktop(ticketsCarouselPnl, teMainFrame);
		} else if ((e.getKeyCode() == NativeKeyEvent.VC_H) && ((e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0)) {

			tray.remove(trayIcon);
			teMainFrame.setVisible(true);
			teMainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			GlobalScreen.removeNativeMouseListener(iMouseCaptureService);
			GlobalScreen.removeNativeKeyListener(iKeyboardCaptureService);

		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {

	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {

	}

	@Override
	public void setTrayIcon(TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}

	@Override
	public void setTray(SystemTray tray) {
		this.tray = tray;
	}

	@Override
	public void setTeMainFrame(TEMainFrame teMainFrame) {
		this.teMainFrame = teMainFrame;
	}

	@Override
	public void setiMouseCaptureService(IMouseCapture iMouseCaptureService) {
		this.iMouseCaptureService = iMouseCaptureService;
	}

	@Override
	public void setiKeyboardCaptureService(IKeyboardCapture iKeyboardCaptureService) {
		this.iKeyboardCaptureService = iKeyboardCaptureService;
	}

	@Override
	public void setTicketsCarouselPnl(JPanel ticketsCarouselPnl) {
		this.ticketsCarouselPnl = ticketsCarouselPnl;
	}

}
