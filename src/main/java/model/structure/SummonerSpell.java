package model.structure;

import config.Config;

public class SummonerSpell {

	/**
	 * summonerspell name
	 */
	private String name;
	/**
	 * summonerspell image path
	 */
	private String image;

	/**
	 * Constructor
	 */
	public SummonerSpell() {
		this.name = "";
		this.image = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * generates the image path
	 */
	public void generateImagePath() {
		this.image = Config.getInstance().getSummonerSpellsFolder() + name.toLowerCase() + ".png";
	}
}