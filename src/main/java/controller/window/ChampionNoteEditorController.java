package controller.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.LogUtil;
import model.ChampionNoteEditorModel;
import model.exception.ConverterException;
import model.exception.ParserException;
import model.structure.Champion;
import model.structure.ChampionNote;
import model.structure.SummonerSpell;
import model.util.Tag;
import model.util.Util;
import view.window.ChampNoteEditorView;
import view.window.PreviewFrame;
import controller.adapter.MouseKeyAdapter;
import controller.interfaces.ICommandChooseSummonerSpell;
import controller.interfaces.ICommandRcvChampNote;

public class ChampionNoteEditorController extends MouseKeyAdapter implements ActionListener, Observer {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the window
	 */
	private ChampNoteEditorView window;
	/**
	 * the model
	 */
	private ChampionNoteEditorModel model;

	/**
	 * the command which is called when the window is closed to transmit the new
	 * champ note
	 */
	private ICommandRcvChampNote sendChampNoteCmd;

	/**
	 * constructor
	 */
	public ChampionNoteEditorController() {
		model = new ChampionNoteEditorModel();
	}

	public void setCloseCmd(ICommandRcvChampNote cmd) {
		this.sendChampNoteCmd = cmd;
	}

	/**
	 * creates the window and inits with the given champ and the given info
	 * 
	 * @param champ
	 *            given champion
	 * @param info
	 *            given info
	 */
	public void createWindow(Champion champ, ChampionNote info) {
		window = new ChampNoteEditorView();

		try {
			window.init();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Erorr while loading random icon:\n" + LogUtil.getStackTrace(e), e);
		}

		window.setVisible(true);

		window.setActionListener(this);
		window.setMouseListener(this);

		window.setLocation(Util.getCenteredWindowCoordinates(window));

		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow(null);
			}
		});

		model.setChampion(champ);
		if (info != null) {
			model.setInfo(new ChampionNote(info));
		}

		// set table model and info
		window.setTableModel(model.getTableModel());
		try {
			window.setInfo(model.getInfo());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Couldn't load summoner spell icons:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("loadImageError");
		}

		// set items
		try {
			window.setItems(model.getItemNames());
		} catch (ParserException e) {
			logger.log(Level.SEVERE, "Couldn't load items:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("loadImageError");
		}
	}

	/**
	 * close window
	 */
	public void closeWindow(ChampionNote note) {
		sendChampNoteCmd.call(note);
		window.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object obj) {
		logger.log(Level.FINER, "got message from observer: " + obs.getClass().getName());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("saveB"))
			saveButtonPressed();
		else if (e.getActionCommand().equals("cancelB"))
			cancelButtonPressed();
		else if (e.getActionCommand().equals("boldB"))
			boldButtonPressed();
		else if (e.getActionCommand().equals("italicB"))
			italicButtonPressed();
		else if (e.getActionCommand().equals("underlineB"))
			underlineButtonPressed();
		else if (e.getActionCommand().equals("listNrB"))
			listNrButtonPressed();
		else if (e.getActionCommand().equals("listUnnrB"))
			listUnnrButtonPressed();
		else if (e.getActionCommand().equals("addB"))
			addButtonPressed();
		else if (e.getActionCommand().equals("previewB"))
			previewButtonPressed();
		else if (e.getActionCommand().equals("htmlChB"))
			isHtmlChecked();
	}

	/**
	 * save button
	 */
	private void saveButtonPressed() {
		logger.log(Level.FINER, "save button pressed.");
		model.setInfo(window.getInfo());

		if (!model.checkInputName()) {
			window.showMessage("noNameGiven");
			return;
		}

		if (!model.checkInputSkill()) {
			window.showMessage("noSkillGiven");
			return;
		}

		closeWindow(model.getInfo());
	}

	/**
	 * cancel button
	 */
	private void cancelButtonPressed() {
		logger.log(Level.FINER, "cancel button pressed.");
		closeWindow(null);
	}

	/**
	 * bold button
	 */
	private void boldButtonPressed() {
		logger.log(Level.FINER, "bold button pressed: " + Arrays.toString(window.getSelectedTextIndices()));

		int[] indices = window.getSelectedTextIndices();
		String txt = window.getText();
		String selectedTxt = window.getSelectedText();

		String newText = model.addTag(Tag.BOLD, indices, txt, selectedTxt, null, -1);

		window.setText(newText);
	}

	/**
	 * italic button
	 */
	private void italicButtonPressed() {
		logger.log(Level.FINER, "italic button pressed: " + Arrays.toString(window.getSelectedTextIndices()));

		int[] indices = window.getSelectedTextIndices();
		String txt = window.getText();
		String selectedTxt = window.getSelectedText();

		String newText = model.addTag(Tag.ITALIC, indices, txt, selectedTxt, null, -1);

		window.setText(newText);
	}

	/**
	 * underline button
	 */
	private void underlineButtonPressed() {
		logger.log(Level.FINER, "underline button pressed: " + Arrays.toString(window.getSelectedTextIndices()));

		int[] indices = window.getSelectedTextIndices();
		String txt = window.getText();
		String selectedTxt = window.getSelectedText();

		String newText = model.addTag(Tag.UNDERLINE, indices, txt, selectedTxt, null, -1);

		window.setText(newText);
	}

	/**
	 * numbered list button
	 */
	private void listNrButtonPressed() {
		logger.log(Level.FINER, "list nr button pressed: " + Arrays.toString(window.getSelectedTextIndices()));

		int[] indices = window.getSelectedTextIndices();
		String txt = window.getText();
		String selectedTxt = window.getSelectedText();

		int rows = window.showRowWindow();
		if (rows == -1)
			return;

		String newText = model.addTag(Tag.LISTNR, indices, txt, selectedTxt, null, rows);

		window.setText(newText);
	}

	/**
	 * unnumbered list button
	 */
	private void listUnnrButtonPressed() {
		logger.log(Level.FINER, "list unnr button pressed: " + Arrays.toString(window.getSelectedTextIndices()));

		int[] indices = window.getSelectedTextIndices();
		String txt = window.getText();
		String selectedTxt = window.getSelectedText();

		int rows = window.showRowWindow();
		if (rows == -1)
			return;

		String newText = model.addTag(Tag.LISTUNNR, indices, txt, selectedTxt, null, rows);

		window.setText(newText);
	}

	/**
	 * add button
	 */
	private void addButtonPressed() {
		logger.log(Level.FINER, "item selected: " + window.getSelectedItem() + " indicies: " + Arrays.toString(window.getSelectedTextIndices()));

		int[] indices = window.getSelectedTextIndices();
		String txt = window.getText();
		String selectedTxt = window.getSelectedText();

		String newText = model.addTag(Tag.ITEM, indices, txt, selectedTxt, window.getSelectedItem(), -1);

		window.setText(newText);
	}

	/**
	 * preview button
	 */
	private void previewButtonPressed() {
		logger.log(Level.FINER, "preview button pressed.");

		try {
			String txt = window.getEditorText();
			String html = model.getInfoText(txt);
			PreviewFrame frame = new PreviewFrame();
			frame.init(html, window.getEditorWidth(), 500);
			frame.setVisible(true);
		} catch (ConverterException e) {
			logger.log(Level.SEVERE, "Error while converting html code:\n" + LogUtil.getStackTrace(e), e);
		}

	}

	/**
	 * use markup checkbox
	 */
	private void isHtmlChecked() {
		logger.log(Level.FINER, "html checked: " + window.isHTMLChecked());

		if (window.isHTMLChecked())
			window.setEditorEnabled(true);
		else
			window.setEditorEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.adapter.MouseKeyAdapter#mouseClicked(java.awt.event.MouseEvent
	 * )
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		logger.log(Level.FINER, "Summoner spell clicked.");

		int spellnr = -1;
		if (e.getSource() == window.getsummoner1IP())
			spellnr = 1;
		else if (e.getSource() == window.getsummoner2IP())
			spellnr = 2;

		logger.log(Level.FINER, "pressed summoner spell: " + spellnr);

		ShowSummonerSpellController con = new ShowSummonerSpellController();

		window.setEnabled(false);
		final int tmp = spellnr;
		con.setChooseCommand(new ICommandChooseSummonerSpell() {

			private int currSpell = tmp;

			@Override
			public void call(SummonerSpell spell) {
				try {
					window.setEnabled(true);
					window.toFront();
					
					if (spell == null)
						return;
					
					window.setSummonerSpell(currSpell, spell);
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Couldn't load image:\n" + LogUtil.getStackTrace(e), e);
					window.showMessage("loadImageError");
				}
				
				window.setEnabled(true);
			}
		});
		con.createWindow();
	}
}