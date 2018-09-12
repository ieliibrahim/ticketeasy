package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;

import com.ieli.tieasy.ui.editor.domain.ImageDrawing;

public abstract class Drawing implements Drawable {

	protected Color foreground;

	protected ImageDrawing previous;

	public Drawing(Color foreground) {
		this.foreground = foreground;
	}

	public ImageDrawing undo() {
		return previous;
	}

}
