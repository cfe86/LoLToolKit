package model.structure;

import config.Constants;

public class Hotkeys {

	/**
	 * Control string sequence
	 */
	public static final String CTRL = "[Ctrl]";
	/**
	 * Shift string sequence
	 */
	public static final String SHIFT = "[Shift]";
	/**
	 * Alt string sequence
	 */
	public static final String ALT = "[Alt]";
	/**
	 * Space string sequence
	 */
	public static final String SPACE = "[Space]";
	/**
	 * string which is shown if space is pressed
	 */
	public static final String SPACE_SHOW = "Sp";
	/**
	 * unbound string sequence
	 */
	public static final String UNBUND = "[<Unbound>]";
	/**
	 * no prefix string sequence
	 */
	public static final String NO_PREFIX = "<no prefix>";

	/**
	 * gets the prefix depending on the given hotkey ID
	 * 
	 * @param h
	 *            hotkey ID
	 * 
	 * @return the string sequence
	 */
	public static String getPrefix(int h) {
		switch (h) {
			case Constants.NOTHING:
				return "";
			case Constants.CTRL:
				return Hotkeys.CTRL;
			case Constants.SHIFT:
				return Hotkeys.SHIFT;
			case Constants.ALT:
				return Hotkeys.ALT;
		}

		return "";
	}

	/*
	 * [GameEvents] evtUseItem evtSmartCastItem evtSelfCastItem
	 * 
	 * evtCastSpell1 evtNormalCastSpell1 evtSmartCastSpell2 evtSelfCastSpell3
	 * evtSmartPlusSelfCastSpell4
	 * 
	 * evtCastAvatarSpell evtNormalCastAvatarSpell1 evtSmartCastAvatarSpell2
	 * evtSelfCastAvatarSpell3 evtSmartPlusSelfCastAvatarSpell4
	 */

	/*
	 * [Quickbinds] evtUseItem4smart evtCastAvatarSpell2smart evtCastSpell2smart
	 */

	// smartcast
	private boolean smartcastSpell1;
	private boolean smartcastSpell2;
	private boolean smartcastSpell3;
	private boolean smartcastSpell4;
	private boolean smartcastSummonerSpell1;
	private boolean smartcastSummonerSpell2;
	private boolean smartcastTrinket;
	private boolean smartcastItem1;
	private boolean smartcastItem2;
	private boolean smartcastItem3;
	private boolean smartcastItem4;
	private boolean smartcastItem5;
	private boolean smartcastItem6;

	// selfcast
	private boolean selfcastSpell1;
	private boolean selfcastSpell2;
	private boolean selfcastSpell3;
	private boolean selfcastSpell4;
	private boolean selfcastSummonerSpell1;
	private boolean selfcastSummonerSpell2;
	private boolean selfcastItem1;
	private boolean selfcastItem2;
	private boolean selfcastItem3;
	private boolean selfcastItem4;
	private boolean selfcastItem5;
	private boolean selfcastItem6;

	// command
	private String spell1;
	private String spell2;
	private String spell3;
	private String spell4;
	private String summonerSpell1;
	private String summonerSpell2;
	private String Trinket;
	private String item1;
	private String item2;
	private String item3;
	private String item4;
	private String item5;
	private String item6;

	/**
	 * the range indicator when quick cast is enabled
	 */
	private boolean showRangeIndicator;

	/**
	 * Constructor
	 */
	public Hotkeys() {
		// smartcast
		smartcastSpell1 = false;
		smartcastSpell2 = false;
		smartcastSpell3 = false;
		smartcastSpell4 = false;
		smartcastSummonerSpell1 = false;
		smartcastSummonerSpell2 = false;
		smartcastTrinket = false;
		smartcastItem1 = false;
		smartcastItem2 = false;
		smartcastItem3 = false;
		smartcastItem4 = false;
		smartcastItem5 = false;
		smartcastItem6 = false;

		// selfcast
		selfcastSpell1 = false;
		selfcastSpell2 = false;
		selfcastSpell3 = false;
		selfcastSpell4 = false;
		selfcastSummonerSpell1 = false;
		selfcastSummonerSpell2 = false;
		selfcastItem1 = false;
		selfcastItem2 = false;
		selfcastItem3 = false;
		selfcastItem4 = false;
		selfcastItem5 = false;
		selfcastItem6 = false;

		// command
		spell1 = "q";
		spell2 = "w";
		spell3 = "e";
		spell4 = "r";
		summonerSpell1 = "d";
		summonerSpell2 = "f";
		Trinket = "4";
		item1 = "1";
		item2 = "2";
		item3 = "3";
		item4 = "5";
		item5 = "6";
		item6 = "7";

		// range indicator
		showRangeIndicator = false;
	}

	public boolean isSmartcastSpell1() {
		return smartcastSpell1;
	}

	public void setSmartcastSpell1(boolean smartcastSpell1) {
		this.smartcastSpell1 = smartcastSpell1;
	}

	public boolean isSmartcastSpell2() {
		return smartcastSpell2;
	}

	public void setSmartcastSpell2(boolean smartcastSpell2) {
		this.smartcastSpell2 = smartcastSpell2;
	}

	public boolean isSmartcastSpell3() {
		return smartcastSpell3;
	}

	public void setSmartcastSpell3(boolean smartcastSpell3) {
		this.smartcastSpell3 = smartcastSpell3;
	}

	public boolean isSmartcastSpell4() {
		return smartcastSpell4;
	}

	public void setSmartcastSpell4(boolean smartcastSpell4) {
		this.smartcastSpell4 = smartcastSpell4;
	}

	public boolean isSmartcastTrinket() {
		return smartcastTrinket;
	}

	public void setSmartcastTrinket(boolean smartcastTrinket) {
		this.smartcastTrinket = smartcastTrinket;
	}

	public boolean isSmartcastItem1() {
		return smartcastItem1;
	}

	public void setSmartcastItem1(boolean smartcastItem1) {
		this.smartcastItem1 = smartcastItem1;
	}

	public boolean isSmartcastItem2() {
		return smartcastItem2;
	}

	public void setSmartcastItem2(boolean smartcastItem2) {
		this.smartcastItem2 = smartcastItem2;
	}

	public boolean isSmartcastItem3() {
		return smartcastItem3;
	}

	public void setSmartcastItem3(boolean smartcastItem3) {
		this.smartcastItem3 = smartcastItem3;
	}

	public boolean isSmartcastItem4() {
		return smartcastItem4;
	}

	public void setSmartcastItem4(boolean smartcastItem4) {
		this.smartcastItem4 = smartcastItem4;
	}

	public boolean isSmartcastItem5() {
		return smartcastItem5;
	}

	public void setSmartcastItem5(boolean smartcastItem5) {
		this.smartcastItem5 = smartcastItem5;
	}

	public boolean isSmartcastItem6() {
		return smartcastItem6;
	}

	public void setSmartcastItem6(boolean smartcastItem6) {
		this.smartcastItem6 = smartcastItem6;
	}

	public boolean isSelfcastSpell1() {
		return selfcastSpell1;
	}

	public void setSelfcastSpell1(boolean selfcastSpell1) {
		this.selfcastSpell1 = selfcastSpell1;
	}

	public boolean isSelfcastSpell2() {
		return selfcastSpell2;
	}

	public void setSelfcastSpell2(boolean selfcastSpell2) {
		this.selfcastSpell2 = selfcastSpell2;
	}

	public boolean isSelfcastSpell3() {
		return selfcastSpell3;
	}

	public void setSelfcastSpell3(boolean selfcastSpell3) {
		this.selfcastSpell3 = selfcastSpell3;
	}

	public boolean isSelfcastSpell4() {
		return selfcastSpell4;
	}

	public void setSelfcastSpell4(boolean selfcastSpell4) {
		this.selfcastSpell4 = selfcastSpell4;
	}

	public boolean isSelfcastItem1() {
		return selfcastItem1;
	}

	public void setSelfcastItem1(boolean selfcastItem1) {
		this.selfcastItem1 = selfcastItem1;
	}

	public boolean isSelfcastItem2() {
		return selfcastItem2;
	}

	public void setSelfcastItem2(boolean selfcastItem2) {
		this.selfcastItem2 = selfcastItem2;
	}

	public boolean isSelfcastItem3() {
		return selfcastItem3;
	}

	public void setSelfcastItem3(boolean selfcastItem3) {
		this.selfcastItem3 = selfcastItem3;
	}

	public boolean isSelfcastItem4() {
		return selfcastItem4;
	}

	public void setSelfcastItem4(boolean selfcastItem4) {
		this.selfcastItem4 = selfcastItem4;
	}

	public boolean isSelfcastItem5() {
		return selfcastItem5;
	}

	public void setSelfcastItem5(boolean selfcastItem5) {
		this.selfcastItem5 = selfcastItem5;
	}

	public boolean isSelfcastItem6() {
		return selfcastItem6;
	}

	public void setSelfcastItem6(boolean selfcastItem6) {
		this.selfcastItem6 = selfcastItem6;
	}

	public String getSpell1() {
		return spell1;
	}

	public void setSpell1(String spell1) {
		this.spell1 = spell1;
	}

	public String getSpell2() {
		return spell2;
	}

	public void setSpell2(String spell2) {
		this.spell2 = spell2;
	}

	public String getSpell3() {
		return spell3;
	}

	public void setSpell3(String spell3) {
		this.spell3 = spell3;
	}

	public String getSpell4() {
		return spell4;
	}

	public void setSpell4(String spell4) {
		this.spell4 = spell4;
	}

	public String getTrinket() {
		return Trinket;
	}

	public void setTrinket(String trinket) {
		Trinket = trinket;
	}

	public String getItem1() {
		return item1;
	}

	public void setItem1(String item1) {
		this.item1 = item1;
	}

	public String getItem2() {
		return item2;
	}

	public void setItem2(String item2) {
		this.item2 = item2;
	}

	public String getItem3() {
		return item3;
	}

	public void setItem3(String item3) {
		this.item3 = item3;
	}

	public String getItem4() {
		return item4;
	}

	public void setItem4(String item4) {
		this.item4 = item4;
	}

	public String getItem5() {
		return item5;
	}

	public void setItem5(String item5) {
		this.item5 = item5;
	}

	public String getItem6() {
		return item6;
	}

	public void setItem6(String item6) {
		this.item6 = item6;
	}

	public boolean isSmartcastSummonerSpell1() {
		return smartcastSummonerSpell1;
	}

	public void setSmartcastSummonerSpell1(boolean smartcastSummonerSpell1) {
		this.smartcastSummonerSpell1 = smartcastSummonerSpell1;
	}

	public boolean isSmartcastSummonerSpell2() {
		return smartcastSummonerSpell2;
	}

	public void setSmartcastSummonerSpell2(boolean smartcastSummonerSpell2) {
		this.smartcastSummonerSpell2 = smartcastSummonerSpell2;
	}

	public boolean isSelfcastSummonerSpell1() {
		return selfcastSummonerSpell1;
	}

	public void setSelfcastSummonerSpell1(boolean selfcastSummonerSpell1) {
		this.selfcastSummonerSpell1 = selfcastSummonerSpell1;
	}

	public boolean isSelfcastSummonerSpell2() {
		return selfcastSummonerSpell2;
	}

	public void setSelfcastSummonerSpell2(boolean selfcastSummonerSpell2) {
		this.selfcastSummonerSpell2 = selfcastSummonerSpell2;
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

	public boolean isShowRangeIndicator() {
		return showRangeIndicator;
	}

	public void setShowRangeIndicator(boolean showRangeIndicator) {
		this.showRangeIndicator = showRangeIndicator;
	}
}