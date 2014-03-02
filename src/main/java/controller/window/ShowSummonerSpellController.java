package controller.window;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.interfaces.ICommandChooseSummonerSpell;
import logging.LogUtil;
import model.ShowSummonerSpellModel;
import model.exception.UpdateRequiredException;
import model.exception.ParserException;
import model.structure.SummonerSpell;
import model.util.Util;
import view.structure.ImagePanel;
import view.window.ShowSummonerSpellView;

public class ShowSummonerSpellController extends Observable implements MouseListener {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the window
	 */
	private ShowSummonerSpellView window;
	/**
	 * the model
	 */
	private ShowSummonerSpellModel model;
	
	private ICommandChooseSummonerSpell chooseCmd;

	/**
	 * constructor
	 */
	public ShowSummonerSpellController() {
		model = new ShowSummonerSpellModel();
	}

	public void setChooseCommand(ICommandChooseSummonerSpell cmd) {
		this.chooseCmd = cmd;
	}
	
	/**
	 * creates the window
	 */
	public void createWindow() {
		window = new ShowSummonerSpellView();

		try {
			window.init(model.getSummonerSpells());
		} catch (IOException | ParserException | UpdateRequiredException e) {
			logger.log(Level.SEVERE, "Erorr while loading summoner spells:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("imageNotFound");
		}

		window.setVisible(true);

		window.setMouseListener(this);
		
		window.setLocation(Util.getCenteredWindowCoordinates(window));

		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow(null);
			}
		});
	}

	/**
	 * close window
	 */
	public void closeWindow(SummonerSpell spell) {
		window.dispose();
		chooseCmd.call(spell);
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		logger.log(Level.FINER, "Summoner spell clicked.");

		int index = 0;
		for (ImagePanel p : window.getImages()) {
			if (e.getSource() == p) {
				SummonerSpell spell = model.getSpell(index);
				logger.log(Level.FINER, "selected spell at index: " + index + " spell: " + spell.getName());

				closeWindow(spell);
			}
			index++;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// unused
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// unused
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// unused
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// unused
	}
}