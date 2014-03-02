package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.Config;
import manager.ConfigurationManager;
import manager.ParserManager;
import model.configcreator.interfaces.IConfigCreator;
import model.configcreator.interfaces.IRangeIndicatorChanger;
import model.exception.ConfigCreatorException;
import model.exception.UpdateRequiredException;
import model.exception.ParserException;
import model.exception.WriteException;
import model.parser.interfaces.IChampionNameParser;
import model.parser.interfaces.IChampionParser;
import model.structure.Champion;

public class SmartcastModel {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the champion names
	 */
	private List<String> names;

	/**
	 * Constructor
	 */
	public SmartcastModel() {
		names = new ArrayList<>();
	}

	/**
	 * get all champion names
	 * 
	 * @return list of the names
	 */
	public List<String> getNames() {
		return this.names;
	}

	/**
	 * reads all champion names from the champion name file
	 * 
	 * @throws ParserException
	 *             thrown if the name file couldn't be parsed
	 * @throws UpdateRequiredException
	 *             thrown if the name file couldn't be found
	 */
	public void readNames() throws ParserException, UpdateRequiredException {
		logger.log(Level.FINER, "read champion names from: " + Config.getInstance().getChampFolder() + Config.getInstance().getChampNamesFile());

		if (!new File(Config.getInstance().getChampFolder() + Config.getInstance().getChampNamesFile()).exists())
			throw new UpdateRequiredException("couldn't find: " + Config.getInstance().getChampFolder() + Config.getInstance().getChampNamesFile());

		IChampionNameParser p = ParserManager.getInstance().getChampionNameParser();
		p.parse(Config.getInstance().getChampFolder() + Config.getInstance().getChampNamesFile());

		names = p.getNames();

		logger.log(Level.FINER, "read " + names.size() + " names.");
		Collections.sort(names, new Comparator<String>() {

			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
	}

	/**
	 * gets all champion information for the champion at the given index
	 * 
	 * @param index
	 *            given index
	 * 
	 * @return the champion
	 * 
	 * @throws ParserException
	 *             thrown if the champion file couldn't be parsed
	 */
	public Champion parseChampion(int index) throws ParserException {
		return parseChampion(this.names.get(index));
	}

	/**
	 * gets all champion information for the champion with the given name
	 * 
	 * @param name
	 *            given name
	 * 
	 * @return the champion
	 * 
	 * @throws ParserException
	 *             thrown if the champion file couldn't be parsed
	 */
	public Champion parseChampion(String name) throws ParserException {
		logger.log(Level.FINER, "get Champion data for champ: " + name);
		Config.getInstance().setCurrentChamp(name);
		String path = Config.getInstance().getChampFolder() + name.toLowerCase() + "/" + Config.getInstance().getChampionFile();

		IChampionParser p = ParserManager.getInstance().getChampionParser();
		p.parse(path);
		Champion result = p.getChampion();
		result.generateImagePaths();
		return result;
	}

	/**
	 * writes the champion xml for the given champion
	 * 
	 * @param champ
	 *            given champion
	 * 
	 * @throws WriteException
	 *             thrown if file couldn't be write
	 */
	public void writeChampXML(Champion champ) throws WriteException {
		String path = Config.getInstance().getChampFolder() + champ.getSpells().getName().toLowerCase() + "/" + Config.getInstance().getChampionFile();
		logger.log(Level.FINER, "write champion.xml to: " + path);
		ParserManager.getInstance().getWriter().writeChampion(champ, path);
	}

	/**
	 * writes the input.ini for the given champ to the lol directory
	 * 
	 * @param champ
	 *            given champion
	 * 
	 * @throws ConfigCreatorException
	 *             thrown if config couldn't be created
	 * @throws IOException
	 *             thrown if file couldn't be write
	 */
	public void writeInputConfig(Champion champ) throws ConfigCreatorException, IOException {
		String path = Config.getInstance().getLolPath() + Config.getInstance().getInputIni();
		logger.log(Level.FINER, "write input.ini to: " + path);

		IConfigCreator c = ConfigurationManager.getInstance().getInputConfigCreator();
		c.generateInputConfig(champ);
		String txt = c.getInputText();

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
		bw.write(txt);
		bw.close();

		path = Config.getInstance().getLolPath() + Config.getInstance().getGameCfg();
		logger.log(Level.FINER, "write game.cfg to: " + path);
		IRangeIndicatorChanger r = ConfigurationManager.getInstance().getRangeIndicatorChanger();
		r.update(champ.getHotkeys().isShowRangeIndicator());
	}
}
