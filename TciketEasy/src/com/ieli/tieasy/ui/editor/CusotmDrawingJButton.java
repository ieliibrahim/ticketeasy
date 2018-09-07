package com.ieli.tieasy.ui.editor;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import com.ieli.tieasy.util.StaticData;

public class CusotmDrawingJButton extends JButton {

	private static final long serialVersionUID = 1L;

	public CusotmDrawingJButton(final ImageIcon icon, final ImageIcon hoverIcon, String toolTip) {

		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorder(new LineBorder(Color.WHITE, 2));
		setBackground(StaticData.THEME_ORANGE_COLOR);
		setIcon(icon);
		setOpaque(true);
		setCursor(StaticData.HAND_CURSOR);
		setToolTipText(toolTip);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(new LineBorder(Color.WHITE, 2));
				setBackground(StaticData.THEME_ORANGE_COLOR);
				setIcon(icon);
			}

			@Override
			public void mouseEntered(MouseEvent e) {

				setBorder(new LineBorder(StaticData.THEME_ORANGE_COLOR, 2));
				setBackground(Color.WHITE);
				setIcon(hoverIcon);

			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}
}
