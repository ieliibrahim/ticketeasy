package com.ieli.tieasy.ui.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.JPanel;

import com.ieli.tieasy.ui.editor.domain.ImageDrawing;
import com.ieli.tieasy.ui.editor.shape.Drawable;
import com.ieli.tieasy.ui.editor.tool.ColorTool;
import com.ieli.tieasy.ui.editor.tool.LineTool;
import com.ieli.tieasy.ui.editor.tool.TextTool;
import com.ieli.tieasy.ui.editor.tool.ToolAdapter;

public class EditorImagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ImageDrawing drawing = new ImageDrawing();
	private ToolAdapter tool;
	private Color color = Color.BLACK;

	public EditorImagePanel(BufferedImage image) {
		this.drawing.setImage(image);
		this.setBackground(Color.WHITE);
		this.setTool(new LineTool(this));
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}

	@Override
	protected void paintComponent(Graphics g) {
		this.setForeground(color);
		this.setColor(color);
		this.setBackground(new Color(0, 0, 0, 255));
		super.paintComponent(g);

		BufferedImage tmp = new BufferedImage(this.drawing.getWidth(), this.drawing.getHeight(),
				this.drawing.getType());
		Graphics gd = tmp.getGraphics();
		gd.drawImage(this.drawing.getImage(), 0, 0, null);
		gd.dispose();

		g.drawImage(tmp, 0, 0, this);

		for (Drawable d : this.drawing.getShapes()) {
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
		this.drawing.add(shape);
		this.repaint();
	}

	public void undo() {
		if (this.drawing.hasShapes()) {
			Drawable pop = this.drawing.pop();
			ImageDrawing undo = pop.undo();
			if (undo != null) {
				this.drawing = undo;
			}
			this.repaint();
		}
	}

	public void save(BufferedImage image) {
		if (this.drawing.hasShapes()) {
			Graphics g = image.getGraphics();
			for (Drawable shape : this.drawing.getShapes()) {
				shape.draw(g);
			}
			g.dispose();
		}
	}

	public void clear() {
		this.drawing.clear();
		this.repaint();
	}

	public void setColor(Color color) {
		this.color = color;
		this.setForeground(color);
	}
	
	public Color getColor() {
		return this.color;
	}

	public ImageDrawing getDrawing() {
		return this.drawing;
	}

	public void setDrawing(ImageDrawing drawing) {
		this.drawing = drawing;
	}

}
