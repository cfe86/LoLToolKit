package model.configcreator.interfaces;

import model.exception.ConfigCreatorException;
import model.structure.Champion;

public interface IConfigCreator {

	/**
	 * gets the created input config
	 * 
	 * @return input config text
	 */
	public String getInputText();

	/**
	 * generates the input.ini
	 * 
	 * @param champion
	 *            the champion for which the input.ini should be created
	 * 
	 * @throws ConfigCreatorException
	 *             thrown if input.ini couldn't be created
	 */
	public void generateInputConfig(Champion champion) throws ConfigCreatorException;
}
