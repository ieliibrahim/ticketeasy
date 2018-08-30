package com.ieli.tieasy.util;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image img;

	public ImagePanel(Image img) {
		this.img = img;
	}

	public void paintComponent(Graphics g) {
		ImageDrawer.drawScaledImage(img, this, g);
	}

}
