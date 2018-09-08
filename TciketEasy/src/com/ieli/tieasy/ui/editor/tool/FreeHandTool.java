package com.ieli.tieasy.ui.editor.tool;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.ieli.tieasy.ui.editor.EditorImagePanel;
import com.ieli.tieasy.ui.editor.shape.FreeHand;

public class FreeHandTool extends ToolAdapter {

	private FreeHand line;

	public FreeHandTool(EditorImagePanel area) {
		super(area);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.line = new FreeHand(e.getComponent().getForeground());
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		this.line.addPoint(event.getX(), event.getY());
		this.area.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		this.area.addShape(this.line);
		this.line = null;
	}

	@Override
	public void partial(Graphics g) {
		if (this.line != null) {
			this.line.draw(g);
		}
		g.dispose();
	}
}
