package com.ieli.tieasy.util.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.ieli.tieasy.ui.editor.EditorImagePanel;

public class TextListener extends KeyAdapter {

	private String text = "|";

	private final EditorImagePanel area;

	public TextListener(EditorImagePanel area) {
		this.area = area;
	}

	@Override
	public void keyTyped(KeyEvent e) {

		String pressedValue = String.valueOf(e.getKeyChar());

		if (e.getKeyChar() == '\b') {
			this.text = this.text.substring(0, this.text.length() - 2).concat("").concat("|");
		} else {
			this.text = this.text.substring(0, this.text.length() - 1).concat(pressedValue).concat("|");
		}

		this.area.repaint();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
