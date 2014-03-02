package model.structure;

import java.util.ArrayList;
import java.util.List;

public class ImportData {

	/**
	 * champion name
	 */
	private String name;
	/**
	 * champion hotkeys
	 */
	private Hotkeys hotkeys;
	/**
	 * champion infos
	 */
	private List<ChampionNote> infos;
	/**
	 * summonerspell1 name
	 */
	private String summonerSpell1;
	/**
	 * summonerspell2 name
	 */
	private String summonerSpell2;

	/**
	 * Constructor
	 */
	public ImportData() {
		this.name = "";
		this.summonerSpell1 = "";
		this.summonerSpell2 = "";
		this.hotkeys = new Hotkeys();
		this.infos = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummonerSpell1() {
		return summonerSpell1;
	}

	public void setSummonerSpell1(String summonerSpell1) {
		this.summonerSpell1 = summonerSpell1;
	}

	public String getSummonerSpell2() {
		return summonerSpell2;
	}

	public void setSummonerSpell2(String summonerSpell2) {
		this.summonerSpell2 = summonerSpell2;
	}

	public Hotkeys getHotkeys() {
		return hotkeys;
	}

	public void addInfo(ChampionNote info) {
		this.infos.add(info);
	}

	public List<ChampionNote> getInfos() {
		return this.infos;
	}
}