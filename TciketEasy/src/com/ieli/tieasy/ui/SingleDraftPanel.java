package com.ieli.tieasy.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import com.ieli.tieasy.model.Draft;
import com.ieli.tieasy.ui.editor.EditDraftDlg;
import com.ieli.tieasy.util.AppUtils;
import com.ieli.tieasy.util.CusotmImageJButton;
import com.ieli.tieasy.util.ImageDrawer;

import net.miginfocom.swing.MigLayout;

public class SingleDraftPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Image img;
	private Draft draft;

	public SingleDraftPanel(final String mainImagePath, final Image img, final JPanel ticketsCarouselPnl,
			final JFrame teMainFrame) {
		this.img = img;

		setBorder(null);
		setLayout(new MigLayout("", "[grow]", "[grow][grow]"));

		CusotmImageJButton btnEdit = new CusotmImageJButton("  Edit  ");
		add(btnEdit, "cell 0 0,alignx center,aligny bottom");
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ImageIcon mainImage = new ImageIcon(mainImagePath);
				EditDraftDlg editDraftDlg = new EditDraftDlg(mainImage, teMainFrame);
				editDraftDlg.setVisible(true);
			}
		});

		CusotmImageJButton btnDelete = new CusotmImageJButton("Delete");

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this image?",
						"Delete Image", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {

					File imageToDelete = new File(draft.getImagePath());
					imageToDelete.delete();

					File thumbImageToDelete = new File(draft.getImageThumb());
					thumbImageToDelete.delete();
					try {
						getDrafst(ticketsCarouselPnl, teMainFrame);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
			}
		});

		add(btnDelete, "cell 0 1,alignx center,aligny top");
	}

	private void getDrafst(JPanel ticketsCarouselPnl, JFrame teMainFrame) throws IOException {

		ticketsCarouselPnl.removeAll();
		File draftsDir = new File("drafts/");
		File[] draftFiles = draftsDir.listFiles();

		for (File draftFile : draftFiles) {

			Draft draft = new Draft();
			BasicFileAttributes attr = Files.readAttributes(draftFile.toPath(), BasicFileAttributes.class);
			draft.setDraftDateTime(AppUtils.formatTime(attr.creationTime()));

			String imagePath = "drafts/" + draftFile.getName();
			draft.setImagePath(imagePath);

			String thumbImagePath = "thumbs/" + draftFile.getName().replaceAll(".png", "_thumb.png");
			draft.setImageThumb(thumbImagePath);

			final SingleDraftPanel singleDraftPanel = new SingleDraftPanel(imagePath,
					new ImageIcon(thumbImagePath).getImage(), ticketsCarouselPnl, teMainFrame);
			singleDraftPanel.setDraft(draft);
			ticketsCarouselPnl.add(singleDraftPanel);

		}

		String newest = "";
		Arrays.sort(draftFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		for (File draftFile : draftFiles) {
			BasicFileAttributes attr = Files.readAttributes(draftFile.toPath(), BasicFileAttributes.class);
			newest = AppUtils.formatTime(attr.lastModifiedTime());
			break;
		}

		String oldest = "";
		Arrays.sort(draftFiles, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
		for (File draftFile : draftFiles) {
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

	public void paintComponent(Graphics g) {
		ImageDrawer.drawScaledImage(img, this, g);
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}

	public Draft getDraft() {
		return draft;
	}

	public void setDraft(Draft draft) {
		this.draft = draft;
	}

}
