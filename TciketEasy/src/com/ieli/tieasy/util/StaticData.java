package com.ieli.tieasy.util;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.ImageIcon;

public class StaticData {

	public static final Color THEME_ORANGE_COLOR = new Color(242, 152, 50);

	public static final Color HEADER_FOOTER_COLOR = new Color(13, 13, 13);

	public static final Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

	public static final Color BLUR_COLOR = new Color(1f, 1f, 1f, .98f);

	public static final ImageIcon ICON_EXIT = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/exit.png"));

	public static final ImageIcon ICON_EXIT_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/exit_hover.png"));

	public static final ImageIcon ICON_HELP = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/help.png"));

	public static final ImageIcon ICON_HELP_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/help_hover.png"));

	public static final String BG_IMAGE = "/com/ieli/tieasy/ui/images/background.png";

	public static final ImageIcon ICON_MENU = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/pop_menu_icon.png"));

	public static final ImageIcon LOGO_IMG = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/Logo.png"));

	public static final ImageIcon TRAY_ICON = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/Icon.png"));

	public static final String HELP_INSTC_VIDEO_ITEM = "<html><u>Instructional video</u></html>";

	public static final String HELP_HELP_ITEM = "<html><u>Help</u></html>";

	public static final String HELP_SETTINGS_ITEM = "<html><u>Settings</u></html>";

	public static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);

	public static final ImageIcon ICON_FREEHAND = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/freeDraw.png"));

	public static final ImageIcon ICON_FREEHAND_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/freeDrawHover.png"));

	public static final ImageIcon ICON_DRAW_LINE = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/drawLine.png"));

	public static final ImageIcon ICON_DRAW_LINE_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/drawLineHover.png"));

	public static final ImageIcon ICON_DRAW_ARROW = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/drawArrow.png"));

	public static final ImageIcon ICON_DRAW_ARROW_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/drawArrowHover.png"));

	public static final ImageIcon ICON_DRAW_EMPTY_SQUARE = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/drawEmptySquare.png"));

	public static final ImageIcon ICON_DRAW_EMPTY_SQUARE_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/drawEmptySquareHover.png"));

	public static final ImageIcon ICON_DRAW_SQUARE = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/drawSquare.png"));

	public static final ImageIcon ICON_DRAW_SQUARE_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/drawSquareHover.png"));

	public static final ImageIcon ICON_DRAW_TEXT = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/inertText.png"));

	public static final ImageIcon ICON_DRAW_TEXT_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/inertTextHover.png"));

	public static final ImageIcon ICON_CHOOSE_COLOR = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/chooseColor.png"));

	public static final ImageIcon ICON_CHOOSE_COLOR_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/chooseColorHover.png"));

	public static final ImageIcon ICON_CROP = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/crop.png"));

	public static final ImageIcon ICON_CROP_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/cropHover.png"));

	public static final ImageIcon ICON_BLUR = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/blur.png"));

	public static final ImageIcon ICON_BLUR_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/blurHover.png"));

	public static final ImageIcon ICON_UNDO = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/undo.png"));

	public static final ImageIcon ICON_UNDO_HOVER = new ImageIcon(
			StaticData.class.getResource("/com/ieli/tieasy/ui/images/drawing_icons/undoHover.png"));

	public static final Color[] PRIMARY_COLORS = new Color[] { new Color(255, 255, 255), new Color(255, 0, 0),
			new Color(0, 255, 0), new Color(0, 0, 255), new Color(255, 255, 0), new Color(0, 0, 0),
			new Color(255, 0, 255), new Color(192, 192, 192), new Color(128, 128, 128), new Color(128, 0, 0),
			new Color(128, 128, 0), new Color(0, 128, 0), new Color(128, 0, 128), new Color(0, 128, 128),
			new Color(0, 0, 128) };
}
