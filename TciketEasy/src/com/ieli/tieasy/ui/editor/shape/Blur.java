package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import com.ieli.tieasy.ui.editor.EditorImagePanel;

public class Blur extends Drawing {

	private Rectangle rectangle;
	private EditorImagePanel area;
	private BufferedImage blurred;

	public Blur(EditorImagePanel area, Rectangle rectangle) {
		super(Color.white);
		this.rectangle = rectangle;
		this.area = area;
	}

	@Override
	public void draw(Graphics g) {
		if (blurred == null) {
			g.setColor(this.foreground);
			blurred = this.area.getDrawing().getImage().getSubimage(this.rectangle.x, this.rectangle.y,
					this.rectangle.width, this.rectangle.height);
			float data[] = { 0.0625f, 0.125f, 0.0625f, 0.125f, 0.25f, 0.125f, 0.0625f, 0.125f, 0.0625f };
			Kernel kernel = new Kernel(3, 3, data);
			ConvolveOp convolve = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

			for (int i = 0; i < 25; i++) {
				blurred = convolve.filter(blurred, null);
			}

		}
		g.drawImage(blurred, this.rectangle.x, this.rectangle.y, this.rectangle.width, this.rectangle.height, null);
	}

}
