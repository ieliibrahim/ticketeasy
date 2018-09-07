package com.ieli.tieasy.ui.editor.tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import com.ieli.tieasy.ui.editor.EditorImagePanel;
import com.ieli.tieasy.ui.editor.shape.Text;
import com.ieli.tieasy.util.ui.TextListener;

public class TextTool extends ToolAdapter {

	protected Point startPoint;
	
	protected Color foreground;

	protected final TextListener listener;

	public TextTool(EditorImagePanel area) {
		super(area);
		this.listener = new TextListener(this.area);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		String text = this.listener.getText();
		if (text != null && !text.isEmpty() && !"|".equals(text)) {
			System.out.println("Added");
			this.area.addShape(
					new Text(foreground, this.startPoint, text.substring(0, text.length() - 1)));
		}
		this.foreground = this.area.getForeground();
		this.startPoint = e.getPoint();
		this.area.repaint();
		this.area.requestFocus();
		this.area.grabFocus();
		this.listener.setText("|");
	}

	@Override
	public void partial(Graphics g) {
		if (this.listener.getText() != null && !this.listener.getText().isEmpty()) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setColor(this.foreground);
			g2d.drawString(this.listener.getText(), this.startPoint.x, this.startPoint.y);
		}
	}

	public TextListener getListener() {
		return listener;
	}

}
