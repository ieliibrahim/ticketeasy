package com.ieli.tieasy.ui.editor.tool;

import java.awt.Point;
import java.awt.event.MouseEvent;

import com.ieli.tieasy.ui.editor.EditorImagePanel;
import com.ieli.tieasy.ui.editor.shape.Arrow;

public class ArrowTool extends LineTool {



	public ArrowTool(EditorImagePanel area) {
		super(area);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.startPoint != null) {
			Point end = limitBounds(e.getPoint());
			Arrow line = new Arrow(e.getComponent().getForeground(), this.startPoint, end);
			this.area.addShape(line);
		}

		this.line = null;
	}

}
