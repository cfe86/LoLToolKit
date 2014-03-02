package config;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.structure.Hotkeys;

public class Config {

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private static Config instance;

	private Map<String, String> config;

	/**
	 * gets the singleton
	 * 
	 * @return the Config
	 */
	public static Config getInstance() {
		return instance;
	}

	/**
	 * Constructor for singleton
	 */
	private Config() {

	}

	/**
	 * inits the config
	 * 
	 * @throws IOException
	 */
	public static void init() {
		instance = new Config();
		instance.readConfig();
	}

	/**
	 * reads the config file (default settings.conf)
	 */
	private void readConfig() {
		logger.log(Level.CONFIG, "read config from: " + Constants.PATH + Constants.CONFIG_PATH);
		this.config = new HashMap<>();
		this.config.put("FontSize", "17");
		this.config.put("currChamp", "");
		this.config.put("currWidth", "650");
		this.config.put("currHeight", "630");
		this.config.put("currSkin", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		this.config.put("currLanguage", "en_US");
		this.config.put("ChampFolder", "data/Champions/");
		this.config.put("SummonerSpellsFolder", "data/Summonerspells/");
		this.config.put("ItemFolder", "data/Items/");
		this.config.put("SummonerSpellFile", "Summonerspells.xml");
		this.config.put("ChampNameFile", "ChampionNames.xml");
		this.config.put("ChampionFile", "champion.xml");
		this.config.put("ItemFile", "items.xml");
		this.config.put("LolPath", "C:\\Riot Games\\League of Legends\\");
		this.config.put("Input.ini", "Config\\input.ini");
		this.config.put("game.cfg", "Config\\game.cfg");
		this.config.put("CustomInput.ini", "");
		this.config.put("useLoLPath", "1");
		this.config.put("overrideItems", "0");
		this.config.put("ChampionNoteFile", "info.xml");
		this.config.put("ChampImage_length", "120");
		this.config.put("SpellItemImage_length", "64");
		this.config.put("SummonerSpellsPerLine", "4");
		this.config.put("editorImageLength", "35");
		this.config.put("normal_Prefix", "0");
		this.config.put("smartcast_Prefix", "3");
		this.config.put("selfcast_Prefix", "1");
		this.config.put("levelup_Prefix", "2");
		this.config.put("normal_when_smartcast_Prefix", "3");
		this.config.put("normal_when_selfcast_Prefix", "1");
		this.config.put("normal_when_smartcast_and_selfcast_Prefix", "3");
		this.config.put("SoftwareUpdateInfoFile", Constants.SOFTWARE_UPDATE_FILE_URL);

		this.config.put("UpdateInfoFile", Constants.UPDATE_FILE_URL);
		this.config.put("DataUpdateFile", "");
		this.config.put("lastUpdate", Constants.LAST_DATA_UPDATE);

		if (!new File(Constants.PATH + Constants.CONFIG_PATH).exists()) {
			logger.log(Level.FINER, "Couldn't find config path. Default config will be loaded.");
			this.config.put("LolPathFound", new File(Config.getInstance().getLolPath() + "/lol.launcher.exe").exists() ? "1" : "0");
			return;
		}

		ConfigReader.findParameters(new File(Constants.PATH + Constants.CONFIG_PATH), this.config, "=");
		
		this.config.put("LolPathFound", new File(Config.getInstance().getLolPath() + "/lol.launcher.exe").exists() ? "1" : "0");
	}

	/**
	 * writes the config file (default settings.conf)
	 * 
	 * @throws IOException thrown if config couldn't be read
	 */
	public void writeConfig() throws IOException {
		logger.log(Level.CONFIG, "Write config to: " + Constants.PATH + Constants.CONFIG_PATH);
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Constants.PATH + Constants.CONFIG_PATH)));

		bw.write("# current selected champ\n");
		bw.write("currChamp=" + this.config.get("currChamp") + "\n");
		bw.write("# length and width of the window\n");
		bw.write("currWidth=" + this.config.get("currWidth") + "\n");
		bw.write("currHeight=" + this.config.get("currHeight") + "\n");
		bw.write("# current selected skin\n");
		bw.write("currSkin=" + this.config.get("currSkin") + "\n");
		bw.write("# the current language\n");
		bw.write("currLanguage=" + this.config.get("currLanguage") + "\n");
		bw.write("# relative path to the champ folder\n");
		bw.write("ChampFolder=" + this.config.get("ChampFolder") + "\n");
		bw.write("# relative path to the item folder\n");
		bw.write("ItemFolder=" + this.config.get("ItemFolder") + "\n");
		bw.write("# relative path to the summonerspells folder\n");
		bw.write("SummonerSpellsFolder=" + this.config.get("SummonerSpellsFolder") + "\n");
		bw.write("# name of the summoner spell file\n");
		bw.write("SummonerSpellFile=" + this.config.get("SummonerSpellFile") + "\n");
		bw.write("# name of the champion name file\n");
		bw.write("ChampNameFile" + this.config.get("ChampNameFile") + "\n");
		bw.write("# name of the champion file for each champion\n");
		bw.write("ChampionFile=" + this.config.get("ChampionFile") + "\n");
		bw.write("# name of the info file for each champion\n");
		bw.write("ChampionNoteFile=" + this.config.get("ChampionNoteFile") + "\n");
		bw.write("# name of the items file\n");
		bw.write("ItemFile=" + this.config.get("ItemFile") + "\n");
		bw.write("# path to the League of Legends Folder\n");
		bw.write("LolPath=" + this.config.get("LolPath") + "\n");
		bw.write("# relative path of the input.ini file in the LoL folder\n");
		bw.write("Input.ini=" + this.config.get("Input.ini") + "\n");
		bw.write("# path to the game.cfg file\n");
		bw.write("game.cfg=" + this.config.get("game.cfg") + "\n");
		bw.write("# path to the custom input.ini file if no lol path is available\n");
		bw.write("CustomInput.ini=" + this.config.get("CustomInput.ini") + "\n");
		bw.write("# 1 if the lolpath should be used, 0 for the custom input.ini path\n");
		bw.write("useLoLPath=" + this.config.get("useLoLPath") + "\n");
		bw.write("# 1 if items should be overriden, else 0\n");
		bw.write("overrideItems=" + this.config.get("overrideItems") + "\n");
		bw.write("# image length of the champ potrait in pixel (between 32 and 160\n");
		bw.write("ChampImage_length=" + this.config.get("ChampImage_length") + "\n");
		bw.write("# image length of the spell and item images in pixel (between 32 and 160)\n");
		bw.write("SpellItemImage_length=" + this.config.get("SpellItemImage_length") + "\n");
		bw.write("# number of summoner spells per line in the choose summoner spell window\n");
		bw.write("SummonerSpellsPerLine=" + this.config.get("SummonerSpellsPerLine") + "\n");
		bw.write("# the length of the image which is used in the editor pane of the info tab\n");
		bw.write("editorImageLength=" + this.config.get("editorImageLength") + "\n");
		bw.write("# the file where to find all software update infos\n");
		bw.write("SoftwareUpdateInfoFile=" + this.config.get("SoftwareUpdateInfoFile") + "\n");		
		bw.write("# prefix: 0 -> nothing, 1 -> alt, 2 -> crtl, 3 -> shift\n");
		bw.write("# normal cast prefix\n");
		bw.write("normal_Prefix=" + this.config.get("normal_Prefix") + "\n");
		bw.write("# smartcast prefix\n");
		bw.write("smartcast_Prefix=" + this.config.get("smartcast_Prefix") + "\n");
		bw.write("# selfcast prefix\n");
		bw.write("selfcast_Prefix=" + this.config.get("selfcast_Prefix") + "\n");
		bw.write("# level up prefix\n");
		bw.write("levelup_Prefix=" + this.config.get("levelup_Prefix") + "\n");
		bw.write("# normal prefix when smartcast is activated\n");
		bw.write("normal_when_smartcast_Prefix=" + this.config.get("normal_when_smartcast_Prefix") + "\n");
		bw.write("# normal prefix when selfcast is activated\n");
		bw.write("normal_when_selfcast_Prefix=" + this.config.get("normal_when_selfcast_Prefix") + "\n");
		bw.write("# normal prefix when smart+selfcast is activated\n");
		bw.write("normal_when_smartcast_and_selfcast_Prefix=" + this.config.get("normal_when_smartcast_and_selfcast_Prefix") + "\n");

		bw.write("\n# these entries are for the specific data collectors");
		bw.write("# link to the last update file\n");
		bw.write("UpdateInfoFile=" + this.config.get("UpdateInfoFile") + "\n");
		bw.write("# time when the last update using fast way is made\n");
		bw.write("lastUpdate=" + this.config.get("lastUpdate"));

		bw.close();
	}

	/**
	 * secures that a path ends with / or \
	 * 
	 * @return the modified path
	 */
	private String formatPath(String path) {
		path = path.replace("\\", "/");
		if (path.endsWith("\\") || path.endsWith("/") || path.trim().length() == 0)
			return path;
		else
			return path + "/";
	}
	
	
	public String getSoftwareUpdateLink() {
		return this.config.get("SoftwareUpdateInfoFile");
	}
	
	public int getFontSize() {
		return Integer.parseInt(this.config.get("FontSize"));
	}
	
	public String getChampFolder() {
		return Constants.PATH + formatPath(this.config.get("ChampFolder"));
	}

	public void setCurrDimension(Dimension dim) {
		this.config.put("currWidth", Integer.toString((int) dim.getWidth()));
		this.config.put("currHeight", Integer.toString((int) dim.getHeight()));

	}

	public Dimension getCurrDimension() {
		return new Dimension(Integer.parseInt(this.config.get("currWidth")), Integer.parseInt(this.config.get("currHeight")));
	}

	public void setCurrentLanguage(Locale locale) {
		this.config.put("currLanguage", locale.toString());
	}

	public Locale getCurrentLanguage() {
		String[] tmp = this.config.get("currLanguage").split("_");
		return new Locale(tmp[0], tmp[1]);
	}

	public String getItemFolder() {
		return Constants.PATH + formatPath(this.config.get("ItemFolder"));
	}

	public String getSummonerSpellsFolder() {
		return Constants.PATH + formatPath(this.config.get("SummonerSpellsFolder"));
	}

	public String getSummonerSpellsFile() {
		return this.config.get("SummonerSpellFile");
	}

	public String getChampNamesFile() {
		return this.config.get("ChampNameFile");
	}

	public String getChampionFile() {
		return this.config.get("ChampionFile");
	}

	public String getChampionNoteFile() {
		return this.config.get("ChampionNoteFile");
	}

	public String getItemFile() {
		return this.config.get("ItemFile");
	}

	public void setCustomInputIniPath(String path) {
		if (path.startsWith("/") || path.startsWith("\\"))
			path = path.substring(1);

		this.config.put("CustomInput.ini", formatPath(path));
	}

	public String getCustomInputIniPath() {
		return formatPath(this.config.get("CustomInput.ini"));
	}

	public void setLolPath(String path) {
		this.config.put("LolPath", formatPath(path));
	}

	public String getLolPath() {
		return formatPath(this.config.get("LolPath"));
	}

	public void setLolPathFound(boolean found) {
		this.config.put("LolPathFound", found ? "1" : "0");
	}

	public boolean getLolPathFound() {
		return this.config.get("LolPathFound").equals("1");
	}

	public void setInputIni(String path) {
		if (path.startsWith("/") || path.startsWith("\\"))
			path = path.substring(1);

		this.config.put("Input.ini", path);
	}

	public String getInputIni() {
		return this.config.get("Input.ini");
	}

	public void setGameCfg(String path) {
		if (path.startsWith("/") || path.startsWith("\\"))
			path = path.substring(1);

		this.config.put("game.cfg", path);
	}

	public String getGameCfg() {
		return this.config.get("game.cfg");
	}

	public void setUseLoLPath(boolean use) {
		this.config.put("useLoLPath", use ? "1" : "0");
	}

	public boolean getUseLolPath() {
		return this.config.get("useLoLPath").equals("1");
	}

	public void setOverrideItems(boolean or) {
		this.config.put("overrideItems", or ? "1" : "0");
	}

	public boolean getOverrideItems() {
		return this.config.get("overrideItems").equals("1");
	}

	public void setChampionImageLength(int length) {
		this.config.put("ChampImage_length", Integer.toString(length));
	}

	public int getChampImageLength() {
		return Integer.parseInt(this.config.get("ChampImage_length"));
	}

	public void setSpellItemLength(int length) {
		this.config.put("SpellItemImage_length", Integer.toString(length));
	}

	public int getSpellItemImageLength() {
		return Integer.parseInt(this.config.get("SpellItemImage_length"));
	}

	public void setSummonerSpellsPerLine(int col) {
		this.config.put("SummonerSpellsPerLine", Integer.toString(col));
	}

	public int getSummonerSpellsPerLine() {
		return Integer.parseInt(this.config.get("SummonerSpellsPerLine"));
	}

	public void setEditorImageLength(int length) {
		this.config.put("editorImageLength", Integer.toString(length));
	}

	public int getEditorImageLength() {
		return Integer.parseInt(this.config.get("editorImageLength"));
	}

	public void setNormalPrefixID(int id) {
		this.config.put("normal_Prefix", Integer.toString(id));
	}

	public int getNormalPrefixID() {
		return Integer.parseInt(this.config.get("normal_Prefix"));
	}

	public String getNormalPrefix() {
		return Hotkeys.getPrefix(Integer.parseInt(this.config.get("normal_Prefix")));
	}

	public void setSmartcastPrefixID(int id) {
		this.config.put("smartcast_Prefix", Integer.toString(id));
	}

	public int getSmartcastPrefixID() {
		return Integer.parseInt(this.config.get("smartcast_Prefix"));
	}

	public String getSmartcastPrefix() {
		return Hotkeys.getPrefix(Integer.parseInt(this.config.get("smartcast_Prefix")));
	}

	public void setSelfcastPrefixID(int id) {
		this.config.put("selfcast_Prefix", Integer.toString(id));
	}

	public int getSelfcastPrefixID() {
		return Integer.parseInt(this.config.get("selfcast_Prefix"));
	}

	public String getSelfcastPrefix() {
		return Hotkeys.getPrefix(Integer.parseInt(this.config.get("selfcast_Prefix")));
	}

	public void setLevelupPrefixID(int id) {
		this.config.put("levelup_Prefix", Integer.toString(id));
	}

	public int getLevelupPrefixID() {
		return Integer.parseInt(this.config.get("levelup_Prefix"));
	}

	public String getLevelupPrefix() {
		return Hotkeys.getPrefix(Integer.parseInt(this.config.get("levelup_Prefix")));
	}

	public void setNormalWhenSmartcastPrefixID(int id) {
		this.config.put("normal_when_smartcast_Prefix", Integer.toString(id));
	}

	public int getNormalWhenSmartcastPrefixID() {
		return Integer.parseInt(this.config.get("normal_when_smartcast_Prefix"));
	}

	public String getNormalWhenSmartcastPrefix() {
		return Hotkeys.getPrefix(Integer.parseInt(this.config.get("normal_when_smartcast_Prefix")));
	}

	public void setNormalWhenSelfcastPrefixID(int id) {
		this.config.put("normal_when_selfcast_Prefix", Integer.toString(id));
	}

	public int getNormalWhenSelfcastPrefixID() {
		return Integer.parseInt(this.config.get("normal_when_selfcast_Prefix"));
	}

	public String getNormalWhenSelfcastPrefix() {
		return Hotkeys.getPrefix(Integer.parseInt(this.config.get("normal_when_selfcast_Prefix")));
	}

	public void setNormalWhenSmartAndSelfcastPrefixID(int id) {
		this.config.put("normal_when_smartcast_and_selfcast_Prefix", Integer.toString(id));
	}

	public int getNormalWhenSmartAndSelfcastPrefixID() {
		return Integer.parseInt(this.config.get("normal_when_smartcast_and_selfcast_Prefix"));
	}

	public String getNormalWhenSmartAndSelfcastPrefix() {
		return Hotkeys.getPrefix(Integer.parseInt(this.config.get("normal_when_smartcast_and_selfcast_Prefix")));
	}

	public String getUpdateInfoLink() {
		return this.config.get("UpdateInfoFile");
	}

	public void setDataUpdateLink(String link) {
		this.config.put("DataUpdateFile", link);
	}

	public String getDataUpdateLink() {
		return this.config.get("DataUpdateFile");
	}

	public void setcurrentSkin(String skin) {
		this.config.put("currSkin", skin);
	}

	public String getCurrentSkin() {
		return this.config.get("currSkin");
	}

	public void setCurrentChamp(String champ) {
		this.config.put("currChamp", champ);
	}

	public String getCurrentChamp() {
		return this.config.get("currChamp");
	}

	public void setLastUpdateDate(Date date) {
		this.config.put("lastUpdate", date.toString());
	}

	public String getLastUpdateDate() {
		return this.config.get("lastUpdate");
	}
}