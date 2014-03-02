package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.Config;
import manager.ParserManager;
import model.exception.UpdateRequiredException;
import model.exception.ParserException;
import model.parser.interfaces.ISummonerSpellParser;
import model.structure.SummonerSpell;

public class ShowSummonerSpellModel {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * all summoner spells
	 */
	private List<SummonerSpell> spells;

	/**
	 * Constructor
	 */
	public ShowSummonerSpellModel() {
		this.spells = new ArrayList<>();
	}

	/**
	 * gets all summoner spells
	 * 
	 * @return list of all spells
	 * 
	 * @throws ParserException
	 *             thrown if the summoner spell file couldn't be parsed
	 * @throws UpdateRequiredException
	 *             thrown if the file couldn't be found
	 */
	public List<SummonerSpell> getSummonerSpells() throws ParserException, UpdateRequiredException {
		String path = Config.getInstance().getSummonerSpellsFolder() + Config.getInstance().getSummonerSpellsFile();
		logger.log(Level.FINER, "parse Summer spells from: " + path);

		if (!new File(path).exists()) {
			logger.log(Level.FINER, "Couldn't find file: " + path);
			throw new UpdateRequiredException("Couldn't find file: " + path);
		}

		ISummonerSpellParser p = ParserManager.getInstance().getSummonerParser();
		p.parse(path);
		this.spells = p.getSummonerSpells();

		Collections.sort(this.spells, new Comparator<SummonerSpell>() {

			@Override
			public int compare(SummonerSpell o1, SummonerSpell o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return this.spells;
	}

	/**
	 * gets the summoner spell at the given index
	 * 
	 * @param index
	 *            given index
	 * 
	 * @return the summoner spell
	 */
	public SummonerSpell getSpell(int index) {
		return this.spells.get(index);
	}
}
