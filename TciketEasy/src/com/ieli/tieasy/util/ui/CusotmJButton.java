package com.ieli.tieasy.util.ui;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;

public class CusotmJButton extends JButton {

	private static final long serialVersionUID = 1L;

	public CusotmJButton(final ImageIcon icon, final ImageIcon hoverIcon, final JPopupMenu popupMenu) {

		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorder(null);
		setMargin(new Insets(0, 0, 0, 0));
		setBackground(Color.BLACK);
		setIcon(icon);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

				if (popupMenu != null) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (popupMenu != null) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setIcon(icon);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setIcon(hoverIcon);

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});
	}
}
