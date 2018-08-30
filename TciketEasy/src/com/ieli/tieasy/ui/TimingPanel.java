package com.ieli.tieasy.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class TimingPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public TimingPanel() {

		setLayout(new BorderLayout(0, 0));

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(Color.WHITE);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth();
		int h = getHeight();
		Point sw = new Point(w / 16, h  / 2);
		Point ne = new Point(w * 15 / 16, h / 2);
		Line2D line = new Line2D.Double(sw, ne);
		g2.draw(line);
		drawArrowHead(g2, ne, sw, Color.WHITE);
	}

	private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color) {
		g2.setPaint(color);
		double dy = tip.y - tail.y;
		double dx = tip.x - tail.x;
		double theta = Math.atan2(dy, dx);
		double phi = Math.toRadians(20);
		double barb = 10;
		double x, y, rho = theta + phi;
		for (int j = 0; j < 2; j++) {
			x = tip.x - barb * Math.cos(rho);
			y = tip.y - barb * Math.sin(rho);
			g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
			rho = theta - phi;
		}
	}
}
