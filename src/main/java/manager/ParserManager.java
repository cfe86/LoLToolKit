package manager;

import model.parser.ChampionNameXMLParser;
import model.parser.ChampionNoteXMLParser;
import model.parser.ChampionXMLParser;
import model.parser.ImportXMLParser;
import model.parser.ItemXMLParser;
import model.parser.SummonerSpellXMLParser;
import model.parser.XMLWriter;
import model.parser.interfaces.IChampionNameParser;
import model.parser.interfaces.IChampionNoteParser;
import model.parser.interfaces.IChampionParser;
import model.parser.interfaces.IImportParser;
import model.parser.interfaces.IItemParser;
import model.parser.interfaces.ISummonerSpellParser;
import model.parser.interfaces.IWriter;

public class ParserManager {

	/**
	 * the instance
	 */
	private static ParserManager instance;

	/**
	 * gets the instance
	 * 
	 * @return the instance
	 */
	public static ParserManager getInstance() {
		if (instance == null)
			instance = new ParserManager();

		return instance;
	}

	/**
	 * Constructor
	 */
	private ParserManager() {

	}

	/**
	 * gets the Writer
	 * 
	 * @return the writer
	 */
	public IWriter getWriter() {
		return new XMLWriter();
	}

	/**
	 * Gets the Champion name parser
	 * 
	 * @return the parser
	 */
	public IChampionNameParser getChampionNameParser() {
		return new ChampionNameXMLParser();
	}

	/**
	 * Gets the champion parser
	 * 
	 * @return the parser
	 */
	public IChampionParser getChampionParser() {
		return new ChampionXMLParser();
	}

	/**
	 * Gets the Summonerspell parser
	 * 
	 * @return the parser
	 */
	public ISummonerSpellParser getSummonerParser() {
		return new SummonerSpellXMLParser();
	}

	/**
	 * Gets the champion info parser
	 * 
	 * @return the parser
	 */
	public IChampionNoteParser getChampionInfoParser() {
		return new ChampionNoteXMLParser();
	}

	/**
	 * Gets the item parser
	 * 
	 * @return the parser
	 */
	public IItemParser getItemParser() {
		return new ItemXMLParser();
	}

	/**
	 * Gets the import parser
	 * 
	 * @return the parser
	 */
	public IImportParser getImportParser() {
		return new ImportXMLParser();
	}
}