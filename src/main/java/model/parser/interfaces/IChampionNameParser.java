package model.parser.interfaces;

import java.util.List;

public interface IChampionNameParser extends IParser {

	/**
	 * gets all champion names parsed from the XML file
	 * 
	 * @return a list with all names
	 */
	public List<String> getNames();
}
