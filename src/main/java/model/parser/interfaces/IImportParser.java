package model.parser.interfaces;

import java.util.List;

import model.structure.ImportData;

public interface IImportParser extends IParser {

	/**
	 * gets a list with all imported data for each champion
	 * 
	 * @return the imported data list
	 */
	public List<ImportData> getImportData();
}