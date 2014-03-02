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

import model.exception.ParserException;
import model.parser.interfaces.IItemParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ItemXMLParser implements IItemParser {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * list of all items
	 */
	private List<String> items;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IItemParser#getItems()
	 */
	@Override
	public List<String> getItems() {
		return this.items;
	}

	/**
	 * Constructor
	 */
	public ItemXMLParser() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IParser#parse(java.lang.String)
	 */
	@Override
	public void parse(String path) throws ParserException {
		logger.log(Level.FINER, "parse items from: " + path);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(path);

			doc.getDocumentElement().normalize();

			this.items = new ArrayList<>();

			extract(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ParserException("Error while parsing item names.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IParser#parse(java.io.InputStream)
	 */
	@Override
	public void parse(InputStream is) throws ParserException {
		logger.log(Level.FINER, "parse items from inputstream");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			doc.getDocumentElement().normalize();

			this.items = new ArrayList<>();

			extract(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ParserException("Error while parsing Champion.");
		}
	}

	/**
	 * extracs all neccessary information of the given document
	 * 
	 * @param doc
	 *            given document
	 */
	private void extract(Document doc) {
		// get item names
		NodeList nodes = doc.getElementsByTagName("item");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element e = (Element) nodes.item(i);
			this.items.add(e.getTextContent());
		}
	}
}