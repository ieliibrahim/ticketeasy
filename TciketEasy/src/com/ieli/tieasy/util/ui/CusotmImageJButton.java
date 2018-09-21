package com.ieli.tieasy.util.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
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
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(StaticData.THEME_ORANGE_COLOR, 2, true),
				BorderFactory.createLineBorder(Color.WHITE, 5, true)));
		setBackground(Color.WHITE);
		setCursor(StaticData.HAND_CURSOR);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				MouseListener[] redPanelMListeners = getParent().getMouseListeners();
				int x = e.getX() + getX();
				int y = e.getY() + getY();
				MouseEvent redPanelmEvt = new MouseEvent(getParent(), e.getID(), e.getWhen(), e.getModifiers(), x, y,
						e.getClickCount(), e.isPopupTrigger());
				for (MouseListener mouseListener : redPanelMListeners) {
					mouseListener.mouseEntered(redPanelmEvt);
				}
				setForeground(Color.WHITE);
				setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true),
						BorderFactory.createLineBorder(StaticData.THEME_ORANGE_COLOR, 5, true)));
				setBackground(StaticData.THEME_ORANGE_COLOR);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseEntered(e);
				MouseListener[] redPanelMListeners = getParent().getMouseListeners();
				int x = e.getX() + getX();
				int y = e.getY() + getY();
				MouseEvent redPanelmEvt = new MouseEvent(getParent(), e.getID(), e.getWhen(), e.getModifiers(), x, y,
						e.getClickCount(), e.isPopupTrigger());
				for (MouseListener mouseListener : redPanelMListeners) {
					mouseListener.mouseExited(redPanelmEvt);
				}

				setBackground(Color.WHITE);
				setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(StaticData.THEME_ORANGE_COLOR, 2, true),
						BorderFactory.createLineBorder(Color.WHITE, 5, true)));
				setForeground(StaticData.THEME_ORANGE_COLOR);

			}
		});

	}
}
