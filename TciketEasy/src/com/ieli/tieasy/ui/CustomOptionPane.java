package com.ieli.tieasy.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.ieli.tieasy.util.StaticData;
import com.ieli.tieasy.util.ui.CustomAppJButton;
import com.ieli.tieasy.util.ui.ScreenConfig;

import net.miginfocom.swing.MigLayout;

public class CustomOptionPane extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static int res = 1;

	public CustomOptionPane(Component component, Object html, String title, int messageType, String type) {
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ScreenConfig.setDialogSizeCustom(this, 500, 500);
		ScreenConfig.setDialogPositionCenter(this, component);
		getContentPane().setBackground(StaticData.HEADER_FOOTER_COLOR);
		contentPanel.setBackground(StaticData.HEADER_FOOTER_COLOR);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBorder(new LineBorder(StaticData.THEME_ORANGE_COLOR, 4));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[grow]", "[grow,fill]"));

		setTitle(title);

		JLabel lblNewLabel = new JLabel(html.toString());
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPanel.add(lblNewLabel, "cell 0 0,alignx center");

		JPanel buttonPane = new JPanel();
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		CustomAppJButton okButton = new CustomAppJButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				res = 0;
				dispose();
			}
		});

		buttonPane.add(okButton, "flowx");

		buttonPane.setLayout(new MigLayout("", "[59px,grow,center]", "[31px]"));

		buttonPane.setBackground(StaticData.HEADER_FOOTER_COLOR);

		if (!type.equals("messageDlg")) {

			CustomAppJButton cancelButton = new CustomAppJButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					res = 1;
					dispose();
				}
			});

			buttonPane.add(cancelButton, "cell 0 0");
		}
		buttonPane.setBorder(new LineBorder(StaticData.THEME_ORANGE_COLOR, 4));
	}

	public static int showConfirmDialog(Component parentComponent, Object html, String title, int messageType) {
		new CustomOptionPane(parentComponent, html, title, messageType, "confrim").setVisible(true);
		return res;
	}

	public static void showMessageDialog(JFrame parentComponent, Object html, String title, int messageType) {
		new CustomOptionPane(parentComponent, html, title, messageType, "messageDlg").setVisible(true);
	}

	public static void showMessageDialog(JFrame parentComponent, Object html) {
		new CustomOptionPane(parentComponent, html, "", 0, "messageDlg").setVisible(true);
	}
}
