package com.ieli.tieasy.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;

import com.ieli.tieasy.service.caputre.IKeyboardCapture;
import com.ieli.tieasy.service.caputre.IMouseCapture;
import com.ieli.tieasy.util.StaticData;

public class TrayPopupMenu extends JPopupMenu {

	final static Logger logger = Logger.getLogger(TrayPopupMenu.class);

	private static final long serialVersionUID = 1L;

	JMenuItem exitItem;
	JMenuItem showItem;

	public TrayPopupMenu(final JFrame teMainFrame, final SystemTray tray, final TrayIcon trayIcon,
			final IMouseCapture iMouseCaptureService, final IKeyboardCapture iKeyboardCaptureService,
			final JPanel ticketsCarouselPnl) {

		exitItem = new JMenuItem("Close");
		exitItem.setOpaque(true);
		exitItem.setBackground(Color.WHITE);
		exitItem.setForeground(StaticData.THEME_ORANGE_COLOR);
		exitItem.setIcon(StaticData.ICON_MENU);
		exitItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		exitItem.setCursor(StaticData.HAND_CURSOR);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		add(exitItem);

		showItem = new JMenuItem("Open");
		showItem.setOpaque(true);
		showItem.setBackground(Color.WHITE);
		showItem.setForeground(StaticData.THEME_ORANGE_COLOR);
		showItem.setIcon(StaticData.ICON_MENU);
		showItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		showItem.setCursor(StaticData.HAND_CURSOR);
		showItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				teMainFrame.setVisible(true);
				teMainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				GlobalScreen.removeNativeMouseListener(iMouseCaptureService);
				GlobalScreen.removeNativeKeyListener(iKeyboardCaptureService);
			}
		});
		add(showItem);

	}

}
