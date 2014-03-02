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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.exception.ParserException;
import model.parser.interfaces.IImportParser;
import model.structure.ChampionNote;
import model.structure.ImportData;

public class ImportXMLParser implements IImportParser {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * list of all import data
	 */
	private List<ImportData> data;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IImportParser#getImportData()
	 */
	@Override
	public List<ImportData> getImportData() {
		return this.data;
	}

	/**
	 * Constructor
	 */
	public ImportXMLParser() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IParser#parse(java.lang.String)
	 */
	@Override
	public void parse(String path) throws ParserException {
		logger.log(Level.FINER, "parse import from: " + path);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(path);

			doc.getDocumentElement().normalize();

			this.data = new ArrayList<>();

			extract(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ParserException("Error while parsing ChampionInfo.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IParser#parse(java.io.InputStream)
	 */
	@Override
	public void parse(InputStream is) throws ParserException {
		logger.log(Level.FINER, "parse champion info from inputstream");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			doc.getDocumentElement().normalize();

			this.data = new ArrayList<>();

			extract(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ParserException("Error while parsing ChampioInfo.");
		}
	}

	/**
	 * extracs all neccessary information of the given document
	 * 
	 * @param doc
	 *            given document
	 */
	private void extract(Document doc) {
		// get champ nodes
		NodeList nodes = doc.getElementsByTagName("champion");

		for (int i = 0; i < nodes.getLength(); i++) {
			ImportData data = new ImportData();
			// get champ
			Node champNode = nodes.item(i);
			Element champ = (Element) champNode;

			data.setName(champ.getElementsByTagName("name").item(0).getTextContent());
			data.getHotkeys().setShowRangeIndicator(champ.getElementsByTagName("rangeindicator").item(0).getTextContent().equals("1"));

			// spell 1
			NodeList nl = champ.getElementsByTagName("spell1");
			Element tmp = (Element) nl.item(0);
			data.getHotkeys().setSpell1(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastSpell1(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastSpell1(tmp.getAttribute("selfcast").equals("1"));

			// spell 2
			nl = champ.getElementsByTagName("spell2");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setSpell2(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastSpell2(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastSpell2(tmp.getAttribute("selfcast").equals("1"));

			// spell 3
			nl = champ.getElementsByTagName("spell3");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setSpell3(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastSpell3(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastSpell3(tmp.getAttribute("selfcast").equals("1"));

			// spell 4
			nl = champ.getElementsByTagName("spell4");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setSpell4(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastSpell4(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastSpell4(tmp.getAttribute("selfcast").equals("1"));

			// spell 4
			nl = champ.getElementsByTagName("spell4");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setSpell4(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastSpell4(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastSpell4(tmp.getAttribute("selfcast").equals("1"));

			// summonerspell 1
			nl = champ.getElementsByTagName("summonerSpell1");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setSummonerSpell1(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastSummonerSpell1(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastSummonerSpell1(tmp.getAttribute("selfcast").equals("1"));
			data.setSummonerSpell1(tmp.getTextContent());

			// summonerspell 2
			nl = champ.getElementsByTagName("summonerSpell2");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setSummonerSpell2(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastSummonerSpell2(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastSummonerSpell2(tmp.getAttribute("selfcast").equals("1"));
			data.setSummonerSpell2(tmp.getTextContent());

			// item 1
			nl = champ.getElementsByTagName("item1");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setItem1(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastItem1(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastItem1(tmp.getAttribute("selfcast").equals("1"));

			// item 2
			nl = champ.getElementsByTagName("item2");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setItem2(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastItem2(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastItem2(tmp.getAttribute("selfcast").equals("1"));

			// item 3
			nl = champ.getElementsByTagName("item3");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setItem3(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastItem3(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastItem3(tmp.getAttribute("selfcast").equals("1"));

			// item 4
			nl = champ.getElementsByTagName("item4");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setItem4(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastItem4(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastItem4(tmp.getAttribute("selfcast").equals("1"));

			// item 5
			nl = champ.getElementsByTagName("item5");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setItem5(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastItem5(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastItem5(tmp.getAttribute("selfcast").equals("1"));

			// item 6
			nl = champ.getElementsByTagName("item6");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setItem6(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastItem6(tmp.getAttribute("smartcast").equals("1"));
			data.getHotkeys().setSelfcastItem6(tmp.getAttribute("selfcast").equals("1"));

			// Trinket
			nl = champ.getElementsByTagName("trinket");
			tmp = (Element) nl.item(0);
			data.getHotkeys().setTrinket(tmp.getAttribute("hotkey"));
			data.getHotkeys().setSmartcastTrinket(tmp.getAttribute("smartcast").equals("1"));

			// get infos
			NodeList infos = champ.getElementsByTagName("info");

			for (int j = 0; j < infos.getLength(); j++) {
				// get info
				ChampionNote cinfo = new ChampionNote();
				Node infoNode = infos.item(j);
				Element info = (Element) infoNode;

				cinfo.setName(info.getElementsByTagName("name").item(0).getTextContent());
				cinfo.setSkill(info.getElementsByTagName("skill").item(0).getTextContent());
				cinfo.setRunepage(info.getElementsByTagName("runepage").item(0).getTextContent());
				cinfo.setMasteryPage(info.getElementsByTagName("mastery").item(0).getTextContent());
				cinfo.setSummonerspell1(info.getElementsByTagName("summonerspell1").item(0).getTextContent());
				cinfo.setSummonerspell2(info.getElementsByTagName("summonerspell2").item(0).getTextContent());
				cinfo.setText(info.getElementsByTagName("text").item(0).getTextContent());
				cinfo.setHtml(info.getElementsByTagName("html").item(0).getTextContent().equals("1"));

				data.addInfo(cinfo);
			}

			this.data.add(data);
		}
	}
}