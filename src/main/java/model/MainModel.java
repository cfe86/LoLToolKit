package model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.Config;
import config.Constants;
import manager.ParserManager;
import model.exception.ParserException;
import model.exception.WriteException;
import model.parser.interfaces.IChampionNameParser;
import model.parser.interfaces.IChampionParser;
import model.parser.interfaces.IImportParser;
import model.parser.interfaces.IWriter;
import model.structure.Champion;
import model.structure.ImportData;
import model.util.Util;

public class MainModel {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Constructor
	 */
	public MainModel() {

	}

	/**
	 * exports all champions to the given path file
	 * 
	 * @param path
	 *            given path to file
	 * 
	 * @throws ParserException
	 *             thrown if a champion file couldn't be read
	 * @throws WriteException
	 *             thrown if the export file couldn't be write
	 */
	public void exportChampions(String path) throws ParserException, WriteException {
		logger.log(Level.FINER, "Export data to" + path);
		IChampionNameParser p = ParserManager.getInstance().getChampionNameParser();
		p.parse(Config.getInstance().getChampFolder() + Config.getInstance().getChampNamesFile());
		List<String> names = p.getNames();

		IWriter writer = ParserManager.getInstance().getWriter();
		writer.writeExportData(names, path);
	}

	/**
	 * imports the champions and infos
	 * 
	 * @param path
	 *            path to import file
	 * 
	 * @throws ParserException
	 *             thrown if file couldn't be parsed
	 * @throws IOException
	 *             thrown if a file couldn't be read
	 * @throws WriteException
	 *             thrown if a file couldn't be write
	 */
	public void importChampions(String path) throws ParserException, IOException, WriteException {
		logger.log(Level.FINER, "Import data from " + path);
		IImportParser p = ParserManager.getInstance().getImportParser();
		p.parse(path);
		List<ImportData> datas = p.getImportData();
		IWriter writer = ParserManager.getInstance().getWriter();

		for (ImportData data : datas) {
			// if champ infos arent there, take next one
			String cpath = Config.getInstance().getChampFolder() + data.getName().toLowerCase() + "/";
			Util.createFoldAndFiles(cpath.replace(Constants.PATH, ""));

			// read champion file and override hotkeys
			Champion c = new Champion();
			if (new File(cpath + Config.getInstance().getChampionFile()).exists()) {
				IChampionParser p2 = ParserManager.getInstance().getChampionParser();
				p2.parse(cpath + Config.getInstance().getChampionFile());
				c = p2.getChampion();
			}

			c.setHotkeys(data.getHotkeys());
			c.getSpells().setSummonerSpell1(data.getSummonerSpell1());
			c.getSpells().setSummonerSpell2(data.getSummonerSpell2());

			// write champion file
			writer.writeChampion(c, cpath + Config.getInstance().getChampionFile());

			// if infos are available, write them
			if (data.getInfos().size() > 0) {
				writer.writeChampionInfo(data.getInfos(), cpath + Config.getInstance().getChampionNoteFile());
			}
		}
	}
}
