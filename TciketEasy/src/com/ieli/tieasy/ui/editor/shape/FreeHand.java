package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class FreeHand extends Drawing {

	private List<Point> points = new LinkedList<>();

	public FreeHand(Color foreground) {
		super(foreground);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.foreground);
		for (int i = 1; i < this.points.size(); i++) {
			Point p1 = this.points.get(i - 1);
			Point p2 = this.points.get(i);
			g.drawLine(p1.x, p1.y, p2.x, p2.y);
		}
	}

	public void addPoint(int x, int y) {
		this.points.add(new Point(x, y));
	}

	public List<Point> getPoints() {
		return points;
	}

}
