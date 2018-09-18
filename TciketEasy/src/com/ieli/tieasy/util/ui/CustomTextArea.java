package com.ieli.tieasy.util.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CustomTextArea extends JTextArea {

	private static final long serialVersionUID = 1L;

	private Font originalFont;
	private Color originalForeground;
	/**
	 * Grey by default*
	 */
	private Color placeholderForeground = Color.BLACK;
	private boolean textWrittenIn;

	public CustomTextArea() {
		setOpaque(false);
	}

	/**
	 * You can insert all constructors. I inserted only this one.*
	 */

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

	@Override
	protected void paintComponent(Graphics g) {
		if (!isOpaque()) {
			int w = getWidth() - 1;
			int h = getHeight() - 1;
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setPaint(UIManager.getColor("TextField.background"));
			g2.fillRoundRect(0, 0, w, h, 30, 30);
			g2.setPaint(Color.GRAY);
			g2.drawRoundRect(0, 0, w, h, 30, 30);
			g2.dispose();
		}
		super.paintComponent(g);
	}

	@Override
	public void updateUI() {
		super.updateUI();
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
	}
}