package com.ieli.tieasy.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.ieli.tieasy.model.Draft;
import com.ieli.tieasy.model.User;
import com.ieli.tieasy.service.drafts.IDraftsService;
import com.ieli.tieasy.util.ScreenConfig;

import net.miginfocom.swing.MigLayout;

public class DraftsDlg extends JDialog {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IDraftsService iDraftsService;

	/**
	 * Create the dialog.
	 */
	public DraftsDlg(User user, ApplicationContext appContext) {

		iDraftsService = (IDraftsService) appContext.getBean("iDraftsService");

		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setFont(new Font("Calibri", Font.PLAIN, 16));
		ScreenConfig.setDialogSizeCustom(this, 300, 300);
		ScreenConfig.setDialogPositionCenter(this, null);

		JPanel containerPnl = new JPanel();
		getContentPane().add(containerPnl, BorderLayout.CENTER);
		containerPnl.setLayout(new MigLayout("", "[][]", "[]"));

		List<Draft> drafts = iDraftsService.getUserDrafts(user.getUserId());
		int rowIndex = 0;
		for (Draft draft : drafts) {

			JPanel singleDraftPnl = new JPanel();
			singleDraftPnl.setLayout(new MigLayout("", "[][grow][][]", "[][][grow][][]"));

			JLabel lblTitle = new JLabel(draft.getTitle());
			singleDraftPnl.add(lblTitle, "cell 1 0");

			JLabel lblTime = new JLabel(draft.getDraftDateTime());
			singleDraftPnl.add(lblTime, "cell 1 1");

			JPanel openDeletePnl = new JPanel();
			singleDraftPnl.add(openDeletePnl, "cell 1 2,grow");
			openDeletePnl.setLayout(new MigLayout("", "[][][]", "[][]"));

			JButton btnOpen = new JButton("Open");
			openDeletePnl.add(btnOpen, "cell 0 0");

			JButton btnDelete = new JButton("Delete");
			openDeletePnl.add(btnDelete, "cell 1 0");

			JLabel imgLbl = new JLabel("");
			imgLbl.setIcon(new ImageIcon(draft.getImagePath()));
			singleDraftPnl.add(imgLbl, "cell 0 0 1 4");

			containerPnl.add(singleDraftPnl, "cell " + rowIndex + " 0,grow");
			rowIndex++;
		}

		// ---------------------------------------

//		JPanel singleDraftPnl = new JPanel();
//		singleDraftPnl.setLayout(new MigLayout("", "[][grow][][]", "[][][grow][][]"));
//
//		JLabel lblTitle = new JLabel("Outlook crashed during start");
//		singleDraftPnl.add(lblTitle, "cell 1 0");
//
//		JLabel lblTime = new JLabel("02:33 12-01-2018");
//		singleDraftPnl.add(lblTime, "cell 1 1");
//
//		JPanel openDeletePnl = new JPanel();
//		singleDraftPnl.add(openDeletePnl, "cell 1 2,grow");
//		openDeletePnl.setLayout(new MigLayout("", "[][][]", "[][]"));
//
//		JButton btnOpen = new JButton("Open");
//		openDeletePnl.add(btnOpen, "cell 0 0");
//
//		JButton btnDelete = new JButton("Delete");
//		openDeletePnl.add(btnDelete, "cell 1 0");
//
//		JLabel imgLbl = new JLabel("");
//		imgLbl.setIcon(new ImageIcon("F:/Imad/work/upwork/Jan Cronborg/PPT to UI/Deploy/drafts/1/2/image1.png"));
//		singleDraftPnl.add(imgLbl, "cell 0 0 1 4");
//
//		containerPnl.add(singleDraftPnl, "cell 0 0,grow");

	}

}
