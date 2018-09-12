package com.ieli.tieasy.ui.editor.tool;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

import com.ieli.tieasy.ui.editor.EditorImagePanel;

public abstract class ToolAdapter extends MouseAdapter {

	public abstract void partial(Graphics g);

	protected final EditorImagePanel area;

	public ToolAdapter(EditorImagePanel area) {
		this.area = area;
	}

	public void execute(ActionEvent e) {

	}

	protected Point limitBounds(Point p) {
		int width = area.getDrawing().getWidth();
		int height = area.getDrawing().getHeight();
		int x = (int) (p.x < 0 ? 0 : p.x > width ? width : p.x);
		int y = (int) (p.y < 0 ? 0 : p.y > height ? height : p.y);
		return new Point(x, y);
	}

}
