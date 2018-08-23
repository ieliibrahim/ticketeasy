package com.ieli.tieasy.ui;

import java.awt.Color;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.ieli.tieasy.util.StaticData;

public class HelpPopupMenu extends JPopupMenu {

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
		
		helpItem.setOpaque(true);
		helpItem.setBackground(Color.WHITE);
		helpItem.setForeground(StaticData.THEME_ORANGE_COLOR);
		helpItem.setIcon(StaticData.ICON_MENU);
		
		settingsItem.setOpaque(true);
		settingsItem.setBackground(Color.WHITE);
		settingsItem.setForeground(StaticData.THEME_ORANGE_COLOR);
		settingsItem.setIcon(StaticData.ICON_MENU);
	}
	
	
}
