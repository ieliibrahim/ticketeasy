package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Rect extends Drawing {
	private Rectangle rectangle;

	public Rect(Color foreground, Rectangle rectangle) {
		super(foreground);
		this.rectangle = rectangle;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(this.foreground);
		g.drawRect(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height);
	}
}
