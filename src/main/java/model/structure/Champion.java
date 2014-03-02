package model.structure;

public class Champion {

	/**
	 * hotkeys for the champion
	 */
	private Hotkeys hotkeys;

	/**
	 * spells of the champions
	 */
	private ChampionSpells spells;

	/**
	 * Constructor
	 */
	public Champion() {
		this.hotkeys = new Hotkeys();
		this.spells = new ChampionSpells();
	}

	/**
	 * merges the current champ with the given champ. The summoner spells and
	 * hotkeys will be taken from the new champ. Everything else will be taken
	 * from the current champ
	 * 
	 * @param champ
	 *            given new champ
	 */
	public void merge(Champion champ) {
		// merge hotkeys and summoner
		this.hotkeys = champ.getHotkeys();
		// this.spells = champ.getSpells();
		this.spells.setSummonerSpell1(champ.getSpells().getSummonerSpell1());
		this.spells.setSummonerSpell2(champ.getSpells().getSummonerSpell2());
	}

	/**
	 * generates the image paths for each spell
	 */
	public void generateImagePaths() {
		this.spells.generateImagePaths();
	}

	/**
	 * gets the hotkeys for the champion
	 * 
	 * @return the champion
	 */
	public Hotkeys getHotkeys() {
		return hotkeys;
	}

	/**
	 * sets the hotkeys
	 * 
	 * @param hotkeys
	 *            given hotkeys
	 */
	public void setHotkeys(Hotkeys hotkeys) {
		this.hotkeys = hotkeys;
	}

	/**
	 * gets the champion spells
	 * 
	 * @return the champion spells
	 */
	public ChampionSpells getSpells() {
		return spells;
	}

	/**
	 * sets the champion spells
	 * 
	 * @param spells
	 *            the champion spells
	 */
	public void setSpells(ChampionSpells spells) {
		this.spells = spells;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = this.spells.getName() + "\n";
		result += "Spells:\n";
		result += this.spells.getPassive() + ": " + this.spells.getPassiveDescr() + "\n";
		result += this.spells.getSpell1() + ": " + this.spells.getSpell1Descr() + "\n";
		result += this.spells.getSpell2() + ": " + this.spells.getSpell2Descr() + "\n";
		result += this.spells.getSpell3() + ": " + this.spells.getSpell3Descr() + "\n";
		result += this.spells.getSpell4() + ": " + this.spells.getSpell4Descr() + "\n";
		result += this.spells.getSummonerSpell1() + "\n";
		result += this.spells.getSummonerSpell2() + "\n\n";

		result += "i1: " + this.hotkeys.getItem1() + " i2: " + this.hotkeys.getItem2() + " i3: " + this.hotkeys.getItem3() + " i4: " + this.hotkeys.getItem4() + " i5: "
				+ this.hotkeys.getItem5() + " i6: " + this.hotkeys.getItem6() + " trinket: " + this.hotkeys.getTrinket();

		return result;
	}
}