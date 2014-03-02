package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import view.tablemodel.ChampionTableModel;
import config.Config;
import manager.ConverterManager;
import manager.ParserManager;
import model.converter.interfaces.IBBtoHTMLConverter;
import model.exception.ConverterException;
import model.exception.ParserException;
import model.exception.WriteException;
import model.parser.interfaces.IChampionNoteParser;
import model.parser.interfaces.IWriter;
import model.structure.Champion;
import model.structure.ChampionNote;

public class ChampionNoteModel {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the current champion
	 */
	private Champion champ;

	/**
	 * the corresponding champion infos
	 */
	private List<ChampionNote> infos;

	/**
	 * Constructor
	 */
	public ChampionNoteModel() {
		this.champ = new Champion();
		this.infos = new ArrayList<>();
	}

	/**
	 * sets the new current champion
	 * 
	 * @param champ
	 *            the champion
	 */
	public void setChampion(Champion champ) {
		this.champ = champ;
	}

	/**
	 * parse infos for the current champion
	 * 
	 * @throws ParserException
	 *             thrown if info couldn't be parsed
	 * 
	 * @return true if file is parsed successfully, else false
	 */
	public boolean parseInfos() throws ParserException {
		String path = Config.getInstance().getChampFolder() + this.champ.getSpells().getName() + "/" + Config.getInstance().getChampionNoteFile();
		logger.log(Level.FINER, "parse infos from: " + path + " exists: " + new File(path).exists());
		this.infos = new ArrayList<>();

		if (!new File(path).exists())
			return false;

		IChampionNoteParser p = ParserManager.getInstance().getChampionInfoParser();
		p.parse(path);

		this.infos = p.getInfos();

		Collections.sort(this.infos, new Comparator<ChampionNote>() {

			@Override
			public int compare(ChampionNote o1, ChampionNote o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return true;
	}

	/**
	 * gets all info names
	 * 
	 * @return List with all names
	 */
	public List<String> getInfoNames() {
		List<String> result = new ArrayList<>();
		for (ChampionNote i : this.infos)
			result.add(formatName(i.getName()));

		return result;
	}

	/**
	 * formats the name to the format [ChampionName] - [InfoName]
	 * 
	 * @param name
	 *            the name
	 * 
	 * @return the formatted name
	 */
	public String formatName(String name) {
		return this.champ.getSpells().getName() + " - " + name;
	}

	/**
	 * gets the info at given index
	 * 
	 * @param index
	 *            given index
	 * 
	 * @return the info
	 */
	public ChampionNote getInfo(int index) {
		return this.infos.get(index);
	}

	/**
	 * gets the current champion
	 * 
	 * @return the champion
	 */
	public Champion getChampion() {
		return this.champ;
	}

	/**
	 * gets the tablemodel for the info at the given index
	 * 
	 * @param index
	 *            given index
	 * 
	 * @return the tablemodel
	 * 
	 * @throws IOException
	 *             thrown if an image couln't be found
	 */
	public ChampionTableModel getTableModel(int index) throws IOException {
		if (index == -1)
			return new ChampionTableModel(champ, new boolean[4][18]);
		return new ChampionTableModel(this.champ, this.infos.get(index).getSkill());
	}

	/**
	 * adds a given info
	 * 
	 * @param info
	 *            the info to add
	 */
	public void addInfo(ChampionNote info) {
		this.infos.add(info);

		Collections.sort(this.infos, new Comparator<ChampionNote>() {

			@Override
			public int compare(ChampionNote o1, ChampionNote o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	/**
	 * removes the info at the given index
	 * 
	 * @param index
	 *            the index
	 */
	public void removeInfo(int index) {
		this.infos.remove(index);
	}

	/**
	 * gets the info text of the info at the given index
	 * 
	 * @param index
	 *            the given index
	 * 
	 * @return the converted text
	 * 
	 * @throws ConverterException
	 *             thrown if the text couldn't be converted
	 */
	public String getInfoText(int index) throws ConverterException {
		if (this.infos.get(index).isHtml()) {
			IBBtoHTMLConverter c = ConverterManager.getInstance().getBBtoHTMLConverter();
			c.convert(this.infos.get(index).getText());
			return c.getHTMLCode();
		} else {
			return this.infos.get(index).getText();
		}
	}

	/**
	 * writes the champion info to the info file, if no info is there, the file
	 * will be deleted
	 * 
	 * @throws WriteException
	 *             thrown if it couldn't get write
	 */
	public void writeChampionInfos() throws WriteException {
		String path = Config.getInstance().getChampFolder() + champ.getSpells().getName() + "/" + Config.getInstance().getChampionNoteFile();

		// check if an info is there, if not, delete the file
		if (infos.size() == 0) {
			logger.log(Level.FINER, "no info available. deleting file: " + new File(path).delete());
			return;
		}

		IWriter w = ParserManager.getInstance().getWriter();
		w.writeChampionInfo(this.infos, path);
	}
}