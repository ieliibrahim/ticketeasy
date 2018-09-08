package com.ieli.tieasy.ui.editor.tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.ieli.tieasy.ui.editor.EditorImagePanel;
import com.ieli.tieasy.ui.editor.shape.BlurRect;
import com.ieli.tieasy.util.StaticData;

public class BlurTool extends ToolAdapter {
	private Point startPoint;
	private Rectangle shape;

	public BlurTool(EditorImagePanel area) {
		super(area);
	}

	public void mousePressed(MouseEvent e) {
		this.startPoint = e.getPoint();
		this.shape = new Rectangle();
	}

	public void mouseDragged(MouseEvent e) {
		int x = Math.min(this.startPoint.x, e.getX());
		int y = Math.min(this.startPoint.y, e.getY());
		int width = Math.abs(this.startPoint.x - e.getX());
		int height = Math.abs(this.startPoint.y - e.getY());

		int limitWidth = x + width;
		int limitHeight = y + height;

		int w = this.area.getImage().getWidth();
		int h = this.area.getImage().getHeight();
		if (limitWidth > w) {
			width -= limitWidth - w;
		}
		if (limitHeight > h) {
			height -= limitHeight - h;
		}
		this.shape.setBounds(x < 0 ? 0 : x, y < 0 ? 0 : y, width, height);
		this.area.repaint();
	}

	public void mouseReleased(MouseEvent e) {
		if ((this.shape.width != 0) || (this.shape.height != 0)) {
			BlurRect cr = new BlurRect(StaticData.BLUR_COLOR, shape);
			this.area.addShape(cr);
		}
		this.shape = null;
	}

	public void partial(Graphics g) {
		if (this.shape != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.BLACK);
			g2d.draw(shape);
			g2d.dispose();
		}
	}
}
