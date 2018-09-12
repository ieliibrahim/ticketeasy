package com.ieli.tieasy.ui.editor.shape;

import java.awt.Graphics;

import com.ieli.tieasy.ui.editor.domain.ImageDrawing;


public interface Drawable {

	void draw(Graphics g);

	ImageDrawing undo();

}
