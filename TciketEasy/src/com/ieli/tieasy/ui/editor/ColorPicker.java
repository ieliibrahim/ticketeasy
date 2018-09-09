package com.ieli.tieasy.ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.ieli.tieasy.util.StaticData;
import com.ieli.tieasy.util.ui.CustomAppJButton;
import com.ieli.tieasy.util.ui.ScreenConfig;

public class ColorPicker extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private Color drawingColor = Color.BLACK;

	public ColorPicker(EditDraftDlg editDraftDlg) {
		setUndecorated(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ScreenConfig.setDialogSizeCustom(this, 500, 500);
		ScreenConfig.setDialogPositionCenter(this, editDraftDlg);
		getContentPane().setBackground(StaticData.HEADER_FOOTER_COLOR);
		contentPanel.setBackground(StaticData.HEADER_FOOTER_COLOR);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(3, 5, 10, 10));

		for (Color color : StaticData.PRIMARY_COLORS) {
			JLabel lblColor = new JLabel();
			lblColor.setBackground(color);
			lblColor.setOpaque(true);
			lblColor.setBorder(new LineBorder(Color.WHITE, 2));
			contentPanel.add(lblColor);
			lblColor.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {

				}

				@Override
				public void mousePressed(MouseEvent e) {
					drawingColor = lblColor.getBackground();
				}

				@Override
				public void mouseExited(MouseEvent e) {

				}

				@Override
				public void mouseEntered(MouseEvent e) {

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					drawingColor = lblColor.getBackground();

				}
			});
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		CustomAppJButton okButton = new CustomAppJButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editDraftDlg.setDrawingColor(drawingColor);
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		CustomAppJButton cancelButton = new CustomAppJButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();

			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		buttonPane.setBackground(StaticData.HEADER_FOOTER_COLOR);
	}

}
