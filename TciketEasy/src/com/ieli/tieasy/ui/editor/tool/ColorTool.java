package com.ieli.tieasy.ui.editor.tool;

import java.awt.Color;
import java.awt.event.ActionEvent;

import com.ieli.tieasy.ui.editor.EditorImagePanel;

public class ColorTool extends LineTool {

	private Color color;

	public ColorTool(EditorImagePanel area) {
		super(area);
	}

	@Override
	public void execute(ActionEvent e) {
		this.area.setColor(color);
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
