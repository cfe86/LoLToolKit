package manager;

import model.converter.BBtoHTMLConverter;
import model.converter.interfaces.IBBtoHTMLConverter;

public class ConverterManager {

	/**
	 * the instance
	 */
	private static ConverterManager instance;

	/**
	 * gets the instance
	 * 
	 * @return the instance
	 */
	public static ConverterManager getInstance() {
		if (instance == null)
			instance = new ConverterManager();

		return instance;
	}

	/**
	 * Constructor
	 */
	private ConverterManager() {

	}

	/**
	 * Gets the BB to HTML converter
	 * 
	 * @return the converter
	 */
	public IBBtoHTMLConverter getBBtoHTMLConverter() {
		return new BBtoHTMLConverter();
	}
}