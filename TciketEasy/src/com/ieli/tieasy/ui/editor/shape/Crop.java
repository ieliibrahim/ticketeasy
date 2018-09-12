package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.ieli.tieasy.ui.editor.EditorImagePanel;
import com.ieli.tieasy.ui.editor.domain.ImageDrawing;

public class Crop extends Drawing {

	private Rectangle rectangle;
	private EditorImagePanel area;
	private boolean drawn;

	public Crop(Color foreground, Rectangle rectangle, EditorImagePanel area) {
		super(Color.BLACK);
		this.rectangle = rectangle;
		this.area = area;
		this.previous = this.area.getDrawing();
	}

	@Override
	public void draw(Graphics g) {
		if (!drawn) {
			ImageDrawing d = this.area.getDrawing();
			BufferedImage tmp = new BufferedImage(d.getWidth(), d.getHeight(), d.getType());
			Graphics gd = tmp.getGraphics();
			gd.drawImage(d.getImage(), 0, 0, null);
			for (Drawable shape : d.getShapes()) {
				if (!(shape instanceof Crop)) {
					shape.draw(gd);
				}
			}
			gd.dispose();

			BufferedImage img = tmp.getSubimage(this.rectangle.x, this.rectangle.y, this.rectangle.width,
					this.rectangle.height);
			g.drawImage(img, 0, 0, null);
			ImageDrawing drawing = new ImageDrawing(img, this);
			this.area.setSize(img.getWidth(), img.getHeight());
			this.area.setDrawing(drawing);
			this.drawn = true;
		}
	}

	@Override
	public ImageDrawing undo() {
		this.area.setSize(this.previous.getWidth(), this.previous.getHeight());
		this.area.undo();
		return this.previous;
	}
}
