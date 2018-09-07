package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

public class Arrow extends Line {

	private Polygon arrowHead;

	private AffineTransform tx = new AffineTransform();

	public Arrow(Color foreground, Point start, Point end) {
		super(foreground, start, end);

		this.arrowHead = new Polygon();
		arrowHead.addPoint(0, 5);
		arrowHead.addPoint(-5, -5);
		arrowHead.addPoint(5, -5);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.foreground);
		g.drawLine(this.start.x, this.start.y, this.end.x, this.end.y);

		tx.setToIdentity();
		double angle = Math.atan2(this.end.y - this.start.y, this.end.x - this.start.x);
		tx.translate(this.end.x, this.end.y);
		tx.rotate((angle - Math.PI / 2d));

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setTransform(tx);
		g2d.fill(arrowHead);
		g2d.dispose();
	}
}
