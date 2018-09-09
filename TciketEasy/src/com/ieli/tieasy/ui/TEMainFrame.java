package com.ieli.tieasy.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Handler;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.ieli.tieasy.model.api.Incident;
import com.ieli.tieasy.model.api.Upload;
import com.ieli.tieasy.service.caputre.IKeyboardCapture;
import com.ieli.tieasy.service.caputre.IMouseCapture;
import com.ieli.tieasy.service.caputre.impl.KeyboardCaptureImpl;
import com.ieli.tieasy.service.caputre.impl.MouseCaptureImpl;
import com.ieli.tieasy.service.restapi.IAPICaller;
import com.ieli.tieasy.service.restapi.impl.APICallerImpl;
import com.ieli.tieasy.service.util.IUtilityService;
import com.ieli.tieasy.service.util.impl.UtilityServiceImpl;
import com.ieli.tieasy.util.AppUtils;
import com.ieli.tieasy.util.StackTraceHandler;
import com.ieli.tieasy.util.StaticData;
import com.ieli.tieasy.util.logs.LogPackager;
import com.ieli.tieasy.util.ui.BackgroundImagePanel;
import com.ieli.tieasy.util.ui.CusotmJButton;
import com.ieli.tieasy.util.ui.CustomAppJButton;
import com.ieli.tieasy.util.ui.CustomTextArea;
import com.ieli.tieasy.util.ui.CustomTextField;
import com.ieli.tieasy.util.ui.ScreenConfig;

import net.miginfocom.swing.MigLayout;

public class TEMainFrame extends JFrame {

	final static Logger logger = Logger.getLogger(TEMainFrame.class);

	private TrayIcon trayIcon;
	private SystemTray tray;

	private static final long serialVersionUID = 1L;
	private CustomTextField ticketTitleTextField;
	private CusotmJButton btnHelp;
	private CusotmJButton btnExit;

	private IMouseCapture iMouseCaptureService = new MouseCaptureImpl();
	private IKeyboardCapture iKeyboardCaptureService = new KeyboardCaptureImpl();
	private IAPICaller iAPICallerService = new APICallerImpl();
	private IUtilityService iUtilityService = new UtilityServiceImpl();

	private CustomAppJButton btnSubmit;

	private TrayPopupMenu trayPopupMenu;

	private CustomTextArea descriptionTextArea;

	private JPanel ticketsCarouselPnl;

	private TimingPanel timingPnl;

	public TEMainFrame() {

		disableNativeHookLogging();
		setIconImage(StaticData.TRAY_ICON.getImage());
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

		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource(StaticData.BG_IMAGE));
		} catch (IOException e) {
			logger.error(StackTraceHandler.getErrString(e));
		}

		BackgroundImagePanel ticketsPnl = new BackgroundImagePanel(img);
		ticketsPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		mainPnl.add(ticketsPnl, "cell 0 1,grow");
		ticketsPnl.setLayout(new MigLayout("", "[grow]", "[][grow,fill][]"));

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
		ticketsCarouselPnl.setLayout(new GridLayout(1, 0, 10, 0));

		ticketsPnl.add(ticketsCarouselPnl, "cell 0 1,grow");

		JPanel footerPnl = new JPanel();
		footerPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		mainPnl.add(footerPnl, "cell 0 2,grow");
		footerPnl.setLayout(new MigLayout("", "[grow][][grow]", "[grow]"));

		JPanel saveSubmitPnl = new JPanel();
		saveSubmitPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		footerPnl.add(saveSubmitPnl, "cell 2 0,alignx right,growy");
		saveSubmitPnl.setLayout(new MigLayout("", "[][][]", "[][]"));

		btnSubmit = new CustomAppJButton("SUBMIT");

		btnSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (ticketsCarouselPnl.getComponentCount() > 0) {

					if (ticketTitleTextField.getText().isEmpty() || descriptionTextArea.getText().isEmpty()
							|| descriptionTextArea.getText().equals("Let us know what the issue is...")
							|| ticketTitleTextField.getText().equals("Please give your ticket a title")) {
						CustomOptionPane.showMessageDialog(TEMainFrame.this, "Please add title and description!!!",
								"Missing title or description", JOptionPane.ERROR_MESSAGE);
					} else {

						File dir = new File("drafts/");

						File tempDir = new File("temp/");
						if (!tempDir.exists()) {
							tempDir.mkdir();
						}

						for (File orgFile : dir.listFiles()) {
							File newTempFile = new File(tempDir + "/" + orgFile.getName());
							orgFile.renameTo(newTempFile);
						}

						final File[] files = tempDir.listFiles();
						Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);

						new Thread(new Runnable() {

							@Override
							public void run() {
								Incident incident = iAPICallerService.createIncident(ticketTitleTextField.getText(),
										descriptionTextArea.getText());

								String res = "";

								if (incident != null) {

									res += "Incident: " + incident.getNumber() + ", created successfuly\n";

									int imageIndex = 10;
									for (File file : files) {
										File newFile = new File(tempDir + "/image" + imageIndex + ".png");
										file.renameTo(newFile);
										Upload upload = iAPICallerService.uploadFile(newFile,
												incident.getIncidentResult().getSysId());
										if (upload != null) {
											res += "Image(" + newFile.getName() + ") uploaded successfuly\n";
										} else {
											res += "Error uploading image(" + newFile.getName() + ")\n";
										}

										imageIndex--;
									}

									LogPackager packager = new LogPackager();
									try {
										File temp = File.createTempFile("log_package", ".zip");
										File logArchive = packager.packageLog(temp.getParent(), temp.getAbsolutePath());
										Upload upload = iAPICallerService.uploadFile(logArchive,
												incident.getIncidentResult().getSysId());

										if (upload != null) {
											res += "Archive(" + temp.getName() + ") uploaded successfuly\n";
										} else {
											res += "Error uploading archive(" + temp.getName() + ")\n";
										}

									} catch (IOException e2) {
										logger.error(StackTraceHandler.getErrString(e2));
									}

									BufferedWriter writer = null;
									try {
										File tempSystemFile = File.createTempFile("systemInfo", ".txt");
										writer = new BufferedWriter(new FileWriter(tempSystemFile));
										writer.write(iUtilityService.getUserOSData().toString());
										writer.close();
										Upload upload = iAPICallerService.uploadFile(tempSystemFile,
												incident.getIncidentResult().getSysId());

										if (upload != null) {
											res += "System Info(" + tempSystemFile.getName()
													+ ") uploaded successfuly\n";
										} else {
											res += "Error uploading system info(" + tempSystemFile.getName() + ")\n";
										}

									} catch (IOException e2) {
										logger.error(StackTraceHandler.getErrString(e2));
									} finally {
										if (writer != null) {
											try {
												writer.close();
											} catch (IOException e) {
												logger.error(StackTraceHandler.getErrString(e));
											}
										}
									}

									logger.info(res);
								} else {
									res += "Error creating incident\n";
								}
								emptyTemp();
								ticketTitleTextField.setText("Please give your ticket a title");
								descriptionTextArea.setText("Let us know what the issue is...");
								ticketTitleTextField.setPlaceholder("Please give your ticket a title");
								descriptionTextArea.setPlaceholder("Let us know what the issue is...");
							}
						}).start();


						CustomOptionPane.showMessageDialog(TEMainFrame.this, "Ticket created successfully");

						addToSystemTry();
					}
				} else {
					CustomOptionPane.showMessageDialog(TEMainFrame.this, "Please add at least one screen shot!!!",
							"Missing screen shots", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		saveSubmitPnl.add(btnSubmit, "cell 0 1");

		tray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(StaticData.TRAY_ICON.getImage(), "Ticket Easy");
		trayIcon.setImageAutoSize(true);

		initSystemProperties();

		trayPopupMenu = new TrayPopupMenu(TEMainFrame.this, tray, trayIcon, iMouseCaptureService,
				iKeyboardCaptureService, ticketsCarouselPnl);

		timingPnl = new TimingPanel();
		timingPnl.setBackground(StaticData.TRANSPARENT_COLOR);
		ticketsPnl.add(timingPnl, "cell 0 2,growx,aligny top");
		timingPnl.setLayout(new BorderLayout(0, 0));
		JLabel oldestLbl = new JLabel(
				"<html><center>Oldest</center><br /><center>" + AppUtils.getCurrentDateTime() + "</center>");
		oldestLbl.setBackground(Color.BLACK);
		oldestLbl.setForeground(Color.WHITE);
		oldestLbl.setName("oldtime");
		timingPnl.add(oldestLbl, BorderLayout.EAST);

		JLabel newestLbl = new JLabel(
				"<html><center>Newest</center><br /><center>" + AppUtils.getCurrentDateTime() + "</center>");
		newestLbl.setBackground(Color.BLACK);
		newestLbl.setForeground(Color.WHITE);
		newestLbl.setName("newtime");
		timingPnl.add(newestLbl, BorderLayout.WEST);

		timingPnl.setVisible(false);

	}

	private void initSystemProperties() {

		iKeyboardCaptureService.setTrayIcon(trayIcon);
		iKeyboardCaptureService.setTray(tray);
		iKeyboardCaptureService.setTeMainFrame(TEMainFrame.this);
		iMouseCaptureService.setTeMainFrame(TEMainFrame.this);
		iKeyboardCaptureService.setiMouseCaptureService(iMouseCaptureService);
		iKeyboardCaptureService.setiKeyboardCaptureService(iKeyboardCaptureService);
		iKeyboardCaptureService.setTicketsCarouselPnl(ticketsCarouselPnl);
		iMouseCaptureService.setTicketsCarouselPnl(ticketsCarouselPnl);

		emptyDrafts();

		emptyTemp();
	}

	private void emptyTemp() {
		File tempDir = new File("temp/");
		if(tempDir != null) {
			if(tempDir.listFiles() != null) {
				
				for (File file : tempDir.listFiles()) {
					if (file.exists()) {
						file.delete();
					}
				}
			}
		}
	}

	private void emptyDrafts() {
		File draftsDir = new File("drafts/");
		for (File file : draftsDir.listFiles()) {
			if (file.exists()) {
				file.delete();
			}
		}
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

	public void addToSystemTry() {

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
