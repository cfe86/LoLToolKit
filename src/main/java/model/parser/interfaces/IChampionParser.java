package model.parser.interfaces;

import model.structure.Champion;

public interface IChampionParser extends IParser {

	/**
	 * gets the parsed champion
	 * 
	 * @return the champion
	 */
	public Champion getChampion();
}