package model.collector;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import logging.LogUtil;
import manager.ParserManager;
import model.collector.interfaces.IDataCollector;
import model.exception.UpdateException;
import model.exception.ParserException;
import model.exception.WriteException;
import model.parser.interfaces.IChampionParser;
import model.progressbar.interfaces.IProgressBar;
import model.structure.Champion;
import model.structure.ChampionSpells;
import model.structure.SummonerSpell;
import model.structure.UpdateData;
import model.util.FileUtil;
import model.util.Util;

import com.cf.util.Regex;

import config.Config;
import config.Constants;

public class WebsiteDataCollector implements IDataCollector {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	// internal use for update file
	private boolean WRITE_UPDATE_FILE = false;
	private final String FILE_PATH = "updatefile.txt";
	
	private final String champNameURL = "http://leagueoflegends.wikia.com/wiki/Category:Released_champion";
//	private final String champNameURL = "http://prod.api.pvp.net/api/lol/euw/v1.1/champion?api_key=5129a04f-bdea-4f51-9d1a-8a32a42a6450";
	private final String summonerSpellURL = "http://leagueoflegends.wikia.com/wiki/Summoner_spell";
	private final String champInfoURL = "http://gameinfo.euw.leagueoflegends.com/en/game-info/champions/";
	private final String itemURL = "http://www.lolking.net/items/";

	/**
	 * Constructor
	 */
	public WebsiteDataCollector() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.collector.interfaces.IDataCollector#update()
	 */
	@Override
	public void update() throws UpdateException {
		update(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.collector.interfaces.IDataCollector#update()
	 */
	@Override
	public void update(IProgressBar progessbar) throws UpdateException {
		try {
			String champFolder = Config.getInstance().getChampFolder();
			String summonerFolder = Config.getInstance().getSummonerSpellsFolder();
			String itemFolder = Config.getInstance().getItemFolder();

			Util.createFoldAndFiles(champFolder.replace(Constants.PATH, ""));
			Util.createFoldAndFiles(summonerFolder.replace(Constants.PATH, ""));
			Util.createFoldAndFiles(itemFolder.replace(Constants.PATH, ""));

			List<String> names = getChampionNames(this.champNameURL);

			// check if champ folder exists
			if (!new File(champFolder).exists()) {
				logger.log(Level.FINER, "create folder:" + new File(champFolder).getAbsolutePath());
				new File(champFolder).mkdir();
			}
			ParserManager.getInstance().getWriter().writeChampionNames(names, champFolder + Config.getInstance().getChampNamesFile());

			List<SummonerSpell> summonerSpells = updateSummonerSpells(this.summonerSpellURL, summonerFolder);
			ParserManager.getInstance().getWriter().writeSummonerspells(summonerSpells, summonerFolder + Config.getInstance().getSummonerSpellsFile());
			if (progessbar != null) {
				progessbar.setMax(names.size());
				progessbar.nextStep();
			}

			List<String> items = updateItems(this.itemURL, itemFolder);
			ParserManager.getInstance().getWriter().writeItems(items, itemFolder + Config.getInstance().getItemFile());
			if (progessbar != null) {
				progessbar.nextStep();
			}

			List<ChampionSpells> championSpells = updateChampionImages(names, this.champInfoURL, champFolder, progessbar);

			for (ChampionSpells c : championSpells) {
				logger.log(Level.FINER, "write champions.xml for champ: " + c.getName());
				// make champ
				Champion champ = new Champion();
				champ.setSpells(c);

				// check if champion.xml exists^
				String xml = champFolder + c.getName() + "/" + Config.getInstance().getChampionFile();
				if (new File(xml).exists()) {
					try {
						IChampionParser p = ParserManager.getInstance().getChampionParser();
						p.parse(xml);
						champ.merge(p.getChampion());
					} catch (ParserException e) {
						logger.log(Level.SEVERE, "local champion.xml seems to be corrupted. will be overriden.");
					}
				}

				ParserManager.getInstance().getWriter().writeChampion(champ, xml);

				if (WRITE_UPDATE_FILE)
					writeUpdateFile(names.size(), summonerSpells.size(), items.size());
			}
		} catch (IOException | WriteException e) {
			logger.log(Level.SEVERE, "Error while updating:\n" + LogUtil.getStackTrace(e), e);
			throw new UpdateException("error while updating.");
		}
	}

	private void writeUpdateFile(int champs, int sums, int items) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(FILE_PATH)));
			bw.write("## champion updates\n");
			bw.write("date=" + new Date().toString() + "\n");
			bw.write("Champs=" + champs + "\n");
			bw.write("summoner=" + sums + "\n");
			bw.write("items=" + items + "\n");
			bw.write("packageURL=https://docs.google.com/uc?authuser=0&id=0B_RbeehtjF99eUs5bW1MQVFYSEU&export=download\n\n");

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.collector.interfaces.IDataCollector#getLastUpdated()
	 */
	@Override
	public UpdateData getUpdateInfo() throws UpdateException {
		return null;
	}

	/**
	 * gets the champion names from the given url
	 * 
	 * @param url
	 *            given url
	 * 
	 * @return list with all names
	 * 
	 * @throws IOException
	 */
	private List<String> getChampionNames(String url) throws IOException {
		logger.log(Level.FINER, "get Champion Names from URL: " + url);
		List<String> result = new ArrayList<String>();

		URL theUrl = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(theUrl.openConnection().getInputStream()));
		String line;
		String json = "";
		while ((line = in.readLine()) != null)
			json += line.trim() + "\n";

		in.close();
		
		Regex r = new Regex(".*<div lang=\"en\" dir=\"ltr\" class=\"mw-content-ltr\"><table width=\"100%\"><tr valign=\"top\"><td width=\"33.3%\">(.*?)</tr></table></div>.*", Pattern.DOTALL);
		
		if (!r.matches(json))
			return result;
		
		String champs = r.getGroup(1);
		
		r = new Regex("<a href=\".*?\" title=\".*?\">.*?</a>", Pattern.DOTALL);
		
		List<String> tmp = r.find(champs);
		
		r = new Regex("<a href=\".*?\" title=\"(.*?)\">.*?</a>", Pattern.DOTALL);
		
		for (String s : tmp) {
			if (r.matches(s)) {
				result.add(clearName(r.getGroup(1)));
			}
		}

//		// get all "name":"[name]" e.g. "name":"Aatrox"
//		Regex regex = new Regex("\"name\":\".*?\"");
//		List<String> tmp = regex.find(json);
//		logger.log(Level.FINER, "parsed JSON: " + json);
//
//		for (String e : tmp) {
//			result.add(e.split(":")[1].replace("\"", ""));
//		}

		logger.log(Level.FINER, "parsed champs (" + result.size() + "): " + Arrays.toString(result.toArray()));

		return result;
	}

	/**
	 * updates the champion images
	 * 
	 * @param names
	 *            list with all champion names
	 * @param champURL
	 *            url where to find the champs
	 * @param champFolder
	 *            the folder where to save the images
	 * 
	 * @return a list with all champions
	 * 
	 * @throws IOException
	 */
	private List<ChampionSpells> updateChampionImages(List<String> names, String champURL, String champFolder, IProgressBar pb) throws IOException {

		URL url;
		ChampionSpells champ;
		List<ChampionSpells> result = new ArrayList<>();
		for (String name : names) {
			// get champ data
			if (pb != null) {
				pb.nextStep();
			}

			champ = parseChampionURL(name, champURL);

			// check folders, if not there create it
			if (!new File(champFolder + champ.getName().toLowerCase()).exists()) {
				logger.log(Level.FINER, "create folder: " + new File(champFolder + champ.getName().toLowerCase()).getAbsolutePath());
				new File(champFolder + champ.getName().toLowerCase()).mkdir();
			}

			// get image and write it to disk
			url = new URL(champ.getImage());
			writeImage(url, champFolder + champ.getName().toLowerCase() + "/image.png");

			// get passive and write it to disk
			url = new URL(champ.getPassiveImage());
			writeImage(url, champFolder + champ.getName().toLowerCase() + "/passive.png");

			// get spell1 and write it to disk
			url = new URL(champ.getSpell1Image());
			writeImage(url, champFolder + champ.getName().toLowerCase() + "/spell1.png");

			// get spell2 and write it to disk
			url = new URL(champ.getSpell2Image());
			writeImage(url, champFolder + champ.getName().toLowerCase() + "/spell2.png");

			// get spell3 and write it to disk
			url = new URL(champ.getSpell3Image());
			writeImage(url, champFolder + champ.getName().toLowerCase() + "/spell3.png");

			// get spell4 and write it to disk
			url = new URL(champ.getSpell4Image());
			writeImage(url, champFolder + champ.getName().toLowerCase() + "/spell4.png");

			result.add(champ);
		}

		return result;
	}

	/**
	 * writes an image from a given url to the hdd
	 * 
	 * @param url
	 *            given url
	 * @param fileName
	 *            filepath
	 * 
	 * @throws IOException
	 */
	private void writeImage(URL url, String fileName) throws FileNotFoundException, IOException {
		logger.log(Level.FINER, "get image from url: " + url.toString());

		URLConnection con = url.openConnection();
		con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedImage img = ImageIO.read(con.getInputStream());
		logger.log(Level.FINER, "write image: " + new File(fileName).getAbsolutePath());
		ImageIO.write(img, "png", new File(fileName));
	}

	
	private String clearName(String name) {
		name = name.replace(" ", "");
		name = name.replace("'", "");
		name = name.replace(".", "");
//		name = name.toLowerCase().replace("wukong", "monkeyking");
		
		return name;
	}
	
	/**
	 * parses the given URL to get the champion spells
	 * 
	 * @param name
	 *            champion name
	 * @param url
	 *            given URL
	 * 
	 * @return the champion
	 * 
	 * @throws IOException
	 */
	private ChampionSpells parseChampionURL(String name, String url) throws IOException {
		logger.log(Level.FINER, "parse url for champ: " + name + " url: " + url + clearName(name).toLowerCase().replace("wukong", "monkeyking"));
		URL theUrl = new URL(url + clearName(name).toLowerCase().replace("wukong", "monkeyking"));
		BufferedReader in = new BufferedReader(new InputStreamReader(theUrl.openConnection().getInputStream()));
		String line;
		String html = "";
		while ((line = in.readLine()) != null)
			html += line.trim() + "\n";

		in.close();

		ChampionSpells result = new ChampionSpells(name);
		// get image
		Regex regex = new Regex("(?i).*<div class=\"default-1-3\">.*?<img class=\"\" src=\"(.*?\\.png)?\">.*?</div>.*", Pattern.DOTALL);
		if (regex.matches(html)) {
			result.setImage(regex.getGroup(1));
			logger.log(Level.FINER, "Found Image: " + result.getImage());
		} else
			logger.log(Level.FINER, "Image not found.");

		regex = new Regex(
				"(?i)<div class=\"default-3-5\">.*?<div class=\"gs-container\">.*?<div class='default-1-6'>.*?<span class=\"content-border\">.*?<img src=\".*?\\.png\"/>.*?</span>.*?</div>.*?<div class='default-5-6'>.*?<h3>.*?</h3>.*?<div class='default-1-1'>.*?<p class='spell-description'>.*?</div>",
				Pattern.DOTALL);
		List<String> spells = regex.find(html);
		logger.log(Level.FINER, "found " + spells.size() + " passive/spells.");

		String spellString = "(?i).*<div class=\"default-3-5\">.*?<div class=\"gs-container\">.*?<div class='default-1-6'>.*?<span class=\"content-border\">.*?<img src=\"(.*?\\.png)\"/>.*?</span>.*?</div>.*?<div class='default-5-6'>.*?<h3>(.*?)</h3>.*?<div class='default-1-1'>.*?(<p class='spell-description'>.*?)</div>.*";
		regex = new Regex(spellString, Pattern.DOTALL);

		// get passive
		if (regex.matches(spells.get(0))) {
			result.setPassive(regex.getGroup(2));
			result.setPassiveImage(regex.getGroup(1).replace(" ", "%20"));
			result.setPassiveDescr(getDescription(spells.get(0)));
			logger.log(Level.FINER, "Found Passive: " + result.getPassive() + " url: " + result.getPassiveImage());
		} else
			logger.log(Level.FINER, "Passive not found.");

		// Spell 1 (q)
		if (regex.matches(spells.get(1))) {
			result.setSpell1(regex.getGroup(2));
			result.setSpell1Image(regex.getGroup(1).replace(" ", "%20"));
			result.setSpell1Descr(getDescription(spells.get(1)));
			logger.log(Level.FINER, "Found Spell 1: " + result.getSpell1() + " url: " + result.getSpell1Image());
		} else
			logger.log(Level.FINER, "Spell1 not found.");

		// Spell 2 (w)
		if (regex.matches(spells.get(2))) {
			result.setSpell2(regex.getGroup(2));
			result.setSpell2Image(regex.getGroup(1).replace(" ", "%20"));
			result.setSpell2Descr(getDescription(spells.get(2)));
			logger.log(Level.FINER, "Found Spell 2: " + result.getSpell2() + " url: " + result.getSpell2Image());
		} else
			logger.log(Level.FINER, "Spell2 not found.");

		// Spell 3 (e)
		if (regex.matches(spells.get(3))) {
			result.setSpell3(regex.getGroup(2));
			result.setSpell3Image(regex.getGroup(1).replace(" ", "%20"));
			result.setSpell3Descr(getDescription(spells.get(3)));
			logger.log(Level.FINER, "Found Spell 3: " + result.getSpell3() + " url: " + result.getSpell3Image());
		} else
			logger.log(Level.FINER, "Spell3 not found.");

		// Spell 4 (R)
		if (regex.matches(spells.get(4))) {
			result.setSpell4(regex.getGroup(2));
			result.setSpell4Image(regex.getGroup(1).replace(" ", "%20"));
			result.setSpell4Descr(getDescription(spells.get(4)));
			logger.log(Level.FINER, "Found Spell 4: " + result.getSpell4() + " url: " + result.getSpell4Image());
		} else
			logger.log(Level.FINER, "Spell4 not found.");

		return result;
	}

	/**
	 * gets the description out of the html code in pure plain text
	 * 
	 * @param html
	 *            given html code
	 * 
	 * @return plain text of the given html code
	 */
	private String getDescription(String html) {
		String result = "";
		// tooltip
		if (html.contains("<p class='spell-tooltip'>")) {
			Regex r = new Regex(".*<p class='spell-tooltip'>(.*?)</p>.*", Pattern.DOTALL);
			if (r.matches(html))
				result = r.getGroup(1);
		}
		// just description
		else {
			Regex r = new Regex(".*<p class='spell-description'>(.*?)</p>.*", Pattern.DOTALL);
			if (r.matches(html))
				result = r.getGroup(1);
		}

		// remove </span>
		result = result.replaceAll("</span>", "");
		result = result.replaceAll("<span .*?>", "");
		// result = result.replaceAll("<br>", "\n");
		return result;
	}

	/**
	 * updates the summoner spell images depending on the given url
	 * 
	 * @param summonerURL
	 *            given summoner spell url
	 * @param summonerFolder
	 *            the folder where to save the images
	 * 
	 * @return a list with all summoner spells
	 * 
	 * @throws IOException
	 */
	private List<SummonerSpell> updateSummonerSpells(String summonerURL, String summonerFolder) throws IOException {
		logger.log(Level.FINER, "update summoner spells");

		if (!new File(summonerFolder).exists()) {
			logger.log(Level.FINER, "create folder: " + new File(summonerFolder).getAbsolutePath());
			new File(summonerFolder).mkdir();
		}

		List<SummonerSpell> result = parseSummonerSpells(summonerURL);

		URL url;
		for (SummonerSpell spell : result) {
			// get image and write it to disk
			url = new URL(spell.getImage());
			writeImage(url, summonerFolder + spell.getName().toLowerCase() + ".png");
		}

		return result;
	}

	/**
	 * gets a List with all Items and writes the items to the HDD
	 * 
	 * @param itemURL
	 *            url where to find the items
	 * @param itemFolder
	 *            the folder where to save the images
	 * 
	 * @return list with all item names
	 * 
	 * @throws IOException
	 *             thrown if images couldn't be write
	 */
	private List<String> updateItems(String itemURL, String itemFolder) throws IOException {
		List<String> result = new ArrayList<>();
		logger.log(Level.FINER, "update items from: " + itemURL + " to: " + itemFolder);

		if (!new File(itemFolder).exists()) {
			logger.log(Level.FINER, "create folder: " + new File(itemFolder).getAbsolutePath());
			new File(itemFolder).mkdir();
		}

		URL theUrl = new URL(itemURL);
		URLConnection uc = theUrl.openConnection();
		uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
		String line;
		String html = "";
		while ((line = in.readLine()) != null)
			html += line.trim() + "\n";

		in.close();

		Regex regex = new Regex("<a href=.*?>.*?" + "<span class=\"lol-icon large\">.*?" + "<span style=\"background:url\\(.*?\\)\"></span>.*?" + "</span>.*?"
				+ "<span class=\"info\">.*?" + "<div class=\"name\">.*?</div>.*?" + "<span class=\"cost\">.*?" + "<span class=\"lol-money gold\">.*?</span><br>.*?"
				+ "<span class=\"lol-money gold\">.*?</span>.*?" + "</span>.*?" + "</span>.*?" + "</a>", Pattern.DOTALL);

		List<String> items = regex.find(html);

		regex = new Regex("<a href=.*?>.*?" + "<span class=\"lol-icon large\">.*?" + "<span style=\"background:url\\((.*?)\\)\"></span>.*?" + "</span>.*?"
				+ "<span class=\"info\">.*?" + "<div class=\"name\">(.*?)</div>.*?" + "<span class=\"cost\">.*?" + "<span class=\"lol-money gold\">.*?</span><br>.*?"
				+ "<span class=\"lol-money gold\">.*?</span>.*?" + "</span>.*?" + "</span>.*?" + "</a>", Pattern.DOTALL);

		Set<String> seen = new HashSet<>();
		for (String item : items) {
			if (regex.matches(item)) {
				try {
					String fileName = FileUtil.formatFilePath(regex.getGroup(2));

					if (seen.contains(fileName))
						continue;

					writeImage(new URL("http://" + regex.getGroup(1).trim().substring(2)), itemFolder + fileName + ".png");
					seen.add(fileName);
				} catch (FileNotFoundException e) {
					continue;
				}

				result.add(regex.getGroup(2).trim());
			}
		}

		return result;
	}

	/**
	 * parses a given url to get all summoner spells
	 * 
	 * @param url
	 *            the given url
	 * 
	 * @return a list with all summoner spells
	 * 
	 * @throws IOException
	 */
	private List<SummonerSpell> parseSummonerSpells(String url) throws IOException {
		logger.log(Level.FINER, "parse url for summoner spells: " + url);
		URL theUrl = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(theUrl.openConnection().getInputStream()));
		String line;
		String html = "";
		while ((line = in.readLine()) != null)
			html += line.trim() + "\n";

		in.close();

		Regex regex = new Regex(
				"(?i).*?src=\"(http\\S*?\\.png)\" width=\"64\" height=\"64\" data-image-name=\".*?\" data-image-key=\".*?\" />.*?</a> <br /><a href=\".*?\" title=\".*?\">(.*?)</a></span>");
		List<String> spells = regex.find(html);
		logger.log(Level.FINER, "got " + spells.size() + " summoner spells.");

		List<SummonerSpell> result = new ArrayList<>();
		for (String s : spells) {
			SummonerSpell spell = new SummonerSpell();
			regex.matches(s);
			spell.setName(regex.getGroup(2));
			spell.setImage(regex.getGroup(1));
			result.add(spell);
			logger.log(Level.FINER, "found spell. name: " + spell.getName() + " image: " + spell.getImage());
		}

		return result;
	}

	public static void main(String[] args) throws UpdateException, SecurityException, IOException {
		Config.init();
		LogUtil.initLogging();
		WebsiteDataCollector d = new WebsiteDataCollector();
		d.WRITE_UPDATE_FILE = true;
		d.update();
		// d.updateItems("http://www.lolking.net/items/",
		// Config.getInstance().getItemFolder());
	}
}