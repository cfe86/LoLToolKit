package model.structure;

import config.Config;

public class ChampionSpells {

	/**
	 * champion name
	 */
	private String name;
	/**
	 * champion image path
	 */
	private String image;
	/**
	 * passive name
	 */
	private String passive;
	/**
	 * passive image path
	 */
	private String passiveImage;
	/**
	 * passive description
	 */
	private String passiveDescr;
	/**
	 * spell1 name
	 */
	private String spell1;
	/**
	 * spell1 image path
	 */
	private String spell1Image;
	/**
	 * spell1 description
	 */
	private String spell1Descr;
	/**
	 * spell2 name
	 */
	private String spell2;
	/**
	 * spell2 image path
	 */
	private String spell2Image;
	/**
	 * spell2 description
	 */
	private String spell2Descr;
	/**
	 * spell3 name
	 */
	private String spell3;
	/**
	 * spell3 image path
	 */
	private String spell3Image;
	/**
	 * spell3 description
	 */
	private String spell3Descr;
	/**
	 * spell4 name
	 */
	private String spell4;
	/**
	 * spell4 image path
	 */
	private String spell4Image;
	/**
	 * spell4 description
	 */
	private String spell4Descr;
	/**
	 * summonerspell1 name
	 */
	private String summonerSpell1;
	/**
	 * summonerspell1 image path
	 */
	private String summonerSpell1Image;
	/**
	 * summonerspell2 name
	 */
	private String summonerSpell2;
	/**
	 * summonerspell2 image path
	 */
	private String summonerSpell2Image;

	/**
	 * Constructor
	 */
	public ChampionSpells() {
		this.name = "";
		this.image = "";
		this.passive = "";
		this.passiveDescr = "";
		this.passiveImage = "";
		this.spell1 = "";
		this.spell1Descr = "";
		this.spell1Image = "";
		this.spell2 = "";
		this.spell2Descr = "";
		this.spell2Image = "";
		this.spell3 = "";
		this.spell3Descr = "";
		this.spell3Image = "";
		this.spell4 = "";
		this.spell4Descr = "";
		this.spell4Image = "";
		this.summonerSpell1 = "Flash";
		this.summonerSpell1Image = "";
		this.summonerSpell2 = "Ignite";
		this.summonerSpell2Image = "";
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 *            given champion name
	 */
	public ChampionSpells(String name) {
		this();
		this.name = name;
	}

	/**
	 * generates all relative image paths
	 */
	public void generateImagePaths() {
		image = Config.getInstance().getChampFolder() + name.toLowerCase() + "/image.png";
		passiveImage = Config.getInstance().getChampFolder() + name.toLowerCase() + "/passive.png";
		spell1Image = Config.getInstance().getChampFolder() + name.toLowerCase() + "/spell1.png";
		spell2Image = Config.getInstance().getChampFolder() + name.toLowerCase() + "/spell2.png";
		spell3Image = Config.getInstance().getChampFolder() + name.toLowerCase() + "/spell3.png";
		spell4Image = Config.getInstance().getChampFolder() + name.toLowerCase() + "/spell4.png";
		summonerSpell1Image = Config.getInstance().getSummonerSpellsFolder() + summonerSpell1.toLowerCase() + ".png";
		summonerSpell2Image = Config.getInstance().getSummonerSpellsFolder() + summonerSpell2.toLowerCase() + ".png";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image.trim();
	}

	public String getPassive() {
		return passive;
	}

	public void setPassive(String passive) {
		this.passive = passive.trim();
	}

	public String getPassiveImage() {
		return passiveImage.trim();
	}

	public void setPassiveImage(String passiveImage) {
		this.passiveImage = passiveImage.trim();
	}

	public String getSpell1() {
		return spell1;
	}

	public void setSpell1(String spell1) {
		this.spell1 = spell1.trim();
	}

	public String getSpell1Image() {
		return spell1Image;
	}

	public void setSpell1Image(String spell1Image) {
		this.spell1Image = spell1Image.trim();
	}

	public String getSpell2() {
		return spell2;
	}

	public void setSpell2(String spell2) {
		this.spell2 = spell2.trim();
	}

	public String getSpell2Image() {
		return spell2Image;
	}

	public void setSpell2Image(String spell2Image) {
		this.spell2Image = spell2Image.trim();
	}

	public String getSpell3() {
		return spell3;
	}

	public void setSpell3(String spell3) {
		this.spell3 = spell3.trim();
	}

	public String getSpell3Image() {
		return spell3Image;
	}

	public void setSpell3Image(String spell3Image) {
		this.spell3Image = spell3Image.trim();
	}

	public String getSpell4() {
		return spell4;
	}

	public void setSpell4(String spell4) {
		this.spell4 = spell4.trim();
	}

	public String getSpell4Image() {
		return spell4Image;
	}

	public void setSpell4Image(String spell4Image) {
		this.spell4Image = spell4Image.trim();
	}

	public String getSummonerSpell1() {
		return summonerSpell1;
	}

	public void setSummonerSpell1(String summonerSpell1) {
		this.summonerSpell1 = summonerSpell1.trim();
	}

	public String getSummonerSpell2() {
		return summonerSpell2;
	}

	public void setSummonerSpell2(String summonerSpell2) {
		this.summonerSpell2 = summonerSpell2.trim();
	}

	public String getSummonerSpell1Image() {
		return summonerSpell1Image;
	}

	public String getSummonerSpell2Image() {
		return summonerSpell2Image;
	}

	public String getPassiveDescr() {
		return passiveDescr;
	}

	public void setPassiveDescr(String passiveDescr) {
		this.passiveDescr = passiveDescr.trim();
	}

	public String getSpell1Descr() {
		return spell1Descr;
	}

	public void setSpell1Descr(String spell1Descr) {
		this.spell1Descr = spell1Descr.trim();
	}

	public String getSpell2Descr() {
		return spell2Descr;
	}

	public void setSpell2Descr(String spell2Descr) {
		this.spell2Descr = spell2Descr.trim();
	}

	public String getSpell3Descr() {
		return spell3Descr;
	}

	public void setSpell3Descr(String spell3Descr) {
		this.spell3Descr = spell3Descr.trim();
	}

	public String getSpell4Descr() {
		return spell4Descr;
	}

	public void setSpell4Descr(String spell4Descr) {
		this.spell4Descr = spell4Descr.trim();
	}
}