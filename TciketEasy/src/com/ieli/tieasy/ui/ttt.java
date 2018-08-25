package com.ieli.tieasy.ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.GridLayout;

public class ttt extends JPanel {

	/**
	 * Create the panel.
	 */
	public ttt() {
		setLayout(new MigLayout("", "[2px,grow]", "[2px,grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 0 0,grow");
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);

	}

}
