package com.ieli.tieasy.util;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.ImageIcon;

public class StaticData {

	public static final Color THEME_ORANGE_COLOR = new Color(242, 152, 50);

	public static final Color HEADER_FOOTER_COLOR = new Color(13, 13, 13);

	public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

	public static final ImageIcon ICON_EXIT = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/exit.png"));

	public static final ImageIcon ICON_EXIT_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/exit_hover.png"));

	public static final ImageIcon ICON_HELP = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/help.png"));

	public static final ImageIcon ICON_HELP_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/help_hover.png"));

	public static final ImageIcon BG_IMAGE = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/background.png"));
	
	public static final ImageIcon ICON_MENU = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/pop_menu_icon.png"));
	
	public static final ImageIcon LOGO_IMG = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/logo.png"));
	
	public static final ImageIcon TRAY_ICON = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/tryIcon.png"));

	public static final String HELP_INSTC_VIDEO_ITEM = "<html><u>Instructional video</u></html>";

	public static final String HELP_HELP_ITEM = "<html><u>Help</u></html>";

	public static final String HELP_SETTINGS_ITEM = "<html><u>Settings</u></html>";
	
	public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
}
