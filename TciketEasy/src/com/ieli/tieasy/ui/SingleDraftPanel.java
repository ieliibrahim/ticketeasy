package com.ieli.tieasy.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.log4j.Logger;

import com.ieli.tieasy.ui.editor.EditDraftDlg;
import com.ieli.tieasy.util.AppUtils;
import com.ieli.tieasy.util.ui.CusotmImageJButton;

import net.miginfocom.swing.MigLayout;

public class SingleDraftPanel extends JPanel {

	final static Logger logger = Logger.getLogger(SingleDraftPanel.class);

	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private String mainImagePathFile;

	public SingleDraftPanel(final BufferedImage img, final String mainImagePath, final JPanel ticketsCarouselPnl,
			final TEMainFrame teMainFrame) {
		this.mainImagePathFile = mainImagePath;
		this.img = img;
		setPreferredSize(new Dimension(250, teMainFrame.getThumbHeight()));
		setBorder(new LineBorder(Color.WHITE, 2));
		setLayout(new MigLayout("", "[grow]", "[grow][grow]"));

		CusotmImageJButton btnEdit = new CusotmImageJButton("  Edit  ");
		add(btnEdit, "cell 0 0,alignx center,aligny bottom");
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				EditDraftDlg editDraftDlg = new EditDraftDlg(img, mainImagePath, teMainFrame, SingleDraftPanel.this);
				editDraftDlg.setVisible(true);
			}
		});

		CusotmImageJButton btnDelete = new CusotmImageJButton("Delete");

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int answer = CustomOptionPane.showConfirmDialog(null, "Are you sure you want to delete this image?",
						"Delete Image", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {

					File imageToDelete = new File(mainImagePath);
					imageToDelete.delete();

					try {
						updateTimeLine(ticketsCarouselPnl);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}
		});

		add(btnDelete, "cell 0 1,alignx center,aligny top");
	}

	private void updateTimeLine(JPanel ticketsCarouselPnl) throws IOException {

		File draftsDir = new File("drafts/");
		File[] newDraftFiles = draftsDir.listFiles();
		File[] oldDraftFiles = draftsDir.listFiles();

		ticketsCarouselPnl.remove(this);

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

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}

	public void updateImage(BufferedImage image) {
		Graphics g = getGraphics();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		invalidate();
		repaint();
		updateUI();
	}

	public String getMainImagePathFile() {
		return mainImagePathFile;
	}

}
