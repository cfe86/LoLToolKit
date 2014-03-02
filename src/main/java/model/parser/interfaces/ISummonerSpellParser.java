package model.parser.interfaces;

import java.util.List;

import model.structure.SummonerSpell;

public interface ISummonerSpellParser extends IParser {

	/**
	 * returns a list with all summoner spells. Attention: thie image path of
	 * this spells is not set!
	 * 
	 * @return a list with all summoner spells without Image Path!
	 */
	public List<SummonerSpell> getSummonerSpells();
}