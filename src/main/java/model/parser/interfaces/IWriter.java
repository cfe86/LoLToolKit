package model.parser.interfaces;

import java.util.List;

import model.exception.WriteException;
import model.structure.Champion;
import model.structure.ChampionNote;
import model.structure.SummonerSpell;

public interface IWriter {

	/**
	 * writes all summoner spells to afile
	 * 
	 * @param spells
	 *            given summoner spells
	 * @param path
	 *            given file path
	 * 
	 * @throws WriteException
	 *             thrown if file couldn't be write
	 */
	public void writeSummonerspells(List<SummonerSpell> spells, String path) throws WriteException;

	/**
	 * writes all champion names to a file
	 * 
	 * @param names
	 *            given champion names
	 * @param path
	 *            given file path
	 * 
	 * @throws WriteException
	 *             thrown if file couldn't be write
	 */
	public void writeChampionNames(List<String> names, String path) throws WriteException;

	/**
	 * writes the champion to a file
	 * 
	 * @param champ
	 *            given champion
	 * @param path
	 *            given file path
	 * 
	 * @throws WriteException
	 *             thrown if file couldn't be write
	 */
	public void writeChampion(Champion champ, String path) throws WriteException;

	/**
	 * writes the champion info to a file
	 * 
	 * @param infos
	 *            given champion info
	 * @param path
	 *            path to the file
	 * 
	 * @throws WriteException
	 *             thrown if file couldn't be write
	 */
	public void writeChampionInfo(List<ChampionNote> infos, String path) throws WriteException;

	/**
	 * writes the items to a file
	 * 
	 * @param items
	 *            given item list
	 * @param path
	 *            given file path
	 * 
	 * @throws WriteException
	 *             thrown if file couldn't be write
	 */
	public void writeItems(List<String> items, String path) throws WriteException;

	/**
	 * writes the champions and infos to an export file
	 * 
	 * @param champs
	 *            given list of all champions to export
	 * @param path
	 *            given file path
	 * 
	 * @throws WriteException
	 *             thrown if file couldn't be write
	 */
	public void writeExportData(List<String> champs, String path) throws WriteException;
}