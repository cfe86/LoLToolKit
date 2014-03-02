package model.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.exception.ParserException;
import model.parser.interfaces.IChampionNameParser;

public class ChampionNameXMLParser implements IChampionNameParser {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * list of all champion names
	 */
	private List<String> names;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IChampionNameParser#getNames()
	 */
	@Override
	public List<String> getNames() {
		return this.names;
	}

	/**
	 * Constructor
	 */
	public ChampionNameXMLParser() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IParser#parse(java.lang.String)
	 */
	@Override
	public void parse(String path) throws ParserException {
		logger.log(Level.FINER, "parse names from: " + path);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(path);

			doc.getDocumentElement().normalize();

			this.names = new ArrayList<>();

			extract(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ParserException("Error while parsing champion names.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IParser#parse(java.io.InputStream)
	 */
	@Override
	public void parse(InputStream is) throws ParserException {
		logger.log(Level.FINER, "parse names inputstream");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			doc.getDocumentElement().normalize();

			this.names = new ArrayList<>();

			extract(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ParserException("Error while parsing champion names.");
		}
	}

	/**
	 * extracs all neccessary information of the given document
	 * 
	 * @param doc
	 *            given document
	 */
	private void extract(Document doc) {
		// get champ names
		NodeList nodes = doc.getElementsByTagName("champion");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element e = (Element) nodes.item(i);
			this.names.add(e.getTextContent());
		}
	}
}