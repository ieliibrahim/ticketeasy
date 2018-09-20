package com.ieli.tieasy.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.log4j.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.ieli.tieasy.model.api.CreateIncidentInput;
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
import com.ieli.tieasy.util.ui.CustomScrollPane;
import com.ieli.tieasy.util.ui.CustomTextArea;
import com.ieli.tieasy.util.ui.CustomTextField;
import com.ieli.tieasy.util.ui.ScreenConfig;
import com.jcabi.aspects.Loggable;
import com.jcabi.aspects.RetryOnFailure;

import net.miginfocom.swing.MigLayout;

public class TEMainFrame extends JFrame {

	final static Logger logger = Logger.getLogger(TEMainFrame.class);

	private TrayIcon trayIcon;
	private SystemTray tray;

	private static final long serialVersionUID = 1L;
	private CusotmJButton btnHelp;
	private CusotmJButton btnExit;

	private IMouseCapture iMouseCaptureService = new MouseCaptureImpl();
	private IKeyboardCapture iKeyboardCaptureService = new KeyboardCaptureImpl();
	private IAPICaller iAPICallerService = new APICallerImpl();
	private IUtilityService iUtilityService = new UtilityServiceImpl();

	private CustomAppJButton btnSubmit;

	private TrayPopupMenu trayPopupMenu;

	private CustomTextField ticketTitleTextField;
	private CustomTextArea descriptionTextArea;

	private JPanel ticketsCarouselPnl;

	private TimingPanel timingPnl;

	private int thumbHeight;

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
		mainPnl.add(headerPnl, "cell 0 0,growx,aligny top");
		headerPnl.setLayout(new MigLayout("insets 0, wrap", "[grow][grow]", "[]"));

		JPanel titlePnl = new JPanel();
		headerPnl.add(titlePnl, "cell 0 0,alignx left,aligny top");
		titlePnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		titlePnl.setLayout(new MigLayout("", "[]", "[]"));

		JLabel lblEasy = new JLabel("");
		lblEasy.setIcon(StaticData.LOGO_IMG);
		titlePnl.add(lblEasy, "cell 0 0,alignx left");

		JPanel helpClosePnl = new JPanel();
		helpClosePnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		headerPnl.add(helpClosePnl, "cell 1 0,alignx right,aligny top");
		helpClosePnl.setLayout(new MigLayout("", "[]30px[][]", "[]"));

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
		ticketsPnl.setLayout(new MigLayout("", "[][grow,fill][]", "[][grow,baseline]20px[]"));

		JPanel ticketInfoPnl = new JPanel();
		ticketInfoPnl.setOpaque(false);
		ticketInfoPnl.setBackground(StaticData.TRANSPARENT_COLOR);
		ticketInfoPnl.setLayout(new MigLayout("", "[100px][grow][100px]", "[80.00][grow][grow]"));
		ticketsPnl.add(ticketInfoPnl, "cell 1 0,grow");

		ticketTitleTextField = new CustomTextField(10);
		ticketTitleTextField.setFont(StaticData.MAIN_FONT);
		ticketTitleTextField.setPlaceholder("Please give your ticket a title");
		ticketInfoPnl.add(ticketTitleTextField, "cell 1 0,growx");

		descriptionTextArea = new CustomTextArea();
		descriptionTextArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		descriptionTextArea.setPlaceholder("Let us know what the issue is...");
		descriptionTextArea.setRows(15);
		descriptionTextArea.setWrapStyleWord(true);
		descriptionTextArea.setLineWrap(true);

		CustomScrollPane scrollPane = new CustomScrollPane(descriptionTextArea);
		ticketInfoPnl.add(scrollPane, "cell 1 1,grow");

		ticketsCarouselPnl = new JPanel();
		ticketsCarouselPnl.setName("tcpnl222");
		ticketsCarouselPnl.setBorder(null);
		ticketsCarouselPnl.setBackground(StaticData.TRANSPARENT_COLOR);
		ticketsCarouselPnl.setLayout(new GridLayout(1, 10, 10, 0));

		ticketsPnl.add(ticketsCarouselPnl, "cell 1 1,aligny bottom");

		JPanel footerPnl = new JPanel();
		footerPnl.setBackground(StaticData.HEADER_FOOTER_COLOR);
		mainPnl.add(footerPnl, "cell 0 2,alignx right,growy");
		footerPnl.setLayout(new MigLayout("", "[97px]", "[29px]"));

		btnSubmit = new CustomAppJButton("SUBMIT");
		footerPnl.add(btnSubmit, "cell 0 0,alignx left,aligny top");

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

						runAPI(files, tempDir);

						CustomOptionPane.showMessageDialog(TEMainFrame.this, "Ticket created successfully");
						ticketTitleTextField.setText("Please give your ticket a title");
						descriptionTextArea.setText("Let us know what the issue is...");
						ticketTitleTextField.setPlaceholder("Please give your ticket a title");
						descriptionTextArea.setPlaceholder("Let us know what the issue is...");
						ticketsCarouselPnl.removeAll();
						ticketsCarouselPnl.invalidate();
						ticketsCarouselPnl.repaint();
						ticketsCarouselPnl.updateUI();
						addToSystemTry();
						try {
							updateTimeLine();
						} catch (IOException e1) {
							logger.error(StackTraceHandler.getErrString(e1));
						}
					}
				} else {
					CustomOptionPane.showMessageDialog(TEMainFrame.this, "Please add at least one screen shot!!!",
							"Missing screen shots", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		tray = SystemTray.getSystemTray();
		trayIcon = new TrayIcon(StaticData.TRAY_ICON.getImage(), "Ticket Easy");
		trayIcon.setImageAutoSize(true);

		trayPopupMenu = new TrayPopupMenu(TEMainFrame.this, tray, trayIcon, iMouseCaptureService,
				iKeyboardCaptureService, ticketsCarouselPnl);

		initSystemProperties();

		timingPnl = new TimingPanel();
		timingPnl.setBackground(StaticData.TRANSPARENT_COLOR);
		ticketsPnl.add(timingPnl, "cell 1 2,growx,aligny top");
		timingPnl.setLayout(new BorderLayout(0, 0));
		JLabel oldestLbl = new JLabel(
				"<html><center>Oldest</center><br /><center>" + AppUtils.getCurrentDateTime() + "</center>");
		oldestLbl.setBackground(Color.BLACK);
		oldestLbl.setForeground(Color.WHITE);
		oldestLbl.setName("oldtime");
		oldestLbl.setFont(StaticData.MAIN_FONT);
		timingPnl.add(oldestLbl, BorderLayout.EAST);

		JLabel newestLbl = new JLabel(
				"<html><center>Newest</center><br /><center>" + AppUtils.getCurrentDateTime() + "</center>");
		newestLbl.setBackground(Color.BLACK);
		newestLbl.setFont(StaticData.MAIN_FONT);
		newestLbl.setForeground(Color.WHITE);
		newestLbl.setName("newtime");
		timingPnl.add(newestLbl, BorderLayout.WEST);

		timingPnl.setVisible(false);
		pack();

		for (int i = 0; i < ticketsPnl.getComponentCount(); i++) {

			if (ticketsPnl.getComponent(i).getName() != null) {
				if (ticketsPnl.getComponent(i).getName().equals("tcpnl222")) {
					thumbHeight = ticketsPnl.getHeight() / 4;
				}
			}
		}

	}

	@RetryOnFailure(attempts = 3, delay = 5, unit = TimeUnit.MINUTES)
	@Loggable(Loggable.DEBUG)
	private void runAPI(File[] files, File tempDir) {
		new Thread(new Runnable() {

			@Override
			public void run() {

				String title = ticketTitleTextField.getText();
				String desc = descriptionTextArea.getText();

				CreateIncidentInput createIncidentInput = new CreateIncidentInput();
				createIncidentInput.setTitle(title);
				createIncidentInput.setDescription(desc);

				Incident incident = iAPICallerService.createIncident(createIncidentInput);

				String res = "";

				if (incident != null) {

					res += "Incident: " + incident.getNumber() + ", created successfuly\n";

					int imageIndex = files.length;
					for (File file : files) {
						File newFile = new File(tempDir + "/image" + imageIndex + ".png");
						file.renameTo(newFile);
						Upload upload = iAPICallerService.uploadFile(newFile, incident.getIncidentResult().getSysId());
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
							res += "System Info(" + tempSystemFile.getName() + ") uploaded successfuly\n";
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

			}
		}).start();
	}

	private void updateTimeLine() throws IOException {

		File draftsDir = new File("drafts/");
		File[] newDraftFiles = draftsDir.listFiles();
		File[] oldDraftFiles = draftsDir.listFiles();

		String newest = "";
		Arrays.sort(newDraftFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		for (File draftFile : newDraftFiles) {
			BasicFileAttributes attr = Files.readAttributes(draftFile.toPath(), BasicFileAttributes.class);
			newest = AppUtils.formatTime(attr.lastModifiedTime());
			break;
		}

		String oldest = "";
		Arrays.sort(oldDraftFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
		for (File draftFile : oldDraftFiles) {
			BasicFileAttributes attr = Files.readAttributes(draftFile.toPath(), BasicFileAttributes.class);
			oldest = AppUtils.formatTime(attr.lastModifiedTime());
			break;
		}

		ticketsCarouselPnl.invalidate();
		ticketsCarouselPnl.repaint();
		ticketsCarouselPnl.updateUI();

		JPanel ticketsPnl = (JPanel) ticketsCarouselPnl.getParent();
		ticketsPnl.invalidate();
		ticketsPnl.repaint();
		ticketsPnl.updateUI();

		for (int i = 0; i < ticketsPnl.getComponentCount(); i++) {
			Component comp = ticketsPnl.getComponent(i);
			if (comp instanceof TimingPanel) {
				TimingPanel tp = (TimingPanel) comp;
				for (int m = 0; m < tp.getComponentCount(); m++) {
					Component tpComp = tp.getComponent(m);

					if (tpComp instanceof JLabel) {
						JLabel lbl = (JLabel) tpComp;
						if (lbl.getName().equals("oldtime")) {
							lbl.setText("<html><center>Newest</center><br /><center>" + newest + "</center>");
						}
						if (lbl.getName().equals("newtime")) {
							lbl.setText("<html><center>Oldest</center><br /><center>" + oldest + "</center>");
						}
					}
				}
				tp.setVisible(true);
				tp.invalidate();
				tp.repaint();
				tp.updateUI();
			}
		}

		JPanel mainPanel = (JPanel) ticketsPnl.getParent();
		mainPanel.invalidate();
		mainPanel.repaint();
		mainPanel.updateUI();
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
		iMouseCaptureService.setTrayPopupMenu(trayPopupMenu);

		emptyDrafts();

		emptyTemp();
	}

	private void emptyTemp() {
		File tempDir = new File("temp/");
		if (tempDir != null) {
			if (tempDir.listFiles() != null) {

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
		if (!draftsDir.exists()) {
			draftsDir.mkdir();
		}
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

				public void mousePressed(MouseEvent e) {
					if (e.getClickCount() >= 2) {
						tray.remove(trayIcon);
						setVisible(true);
						setExtendedState(JFrame.MAXIMIZED_BOTH);
						GlobalScreen.removeNativeMouseListener(iMouseCaptureService);
						GlobalScreen.removeNativeKeyListener(iKeyboardCaptureService);
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

	public int getThumbHeight() {
		return thumbHeight;
	}
}
