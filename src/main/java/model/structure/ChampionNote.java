package model.structure;

public class ChampionNote {

	/**
	 * Champions name
	 */
	private String name;
	/**
	 * skill array up to down -> skill, left to right -> level
	 */
	private boolean[][] skill;
	/**
	 * runepage name
	 */
	private String runepage;
	/**
	 * mastery page name
	 */
	private String masteryPage;
	/**
	 * summonerspell 1 name
	 */
	private String summonerspell1;
	/**
	 * summonerspell 2 name
	 */
	private String summonerspell2;
	/**
	 * info text in plain or BB
	 */
	private String text;
	/**
	 * true if info text is BB, else false
	 */
	private boolean html;

	/**
	 * Constructor
	 */
	public ChampionNote() {
		name = "";
		skill = new boolean[4][18];
		runepage = "";
		masteryPage = "";
		summonerspell1 = "not set";
		summonerspell2 = "not set";
		text = "";
		html = false;
	}

	/**
	 * Constructor
	 * 
	 * @param info
	 *            the info to copy
	 */
	public ChampionNote(ChampionNote info) {
		name = info.getName();

		skill = new boolean[4][18];
		for (int i = 0; i < skill.length; i++)
			for (int j = 0; j < skill[0].length; j++)
				this.skill[i][j] = info.getSkill()[i][j];

		runepage = info.getRunepage();
		masteryPage = info.getMasteryPage();
		summonerspell1 = info.getSummonerspell1();
		summonerspell2 = info.getSummonerspell2();
		text = info.getText();
		html = info.isHtml();
	}

	/**
	 * gets the champions name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name
	 * 
	 * @param name
	 *            given name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets the skill array
	 * 
	 * @return the skills
	 */
	public boolean[][] getSkill() {
		return skill;
	}

	/**
	 * gets the skills as a string of the form a,b,c,d,a,... a,b,c in {0,1,2,3}
	 * 
	 * @return the skill string
	 */
	public String getSkillString() {
		String result = "";
		for (int col = 0; col < this.skill[0].length; col++) {
			for (int row = 0; row < this.skill.length; row++) {
				if (this.skill[row][col])
					result += row + ",";
			}
		}

		return result.substring(0, result.length() - 1);
	}

	/**
	 * sets the skill array
	 * 
	 * @param skill
	 *            the array
	 */
	public void setSkill(boolean[][] skill) {
		this.skill = skill;
	}

	/**
	 * sets the skill string of the form a,b,c,d,a,... a,b,c in {0,1,2,3}
	 * 
	 * @param skill
	 *            the skill string
	 */
	public void setSkill(String skill) {
		String[] tmp = skill.split(",");

		for (int i = 0; i < tmp.length; i++) {
			this.skill[Integer.parseInt(tmp[i].trim())][i] = true;
		}
	}

	/**
	 * gets the runepage name
	 * 
	 * @return the name
	 */
	public String getRunepage() {
		return runepage;
	}

	/**
	 * sets the runepage name
	 * 
	 * @param runepage
	 *            the name
	 */
	public void setRunepage(String runepage) {
		this.runepage = runepage;
	}

	/**
	 * gets the mastery page name
	 * 
	 * @return the name
	 */
	public String getMasteryPage() {
		return masteryPage;
	}

	/**
	 * sets the mastery page name
	 * 
	 * @param masteryPage
	 *            the name
	 */
	public void setMasteryPage(String masteryPage) {
		this.masteryPage = masteryPage;
	}

	/**
	 * gets the summonerspell 1 name
	 * 
	 * @return the name
	 */
	public String getSummonerspell1() {
		return summonerspell1;
	}

	/**
	 * sets the summoner spell 1
	 * 
	 * @param summonerspell1
	 *            the name
	 */
	public void setSummonerspell1(String summonerspell1) {
		this.summonerspell1 = summonerspell1;
	}

	/**
	 * gets the summonerspell 2 name
	 * 
	 * @return the name
	 */
	public String getSummonerspell2() {
		return summonerspell2;
	}

	/**
	 * sets the summonerspell 2 nam
	 * 
	 * @param summonerspell2
	 *            the name
	 */
	public void setSummonerspell2(String summonerspell2) {
		this.summonerspell2 = summonerspell2;
	}

	/**
	 * gets the info text
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * sets the info text
	 * 
	 * @param text
	 *            the text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * gets the html boolean
	 * 
	 * @return true if text is BB, else false
	 */
	public boolean isHtml() {
		return html;
	}

	/**
	 * sets the html boolean
	 * 
	 * @param html
	 *            true if text is BB, else false
	 */
	public void setHtml(boolean html) {
		this.html = html;
	}
}