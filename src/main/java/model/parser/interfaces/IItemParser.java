package model.parser.interfaces;

import java.util.List;

public interface IItemParser extends IParser {

	/**
	 * gets a list with item names
	 * 
	 * @return the item name list
	 */
	public List<String> getItems();
}