package controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.interfaces.AbstractController;
import controller.interfaces.ICommandRcvChampNote;
import controller.window.ChampionNoteEditorController;
import logging.LogUtil;
import model.ChampionNoteModel;
import model.exception.ControllerInitException;
import model.exception.ConverterException;
import model.exception.ParserException;
import model.exception.WriteException;
import model.structure.Champion;
import model.structure.ChampionNote;
import view.ChampNotePanel;
import view.interfaces.AbstractTab;

public class ChampionNoteController extends AbstractController {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the window
	 */
	private ChampNotePanel window;
	/**
	 * the model
	 */
	private ChampionNoteModel model;
	/**
	 * current index of the chosen info
	 */
	private int currIndex;

	/**
	 * constructor
	 */
	public ChampionNoteController() {
		this.model = new ChampionNoteModel();
		this.currIndex = -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.interfaces.AbstractController#init(view.interfaces.AbstractTab
	 * )
	 */
	@Override
	public void init(AbstractTab tab) throws ControllerInitException {
		logger.log(Level.FINER, "init champion note panel.");
		this.window = (ChampNotePanel) tab;

		this.window.setActionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object obj) {
		logger.log(Level.FINER, "got message from observer: " + obs.getClass().getName());

		// from smartcast
		if (obs.getClass().getName().equals(controller.SmartcastController.class.getName())) {
			// if it is no champion its not for me
			if (!(obj instanceof Champion))
				return;
			// this is necessary because the smartcast manager notifies for each
			// champion which is added to the combobox
			else if (this.window == null)
				return;

			Champion champ = (Champion) obj;
			model.setChampion(champ);
			try {
				model.parseInfos();
			} catch (ParserException e) {
				logger.log(Level.SEVERE, "Error while parsing champion info:\n" + LogUtil.getStackTrace(e), e);
				return;
			}

			this.window.setPositions(model.getInfoNames());

			// check if something is selected
			int index = window.getSelectedPositionIndex();
			try {
				if (index != -1) {
					window.setTableModel(model.getTableModel(index));
					window.setInfo(model.getInfo(index));
					window.setInfoText(model.getInfoText(index));

				} else {
					window.resetInfo();
					window.setTableModel(model.getTableModel(-1));
				}

			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error while setting champion infos:\n" + LogUtil.getStackTrace(e), e);
			} catch (ConverterException e) {
				logger.log(Level.SEVERE, "Error while converting info text to html:\n" + LogUtil.getStackTrace(e), e);
				window.showMessage("convertError");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("newB"))
			newButtonPressed();
		else if (e.getActionCommand().equals("editB"))
			editButtonPressed();
		else if (e.getActionCommand().equals("deleteB"))
			deleteButtonPressed();
		else if (e.getActionCommand().equals("positionCB"))
			positionChanged();
	}

	/**
	 * new button is pressed
	 */
	private void newButtonPressed() {
		logger.log(Level.FINER, "new button pressed.");

		this.currIndex = -1;
		ChampionNoteEditorController con = new ChampionNoteEditorController();
		con.setCloseCmd(new ICommandRcvChampNote() {

			@Override
			public void call(ChampionNote note) {
				champNoteReceived(note);
			}
		});

		mainWindow.setWindowEnabled(false);
		con.createWindow(model.getChampion(), null);
	}

	/**
	 * edit button is pressed
	 */
	private void editButtonPressed() {
		logger.log(Level.FINER, "edit button pressed.");

		int index = window.getSelectedPositionIndex();

		if (index == -1)
			return;

		this.currIndex = index;
		ChampionNoteEditorController con = new ChampionNoteEditorController();
		con.setCloseCmd(new ICommandRcvChampNote() {

			@Override
			public void call(ChampionNote note) {
				champNoteReceived(note);
			}
		});

		mainWindow.setWindowEnabled(false);
		con.createWindow(model.getChampion(), model.getInfo(index));
	}

	/**
	 * delete button is pressed
	 */
	private void deleteButtonPressed() {
		logger.log(Level.FINER, "delete Button pressed.");

		int index = window.getSelectedPositionIndex();

		if (index == -1)
			return;

		model.removeInfo(index);
		try {
			model.writeChampionInfos();
		} catch (WriteException e) {
			logger.log(Level.SEVERE, "Error while writing info xml:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("writeError");
		}

		try {
			window.resetInfo();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while resetting:\n" + LogUtil.getStackTrace(e), e);
		}
		positionChanged();
	}

	/**
	 * a new info is selected
	 */
	private void positionChanged() {
		logger.log(Level.FINER, "changed position: " + window.getSelectedPositionIndex());

		int index = window.getSelectedPositionIndex();

		if (index == -1)
			return;

		try {
			window.setTableModel(model.getTableModel(index));
			window.setInfo(model.getInfo(index));
			window.setInfoText(model.getInfoText(index));

			// notify obervers
			super.setChanged();
			super.notifyObservers(model.getInfo(index));

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while setting champion infos:\n" + LogUtil.getStackTrace(e), e);
		} catch (ConverterException e) {
			logger.log(Level.SEVERE, "Error while converting info text to html:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("convertError");
		}
	}

	/**
	 * called when a champion note is received from the note editor
	 * 
	 * @param note
	 *            the new note
	 */
	private void champNoteReceived(ChampionNote note) {

		mainWindow.setWindowEnabled(true);

		if (note == null)
			return;

		// if new one (-1) just add it, else remove the old one
		if (currIndex != -1)
			model.removeInfo(currIndex);

		model.addInfo(note);

		window.setPositions(model.getInfoNames());
		window.setPosition(model.formatName(note.getName()));
		positionChanged();

		try {
			model.writeChampionInfos();
		} catch (WriteException e) {
			logger.log(Level.SEVERE, "Error while writing info xml:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("writeError");
		}
	}
}