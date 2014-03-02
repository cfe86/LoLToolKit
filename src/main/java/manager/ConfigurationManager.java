package manager;

import model.configcreator.InputConfigCreator;
import model.configcreator.RangeIndicatorChanger;
import model.configcreator.interfaces.IConfigCreator;
import model.configcreator.interfaces.IRangeIndicatorChanger;

public class ConfigurationManager {

	/**
	 * the instance
	 */
	private static ConfigurationManager instance;

	/**
	 * gets the instance
	 * 
	 * @return the instance
	 */
	public static ConfigurationManager getInstance() {
		if (instance == null)
			instance = new ConfigurationManager();

		return instance;
	}

	/**
	 * Constructor
	 */
	private ConfigurationManager() {

	}

	/**
	 * Gets the Inputconfig parser
	 * 
	 * @return the parser
	 */
	public IConfigCreator getInputConfigCreator() {
		return new InputConfigCreator();
	}

	/**
	 * Gets the range indicator changer
	 * 
	 * @return the changer
	 */
	public IRangeIndicatorChanger getRangeIndicatorChanger() {
		return new RangeIndicatorChanger();
	}
}