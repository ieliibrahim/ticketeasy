package com.ieli.tieasy.util.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CustomTextField extends JTextField {
	private static final long serialVersionUID = 1L;
	private Font originalFont;
	private Color originalForeground;
	/**
	 * Grey by default*
	 */
	private Color placeholderForeground = Color.BLACK;
	private boolean textWrittenIn;

	private Shape shape;
	private static final int RADIUS = 7;

	/**
	 * You can insert all constructors. I inserted only this one.*
	 */
	public CustomTextField(int columns) {
		super(columns);
		setOpaque(false);
	}

	@Override
	public void setFont(Font f) {
		super.setFont(f);
		if (!isTextWrittenIn()) {
			originalFont = f;
		}
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (!isTextWrittenIn()) {
			originalForeground = fg;
		}
	}

	public Color getPlaceholderForeground() {
		return placeholderForeground;
	}

	public void setPlaceholderForeground(Color placeholderForeground) {
		this.placeholderForeground = placeholderForeground;
	}

	public boolean isTextWrittenIn() {
		return textWrittenIn;
	}

	public void setTextWrittenIn(boolean textWrittenIn) {
		this.textWrittenIn = textWrittenIn;
	}

	public void setPlaceholder(final String text) {

		this.customizeText(text);

		this.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				warn();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				warn();
			}

			public void warn() {
				if (getText().trim().length() != 0) {
					setFont(originalFont);
					setForeground(originalForeground);
					setTextWrittenIn(true);
				}

			}
		});

		this.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!isTextWrittenIn()) {
					setText("");
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().trim().length() == 0) {
					customizeText(text);
				}
			}

		});

	}

	private void customizeText(String text) {
		setText(text);
		/**
		 * If you change font, family and size will follow changes, while style
		 * will always be italic
		 **/
		setFont(new Font(getFont().getFamily(), Font.PLAIN, getFont().getSize()));
		setForeground(getPlaceholderForeground());
		setTextWrittenIn(false);
	}

	protected void paintComponent(Graphics g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
	}

	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, RADIUS, RADIUS);
		}
		return shape.contains(x, y);
	}
}