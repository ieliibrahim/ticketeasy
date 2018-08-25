package com.ieli.tieasy.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class SingleDraftPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Image img;

	public SingleDraftPanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public SingleDraftPanel(Image img) {
		setBorder(new LineBorder(Color.WHITE, 2));
		setLayout(new MigLayout("", "[]", "[]"));

		this.img = img;
		Dimension size = new Dimension(300, 200);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}

}
