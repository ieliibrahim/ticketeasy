package com.ieli.tieasy.ui.editor.domain;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Stack;

import com.ieli.tieasy.ui.editor.shape.Drawable;

public class ImageDrawing implements Serializable {

	private static final long serialVersionUID = -6330346498051204399L;

	private Stack<Drawable> shapes = new Stack<>();

	private BufferedImage image;

	public ImageDrawing() {
		this(null);
	}

	public ImageDrawing(BufferedImage image) {
		this.image = image;
	}

	public ImageDrawing(BufferedImage image, Drawable... shapes) {
		this.image = image;
		if (shapes != null && shapes.length > 0) {
			for (Drawable d : shapes) {
				this.shapes.add(d);
			}
		}
	}

	public Stack<Drawable> getShapes() {
		return shapes;
	}

	public void setShapes(Stack<Drawable> shapes) {
		this.shapes = shapes;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getWidth() {
		return this.image.getWidth();
	}

	public int getHeight() {
		return this.image.getHeight();
	}

	public int getType() {
		return this.image.getType();
	}

	public void add(Drawable shape) {
		this.shapes.add(shape);
	}

	public boolean hasShapes() {
		return this.shapes != null && !this.shapes.isEmpty();
	}

	public Drawable pop() {
		return this.shapes.pop();
	}

	public void clear() {
		this.shapes.clear();
	}
}
