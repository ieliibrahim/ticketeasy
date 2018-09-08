package com.ieli.tieasy;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.ieli.tieasy.ui.TEMainFrame;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;

public class RunTicketEasy {

	public static void main(String[] args) {

		String appId = "tickitEasyAppId";
		boolean alreadyRunning;
		try {
			JUnique.acquireLock(appId);
			alreadyRunning = false;
		} catch (AlreadyLockedException e) {
			alreadyRunning = true;
		}
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
		if (!alreadyRunning) {

			TEMainFrame teMainFrame = new TEMainFrame();
			teMainFrame.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "The application is already running!!!");
		}

	}

}
