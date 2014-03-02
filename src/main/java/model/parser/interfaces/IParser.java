package model.parser.interfaces;

import java.io.InputStream;

import model.exception.ParserException;

public interface IParser {

	/**
	 * parses the file found at the given path
	 * 
	 * @param path
	 *            given path
	 * 
	 * @throws ParserException
	 *             thrown if file couldn't be parsed
	 */
	public void parse(String path) throws ParserException;

	/**
	 * parses the file from this inputstream
	 * 
	 * @param is
	 *            given inputstream
	 * 
	 * @throws ParserException
	 *             thrown if file couldn't be parsed
	 */
	public void parse(InputStream is) throws ParserException;
}