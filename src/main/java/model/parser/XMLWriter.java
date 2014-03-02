package model.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import config.Config;
import logging.LogUtil;
import manager.ParserManager;
import model.exception.ParserException;
import model.exception.WriteException;
import model.parser.interfaces.IChampionNoteParser;
import model.parser.interfaces.IChampionParser;
import model.parser.interfaces.IWriter;
import model.structure.Champion;
import model.structure.ChampionNote;
import model.structure.SummonerSpell;

public class XMLWriter implements IWriter {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IWriter#writeSummonerspells(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public void writeSummonerspells(List<SummonerSpell> spells, String path) throws WriteException {
		logger.log(Level.FINER, "writing " + spells.size() + " summonerspells to: " + path);
		// sort list
		Collections.sort(spells, new Comparator<SummonerSpell>() {

			@Override
			public int compare(SummonerSpell o1, SummonerSpell o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = docFactory.newDocumentBuilder();

			// root elements
			Document doc = db.newDocument();
			Element rootElement = doc.createElement("summonerspells");
			doc.appendChild(rootElement);

			for (SummonerSpell spell : spells) {
				// name elements
				Element champ = doc.createElement("summonerspell");
				champ.appendChild(doc.createTextNode(spell.getName()));
				rootElement.appendChild(champ);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) {
			logger.log(Level.SEVERE, "Error while writing summoner spells:\n" + LogUtil.getStackTrace(e), e);
			throw new WriteException("Error while writing summoner spells.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IWriter#writeChampionNames(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public void writeChampionNames(List<String> names, String path) throws WriteException {
		logger.log(Level.FINER, "writing " + names.size() + " champion names to: " + path);

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = docFactory.newDocumentBuilder();

			// root elements
			Document doc = db.newDocument();
			Element rootElement = doc.createElement("champions");
			doc.appendChild(rootElement);

			for (String name : names) {
				// name elements
				Element champ = doc.createElement("champion");
				champ.appendChild(doc.createTextNode(name));
				rootElement.appendChild(champ);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) {
			logger.log(Level.SEVERE, "Error while writing champion names:\n" + LogUtil.getStackTrace(e), e);
			throw new WriteException("Error while writing Champion names.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * model.parser.interfaces.IWriter#writeChampion(model.structure.Champion,
	 * java.lang.String)
	 */
	@Override
	public void writeChampion(Champion champ, String path) throws WriteException {
		logger.log(Level.FINER, "writing champion to: " + path);

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = docFactory.newDocumentBuilder();

			// root elements
			Document doc = db.newDocument();
			Element rootElement = doc.createElement("champion");
			doc.appendChild(rootElement);

			// name elements
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(champ.getSpells().getName()));
			rootElement.appendChild(name);

			// spells elements
			Element spells = doc.createElement("spells");
			rootElement.appendChild(spells);

			// passive elements
			Element passive = doc.createElement("passive");
			spells.appendChild(passive);

			// passive name elements
			Element passiveName = doc.createElement("name");
			passiveName.appendChild(doc.createTextNode(champ.getSpells().getPassive()));
			passive.appendChild(passiveName);

			// passive descr elements
			Element passiveDescr = doc.createElement("description");
			passiveDescr.appendChild(doc.createTextNode(champ.getSpells().getPassiveDescr()));
			passive.appendChild(passiveDescr);

			// spell1 elements
			Element spell1 = doc.createElement("spell1");
			spell1.setAttribute("hotkey", champ.getHotkeys().getSpell1());
			spell1.setAttribute("smartcast", champ.getHotkeys().isSmartcastSpell1() ? "1" : "0");
			spell1.setAttribute("selfcast", champ.getHotkeys().isSelfcastSpell1() ? "1" : "0");
			spells.appendChild(spell1);

			// spell1 name elements
			Element spell1Name = doc.createElement("name");
			spell1Name.appendChild(doc.createTextNode(champ.getSpells().getSpell1()));
			spell1.appendChild(spell1Name);

			// spell1 descr elements
			Element spell1Descr = doc.createElement("description");
			spell1Descr.appendChild(doc.createTextNode(champ.getSpells().getSpell1Descr()));
			spell1.appendChild(spell1Descr);

			// spell2 elements
			Element spell2 = doc.createElement("spell2");
			spell2.setAttribute("hotkey", champ.getHotkeys().getSpell2());
			spell2.setAttribute("smartcast", champ.getHotkeys().isSmartcastSpell2() ? "1" : "0");
			spell2.setAttribute("selfcast", champ.getHotkeys().isSelfcastSpell2() ? "1" : "0");
			spells.appendChild(spell2);

			// spell2 name elements
			Element spell2Name = doc.createElement("name");
			spell2Name.appendChild(doc.createTextNode(champ.getSpells().getSpell2()));
			spell2.appendChild(spell2Name);

			// spell2 descr elements
			Element spell2Descr = doc.createElement("description");
			spell2Descr.appendChild(doc.createTextNode(champ.getSpells().getSpell2Descr()));
			spell2.appendChild(spell2Descr);

			// spell3 elements
			Element spell3 = doc.createElement("spell3");
			spell3.setAttribute("hotkey", champ.getHotkeys().getSpell3());
			spell3.setAttribute("smartcast", champ.getHotkeys().isSmartcastSpell3() ? "1" : "0");
			spell3.setAttribute("selfcast", champ.getHotkeys().isSelfcastSpell3() ? "1" : "0");
			spells.appendChild(spell3);

			// spell3 name elements
			Element spell3Name = doc.createElement("name");
			spell3Name.appendChild(doc.createTextNode(champ.getSpells().getSpell3()));
			spell3.appendChild(spell3Name);

			// spell3 descr elements
			Element spell3Descr = doc.createElement("description");
			spell3Descr.appendChild(doc.createTextNode(champ.getSpells().getSpell3Descr()));
			spell3.appendChild(spell3Descr);

			// spell4 elements
			Element spell4 = doc.createElement("spell4");
			spell4.setAttribute("hotkey", champ.getHotkeys().getSpell4());
			spell4.setAttribute("smartcast", champ.getHotkeys().isSmartcastSpell4() ? "1" : "0");
			spell4.setAttribute("selfcast", champ.getHotkeys().isSelfcastSpell4() ? "1" : "0");
			spells.appendChild(spell4);

			// spell4 name elements
			Element spell4Name = doc.createElement("name");
			spell4Name.appendChild(doc.createTextNode(champ.getSpells().getSpell4()));
			spell4.appendChild(spell4Name);

			// spell4 descr elements
			Element spell4Descr = doc.createElement("description");
			spell4Descr.appendChild(doc.createTextNode(champ.getSpells().getSpell4Descr()));
			spell4.appendChild(spell4Descr);

			// summoner spell1 elements
			Element summonerSpell1 = doc.createElement("summonerSpell1");
			summonerSpell1.setAttribute("hotkey", champ.getHotkeys().getSummonerSpell1());
			summonerSpell1.setAttribute("smartcast", champ.getHotkeys().isSmartcastSummonerSpell1() ? "1" : "0");
			summonerSpell1.setAttribute("selfcast", champ.getHotkeys().isSelfcastSummonerSpell1() ? "1" : "0");
			summonerSpell1.appendChild(doc.createTextNode(champ.getSpells().getSummonerSpell1()));
			spells.appendChild(summonerSpell1);

			// summoner spell2 elements
			Element summonerSpell2 = doc.createElement("summonerSpell2");
			summonerSpell2.setAttribute("hotkey", champ.getHotkeys().getSummonerSpell2());
			summonerSpell2.setAttribute("smartcast", champ.getHotkeys().isSmartcastSummonerSpell2() ? "1" : "0");
			summonerSpell2.setAttribute("selfcast", champ.getHotkeys().isSelfcastSummonerSpell2() ? "1" : "0");
			summonerSpell2.appendChild(doc.createTextNode(champ.getSpells().getSummonerSpell2()));
			spells.appendChild(summonerSpell2);

			// items elements
			Element items = doc.createElement("items");
			rootElement.appendChild(items);

			// item1 elements
			Element item1 = doc.createElement("item1");
			item1.setAttribute("hotkey", champ.getHotkeys().getItem1());
			item1.setAttribute("smartcast", champ.getHotkeys().isSmartcastItem1() ? "1" : "0");
			item1.setAttribute("selfcast", champ.getHotkeys().isSelfcastItem1() ? "1" : "0");
			items.appendChild(item1);

			// item2 elements
			Element item2 = doc.createElement("item2");
			item2.setAttribute("hotkey", champ.getHotkeys().getItem2());
			item2.setAttribute("smartcast", champ.getHotkeys().isSmartcastItem2() ? "1" : "0");
			item2.setAttribute("selfcast", champ.getHotkeys().isSelfcastItem2() ? "1" : "0");
			items.appendChild(item2);

			// item3 elements
			Element item3 = doc.createElement("item3");
			item3.setAttribute("hotkey", champ.getHotkeys().getItem3());
			item3.setAttribute("smartcast", champ.getHotkeys().isSmartcastItem3() ? "1" : "0");
			item3.setAttribute("selfcast", champ.getHotkeys().isSelfcastItem3() ? "1" : "0");
			items.appendChild(item3);

			// item4 elements
			Element item4 = doc.createElement("item4");
			item4.setAttribute("hotkey", champ.getHotkeys().getItem4());
			item4.setAttribute("smartcast", champ.getHotkeys().isSmartcastItem4() ? "1" : "0");
			item4.setAttribute("selfcast", champ.getHotkeys().isSelfcastItem4() ? "1" : "0");
			items.appendChild(item4);

			// item5 elements
			Element item5 = doc.createElement("item5");
			item5.setAttribute("hotkey", champ.getHotkeys().getItem5());
			item5.setAttribute("smartcast", champ.getHotkeys().isSmartcastItem5() ? "1" : "0");
			item5.setAttribute("selfcast", champ.getHotkeys().isSelfcastItem5() ? "1" : "0");
			items.appendChild(item5);

			// item6 elements
			Element item6 = doc.createElement("item6");
			item6.setAttribute("hotkey", champ.getHotkeys().getItem6());
			item6.setAttribute("smartcast", champ.getHotkeys().isSmartcastItem6() ? "1" : "0");
			item6.setAttribute("selfcast", champ.getHotkeys().isSelfcastItem6() ? "1" : "0");
			items.appendChild(item6);

			// trinket
			Element trinket = doc.createElement("trinket");
			trinket.setAttribute("hotkey", champ.getHotkeys().getTrinket());
			trinket.setAttribute("smartcast", champ.getHotkeys().isSmartcastTrinket() ? "1" : "0");
			items.appendChild(trinket);

			// range indicator
			Element rangeIndicator = doc.createElement("rangeindicator");
			rangeIndicator.appendChild(doc.createTextNode(champ.getHotkeys().isShowRangeIndicator() ? "1" : "0"));
			rootElement.appendChild(rangeIndicator);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException e) {
			logger.log(Level.SEVERE, "Error while writing champion:\n" + LogUtil.getStackTrace(e), e);
			throw new WriteException("Error while writing Champion.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IWriter#writeChampionInfo(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public void writeChampionInfo(List<ChampionNote> infos, String path) throws WriteException {
		logger.log(Level.FINER, "writing champion info to: " + path);

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = docFactory.newDocumentBuilder();

			// root elements
			Document doc = db.newDocument();
			Element rootElement = doc.createElement("infos");
			doc.appendChild(rootElement);

			// write info
			for (ChampionNote info : infos) {
				Element infoNode = doc.createElement("info");

				// name
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(info.getName()));
				infoNode.appendChild(name);

				// skill
				Element skill = doc.createElement("skill");
				skill.appendChild(doc.createTextNode(info.getSkillString()));
				infoNode.appendChild(skill);

				// runepage
				Element runepage = doc.createElement("runepage");
				runepage.appendChild(doc.createTextNode(info.getRunepage()));
				infoNode.appendChild(runepage);

				// masterypage
				Element masterypage = doc.createElement("mastery");
				masterypage.appendChild(doc.createTextNode(info.getMasteryPage()));
				infoNode.appendChild(masterypage);

				// summonerspell 1
				Element sspell1 = doc.createElement("summonerspell1");
				sspell1.appendChild(doc.createTextNode(info.getSummonerspell1()));
				infoNode.appendChild(sspell1);

				// summonerspell 2
				Element sspell2 = doc.createElement("summonerspell2");
				sspell2.appendChild(doc.createTextNode(info.getSummonerspell2()));
				infoNode.appendChild(sspell2);

				// html
				Element html = doc.createElement("html");
				html.appendChild(doc.createTextNode(info.isHtml() ? "1" : "0"));
				infoNode.appendChild(html);

				// text
				Element text = doc.createElement("text");
				text.appendChild(doc.createTextNode(info.getText()));
				infoNode.appendChild(text);

				rootElement.appendChild(infoNode);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException e) {
			logger.log(Level.SEVERE, "Error while writing champion info:\n" + LogUtil.getStackTrace(e), e);
			throw new WriteException("Error while writing Champion info.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IWriter#writeItems(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public void writeItems(List<String> items, String path) throws WriteException {
		logger.log(Level.FINER, "writing " + items.size() + " items to: " + path);

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = docFactory.newDocumentBuilder();

			// root elements
			Document doc = db.newDocument();
			Element rootElement = doc.createElement("items");
			doc.appendChild(rootElement);

			for (String item : items) {
				// item element
				Element itemNode = doc.createElement("item");
				itemNode.appendChild(doc.createTextNode(item));
				rootElement.appendChild(itemNode);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException e) {
			logger.log(Level.SEVERE, "Error while writing champion info:\n" + LogUtil.getStackTrace(e), e);
			throw new WriteException("Error while writing Champion info.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.parser.interfaces.IWriter#writeExportData(java.util.List,
	 * java.lang.String)
	 */
	@Override
	public void writeExportData(List<String> champs, String path) throws WriteException {
		logger.log(Level.FINER, "writing " + champs.size() + " champs to: " + path);

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = docFactory.newDocumentBuilder();

			// root elements
			Document doc = db.newDocument();
			Element rootElement = doc.createElement("champions");
			doc.appendChild(rootElement);

			for (String champ : champs) {
				Element champion = doc.createElement("champion");
				rootElement.appendChild(champion);

				String champPath = Config.getInstance().getChampFolder() + champ.toLowerCase() + "/";

				Champion c = new Champion();
				if (new File(champPath + Config.getInstance().getChampionFile()).exists()) {
					IChampionParser p = ParserManager.getInstance().getChampionParser();
					p.parse(champPath + Config.getInstance().getChampionFile());
					c = p.getChampion();
				} else
					continue;

				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(c.getSpells().getName()));
				champion.appendChild(name);

				Element spells = doc.createElement("spells");
				champion.appendChild(spells);

				// spell 1
				Element spell1 = doc.createElement("spell1");
				spell1.setAttribute("hotkey", c.getHotkeys().getSpell1());
				spell1.setAttribute("smartcast", c.getHotkeys().isSmartcastSpell1() ? "1" : "0");
				spell1.setAttribute("selfcast", c.getHotkeys().isSelfcastSpell1() ? "1" : "0");
				spells.appendChild(spell1);

				// spell 2
				Element spell2 = doc.createElement("spell2");
				spell2.setAttribute("hotkey", c.getHotkeys().getSpell2());
				spell2.setAttribute("smartcast", c.getHotkeys().isSmartcastSpell2() ? "1" : "0");
				spell2.setAttribute("selfcast", c.getHotkeys().isSelfcastSpell2() ? "1" : "0");
				spells.appendChild(spell2);

				// spell 3
				Element spell3 = doc.createElement("spell3");
				spell3.setAttribute("hotkey", c.getHotkeys().getSpell3());
				spell3.setAttribute("smartcast", c.getHotkeys().isSmartcastSpell3() ? "1" : "0");
				spell3.setAttribute("selfcast", c.getHotkeys().isSelfcastSpell3() ? "1" : "0");
				spells.appendChild(spell3);

				// spell 4
				Element spell4 = doc.createElement("spell4");
				spell4.setAttribute("hotkey", c.getHotkeys().getSpell4());
				spell4.setAttribute("smartcast", c.getHotkeys().isSmartcastSpell4() ? "1" : "0");
				spell4.setAttribute("selfcast", c.getHotkeys().isSelfcastSpell4() ? "1" : "0");
				spells.appendChild(spell4);

				// summonerspell1
				Element summonerSpell1 = doc.createElement("summonerSpell1");
				summonerSpell1.setAttribute("hotkey", c.getHotkeys().getSummonerSpell1());
				summonerSpell1.setAttribute("smartcast", c.getHotkeys().isSmartcastSummonerSpell1() ? "1" : "0");
				summonerSpell1.setAttribute("selfcast", c.getHotkeys().isSelfcastSummonerSpell1() ? "1" : "0");
				summonerSpell1.appendChild(doc.createTextNode(c.getSpells().getSummonerSpell1()));
				champion.appendChild(summonerSpell1);

				// summonerspell2
				Element summonerSpell2 = doc.createElement("summonerSpell2");
				summonerSpell2.setAttribute("hotkey", c.getHotkeys().getSummonerSpell2());
				summonerSpell2.setAttribute("smartcast", c.getHotkeys().isSmartcastSummonerSpell2() ? "1" : "0");
				summonerSpell2.setAttribute("selfcast", c.getHotkeys().isSelfcastSummonerSpell2() ? "1" : "0");
				summonerSpell2.appendChild(doc.createTextNode(c.getSpells().getSummonerSpell2()));
				champion.appendChild(summonerSpell2);

				// items
				Element items = doc.createElement("items");
				champion.appendChild(items);

				// item1
				Element item1 = doc.createElement("item1");
				item1.setAttribute("hotkey", c.getHotkeys().getItem1());
				item1.setAttribute("smartcast", c.getHotkeys().isSmartcastItem1() ? "1" : "0");
				item1.setAttribute("selfcast", c.getHotkeys().isSelfcastItem1() ? "1" : "0");
				items.appendChild(item1);

				// item2
				Element item2 = doc.createElement("item2");
				item2.setAttribute("hotkey", c.getHotkeys().getItem2());
				item2.setAttribute("smartcast", c.getHotkeys().isSmartcastItem2() ? "1" : "0");
				item2.setAttribute("selfcast", c.getHotkeys().isSelfcastItem2() ? "1" : "0");
				items.appendChild(item2);

				// item3
				Element item3 = doc.createElement("item3");
				item3.setAttribute("hotkey", c.getHotkeys().getItem3());
				item3.setAttribute("smartcast", c.getHotkeys().isSmartcastItem3() ? "1" : "0");
				item3.setAttribute("selfcast", c.getHotkeys().isSelfcastItem3() ? "1" : "0");
				items.appendChild(item3);

				// item4
				Element item4 = doc.createElement("item4");
				item4.setAttribute("hotkey", c.getHotkeys().getItem4());
				item4.setAttribute("smartcast", c.getHotkeys().isSmartcastItem4() ? "1" : "0");
				item4.setAttribute("selfcast", c.getHotkeys().isSelfcastItem4() ? "1" : "0");
				items.appendChild(item4);

				// item5
				Element item5 = doc.createElement("item5");
				item5.setAttribute("hotkey", c.getHotkeys().getItem5());
				item5.setAttribute("smartcast", c.getHotkeys().isSmartcastItem5() ? "1" : "0");
				item5.setAttribute("selfcast", c.getHotkeys().isSelfcastItem5() ? "1" : "0");
				items.appendChild(item5);

				// item6
				Element item6 = doc.createElement("item6");
				item6.setAttribute("hotkey", c.getHotkeys().getItem6());
				item6.setAttribute("smartcast", c.getHotkeys().isSmartcastItem6() ? "1" : "0");
				item6.setAttribute("selfcast", c.getHotkeys().isSelfcastItem6() ? "1" : "0");
				items.appendChild(item6);

				// trinket
				Element trinket = doc.createElement("trinket");
				trinket.setAttribute("hotkey", c.getHotkeys().getTrinket());
				trinket.setAttribute("smartcast", c.getHotkeys().isSmartcastTrinket() ? "1" : "0");
				items.appendChild(trinket);

				Element rangeIndicator = doc.createElement("rangeindicator");
				rangeIndicator.appendChild(doc.createTextNode(c.getHotkeys().isShowRangeIndicator() ? "1" : "0"));
				champion.appendChild(rangeIndicator);

				// get infos
				List<ChampionNote> infos = new ArrayList<>();
				if (new File(champPath + Config.getInstance().getChampionNoteFile()).exists()) {
					IChampionNoteParser p = ParserManager.getInstance().getChampionInfoParser();
					p.parse(champPath + Config.getInstance().getChampionNoteFile());
					infos = p.getInfos();
				}

				Element infosNode = doc.createElement("infos");
				for (ChampionNote info : infos) {
					Element infoNode = doc.createElement("info");

					// name
					Element infoName = doc.createElement("name");
					infoName.appendChild(doc.createTextNode(info.getName()));
					infoNode.appendChild(infoName);

					// skill
					Element skill = doc.createElement("skill");
					skill.appendChild(doc.createTextNode(info.getSkillString()));
					infoNode.appendChild(skill);

					// runepage
					Element runepage = doc.createElement("runepage");
					runepage.appendChild(doc.createTextNode(info.getRunepage()));
					infoNode.appendChild(runepage);

					// masterypage
					Element masterypage = doc.createElement("mastery");
					masterypage.appendChild(doc.createTextNode(info.getMasteryPage()));
					infoNode.appendChild(masterypage);

					// summonerspell 1
					Element sspell1 = doc.createElement("summonerspell1");
					sspell1.appendChild(doc.createTextNode(info.getSummonerspell1()));
					infoNode.appendChild(sspell1);

					// summonerspell 2
					Element sspell2 = doc.createElement("summonerspell2");
					sspell2.appendChild(doc.createTextNode(info.getSummonerspell2()));
					infoNode.appendChild(sspell2);

					// html
					Element html = doc.createElement("html");
					html.appendChild(doc.createTextNode(info.isHtml() ? "1" : "0"));
					infoNode.appendChild(html);

					// text
					Element text = doc.createElement("text");
					text.appendChild(doc.createTextNode(info.getText()));
					infoNode.appendChild(text);

					infosNode.appendChild(infoNode);
				}

				champion.appendChild(infosNode);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			StreamResult result = new StreamResult(new File(path));

			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException | ParserException e) {
			logger.log(Level.SEVERE, "Error while writing export data:\n" + LogUtil.getStackTrace(e), e);
			throw new WriteException("Error while writing export data.");
		}
	}
}