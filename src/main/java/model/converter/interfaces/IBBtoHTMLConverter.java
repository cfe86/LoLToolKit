package model.converter.interfaces;

import model.exception.ConverterException;

public interface IBBtoHTMLConverter {

	/**
	 * gets the converted HTML text
	 * 
	 * @return the HTML Code
	 */
	public String getHTMLCode();

	/**
	 * converts the given String from BB to HTML
	 * 
	 * @param txt
	 *            given BB Code
	 * 
	 * @throws ConverterException
	 *             thrown if BB Code couldn't be converted
	 */
	public void convert(String txt) throws ConverterException;
}