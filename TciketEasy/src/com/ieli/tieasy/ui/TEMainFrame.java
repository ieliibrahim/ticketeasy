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
import java.util.logging.Handler;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.springframework.context.ApplicationContext;

import com.ieli.tieasy.model.Ticket;
import com.ieli.tieasy.model.User;
import com.ieli.tieasy.service.caputre.IKeyboardCapture;
import com.ieli.tieasy.service.caputre.IMouseCapture;
import com.ieli.tieasy.service.drafts.IDraftsService;
import com.ieli.tieasy.service.tickets.ITicketsService;
import com.ieli.tieasy.service.user.IUserService;
import com.ieli.tieasy.util.AppUtils;
import com.ieli.tieasy.util.CusotmJButton;
import com.ieli.tieasy.util.CustomTextArea;
import com.ieli.tieasy.util.CustomTextField;
import com.ieli.tieasy.util.ScreenConfig;
import com.ieli.tieasy.util.StackTraceHandler;
import com.ieli.tieasy.util.StaticData;

import net.miginfocom.swing.MigLayout;

public class TEMainFrame extends JFrame {

	final static Logger logger = Logger.getLogger(TEMainFrame.class);

	private Ticket dbTicket;
	private TrayIcon trayIcon;
	private SystemTray tray;

	private static final long serialVersionUID = 1L;
	private CustomTextField ticketTitleTextField;
	private CusotmJButton btnHelp;
	private CusotmJButton btnExit;

	private IUserService iUserService;

	private IMouseCapture iMouseCaptureService;
	private IKeyboardCapture iKeyboardCaptureService;

	private ITicketsService iTicketsService;

	private IDraftsService iDraftsService;

	private JButton btnSubmit;

	private TrayPopupMenu trayPopupMenu;

	private CustomTextArea descriptionTextArea;

	private JPanel ticketsCarouselPnl;

	public TEMainFrame(final ApplicationContext appContext) {

		disableNativeHookLogging();

		setUndecorated(true);
		setResizable(false);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

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

		JPanel ticketsPnl = new JPanel();
		ticketsPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
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

		descriptionTextArea = new CustomTextArea();
		descriptionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		descriptionTextArea.setBorder(new LineBorder(UIManager.getColor("TextField.light")));
		descriptionTextArea.setPlaceholder("Let us know what the issue is...");
		descriptionTextArea.setRows(8);
		ticketInfoPnl.add(descriptionTextArea, "cell 0 1,grow");

		ticketsCarouselPnl = new JPanel();
		ticketsCarouselPnl.setBorder(null);
		ticketsCarouselPnl.setBackground(StaticData.TRANSPARENT_COLOR);
		ticketsCarouselPnl.setLayout(new MigLayout("aligny bottom", "[][]", "[][]"));

		ticketsCarouselPnl.setBorder(null);
		ticketsPnl.add(ticketsCarouselPnl, "cell 0 1,growx,aligny bottom");

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

		iUserService = (IUserService) appContext.getBean("iUserService");
		iMouseCaptureService = (IMouseCapture) appContext.getBean("iMouseCaptureService");
		iKeyboardCaptureService = (IKeyboardCapture) appContext.getBean("iKeyboardCaptureService");
		iTicketsService = (ITicketsService) appContext.getBean("iTicketsService");
		iDraftsService = (IDraftsService) appContext.getBean("iDraftsService");

		tray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(StaticData.TRAY_ICON.getImage(), "Ticket Easy");
		trayIcon.setImageAutoSize(true);

		addUserTicket();

		trayPopupMenu = new TrayPopupMenu(TEMainFrame.this, tray, trayIcon, iMouseCaptureService,
				iKeyboardCaptureService, iDraftsService, dbTicket.getTicketId(), ticketsCarouselPnl);

	}

	private void addUserTicket() {

		User user = iUserService.getUserByCred("imad", "imad123");
		Ticket ticket = new Ticket();
		ticket.setDescription(descriptionTextArea.getText());
		ticket.setEnabled(1);
		ticket.setTicketDateTime(AppUtils.getCurrentDateTime());
		ticket.setTitle(ticketTitleTextField.getText());
		ticket.setUserId(user.getUserId());
		dbTicket = iTicketsService.saveTicket(ticket);
		iMouseCaptureService.setTicketId(dbTicket.getTicketId());
		iMouseCaptureService.setUserId(user.getUserId());
		iKeyboardCaptureService.setTicketId(dbTicket.getTicketId());
		iKeyboardCaptureService.setUserId(user.getUserId());
		iKeyboardCaptureService.setTrayIcon(trayIcon);
		iKeyboardCaptureService.setTray(tray);
		iKeyboardCaptureService.setTeMainFrame(TEMainFrame.this);
		iKeyboardCaptureService.setiMouseCaptureService(iMouseCaptureService);
		iKeyboardCaptureService.setiKeyboardCaptureService(iKeyboardCaptureService);
		iKeyboardCaptureService.setiDraftsService(iDraftsService);
		iKeyboardCaptureService.setTicketsCarouselPnl(ticketsCarouselPnl);
	}

	private void disableNativeHookLogging() {
		java.util.logging.Logger glLogger = java.util.logging.Logger
				.getLogger(GlobalScreen.class.getPackage().getName());
		glLogger.setLevel(java.util.logging.Level.OFF);

		Handler[] handlers = java.util.logging.Logger.getLogger("").getHandlers();
		for (int i = 0; i < handlers.length; i++) {
			handlers[i].setLevel(java.util.logging.Level.OFF);
		}
	}

	private void addToSystemTry() {

		if (SystemTray.isSupported()) {

			try {
				GlobalScreen.registerNativeHook();
				GlobalScreen.addNativeMouseListener(iMouseCaptureService);
				GlobalScreen.addNativeKeyListener(iKeyboardCaptureService);

			} catch (NativeHookException e1) {
				logger.error(StackTraceHandler.getErrString(e1));
			}

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
