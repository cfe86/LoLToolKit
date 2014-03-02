package controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;

import config.Config;
import controller.interfaces.AbstractMouseKeyController;
import controller.interfaces.ICommandChooseSummonerSpell;
import controller.window.ShowSummonerSpellController;
import logging.LogUtil;
import model.SmartcastModel;
import model.exception.ConfigCreatorException;
import model.exception.ControllerInitException;
import model.exception.UpdateRequiredException;
import model.exception.ParserException;
import model.exception.WriteException;
import model.structure.Champion;
import model.structure.SummonerSpell;
import view.SmartcastPanel;
import view.interfaces.AbstractTab;

public class SmartcastController extends AbstractMouseKeyController {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the window
	 */
	private SmartcastPanel window;
	/**
	 * the model
	 */
	private SmartcastModel model;

	/**
	 * constructor
	 */
	public SmartcastController() {
		this.model = new SmartcastModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.interfaces.AbstractController#init(view.interfaces.AbstractTab
	 * )
	 */
	@Override
	public void init(AbstractTab window) throws ControllerInitException {
		logger.log(Level.FINER, "init smartcast panel.");
		this.window = (SmartcastPanel) window;

		this.window.setActionListener(this);
		this.window.setKeyListener(this);
		this.window.setMouseListener(this);

		boolean needsUpdate = false;
		try {
			model.readNames();
			this.window.setChampionNames(model.getNames(), Config.getInstance().getCurrentChamp());
		} catch (ParserException | UpdateRequiredException e) {
			logger.log(Level.SEVERE, "Error while reading champ names:\n" + LogUtil.getStackTrace(e), e);
			needsUpdate = true;
		}

		try {
			if (Config.getInstance().getCurrentChamp().trim().length() != 0)
				this.window.setChampionData(model.parseChampion(Config.getInstance().getCurrentChamp()));
			else {
				if (this.window.getSelectedChampIndex() != -1)
					this.window.setChampionData(model.parseChampion(this.window.getSelectedChampIndex()));
			}
		} catch (ParserException | IOException e) {
			logger.log(Level.SEVERE, "Error while setting champ data to view:\n" + LogUtil.getStackTrace(e), e);
		}

		checkChooseButton();

		if (needsUpdate) {
			super.setChanged();
			super.notifyObservers("update");
		}
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

	/**
	 * checks if the choose button is available or not (depends if the LoL path
	 * is given)
	 */
	public void checkChooseButton() {
		window.setChooseButtonEnabled(Config.getInstance().getLolPathFound());
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
		else if (e.getActionCommand().equals("resetB"))
			resetButtonPressed();
		else if (e.getActionCommand().equals("chooseB"))
			chooseButtonPressed();
		else if (e.getActionCommand().equals("nameCB"))
			nameCBChanged();
		else if (e.getActionCommand().equals("smartcastItem1ChB"))
			itemChanged(1, true);
		else if (e.getActionCommand().equals("selfcastItem1ChB"))
			itemChanged(1, false);
		else if (e.getActionCommand().equals("smartcastItem2ChB"))
			itemChanged(2, true);
		else if (e.getActionCommand().equals("selfcastItem2ChB"))
			itemChanged(2, false);
		else if (e.getActionCommand().equals("smartcastItem3ChB"))
			itemChanged(3, true);
		else if (e.getActionCommand().equals("selfcastItem3ChB"))
			itemChanged(3, false);
		else if (e.getActionCommand().equals("smartcastItem4ChB"))
			itemChanged(4, true);
		else if (e.getActionCommand().equals("selfcastItem4ChB"))
			itemChanged(4, false);
		else if (e.getActionCommand().equals("smartcastItem5ChB"))
			itemChanged(5, true);
		else if (e.getActionCommand().equals("selfcastItem5ChB"))
			itemChanged(5, false);
		else if (e.getActionCommand().equals("smartcastItem6ChB"))
			itemChanged(6, true);
		else if (e.getActionCommand().equals("selfcastItem6ChB"))
			itemChanged(6, false);
		else if (e.getActionCommand().equals("overrideItemsChB"))
			overrideItemsChBChecked();
	}

	/**
	 * save button pressed
	 */
	private void saveButtonPressed() {
		logger.log(Level.FINER, "save button pressed.");
		Champion champ = window.getChampionData();
		try {
			model.writeChampXML(champ);
			window.setDone(SmartcastPanel.SAVE);
		} catch (WriteException e) {
			logger.log(Level.SEVERE, "Error while writing Champion.xml:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("writeError");
		}
	}

	/**
	 * reset pressed
	 */
	private void resetButtonPressed() {
		logger.log(Level.FINER, "reset button pressed.");
		window.resetChampionData();
		window.setDone(SmartcastPanel.RESET);
	}

	/**
	 * choose button pressed
	 */
	private void chooseButtonPressed() {
		logger.log(Level.FINER, "choose button pressed.");
		Champion champ = window.getChampionData();
		try {
			if (Config.getInstance().getUseLolPath()) {
				model.writeInputConfig(champ);
				window.setDone(SmartcastPanel.CHOOSE);
			}
		} catch (ConfigCreatorException | IOException e) {
			logger.log(Level.SEVERE, "Error while writing input.ini or game.cfg:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("writeError");
		}
	}

	/**
	 * another champion is chosen
	 */
	public void nameCBChanged() {
		logger.log(Level.FINER, "champion changed. got index: " + window.getSelectedChampIndex());

		int index = window.getSelectedChampIndex();

		if (index == -1)
			return;

		Champion champ = null;
		try {
			champ = model.parseChampion(index);
			window.setChampionData(champ);
		} catch (IOException | ParserException e) {
			logger.log(Level.SEVERE, "Error while setting champ data to view:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("readError");
		}

		super.setChanged();
		super.notifyObservers(champ);
	}

	/**
	 * override item shortcuts checked
	 */
	public void overrideItemsChBChecked() {
		logger.log(Level.FINER, "override item checkbox checked: " + window.isOverrideItemsChBChecked());

		Config.getInstance().setOverrideItems(window.isOverrideItemsChBChecked());
		window.setItemsEnabled(window.isOverrideItemsChBChecked());
	}

	/**
	 * quickcast item changed
	 * 
	 * @param itemnr
	 *            item number
	 * @param smartcast
	 *            true if smartcast, else false
	 */
	private void itemChanged(int itemnr, boolean smartcast) {
		logger.log(Level.FINER, "Item changed. nr: " + itemnr + " smartcast: " + smartcast);
		switch (itemnr) {
			case 1:
				window.item1Changed(smartcast);
				break;
			case 2:
				window.item2Changed(smartcast);
				break;
			case 3:
				window.item3Changed(smartcast);
				break;
			case 4:
				window.item4Changed(smartcast);
				break;
			case 5:
				window.item5Changed(smartcast);
				break;
			case 6:
				window.item6Changed(smartcast);
				break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.adapter.MouseKeyAdapter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		// if space set sp, else set the character
		JTextField f = (JTextField) e.getSource();
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			logger.log(Level.FINER, "Space was pressed.");

			f.setText("Sp");
		} else {
			logger.log(Level.FINER, e.getKeyChar() + " was pressed.");

			f.setText("");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * controller.adapter.MouseKeyAdapter#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		JTextField f = (JTextField) e.getSource();
		f.setText(f.getText().trim());
		// set focus to window to loose it from textfield
		this.window.requestFocus();
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
		if (e.getSource() == window.getSummonerSpell1IP())
			spellnr = 1;
		else if (e.getSource() == window.getSummonerSpell2IP())
			spellnr = 2;

		logger.log(Level.FINER, "pressed summoner spell: " + spellnr);

		ShowSummonerSpellController con = new ShowSummonerSpellController();

		mainWindow.setWindowEnabled(false);
		final int tmp = spellnr;
		con.setChooseCommand(new ICommandChooseSummonerSpell() {

			private int currSpell = tmp;

			@Override
			public void call(SummonerSpell spell) {
				try {
					mainWindow.setWindowEnabled(true);
					
					if (spell == null)
						return;
					
					window.setSummonerSpell(currSpell, spell);
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Couldn't load image:\n" + LogUtil.getStackTrace(e), e);
					window.showMessage("loadImageError");
				}
				
				mainWindow.setWindowEnabled(true);
			}
		});
		con.createWindow();
	}
}