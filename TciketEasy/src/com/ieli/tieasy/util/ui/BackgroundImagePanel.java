package com.ieli.tieasy.util.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image img;
	private Image scaled;
	private Color transColor = new Color(0, 0, 0, 175);

	public BackgroundImagePanel(Image img) {
		this.img = img;
	}

	@Override
	public void invalidate() {
		super.invalidate();
		int width = getWidth();
		int height = getHeight();

		if (width > 0 && height > 0) {
			scaled = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(this), img.getHeight(this));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(transColor);
		g.drawImage(scaled, 0, 0, null);
		g.fillRect(0, 0, scaled.getWidth(null), scaled.getHeight(null));
	}

}
