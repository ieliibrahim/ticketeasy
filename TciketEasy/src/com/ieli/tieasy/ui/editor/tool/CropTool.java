package com.ieli.tieasy.ui.editor.tool;

import java.awt.event.MouseEvent;

import com.ieli.tieasy.ui.editor.EditorImagePanel;
import com.ieli.tieasy.ui.editor.shape.Crop;

public class CropTool extends RectangleTool {

	public CropTool(EditorImagePanel area) {
		super(area);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (shape.width != 0 || shape.height != 0) {
			Crop cr = new Crop(e.getComponent().getForeground(), shape, this.area);
			this.area.addShape(cr);
		}

		this.shape = null;
	}

}
