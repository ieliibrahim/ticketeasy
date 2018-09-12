package com.ieli.tieasy.ui.editor.tool;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import com.ieli.tieasy.ui.editor.EditorImagePanel;
import com.ieli.tieasy.ui.editor.shape.Line;

public class LineTool extends ToolAdapter {

	protected Point startPoint;

	protected Line2D line;

	public LineTool(EditorImagePanel area) {
		super(area);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.startPoint = this.limitBounds(e.getPoint());
		this.line = new Line2D.Double(new Point(this.startPoint.x, this.startPoint.y),
				new Point(this.startPoint.x, this.startPoint.y));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point end = limitBounds(e.getPoint());
		this.line.setLine(startPoint.x, startPoint.y, end.x, end.y);
		this.area.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.startPoint != null) {
			Point end = limitBounds(e.getPoint());
			Line line = new Line(e.getComponent().getForeground(), this.startPoint, end);
			this.area.addShape(line);
		}

		this.line = null;
	}

	@Override
	public void partial(Graphics g) {
		if (this.line != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(g.getColor());
			g2d.draw(line);
		}
	}

}
