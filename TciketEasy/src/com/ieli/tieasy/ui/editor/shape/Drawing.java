package com.ieli.tieasy.ui.editor.shape;

import java.awt.Color;

public abstract class Drawing implements Drawable {

	protected Color foreground;

	public Drawing(Color foreground) {
		this.foreground = foreground;
	}

}
