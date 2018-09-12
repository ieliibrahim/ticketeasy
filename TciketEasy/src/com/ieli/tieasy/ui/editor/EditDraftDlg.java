package com.ieli.tieasy.ui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import com.ieli.tieasy.ui.CustomOptionPane;
import com.ieli.tieasy.ui.SingleDraftPanel;
import com.ieli.tieasy.ui.editor.tool.ArrowTool;
import com.ieli.tieasy.ui.editor.tool.BlurTool;
import com.ieli.tieasy.ui.editor.tool.ColorTool;
import com.ieli.tieasy.ui.editor.tool.CropTool;
import com.ieli.tieasy.ui.editor.tool.FreeHandTool;
import com.ieli.tieasy.ui.editor.tool.LineTool;
import com.ieli.tieasy.ui.editor.tool.RectangleTool;
import com.ieli.tieasy.ui.editor.tool.TextTool;
import com.ieli.tieasy.util.StackTraceHandler;
import com.ieli.tieasy.util.StaticData;
import com.ieli.tieasy.util.ui.CustomAppJButton;
import com.ieli.tieasy.util.ui.ScreenConfig;

import net.miginfocom.swing.MigLayout;

public class EditDraftDlg extends JDialog {

	final static Logger logger = Logger.getLogger(EditDraftDlg.class);

	private static final long serialVersionUID = 1L;

	private CusotmDrawingJButton freehandBtn;
	private CusotmDrawingJButton drawLineBtn;
	private CusotmDrawingJButton drawArrowBtn;

	private CusotmDrawingJButton drawEmptySquareBtn;
	private CusotmDrawingJButton inserTextBtn;
	private CusotmDrawingJButton chooseColorBtn;

	private CusotmDrawingJButton cropBtn;
	private CusotmDrawingJButton blurBtn;
	private CusotmDrawingJButton undoBtn;

	private JComboBox<Integer> fontSizeCBox;

	private Color drawingColor = Color.BLACK;

	private CustomAppJButton btnSave;
	private CustomAppJButton btnExit;

	private EditorImagePanel editorImagePanel;

	protected FreeHandTool freeHandTool;

	protected LineTool lineTool;

	protected ArrowTool arrowTool;

	protected RectangleTool rectangleTool;

	protected TextTool textTool;

	protected ColorTool colorTool;

	protected BlurTool blurTool;

	protected CropTool cropTool;

	private BufferedImage mainImg;

	private File imageFile;

	@SuppressWarnings("unchecked")
	public EditDraftDlg(BufferedImage mainImg, String mainImagePath, JFrame teMainFrame,
			SingleDraftPanel singleDraftPanel) {

		imageFile = new File(mainImagePath);
		this.mainImg = mainImg;
		setModal(true);
		setIconImage(StaticData.TRAY_ICON.getImage());
		setUndecorated(true);
		setResizable(false);
		ScreenConfig.setDialogSizeCustom(this, 50, 50);
		ScreenConfig.setDialogPositionCenter(this, teMainFrame);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new MigLayout("", "[434px,grow]", "[229px,grow]"));

		JPanel mainPnl = new JPanel();
		mainPnl.setBackground(Color.BLACK);
		getContentPane().add(mainPnl, "cell 0 0,grow");
		mainPnl.setLayout(new BorderLayout(0, 0));

		JPanel iconsPnl = new JPanel();
		iconsPnl.setBackground(Color.BLACK);
		mainPnl.add(iconsPnl, BorderLayout.WEST);
		iconsPnl.setLayout(new MigLayout("", "[]", "[]"));

		freehandBtn = new CusotmDrawingJButton(StaticData.ICON_FREEHAND, StaticData.ICON_FREEHAND_HOVER,
				"Draw freehand");
		iconsPnl.add(freehandBtn, "cell 0 0");

		freehandBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editorImagePanel.setTool(freeHandTool);
				freeHandTool.execute(e);
				fontSizeCBox.setVisible(false);
			}
		});

		drawLineBtn = new CusotmDrawingJButton(StaticData.ICON_DRAW_LINE, StaticData.ICON_DRAW_LINE_HOVER, "Draw line");
		iconsPnl.add(drawLineBtn, "cell 0 1");

		drawLineBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editorImagePanel.setTool(lineTool);
				lineTool.execute(e);
				fontSizeCBox.setVisible(false);
			}
		});

		drawArrowBtn = new CusotmDrawingJButton(StaticData.ICON_DRAW_ARROW, StaticData.ICON_DRAW_ARROW_HOVER,
				"Draw arrow");
		iconsPnl.add(drawArrowBtn, "cell 0 2");

		drawArrowBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editorImagePanel.setTool(arrowTool);
				arrowTool.execute(e);
				fontSizeCBox.setVisible(false);
			}
		});

		drawEmptySquareBtn = new CusotmDrawingJButton(StaticData.ICON_DRAW_EMPTY_SQUARE,
				StaticData.ICON_DRAW_EMPTY_SQUARE_HOVER, "Draw square");
		iconsPnl.add(drawEmptySquareBtn, "cell 0 3");

		drawEmptySquareBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editorImagePanel.setTool(rectangleTool);
				rectangleTool.execute(e);
				fontSizeCBox.setVisible(false);
			}
		});

		inserTextBtn = new CusotmDrawingJButton(StaticData.ICON_DRAW_TEXT, StaticData.ICON_DRAW_TEXT_HOVER,
				"Draw text");
		iconsPnl.add(inserTextBtn, "cell 0 5");

		inserTextBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editorImagePanel.setTool(textTool);
				textTool.execute(e);
				fontSizeCBox.setVisible(true);
			}
		});

		chooseColorBtn = new CusotmDrawingJButton(StaticData.ICON_CHOOSE_COLOR, StaticData.ICON_CHOOSE_COLOR_HOVER,
				"Choose color");
		iconsPnl.add(chooseColorBtn, "cell 0 6");

		chooseColorBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ColorPicker colorPicker = new ColorPicker(EditDraftDlg.this);
				colorPicker.setVisible(true);
				editorImagePanel.setTool(colorTool);
				colorTool.setColor(drawingColor);
				colorTool.execute(e);
			}
		});

		cropBtn = new CusotmDrawingJButton(StaticData.ICON_CROP, StaticData.ICON_CROP_HOVER, "Crop");
		iconsPnl.add(cropBtn, "cell 0 7");

		cropBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editorImagePanel.setTool(cropTool);
				cropTool.execute(e);
				fontSizeCBox.setVisible(false);
			}
		});

		blurBtn = new CusotmDrawingJButton(StaticData.ICON_BLUR, StaticData.ICON_BLUR_HOVER, "Blur");
		iconsPnl.add(blurBtn, "cell 0 8");

		blurBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editorImagePanel.setTool(blurTool);
				blurTool.execute(e);
				fontSizeCBox.setVisible(false);
			}
		});

		undoBtn = new CusotmDrawingJButton(StaticData.ICON_UNDO, StaticData.ICON_UNDO_HOVER, "Undo");
		iconsPnl.add(undoBtn, "cell 0 9");

		undoBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editorImagePanel.undo();
			}
		});

		fontSizeCBox = new JComboBox<Integer>(new Integer[] { 12, 14, 16, 18, 20 });
		ComboBoxRenderer renderer = new ComboBoxRenderer();
		fontSizeCBox.setRenderer(renderer);
		fontSizeCBox.setBackground(StaticData.THEME_ORANGE_COLOR);

		fontSizeCBox.setToolTipText("Font size");
		fontSizeCBox.setFont(new Font("Tahoma", Font.PLAIN, 14));

		iconsPnl.add(fontSizeCBox, "cell 0 10");
		fontSizeCBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer fontSize = fontSizeCBox.getItemAt(fontSizeCBox.getSelectedIndex());
				if (textTool != null) {
					textTool.setFontSize(fontSize);
				}

			}
		});

		fontSizeCBox.setVisible(false);

		editorImagePanel = new EditorImagePanel(this.mainImg);
		editorImagePanel.setBorder(new LineBorder(Color.WHITE));
		JScrollPane ticketsPanelPane = new JScrollPane(editorImagePanel);
		mainPnl.add(ticketsPanelPane, BorderLayout.CENTER);

		JPanel actionPnl = new JPanel();
		actionPnl.setBackground(Color.BLACK);
		mainPnl.add(actionPnl, BorderLayout.SOUTH);
		actionPnl.setLayout(new MigLayout("", "[grow][]", "[]"));

		btnSave = new CustomAppJButton("SAVE");
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				saveAndUpdateImg(singleDraftPanel);
				dispose();
			}

		});

		btnExit = new CustomAppJButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int confirmed = CustomOptionPane.showConfirmDialog(null, "Do you want to <br> save image before exit?",
						"Save progress", JOptionPane.YES_NO_OPTION);
				if (confirmed == JOptionPane.YES_OPTION) {
					saveAndUpdateImg(singleDraftPanel);
					dispose();
				} else if (confirmed == JOptionPane.NO_OPTION) {
					dispose();
				}

			}
		});

		actionPnl.add(btnExit, "cell 0 0,alignx right");
		actionPnl.add(btnSave, "cell 1 0,alignx right");

		initTools();

	}

	private void saveAndUpdateImg(SingleDraftPanel singleDraftPanel) {
		try {

			editorImagePanel.save(editorImagePanel.getDrawing().getImage());
			ImageIO.write(editorImagePanel.getDrawing().getImage(), "png", imageFile.getAbsoluteFile());
			singleDraftPanel.updateImage(editorImagePanel.getDrawing().getImage());

		} catch (IOException e1) {
			logger.error(StackTraceHandler.getErrString(e1));
		}
	}

	private void initTools() {

		freeHandTool = new FreeHandTool(editorImagePanel);
		lineTool = new LineTool(editorImagePanel);
		arrowTool = new ArrowTool(editorImagePanel);
		rectangleTool = new RectangleTool(editorImagePanel);
		textTool = new TextTool(editorImagePanel, 14);
		blurTool = new BlurTool(editorImagePanel);
		cropTool = new CropTool(editorImagePanel);
		colorTool = new ColorTool(editorImagePanel);
		colorTool.setColor(drawingColor);

	}

	class ComboBoxRenderer extends javax.swing.plaf.basic.BasicComboBoxRenderer {
		private static final long serialVersionUID = 1L;

		public ComboBoxRenderer() {
			super();
			setOpaque(true);
		}

		public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index,
				boolean isSelected, boolean cellHasFocus) {
			setText(value.toString());
			if (isSelected)
				setBackground(Color.WHITE);
			else
				setBackground(StaticData.THEME_ORANGE_COLOR);
			return this;
		}
	}

	public void setDrawingColor(Color drawingColor) {
		this.drawingColor = drawingColor;
	}

}
