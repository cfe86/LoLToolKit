package model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.Config;
import view.tablemodel.ChampionEditorTableModel;
import manager.ConverterManager;
import manager.ParserManager;
import model.converter.interfaces.IBBtoHTMLConverter;
import model.exception.ConverterException;
import model.exception.ParserException;
import model.parser.interfaces.IItemParser;
import model.structure.Champion;
import model.structure.ChampionNote;
import model.util.Tag;

public class ChampionNoteEditorModel {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the current champion
	 */
	private Champion champ;
	/**
	 * current champion info
	 */
	private ChampionNote info;

	/**
	 * constructor
	 */
	public ChampionNoteEditorModel() {
		champ = new Champion();
		info = new ChampionNote();
	}

	/**
	 * gets all item names
	 * 
	 * @return the list of item names
	 * 
	 * @throws ParserException
	 *             thrown if items couldn't be read
	 */
	public List<String> getItemNames() throws ParserException {
		logger.log(Level.FINER, "Get all item names.");
		IItemParser p = ParserManager.getInstance().getItemParser();
		p.parse(Config.getInstance().getItemFolder() + Config.getInstance().getItemFile());
		List<String> items = p.getItems();

		Collections.sort(items, new Comparator<String>() {

			@Override
			public int compare(String arg0, String arg1) {
				return arg0.compareToIgnoreCase(arg1);
			}
		});

		return items;
	}

	/**
	 * gets the current champion
	 * 
	 * @return the champion
	 */
	public Champion getChampion() {
		return champ;
	}

	/**
	 * gets the current info
	 * 
	 * @return the info
	 */
	public ChampionNote getInfo() {
		return info;
	}

	/**
	 * sets the current champion
	 * 
	 * @param champ
	 *            the champion
	 */
	public void setChampion(Champion champ) {
		this.champ = champ;
	}

	/**
	 * sets the current info
	 * 
	 * @param info
	 *            the info
	 */
	public void setInfo(ChampionNote info) {
		this.info = info;
	}

	/**
	 * gets the tablemodel for the champion and info
	 * 
	 * @return the tablemodel
	 */
	public ChampionEditorTableModel getTableModel() {
		return new ChampionEditorTableModel(champ, info.getSkill());
	}

	/**
	 * checks if the given position name is valid
	 * 
	 * @return true if valid, else false
	 */
	public boolean checkInputName() {
		// check for name
		if (this.info.getName().trim().length() == 0)
			return false;
		else
			return true;
	}

	/**
	 * checks if exactly one point per level is given
	 * 
	 * @return true if valid, else false
	 */
	public boolean checkInputSkill() {
		// check 1 point per lvl
		int count = 0;
		for (int col = 0; col < this.info.getSkill()[0].length; col++) {
			for (int row = 0; row < this.info.getSkill().length; row++) {
				if (this.info.getSkill()[row][col])
					count++;
			}

			if (count != 1)
				return false;
			else
				count = 0;
		}

		return true;
	}

	/**
	 * adds a tag to the given text
	 * 
	 * @param tag
	 *            given Tag
	 * @param indices
	 *            start and end indices
	 * @param text
	 *            given text
	 * @param selectedText
	 *            the selected text
	 * @param item
	 *            if add item is chosen, the item
	 * @param rows
	 *            if list is chosen, the rows
	 * 
	 * @return the modified text
	 */
	public String addTag(int tag, int[] indices, String text, String selectedText, String item, int rows) {
		String result = text;

		if (selectedText == null)
			selectedText = "";

		switch (tag) {
			case Tag.BOLD:
				result = text.substring(0, indices[0]) + Tag.BOLD_START + selectedText + Tag.BOLD_END + text.substring(indices[1]);
				break;
			case Tag.ITALIC:
				result = text.substring(0, indices[0]) + Tag.ITALIC_START + selectedText + Tag.ITALIC_END + text.substring(indices[1]);
				break;
			case Tag.UNDERLINE:
				result = text.substring(0, indices[0]) + Tag.UNDERLINE_START + selectedText + Tag.UNDERLINE_END + text.substring(indices[1]);
				break;
			case Tag.LISTNR:
				result = text.substring(0, indices[0]) + createList(true, rows) + text.substring(indices[0]);
				break;
			case Tag.LISTUNNR:
				result = text.substring(0, indices[0]) + createList(false, rows) + text.substring(indices[0]);
				break;
			case Tag.ITEM:
				result = text.substring(0, indices[0]) + createItem(item) + text.substring(indices[0]);
				break;
		}

		return result;
	}

	/**
	 * creates a list
	 * 
	 * @param numbered
	 *            true if numbered, else false
	 * @param rows
	 *            number of rows
	 * 
	 * @return the list string
	 */
	private String createList(boolean numbered, int rows) {
		String lst = (numbered ? Tag.LISTNR_START : Tag.LISTUNNR_START) + "\n";
		for (int i = 0; i < rows; i++)
			lst += "[*] row " + (i + 1) + "\n";
		lst += (numbered ? Tag.LISTNR_END : Tag.LISTUNNR_END) + "\n";

		return lst;
	}

	/**
	 * creates the item with tags
	 * 
	 * @param item
	 *            given item
	 * 
	 * @return item with tags
	 */
	private String createItem(String item) {
		return Tag.ITEM_START + item + Tag.ITEM_END;
	}

	/**
	 * gets the converted text
	 * 
	 * @param txt
	 *            given text
	 * 
	 * @return converted text
	 * 
	 * @throws ConverterException
	 *             thrown if it couldn't be converted
	 */
	public String getInfoText(String txt) throws ConverterException {
		IBBtoHTMLConverter c = ConverterManager.getInstance().getBBtoHTMLConverter();
		c.convert(txt);
		return c.getHTMLCode();
	}
}