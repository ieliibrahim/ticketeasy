package com.ieli.tieasy.ui.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.JPanel;

import com.ieli.tieasy.ui.editor.shape.Drawable;
import com.ieli.tieasy.ui.editor.tool.ColorTool;
import com.ieli.tieasy.ui.editor.tool.LineTool;
import com.ieli.tieasy.ui.editor.tool.TextTool;
import com.ieli.tieasy.ui.editor.tool.ToolAdapter;

public class EditorImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Stack<Drawable> shapes = new Stack<>();
	private BufferedImage image;
	private ToolAdapter tool;
	private Color color = Color.BLACK;

	public EditorImagePanel() {
		this.setTool(new LineTool(this));
		this.setFocusable(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		this.setForeground(color);
		this.setColor(color);
		this.setBackground(new Color(0, 0, 0, 255));
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);

		for (Drawable d : shapes) {
			d.draw(g);
		}

		tool.partial(g);
	}

	public void setTool(ToolAdapter tool) {
		if (!(tool instanceof ColorTool)) {
			this.removeMouseListener(this.tool);
			this.removeMouseMotionListener(this.tool);
			this.tool = tool;
			this.addMouseListener(tool);
			this.addMouseMotionListener(tool);
			if (tool instanceof TextTool) {
				KeyListener listener = ((TextTool) tool).getListener();
				if (!Arrays.asList(this.getKeyListeners()).contains(listener)) {
					this.addKeyListener(listener);
				}
			}
		}
	}

	public void addShape(Drawable shape) {
		shapes.add(shape);
		this.repaint();
	}

	public void undo() {
		if (this.shapes != null && !this.shapes.isEmpty()) {
			this.shapes.pop();
			this.repaint();
		}
	}

	public void setImage(BufferedImage image) {
		this.save(this.image);
		this.image = image;
		this.shapes = new Stack<>();
		this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		this.repaint();
	}

	public void save(BufferedImage image) {
		if (this.shapes != null && !this.shapes.isEmpty()) {
			Graphics g = image.getGraphics();
			for (Drawable shape : shapes) {
				shape.draw(g);
			}
			g.dispose();
		}
	}

	public void clear() {
		shapes.clear();
		this.repaint();
	}

	public void setColor(Color color) {
		this.color = color;
		this.setForeground(color);
	}

	public BufferedImage getImage() {
		return image;
	}
}
