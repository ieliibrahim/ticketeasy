package com.ieli.tieasy;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ieli.tieasy.ui.TEMainFrame;

public class RunTicketEasy {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		TEMainFrame teMainFrame = new TEMainFrame();
		teMainFrame.setVisible(true);

	}

}
