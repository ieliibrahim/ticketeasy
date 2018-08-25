package com.ieli.tieasy.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;

import com.ieli.tieasy.model.Draft;
import com.ieli.tieasy.service.caputre.IKeyboardCapture;
import com.ieli.tieasy.service.caputre.IMouseCapture;
import com.ieli.tieasy.service.drafts.IDraftsService;
import com.ieli.tieasy.util.StaticData;

public class TrayPopupMenu extends JPopupMenu {

	final static Logger logger = Logger.getLogger(TrayPopupMenu.class);

	private static final long serialVersionUID = 1L;

	JMenuItem exitItem;
	JMenuItem showItem;

	public TrayPopupMenu(final JFrame teMainFrame, final SystemTray tray, final TrayIcon trayIcon,
			final IMouseCapture iMouseCaptureService, final IKeyboardCapture iKeyboardCaptureService, final IDraftsService iDraftsService, final Integer ticketId,
			final JPanel ticketsCarouselPnl) {

		exitItem = new JMenuItem("Exit");
		exitItem.setOpaque(true);
		exitItem.setBackground(Color.WHITE);
		exitItem.setForeground(StaticData.THEME_ORANGE_COLOR);
		exitItem.setIcon(StaticData.ICON_MENU);
		exitItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		exitItem.setCursor(StaticData.HAND_CURSOR);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		add(exitItem);

		showItem = new JMenuItem("Show");
		showItem.setOpaque(true);
		showItem.setBackground(Color.WHITE);
		showItem.setForeground(StaticData.THEME_ORANGE_COLOR);
		showItem.setIcon(StaticData.ICON_MENU);
		showItem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		showItem.setCursor(StaticData.HAND_CURSOR);
		showItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				teMainFrame.setVisible(true);
				teMainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				GlobalScreen.removeNativeMouseListener(iMouseCaptureService);
				GlobalScreen.removeNativeKeyListener(iKeyboardCaptureService);
				List<Draft> drafts = iDraftsService.getTicketDrafts(ticketId);
				getTicketDrafts(drafts, ticketsCarouselPnl);
			}
		});
		add(showItem);

	}

	private void getTicketDrafts(List<Draft> drafts, JPanel ticketsCarouselPnl) {
		int draftIndex = 0;
		for (Draft draft : drafts) {

			SingleDraftPanel singleDraftPanel = new SingleDraftPanel(new ImageIcon(draft.getImageThumb()).getImage());
			ticketsCarouselPnl.add(singleDraftPanel, "cell " + draftIndex + " 0");
			draftIndex += 3;
			singleDraftPanel.updateUI();
		}

		ticketsCarouselPnl.updateUI();
	}
}
