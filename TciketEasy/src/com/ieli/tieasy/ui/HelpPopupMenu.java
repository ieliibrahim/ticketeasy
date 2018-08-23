package com.ieli.tieasy.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import com.ieli.tieasy.util.AppUtils;
import com.ieli.tieasy.util.StackTraceHandler;
import com.ieli.tieasy.util.StaticData;

public class HelpPopupMenu extends JPopupMenu {

	final static Logger logger = Logger.getLogger(HelpPopupMenu.class);

	private static final long serialVersionUID = 1L;

	JMenuItem instVideoItem;
	JMenuItem helpItem;
	JMenuItem settingsItem;

	public HelpPopupMenu() {
		instVideoItem = new JMenuItem(StaticData.HELP_INSTC_VIDEO_ITEM);
		add(instVideoItem);

		helpItem = new JMenuItem(StaticData.HELP_HELP_ITEM);
		add(helpItem);

		settingsItem = new JMenuItem(StaticData.HELP_SETTINGS_ITEM);
		add(settingsItem);

		instVideoItem.setOpaque(true);
		instVideoItem.setBackground(Color.WHITE);
		instVideoItem.setForeground(StaticData.THEME_ORANGE_COLOR);
		instVideoItem.setIcon(StaticData.ICON_MENU);
		instVideoItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		instVideoItem.setCursor(StaticData.HAND_CURSOR);
		openURL(instVideoItem);

		helpItem.setOpaque(true);
		helpItem.setBackground(Color.WHITE);
		helpItem.setForeground(StaticData.THEME_ORANGE_COLOR);
		helpItem.setIcon(StaticData.ICON_MENU);
		helpItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		helpItem.setCursor(StaticData.HAND_CURSOR);
		openURL(helpItem);

		settingsItem.setOpaque(true);
		settingsItem.setBackground(Color.WHITE);
		settingsItem.setForeground(StaticData.THEME_ORANGE_COLOR);
		settingsItem.setIcon(StaticData.ICON_MENU);
		settingsItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		settingsItem.setCursor(StaticData.HAND_CURSOR);
		openURL(settingsItem);
	}

	private void openURL(JMenuItem item) {
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AppUtils.openWebpage(new URL("https://ticketeasy-software.com"));
				} catch (MalformedURLException e1) {
					logger.error(StackTraceHandler.getErrString(e1));
				}

			}
		});
	}
}
