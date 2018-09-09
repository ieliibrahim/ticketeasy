package com.ieli.tieasy.util.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import com.ieli.tieasy.util.StaticData;

public class ComboBoxRenderer extends JPanel implements ListCellRenderer {

	private static final long serialVersionUID = -1L;
	private Integer[] strings;

	JPanel textPanel;
	JLabel text;

	public ComboBoxRenderer(JComboBox combo) {

		textPanel = new JPanel();
		textPanel.setBorder(new LineBorder(Color.WHITE, 2));
		textPanel.setBackground(StaticData.THEME_ORANGE_COLOR);
		textPanel.setOpaque(true);
		textPanel.setCursor(StaticData.HAND_CURSOR);
		textPanel.setToolTipText("Font size");

		textPanel.add(this);
		text = new JLabel();
		text.setOpaque(true);
		text.setFont(combo.getFont());
		textPanel.add(text);
	}

	public void setStrings(Integer[] str) {
		strings = str;
	}

	public Integer[] getStrings() {
		return strings;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		if (isSelected) {
			setBackground(Color.WHITE);
		} else {
			setBackground(StaticData.THEME_ORANGE_COLOR);
		}

		text.setBackground(StaticData.THEME_ORANGE_COLOR);

		text.setText(value.toString());
		text.setForeground(Color.WHITE);
		return text;
	}
}