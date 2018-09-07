package com.ieli.tieasy.util.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.ieli.tieasy.util.StaticData;

public class CusotmImageJButton extends JButton {

	private static final long serialVersionUID = 1L;

	public CusotmImageJButton(final String text) {

		setText(text);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setFont(new Font("Tahoma", Font.BOLD, 14));
		setContentAreaFilled(false);
		setOpaque(true);
		setForeground(StaticData.THEME_ORANGE_COLOR);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(StaticData.THEME_ORANGE_COLOR, 2),
				BorderFactory.createLineBorder(Color.WHITE, 5)));
		setBackground(Color.WHITE);
		setCursor(StaticData.HAND_CURSOR);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Color.WHITE);
				setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(StaticData.THEME_ORANGE_COLOR, 2),
						BorderFactory.createLineBorder(Color.WHITE, 5)));
				setForeground(StaticData.THEME_ORANGE_COLOR);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(Color.WHITE);
				setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2),
						BorderFactory.createLineBorder(StaticData.THEME_ORANGE_COLOR, 5)));
				setBackground(StaticData.THEME_ORANGE_COLOR);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

	}
}
