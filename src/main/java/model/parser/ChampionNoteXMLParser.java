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
import model.parser.interfaces.IChampionNoteParser;
import model.structure.ChampionNote;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ChampionNoteXMLParser implements IChampionNoteParser {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * a list with all champion infos
	 */
	private List<ChampionNote> infos;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IChampionInfoParser#getInfos()
	 */
	@Override
	public List<ChampionNote> getInfos() {
		return this.infos;
	}

	/**
	 * Constructor
	 */
	public ChampionNoteXMLParser() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IChampionInfoParser#parse(java.lang.String)
	 */
	@Override
	public void parse(String path) throws ParserException {
		logger.log(Level.FINER, "parse info from: " + path);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(path);

			doc.getDocumentElement().normalize();

			this.infos = new ArrayList<>();

			extract(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ParserException("Error while parsing Champion.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * model.parser.interfaces.IChampionInfoParser#parse(java.io.InputStream)
	 */
	@Override
	public void parse(InputStream is) throws ParserException {
		logger.log(Level.FINER, "parse champion info from inputstream");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			doc.getDocumentElement().normalize();

			this.infos = new ArrayList<>();

			extract(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ParserException("Error while parsing Champion.");
		}
	}

	/**
	 * extracts all data from the given document
	 * 
	 * @param doc
	 *            given document
	 */
	private void extract(Document doc) {
		// get info nodes
		NodeList nodes = doc.getElementsByTagName("info");

		for (int i = 0; i < nodes.getLength(); i++) {
			// get info node
			ChampionNote cinfo = new ChampionNote();
			Node info = nodes.item(i);

			Element ele = (Element) info;

			cinfo.setName(ele.getElementsByTagName("name").item(0).getTextContent());
			cinfo.setSkill(ele.getElementsByTagName("skill").item(0).getTextContent());
			cinfo.setRunepage(ele.getElementsByTagName("runepage").item(0).getTextContent());
			cinfo.setMasteryPage(ele.getElementsByTagName("mastery").item(0).getTextContent());
			cinfo.setSummonerspell1(ele.getElementsByTagName("summonerspell1").item(0).getTextContent());
			cinfo.setSummonerspell2(ele.getElementsByTagName("summonerspell2").item(0).getTextContent());
			cinfo.setText(ele.getElementsByTagName("text").item(0).getTextContent());
			cinfo.setHtml(ele.getElementsByTagName("html").item(0).getTextContent().equals("1"));

			this.infos.add(cinfo);
		}
	}
}