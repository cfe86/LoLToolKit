package model.parser;

import java.io.IOException;
import java.io.InputStream;
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
import model.parser.interfaces.IChampionParser;
import model.structure.Champion;

public class ChampionXMLParser implements IChampionParser {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * parsed champion
	 */
	private Champion champ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IChampionParser#getChampion()
	 */
	@Override
	public Champion getChampion() {
		return this.champ;
	}

	/**
	 * Constructor
	 */
	public ChampionXMLParser() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IParser#parse(java.lang.String)
	 */
	@Override
	public void parse(String path) throws ParserException {
		logger.log(Level.FINER, "parse champion from: " + path);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(path);

			doc.getDocumentElement().normalize();

			this.champ = new Champion();

			extract(doc);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new ParserException("Error while parsing Champion.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IParser#parse(java.io.InputStream)
	 */
	@Override
	public void parse(InputStream is) throws ParserException {
		logger.log(Level.FINER, "parse champion from inputstream");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			doc.getDocumentElement().normalize();

			this.champ = new Champion();

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
		// get name
		NodeList nodes = doc.getElementsByTagName("champion");
		this.champ.getSpells().setName(((Element) nodes.item(0)).getElementsByTagName("name").item(0).getTextContent());

		// spells
		// // passive
		nodes = doc.getElementsByTagName("passive");
		Element passive = (Element) nodes.item(0);
		this.champ.getSpells().setPassive(passive.getElementsByTagName("name").item(0).getTextContent());
		this.champ.getSpells().setPassiveDescr(passive.getElementsByTagName("description").item(0).getTextContent());

		// // spell 1
		nodes = doc.getElementsByTagName("spell1");
		Element spell1 = (Element) nodes.item(0);
		this.champ.getHotkeys().setSpell1(spell1.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastSpell1(spell1.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastSpell1(spell1.getAttribute("selfcast").equals("1"));
		this.champ.getSpells().setSpell1(spell1.getElementsByTagName("name").item(0).getTextContent());
		this.champ.getSpells().setSpell1Descr(spell1.getElementsByTagName("description").item(0).getTextContent());

		// // spell 2
		nodes = doc.getElementsByTagName("spell2");
		Element spell2 = (Element) nodes.item(0);
		this.champ.getHotkeys().setSpell2(spell2.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastSpell2(spell2.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastSpell2(spell2.getAttribute("selfcast").equals("1"));
		this.champ.getSpells().setSpell2(spell2.getElementsByTagName("name").item(0).getTextContent());
		this.champ.getSpells().setSpell2Descr(spell2.getElementsByTagName("description").item(0).getTextContent());

		// // spell 3
		nodes = doc.getElementsByTagName("spell3");
		Element spell3 = (Element) nodes.item(0);
		this.champ.getHotkeys().setSpell3(spell3.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastSpell3(spell3.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastSpell3(spell3.getAttribute("selfcast").equals("1"));
		this.champ.getSpells().setSpell3(spell3.getElementsByTagName("name").item(0).getTextContent());
		this.champ.getSpells().setSpell3Descr(spell3.getElementsByTagName("description").item(0).getTextContent());

		// // spell 4
		nodes = doc.getElementsByTagName("spell4");
		Element spell4 = (Element) nodes.item(0);
		this.champ.getHotkeys().setSpell4(spell4.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastSpell4(spell4.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastSpell4(spell4.getAttribute("selfcast").equals("1"));
		this.champ.getSpells().setSpell4(spell4.getElementsByTagName("name").item(0).getTextContent());
		this.champ.getSpells().setSpell4Descr(spell4.getElementsByTagName("description").item(0).getTextContent());

		// // summoner spell 1
		nodes = doc.getElementsByTagName("summonerSpell1");
		Element summonerSpell1 = (Element) nodes.item(0);
		this.champ.getHotkeys().setSummonerSpell1(summonerSpell1.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastSummonerSpell1(summonerSpell1.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastSummonerSpell1(summonerSpell1.getAttribute("selfcast").equals("1"));
		this.champ.getSpells().setSummonerSpell1(summonerSpell1.getTextContent());

		// // summoner spell 2
		nodes = doc.getElementsByTagName("summonerSpell2");
		Element summonerSpell2 = (Element) nodes.item(0);
		this.champ.getHotkeys().setSummonerSpell2(summonerSpell2.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastSummonerSpell2(summonerSpell2.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastSummonerSpell2(summonerSpell2.getAttribute("selfcast").equals("1"));
		this.champ.getSpells().setSummonerSpell2(summonerSpell2.getTextContent());

		// items
		// // item1
		nodes = doc.getElementsByTagName("item1");
		Element item1 = (Element) nodes.item(0);
		this.champ.getHotkeys().setItem1(item1.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastItem1(item1.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastItem1(item1.getAttribute("selfcast").equals("1"));

		// // item2
		nodes = doc.getElementsByTagName("item2");
		Element item2 = (Element) nodes.item(0);
		this.champ.getHotkeys().setItem2(item2.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastItem2(item2.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastItem2(item2.getAttribute("selfcast").equals("1"));

		// // item3
		nodes = doc.getElementsByTagName("item3");
		Element item3 = (Element) nodes.item(0);
		this.champ.getHotkeys().setItem3(item3.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastItem3(item3.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastItem3(item3.getAttribute("selfcast").equals("1"));

		// // item4
		nodes = doc.getElementsByTagName("item4");
		Element item4 = (Element) nodes.item(0);
		this.champ.getHotkeys().setItem4(item4.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastItem4(item4.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastItem4(item4.getAttribute("selfcast").equals("1"));

		// // item5
		nodes = doc.getElementsByTagName("item5");
		Element item5 = (Element) nodes.item(0);
		this.champ.getHotkeys().setItem5(item5.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastItem5(item5.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastItem5(item5.getAttribute("selfcast").equals("1"));

		// // item6
		nodes = doc.getElementsByTagName("item6");
		Element item6 = (Element) nodes.item(0);
		this.champ.getHotkeys().setItem6(item6.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastItem6(item6.getAttribute("smartcast").equals("1"));
		this.champ.getHotkeys().setSelfcastItem6(item6.getAttribute("selfcast").equals("1"));

		// // trinket
		nodes = doc.getElementsByTagName("trinket");
		Element trinket = (Element) nodes.item(0);
		this.champ.getHotkeys().setTrinket(trinket.getAttribute("hotkey"));
		this.champ.getHotkeys().setSmartcastTrinket(trinket.getAttribute("smartcast").equals("1"));

		// range indicator
		nodes = doc.getElementsByTagName("rangeindicator");
		Element rangeIndicator = (Element) nodes.item(0);
		this.champ.getHotkeys().setShowRangeIndicator(rangeIndicator.getTextContent().equals("1"));
	}

	public static void main(String[] args) throws ParserException {
		ChampionXMLParser m = new ChampionXMLParser();
		m.parse("Champion.xml");
	}
}