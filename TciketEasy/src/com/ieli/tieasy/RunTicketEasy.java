package com.ieli.tieasy;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ieli.tieasy.config.AppConfig;
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

		ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

		TEMainFrame teMainFrame = new TEMainFrame(appContext);
		teMainFrame.setVisible(true);

	}

}
