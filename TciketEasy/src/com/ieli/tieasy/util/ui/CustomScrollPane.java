package com.ieli.tieasy.util.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CustomScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private Shape shape;
	private static final int RADIUS = 50;

	public CustomScrollPane(JTextArea jTextArea) {
		super(jTextArea);
		setOpaque(false);
		getViewport().setOpaque(false);
	}

	/**
	 * You can insert all constructors. I inserted only this one.*
	 */

	@Override
	public void setFont(Font f) {
		super.setFont(f);
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
	}

	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
		}
		return shape.contains(x, y);
	}
}