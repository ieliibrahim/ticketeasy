package com.ieli.tieasy.ui.editor.tool;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.ieli.tieasy.ui.editor.EditorImagePanel;
import com.ieli.tieasy.ui.editor.shape.Rect;

public class RectangleTool extends ToolAdapter {

	private Point startPoint;

	protected Rectangle shape;

	public RectangleTool(EditorImagePanel area) {
		super(area);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startPoint = e.getPoint();
		shape = new Rectangle();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = Math.min(startPoint.x, e.getX());
		int y = Math.min(startPoint.y, e.getY());
		int width = Math.abs(startPoint.x - e.getX());
		int height = Math.abs(startPoint.y - e.getY());

		int limitWidth = x + width;
		int limitHeight = y + height;

		int w = this.area.getDrawing().getWidth();
		int h = this.area.getDrawing().getHeight();

		if (limitWidth > w) {
			width -= limitWidth - w;
		}

		if (limitHeight > h) {
			height -= limitHeight - h;
		}

		this.shape.setBounds(x < 0 ? 0 : x, y < 0 ? 0 : y, width, height);
		this.area.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (shape.width != 0 || shape.height != 0) {
			Rect cr = new Rect(e.getComponent().getForeground(), shape);
			this.area.addShape(cr);
		}

		this.shape = null;
	}

	@Override
	public void partial(Graphics g) {
		if (shape != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(g.getColor());
			g2d.draw(shape);
		}
	}

}
