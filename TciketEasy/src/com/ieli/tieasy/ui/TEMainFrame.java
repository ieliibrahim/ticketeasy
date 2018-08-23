package com.ieli.tieasy.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.ieli.tieasy.service.user.IUserService;
import com.ieli.tieasy.util.CusotmJButton;
import com.ieli.tieasy.util.CustomTextArea;
import com.ieli.tieasy.util.CustomTextField;
import com.ieli.tieasy.util.ImagePanel;
import com.ieli.tieasy.util.ScreenConfig;
import com.ieli.tieasy.util.StackTraceHandler;
import com.ieli.tieasy.util.StaticData;

import net.miginfocom.swing.MigLayout;

public class TEMainFrame extends JFrame {

	final static Logger logger = Logger.getLogger(TEMainFrame.class);

	private TrayIcon trayIcon;
	private SystemTray tray;

	private static final long serialVersionUID = 1L;
	private CustomTextField ticketTitleTextField;
	private CusotmJButton btnHelp;
	private CusotmJButton btnExit;

	private IUserService iUserService;

	private JButton btnSubmit;

	private TrayPopupMenu trayPopupMenu;

	public TEMainFrame(final ApplicationContext appContext) {
		tray = SystemTray.getSystemTray();

		trayIcon = new TrayIcon(StaticData.TRAY_ICON.getImage(), "Ticket Easy");
		trayIcon.setImageAutoSize(true);
		trayPopupMenu = new TrayPopupMenu(TEMainFrame.this, tray, trayIcon);
		setUndecorated(true);
		setResizable(false);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		iUserService = (IUserService) appContext.getBean("iUserService");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ticket Easy");
		ScreenConfig.setFrameSizeMax(this);

		JPanel mainPnl = new JPanel();
		mainPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		getContentPane().add(mainPnl, BorderLayout.CENTER);
		mainPnl.setLayout(new MigLayout("", "[grow]", "[][grow,fill][]"));

		JPanel headerPnl = new JPanel();
		headerPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		mainPnl.add(headerPnl, "cell 0 0,grow");
		headerPnl.setLayout(new MigLayout("", "[grow][grow]", "[grow]"));

		JPanel titlePnl = new JPanel();
		headerPnl.add(titlePnl, "cell 0 0,alignx left,growy");
		titlePnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		titlePnl.setLayout(new MigLayout("", "[]", "[]"));

		JLabel lblEasy = new JLabel("Easy");
		lblEasy.setIcon(StaticData.LOGO_IMG);
		titlePnl.add(lblEasy, "cell 0 0,alignx left");

		JPanel helpClosePnl = new JPanel();
		helpClosePnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		headerPnl.add(helpClosePnl, "cell 1 0,alignx right,growy");
		helpClosePnl.setLayout(new MigLayout("", "[][][]", "[]"));

		HelpPopupMenu helpPopupMenu = new HelpPopupMenu();

		btnHelp = new CusotmJButton(StaticData.ICON_HELP, StaticData.ICON_HELP_HOVER, helpPopupMenu);
		btnHelp.setCursor(StaticData.HAND_CURSOR);
		helpClosePnl.add(btnHelp, "cell 0 0");

		btnExit = new CusotmJButton(StaticData.ICON_EXIT, StaticData.ICON_EXIT_HOVER, null);
		btnExit.setCursor(StaticData.HAND_CURSOR);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				addToSystemTry();
			}

		});
		helpClosePnl.add(btnExit, "cell 1 0");

		Image pnlImg = StaticData.BG_IMAGE.getImage();

		ImagePanel ticketsPnl = new ImagePanel(pnlImg);
		mainPnl.add(ticketsPnl, "cell 0 1,grow");
		ticketsPnl.setLayout(new MigLayout("", "[grow]", "[][grow,fill]"));

		JPanel ticketInfoPnl = new JPanel();
		ticketsPnl.add(ticketInfoPnl, "cell 0 0,grow");
		ticketInfoPnl.setBackground(StaticData.TRANSPARENT_COLOR);
		ticketInfoPnl.setLayout(new MigLayout("", "[grow]", "[30.00][grow][grow]"));

		ticketTitleTextField = new CustomTextField(10);
		ticketTitleTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ticketTitleTextField.setPlaceholder("Please give your ticket a title");
		ticketInfoPnl.add(ticketTitleTextField, "cell 0 0,growx");

		CustomTextArea textArea = new CustomTextArea();
		textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textArea.setBorder(new LineBorder(UIManager.getColor("TextField.light")));
		textArea.setPlaceholder("Let us know what the issue is...");
		textArea.setRows(4);
		ticketInfoPnl.add(textArea, "cell 0 1,grow");

		JPanel ticketsCarouselPnl = new JPanel();
		ticketsCarouselPnl.setBackground(StaticData.TRANSPARENT_COLOR);
		ticketsPnl.add(ticketsCarouselPnl, "cell 0 1,grow");
		ticketsCarouselPnl.setLayout(new MigLayout("", "[]", "[]"));

		JPanel footerPnl = new JPanel();
		footerPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		mainPnl.add(footerPnl, "cell 0 2,grow");
		footerPnl.setLayout(new MigLayout("", "[grow][][grow]", "[grow]"));

		JPanel savedDraftsPnl = new JPanel();
		savedDraftsPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		footerPnl.add(savedDraftsPnl, "cell 0 0,alignx left,growy");
		savedDraftsPnl.setLayout(new MigLayout("", "[]", "[][]"));

		JPanel saveSubmitPnl = new JPanel();
		saveSubmitPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		footerPnl.add(saveSubmitPnl, "cell 2 0,alignx right,growy");
		saveSubmitPnl.setLayout(new MigLayout("", "[][][]", "[][]"));

		btnSubmit = new JButton("SUBMIT");
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSubmit.setContentAreaFilled(false);
		btnSubmit.setOpaque(true);
		btnSubmit.setForeground(Color.WHITE);
		btnSubmit.setBackground(StaticData.THEME_ORANGE_COLOR);
		btnSubmit.setCursor(StaticData.HAND_CURSOR);

		btnSubmit.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnSubmit.setBackground(StaticData.THEME_ORANGE_COLOR);
				btnSubmit.setForeground(Color.WHITE);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnSubmit.setForeground(StaticData.THEME_ORANGE_COLOR);
				btnSubmit.setBackground(Color.WHITE);
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		saveSubmitPnl.add(btnSubmit, "cell 0 1");

	}

	private void addToSystemTry() {
		if (SystemTray.isSupported()) {

			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if (e.isPopupTrigger()) {
						trayPopupMenu.setLocation(e.getX(), e.getY());
						trayPopupMenu.setInvoker(trayPopupMenu);
						trayPopupMenu.setVisible(true);
					}
				}
			});

			try {
				tray.add(trayIcon);
				setVisible(false);
			} catch (AWTException ex) {
				logger.error(StackTraceHandler.getErrString(ex));
			}

		}
	}
}
