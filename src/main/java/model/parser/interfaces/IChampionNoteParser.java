package model.parser.interfaces;

import java.util.List;

import model.structure.ChampionNote;

public interface IChampionNoteParser extends IParser {

	/**
	 * gets a List with all champion infos
	 * 
	 * @return a list with all infos
	 */
	public List<ChampionNote> getInfos();
}
