package com.ieli.tieasy.util.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

import com.ieli.tieasy.util.StaticData;

public class CustomAppJButton extends JButton {

	private static final long serialVersionUID = 1L;

	Shape shape;
	private static final int RADIUS = 7;

	public CustomAppJButton(String text) {
		super(text);

		setFont(new Font("Tahoma", Font.BOLD, 18));
		setContentAreaFilled(false);
		setOpaque(false);
		setForeground(Color.WHITE);
		setBackground(StaticData.THEME_ORANGE_COLOR);
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
				setBackground(StaticData.THEME_ORANGE_COLOR);
				setForeground(Color.WHITE);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setForeground(StaticData.THEME_ORANGE_COLOR);
				setBackground(Color.WHITE);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

	}

	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
		}
		return shape.contains(x, y);
	}
}
