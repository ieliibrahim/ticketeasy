package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.ieli.tieasy.util.StaticData;

public class BlurRect extends Drawing {
	private Rectangle rectangle;

	public BlurRect(Color foreground, Rectangle rectangle) {
		super(foreground);
		this.rectangle = rectangle;
	}

	public void draw(Graphics g) {
		g.setColor(StaticData.BLUR_COLOR);
		g.fillRect(this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height);
	}
}