package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Line extends Drawing {

	protected Point start;

	protected Point end;

	public Line(Color foreground, Point start, Point end) {
		super(foreground);
		this.start = start;
		this.end = end;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.foreground);
		g.drawLine(this.start.x, this.start.y, this.end.x, this.end.y);
	}

}
