package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

public class Text extends Drawing {

	protected Point start;

	protected String text;

	protected int fontSize;

	public Text(Color foreground, Point start, String text, int fontSize) {
		super(foreground);
		this.start = start;
		this.text = text;
		this.fontSize = fontSize;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setColor(this.foreground);
		g2d.setFont(new Font("Tahoma", Font.PLAIN, fontSize));
		g2d.drawString(this.text, this.start.x, this.start.y);
	}

}
