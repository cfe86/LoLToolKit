package model.configcreator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.Config;
import config.ConfigReader;
import logging.LogUtil;
import model.configcreator.interfaces.IConfigCreator;
import model.exception.ConfigCreatorException;
import model.structure.Champion;
import model.structure.Hotkeys;

public class InputConfigCreator implements IConfigCreator {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * evtUseXXXsmart ID
	 */
	private final int evtUseXXXsmart = 0;

	/**
	 * evtUseCast ID
	 */
	private final int evtUseCast = 1;

	/**
	 * evtNormal ID
	 */
	private final int evtNormal = 2;

	/**
	 * evtSmart ID
	 */
	private final int evtSmart = 3;

	/**
	 * evtSelf ID
	 */
	private final int evtSelf = 4;

	/**
	 * evtSmartSelf ID
	 */
	private final int evtSmartSelf = 5;

	/**
	 * the input.ini text after creation
	 */
	private String inputTxt;

	//@formatter:off
	/*
	 * [GameEvents]  
	 * evtUseItem1
	 * evtUseItem2
	 * evtUseItem3
	 * evtUseItem4
	 * evtUseItem5
	 * evtUseItem6
	 *
	 * evtNormalCastItem1
	 * evtNormalCastItem2
	 * evtNormalCastItem3
	 * evtNormalCastItem4
	 * evtNormalCastItem5
	 * evtNormalCastItem6
	 * 
	 * evtSmartCastItem1
	 * evtSmartCastItem2
	 * evtSmartCastItem3
	 * evtSmartCastItem4
	 * evtSmartCastItem5
	 * evtSmartCastItem6
	 * 
	 * evtSelfCastItem1
	 * evtSelfCastItem2
	 * evtSelfCastItem3
	 * evtSelfCastItem4
	 * evtSelfCastItem5
	 * evtSelfCastItem6
	 * 
	 * 
	 * 
	 * -- normal cast oben gesetzt && normal cast = evtCast -> evtNormalCastItem3=leer
	 * evtCastSpell1
	 * evtCastSpell2
	 * evtCastSpell3
	 * evtCastSpell4
	 * evtCastAvatarSpell1
	 * evtCastAvatarSpell2
	 * 
	 * 
	 * -- normal cast unten gesetzt %% normal cast = event cast -> evtCastSpell = unbound
	 * evtNormalCastSpell1
	 * evtNormalCastSpell2
	 * evtNormalCastSpell3
	 * evtNormalCastSpell4
	 * evtNormalCastAvatarSpell1
	 * evtNormalCastAvatarSpell2
	 * 
	 * -- smart cast spell 
	 * evtSmartCastSpell1
	 * evtSmartCastSpell2
	 * evtSmartCastSpell3
	 * evtSmartCastSpell4
	 * evtSmartCastAvatarSpell1
	 * evtSmartCastAvatarSpell2
	 * 
	 *  -- self cast spell
	 * evtSelfCastSpell1
	 * evtSelfCastSpell2
	 * evtSelfCastSpell3
	 * evtSelfCastSpell4
	 * evtSelfCastAvatarSpell1
	 * evtSelfCastAvatarSpell2
	 * 
	 *  -- smart + self spell
	 * evtSmartPlusSelfCastSpell1
	 * evtSmartPlusSelfCastSpell2
	 * evtSmartPlusSelfCastSpell3
	 * evtSmartPlusSelfCastSpell4
	 * evtSmartPlusSelfCastAvatarSpell1
	 * evtSmartPlusSelfCastAvatarSpell2
	 * 
	 * evtUseVisionItem
	 * 
	 * evtNormalCastVisionItem
	 * 
	 * evtSmartCastVisionItem
	 * 
	 * evtLevelSpell1
	 * evtLevelSpell2
	 * evtLevelSpell3
	 * evtLevelSpell4
	 * 
	 * 
	 * -- the green button at the top under binding 1-> highlight, 0 -> unhighlight
	 * [Quickbinds]
	 * evtUseItem1smart=
	 * evtUseItem2smart=
	 * evtUseItem3smart= 
	 * evtUseItem4smart=
	 * evtUseItem5smart=
	 * evtUseItem6smart= 
	 * evtCastAvatarSpell1smart=
	 * evtCastAvatarSpell2smart= 
	 * evtCastSpell1smart=
	 * evtCastSpell2smart=
	 * evtCastSpell3smart=
	 * evtCastSpell4smart=
	 * evtUseVisionItemsmart=
	 */
	//@formatter:on

	/**
	 * Constructor
	 */
	public InputConfigCreator() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.configcreator.interfaces.IConfigCreator#getInputText()
	 */
	@Override
	public String getInputText() {
		return this.inputTxt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * model.configcreator.interfaces.IConfigCreator#generateInputConfig(model
	 * .collector.Champion)
	 */
	@Override
	public void generateInputConfig(Champion champion) throws ConfigCreatorException {

		try {
			Hotkeys h = champion.getHotkeys();

			// fill map with default values
			Map<String, String> params = new HashMap<>();
			params.put("evtUseItem1", "[1]");
			params.put("evtUseItem2", "[2]");
			params.put("evtUseItem3", "[3]");
			params.put("evtUseItem4", "[5]");
			params.put("evtUseItem5", "[6]");
			params.put("evtUseItem6", "[7]");

			params.put("evtNormalCastItem1", "");
			params.put("evtNormalCastItem2", "");
			params.put("evtNormalCastItem3", "");
			params.put("evtNormalCastItem4", "");
			params.put("evtNormalCastItem5", "");
			params.put("evtNormalCastItem6", "");

			params.put("evtSmartCastItem1", "[Shift][1]");
			params.put("evtSmartCastItem2", "[Shift][2]");
			params.put("evtSmartCastItem3", "[Shift][3]");
			params.put("evtSmartCastItem4", "[Shift][5]");
			params.put("evtSmartCastItem5", "[Shift][6]");
			params.put("evtSmartCastItem6", "[Shift][7]");

			params.put("evtSelfCastItem1", "[Alt][1]");
			params.put("evtSelfCastItem2", "[Alt][2]");
			params.put("evtSelfCastItem3", "[Alt][3]");
			params.put("evtSelfCastItem4", "[Alt][5]");
			params.put("evtSelfCastItem5", "[Alt][6]");
			params.put("evtSelfCastItem6", "[Alt][7]");

			params.put("evtCastSpell1", "[q]");
			params.put("evtCastSpell2", "[w]");
			params.put("evtCastSpell3", "[e]");
			params.put("evtCastSpell4", "[r]");
			params.put("evtCastAvatarSpell1", "[d]");
			params.put("evtCastAvatarSpell2", "[f]");

			params.put("evtNormalCastSpell1", "");
			params.put("evtNormalCastSpell2", "");
			params.put("evtNormalCastSpell3", "");
			params.put("evtNormalCastSpell4", "");
			params.put("evtNormalCastAvatarSpell1", "");
			params.put("evtNormalCastAvatarSpell2", "");

			params.put("evtSmartCastSpell1", "[Shift][q]");
			params.put("evtSmartCastSpell2", "[Shift][w]");
			params.put("evtSmartCastSpell3", "[Shift][e]");
			params.put("evtSmartCastSpell4", "[Shift][r]");
			params.put("evtSmartCastAvatarSpell1", "[Shift][d]");
			params.put("evtSmartCastAvatarSpell2", "[Shift][f]");

			params.put("evtSelfCastSpell1", "[Alt][q]");
			params.put("evtSelfCastSpell2", "[Alt][w]");
			params.put("evtSelfCastSpell3", "[Alt][e]");
			params.put("evtSelfCastSpell4", "[Alt][r]");
			params.put("evtSelfCastAvatarSpell1", "[Alt][d]");
			params.put("evtSelfCastAvatarSpell2", "[Alt][f]");

			params.put("evtSmartPlusSelfCastSpell1", "");
			params.put("evtSmartPlusSelfCastSpell2", "");
			params.put("evtSmartPlusSelfCastSpell3", "");
			params.put("evtSmartPlusSelfCastSpell4", "");
			params.put("evtSmartPlusSelfCastAvatarSpell1", "");
			params.put("evtSmartPlusSelfCastAvatarSpell2", "");

			params.put("evtUseVisionItem", "[4]");
			params.put("evtNormalCastVisionItem", "");
			params.put("evtSmartCastVisionItem", "[Shift][4]");

			params.put("evtLevelSpell1", "[Ctrl][q]");
			params.put("evtLevelSpell2", "[Ctrl][w]");
			params.put("evtLevelSpell3", "[Ctrl][e]");
			params.put("evtLevelSpell4", "[Ctrl][r]");

			params.put("evtUseItem1smart", "0");
			params.put("evtUseItem2smart", "0");
			params.put("evtUseItem3smart", "0");
			params.put("evtUseItem4smart", "0");
			params.put("evtUseItem5smart", "0");
			params.put("evtUseItem6smart", "0");
			params.put("evtCastAvatarSpell1smart", "0");
			params.put("evtCastAvatarSpell2smart", "0");
			params.put("evtCastSpell1smart", "0");
			params.put("evtCastSpell2smart", "0");
			params.put("evtCastSpell3smart", "0");
			params.put("evtCastSpell4smart", "0");
			params.put("evtUseVisionItemsmart", "0");

			// read file and load this config into params
			String path = Config.getInstance().getLolPath() + Config.getInstance().getInputIni();
			logger.log(Level.FINER, "read input.ini from " + path + " exists: " + new File(path).exists());

			if (new File(path).exists()) {
				BufferedReader br = new BufferedReader(new FileReader(new File(path)));
				String ini = "";
				String line;
				while ((line = br.readLine()) != null)
					ini += line.trim() + "\n";

				br.close();
				ini = ini.trim();
				ConfigReader.findParameters(ini, params, "=");
			}

			// override all neccessary read settings
			if (Config.getInstance().getOverrideItems()) {
				params.put("evtUseItem1", getShortcut(evtUseCast, h.isSmartcastItem1(), h.isSelfcastItem1(), h.getItem1()));
				params.put("evtUseItem2", getShortcut(evtUseCast, h.isSmartcastItem2(), h.isSelfcastItem2(), h.getItem2()));
				params.put("evtUseItem3", getShortcut(evtUseCast, h.isSmartcastItem3(), h.isSelfcastItem3(), h.getItem3()));
				params.put("evtUseItem4", getShortcut(evtUseCast, h.isSmartcastItem4(), h.isSelfcastItem4(), h.getItem4()));
				params.put("evtUseItem5", getShortcut(evtUseCast, h.isSmartcastItem5(), h.isSelfcastItem5(), h.getItem5()));
				params.put("evtUseItem6", getShortcut(evtUseCast, h.isSmartcastItem6(), h.isSelfcastItem6(), h.getItem6()));

				params.put("evtNormalCastItem1", getShortcut(evtNormal, h.isSmartcastItem1(), h.isSelfcastItem1(), h.getItem1()));
				params.put("evtNormalCastItem2", getShortcut(evtNormal, h.isSmartcastItem2(), h.isSelfcastItem2(), h.getItem2()));
				params.put("evtNormalCastItem3", getShortcut(evtNormal, h.isSmartcastItem3(), h.isSelfcastItem3(), h.getItem3()));
				params.put("evtNormalCastItem4", getShortcut(evtNormal, h.isSmartcastItem4(), h.isSelfcastItem4(), h.getItem4()));
				params.put("evtNormalCastItem5", getShortcut(evtNormal, h.isSmartcastItem5(), h.isSelfcastItem5(), h.getItem5()));
				params.put("evtNormalCastItem6", getShortcut(evtNormal, h.isSmartcastItem6(), h.isSelfcastItem6(), h.getItem6()));

				params.put("evtSmartCastItem1", getShortcut(evtSmart, h.isSmartcastItem1(), h.isSelfcastItem1(), h.getItem1()));
				params.put("evtSmartCastItem2", getShortcut(evtSmart, h.isSmartcastItem2(), h.isSelfcastItem2(), h.getItem2()));
				params.put("evtSmartCastItem3", getShortcut(evtSmart, h.isSmartcastItem3(), h.isSelfcastItem3(), h.getItem3()));
				params.put("evtSmartCastItem4", getShortcut(evtSmart, h.isSmartcastItem4(), h.isSelfcastItem4(), h.getItem4()));
				params.put("evtSmartCastItem5", getShortcut(evtSmart, h.isSmartcastItem5(), h.isSelfcastItem5(), h.getItem5()));
				params.put("evtSmartCastItem6", getShortcut(evtSmart, h.isSmartcastItem6(), h.isSelfcastItem6(), h.getItem6()));

				params.put("evtSelfCastItem1", getShortcut(evtSelf, h.isSmartcastItem1(), h.isSelfcastItem1(), h.getItem1()));
				params.put("evtSelfCastItem2", getShortcut(evtSelf, h.isSmartcastItem2(), h.isSelfcastItem2(), h.getItem2()));
				params.put("evtSelfCastItem3", getShortcut(evtSelf, h.isSmartcastItem3(), h.isSelfcastItem3(), h.getItem3()));
				params.put("evtSelfCastItem4", getShortcut(evtSelf, h.isSmartcastItem4(), h.isSelfcastItem4(), h.getItem4()));
				params.put("evtSelfCastItem5", getShortcut(evtSelf, h.isSmartcastItem5(), h.isSelfcastItem5(), h.getItem5()));
				params.put("evtSelfCastItem6", getShortcut(evtSelf, h.isSmartcastItem6(), h.isSelfcastItem6(), h.getItem6()));
			}

			params.put("evtCastSpell1", getShortcut(evtUseCast, h.isSmartcastSpell1(), h.isSelfcastSpell1(), h.getSpell1()));
			params.put("evtCastSpell2", getShortcut(evtUseCast, h.isSmartcastSpell2(), h.isSelfcastSpell2(), h.getSpell2()));
			params.put("evtCastSpell3", getShortcut(evtUseCast, h.isSmartcastSpell3(), h.isSelfcastSpell3(), h.getSpell3()));
			params.put("evtCastSpell4", getShortcut(evtUseCast, h.isSmartcastSpell4(), h.isSelfcastSpell4(), h.getSpell4()));
			params.put("evtCastAvatarSpell1", getShortcut(evtUseCast, h.isSmartcastSummonerSpell1(), h.isSelfcastSummonerSpell1(), h.getSummonerSpell1()));
			params.put("evtCastAvatarSpell2", getShortcut(evtUseCast, h.isSmartcastSummonerSpell2(), h.isSelfcastSummonerSpell2(), h.getSummonerSpell2()));

			params.put("evtNormalCastSpell1", getShortcut(evtNormal, h.isSmartcastSpell1(), h.isSelfcastSpell1(), h.getSpell1()));
			params.put("evtNormalCastSpell2", getShortcut(evtNormal, h.isSmartcastSpell2(), h.isSelfcastSpell2(), h.getSpell2()));
			params.put("evtNormalCastSpell3", getShortcut(evtNormal, h.isSmartcastSpell3(), h.isSelfcastSpell3(), h.getSpell3()));
			params.put("evtNormalCastSpell4", getShortcut(evtNormal, h.isSmartcastSpell4(), h.isSelfcastSpell4(), h.getSpell4()));
			params.put("evtNormalCastAvatarSpell1", getShortcut(evtNormal, h.isSmartcastSummonerSpell1(), h.isSelfcastSummonerSpell1(), h.getSummonerSpell1()));
			params.put("evtNormalCastAvatarSpell2", getShortcut(evtNormal, h.isSmartcastSummonerSpell2(), h.isSelfcastSummonerSpell2(), h.getSummonerSpell2()));

			params.put("evtSmartCastSpell1", getShortcut(evtSmart, h.isSmartcastSpell1(), h.isSelfcastSpell1(), h.getSpell1()));
			params.put("evtSmartCastSpell2", getShortcut(evtSmart, h.isSmartcastSpell2(), h.isSelfcastSpell2(), h.getSpell2()));
			params.put("evtSmartCastSpell3", getShortcut(evtSmart, h.isSmartcastSpell3(), h.isSelfcastSpell3(), h.getSpell3()));
			params.put("evtSmartCastSpell4", getShortcut(evtSmart, h.isSmartcastSpell4(), h.isSelfcastSpell4(), h.getSpell4()));
			params.put("evtSmartCastAvatarSpell1", getShortcut(evtSmart, h.isSmartcastSummonerSpell1(), h.isSelfcastSummonerSpell1(), h.getSummonerSpell1()));
			params.put("evtSmartCastAvatarSpell2", getShortcut(evtSmart, h.isSmartcastSummonerSpell2(), h.isSelfcastSummonerSpell2(), h.getSummonerSpell2()));

			params.put("evtSelfCastSpell1", getShortcut(evtSelf, h.isSmartcastSpell1(), h.isSelfcastSpell1(), h.getSpell1()));
			params.put("evtSelfCastSpell2", getShortcut(evtSelf, h.isSmartcastSpell2(), h.isSelfcastSpell2(), h.getSpell2()));
			params.put("evtSelfCastSpell3", getShortcut(evtSelf, h.isSmartcastSpell3(), h.isSelfcastSpell3(), h.getSpell3()));
			params.put("evtSelfCastSpell4", getShortcut(evtSelf, h.isSmartcastSpell4(), h.isSelfcastSpell4(), h.getSpell4()));
			params.put("evtSelfCastAvatarSpell1", getShortcut(evtSelf, h.isSmartcastSummonerSpell1(), h.isSelfcastSummonerSpell1(), h.getSummonerSpell1()));
			params.put("evtSelfCastAvatarSpell2", getShortcut(evtSelf, h.isSmartcastSummonerSpell2(), h.isSelfcastSummonerSpell2(), h.getSummonerSpell2()));

			params.put("evtSmartPlusSelfCastSpell1", getShortcut(evtSmartSelf, h.isSmartcastSpell1(), h.isSelfcastSpell1(), h.getSpell1()));
			params.put("evtSmartPlusSelfCastSpell2", getShortcut(evtSmartSelf, h.isSmartcastSpell2(), h.isSelfcastSpell2(), h.getSpell2()));
			params.put("evtSmartPlusSelfCastSpell3", getShortcut(evtSmartSelf, h.isSmartcastSpell3(), h.isSelfcastSpell3(), h.getSpell3()));
			params.put("evtSmartPlusSelfCastSpell4", getShortcut(evtSmartSelf, h.isSmartcastSpell4(), h.isSelfcastSpell4(), h.getSpell4()));
			params.put("evtSmartPlusSelfCastAvatarSpell1", getShortcut(evtSmartSelf, h.isSmartcastSummonerSpell1(), h.isSelfcastSummonerSpell1(), h.getSummonerSpell1()));
			params.put("evtSmartPlusSelfCastAvatarSpell2", getShortcut(evtSmartSelf, h.isSmartcastSummonerSpell2(), h.isSelfcastSummonerSpell2(), h.getSummonerSpell2()));

			params.put("evtUseVisionItem", getShortcut(evtUseCast, h.isSmartcastTrinket(), false, h.getTrinket()));
			params.put("evtNormalCastVisionItem", "");
			params.put("evtSmartCastVisionItem", getShortcut(evtSelf, h.isSmartcastTrinket(), false, h.getTrinket()));

			params.put("evtLevelSpell1", Config.getInstance().getLevelupPrefix() + getHotkey(h.getSpell1()));
			params.put("evtLevelSpell2", Config.getInstance().getLevelupPrefix() + getHotkey(h.getSpell2()));
			params.put("evtLevelSpell3", Config.getInstance().getLevelupPrefix() + getHotkey(h.getSpell3()));
			params.put("evtLevelSpell4", Config.getInstance().getLevelupPrefix() + getHotkey(h.getSpell4()));

			params.put("evtUseItem1smart", getShortcut(evtUseXXXsmart, h.isSmartcastItem1(), h.isSelfcastItem1(), h.getItem1()));
			params.put("evtUseItem2smart", getShortcut(evtUseXXXsmart, h.isSmartcastItem2(), h.isSelfcastItem2(), h.getItem2()));
			params.put("evtUseItem3smart", getShortcut(evtUseXXXsmart, h.isSmartcastItem3(), h.isSelfcastItem3(), h.getItem3()));
			params.put("evtUseItem4smart", getShortcut(evtUseXXXsmart, h.isSmartcastItem4(), h.isSelfcastItem4(), h.getItem4()));
			params.put("evtUseItem5smart", getShortcut(evtUseXXXsmart, h.isSmartcastItem5(), h.isSelfcastItem5(), h.getItem5()));
			params.put("evtUseItem6smart", getShortcut(evtUseXXXsmart, h.isSmartcastItem6(), h.isSelfcastItem6(), h.getItem6()));
			params.put("evtCastAvatarSpell1smart", getShortcut(evtUseXXXsmart, h.isSmartcastSummonerSpell1(), h.isSelfcastSummonerSpell1(), h.getSummonerSpell1()));
			params.put("evtCastAvatarSpell2smart", getShortcut(evtUseXXXsmart, h.isSmartcastSummonerSpell2(), h.isSelfcastSummonerSpell2(), h.getSummonerSpell2()));
			params.put("evtCastSpell1smart", getShortcut(evtUseXXXsmart, h.isSmartcastSpell1(), h.isSelfcastSpell1(), h.getSpell1()));
			params.put("evtCastSpell2smart", getShortcut(evtUseXXXsmart, h.isSmartcastSpell2(), h.isSelfcastSpell2(), h.getSpell2()));
			params.put("evtCastSpell3smart", getShortcut(evtUseXXXsmart, h.isSmartcastSpell3(), h.isSelfcastSpell3(), h.getSpell3()));
			params.put("evtCastSpell4smart", getShortcut(evtUseXXXsmart, h.isSmartcastSpell4(), h.isSelfcastSpell4(), h.getSpell4()));
			params.put("evtUseVisionItemsmart", getShortcut(evtUseXXXsmart, h.isSmartcastTrinket(), false, h.getTrinket()));

			// generate input text
			//@formatter:off
			this.inputTxt = "[GameEvents]\n" +  
						"evtUseItem1=" + params.get("evtUseItem1") + "\n" +
						"evtUseItem2=" + params.get("evtUseItem2") + "\n" +
						"evtUseItem3=" + params.get("evtUseItem3") + "\n" +
						"evtUseItem4=" + params.get("evtUseItem4") + "\n" +
						"evtUseItem5=" + params.get("evtUseItem5") + "\n" +
						"evtUseItem6=" + params.get("evtUseItem6") + "\n" +
	 
						"evtNormalCastItem1=" + params.get("evtNormalCastItem1") + "\n" +
						"evtNormalCastItem2=" + params.get("evtNormalCastItem2") + "\n" +
						"evtNormalCastItem3=" + params.get("evtNormalCastItem3") + "\n" +
						"evtNormalCastItem4=" + params.get("evtNormalCastItem4") + "\n" +
						"evtNormalCastItem5=" + params.get("evtNormalCastItem5") + "\n" +
						"evtNormalCastItem6=" + params.get("evtNormalCastItem6") + "\n" +
	 
						"evtSmartCastItem1=" + params.get("evtSmartCastItem1") + "\n" +
						"evtSmartCastItem2=" + params.get("evtSmartCastItem2") + "\n" +
						"evtSmartCastItem3=" + params.get("evtSmartCastItem3") + "\n" +
						"evtSmartCastItem4=" + params.get("evtSmartCastItem4") + "\n" +
	  					"evtSmartCastItem5=" + params.get("evtSmartCastItem5") + "\n" +
	  					"evtSmartCastItem6=" + params.get("evtSmartCastItem6") + "\n" +
	  
	  					"evtSelfCastItem1=" + params.get("evtSelfCastItem1") + "\n" +
	  					"evtSelfCastItem2=" + params.get("evtSelfCastItem2") + "\n" +
	  					"evtSelfCastItem3=" + params.get("evtSelfCastItem3") + "\n" +
	  					"evtSelfCastItem4=" + params.get("evtSelfCastItem4") + "\n" +
	  					"evtSelfCastItem5=" + params.get("evtSelfCastItem5") + "\n" +
	  					"evtSelfCastItem6=" + params.get("evtSelfCastItem6") + "\n" +
	  
	  					"evtCastSpell1=" + params.get("evtCastSpell1") + "\n" +
	  					"evtCastSpell2=" + params.get("evtCastSpell2") + "\n" +
	  					"evtCastSpell3=" + params.get("evtCastSpell3") + "\n" +
	  					"evtCastSpell4=" + params.get("evtCastSpell4") + "\n" +
	  					"evtCastAvatarSpell1=" + params.get("evtCastAvatarSpell1") + "\n" +
	  					"evtCastAvatarSpell2=" + params.get("evtCastAvatarSpell2") + "\n" +
	  
	  					"evtNormalCastSpell1=" + params.get("evtNormalCastSpell1") + "\n" +
	  					"evtNormalCastSpell2=" + params.get("evtNormalCastSpell2") + "\n" +
	  					"evtNormalCastSpell3=" + params.get("evtNormalCastSpell3") + "\n" +
	  					"evtNormalCastSpell4=" + params.get("evtNormalCastSpell4") + "\n" +
	  					"evtNormalCastAvatarSpell1=" + params.get("evtNormalCastAvatarSpell1") + "\n" +
	  					"evtNormalCastAvatarSpell2=" + params.get("evtNormalCastAvatarSpell2") + "\n" +
	  
	  					"evtSmartCastSpell1=" + params.get("evtSmartCastSpell1") + "\n" +
	  					"evtSmartCastSpell2=" + params.get("evtSmartCastSpell2") + "\n" +
	  					"evtSmartCastSpell3=" + params.get("evtSmartCastSpell3") + "\n" +
	  					"evtSmartCastSpell4=" + params.get("evtSmartCastSpell4") + "\n" +
	  					"evtSmartCastAvatarSpell1=" + params.get("evtSmartCastAvatarSpell1") + "\n" +
	  					"evtSmartCastAvatarSpell2=" + params.get("evtSmartCastAvatarSpell2") + "\n" +
	  
	  					"evtSelfCastSpell1=" + params.get("evtSelfCastSpell1") + "\n" +
	  					"evtSelfCastSpell2=" + params.get("evtSelfCastSpell2") + "\n" +
	  					"evtSelfCastSpell3=" + params.get("evtSelfCastSpell3") + "\n" +
	  					"evtSelfCastSpell4=" + params.get("evtSelfCastSpell4") + "\n" +
	  					"evtSelfCastAvatarSpell1=" + params.get("evtSelfCastAvatarSpell1") + "\n" +
	  					"evtSelfCastAvatarSpell2=" + params.get("evtSelfCastAvatarSpell2") + "\n" +
	  
	  					"evtSmartPlusSelfCastSpell1=" + params.get("evtSmartPlusSelfCastSpell1") + "\n" +
	  					"evtSmartPlusSelfCastSpell2=" + params.get("evtSmartPlusSelfCastSpell2") + "\n" +
	  					"evtSmartPlusSelfCastSpell3=" + params.get("evtSmartPlusSelfCastSpell3") + "\n" +
	  					"evtSmartPlusSelfCastSpell4=" + params.get("evtSmartPlusSelfCastSpell4") + "\n" +
	  					"evtSmartPlusSelfCastAvatarSpell1=" + params.get("evtSmartPlusSelfCastAvatarSpell1") + "\n" +
	  					"evtSmartPlusSelfCastAvatarSpell2=" + params.get("evtSmartPlusSelfCastAvatarSpell2") + "\n" +
	  					
	  					"evtUseVisionItem=" + params.get("evtUseVisionItem") + "\n" +
	  					"evtNormalCastVisionItem=\n" +
	  					"evtSmartCastVisionItem=" + params.get("evtSmartCastVisionItem") + "\n" +
						
	  					"evtLevelSpell1=" + Config.getInstance().getLevelupPrefix() + getHotkey(h.getSpell1()) + "\n" +
	  					"evtLevelSpell2=" + Config.getInstance().getLevelupPrefix() + getHotkey(h.getSpell2()) + "\n" +
	  					"evtLevelSpell3=" + Config.getInstance().getLevelupPrefix() + getHotkey(h.getSpell3()) + "\n" +
	  					"evtLevelSpell4=" + Config.getInstance().getLevelupPrefix() + getHotkey(h.getSpell4()) + "\n\n\n" +
						
						"[Quickbinds]\n" +
						"evtUseItem1smart=" + params.get("evtUseItem1smart") + "\n" +
						"evtUseItem2smart=" + params.get("evtUseItem2smart") + "\n" +
						"evtUseItem3smart=" + params.get("evtUseItem3smart") + "\n" +
						"evtUseItem4smart=" + params.get("evtUseItem4smart") + "\n" +
						"evtUseItem5smart=" + params.get("evtUseItem5smart") + "\n" +
						"evtUseItem6smart=" + params.get("evtUseItem6smart") + "\n" +
						"evtCastAvatarSpell1smart=" + params.get("evtCastAvatarSpell1smart") + "\n" +
						"evtCastAvatarSpell2smart=" + params.get("evtCastAvatarSpell2smart") + "\n" +
						"evtCastSpell1smart=" + params.get("evtCastSpell1smart") + "\n" +
						"evtCastSpell2smart=" + params.get("evtCastSpell2smart") + "\n" +
						"evtCastSpell3smart=" + params.get("evtCastSpell3smart") + "\n" +
						"evtCastSpell4smart=" + params.get("evtCastSpell4smart") + "\n" +
						"evtUseVisionItemsmart=" + params.get("evtUseVisionItemsmart");		
		//@formatter:on
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while reading input.ini:\n" + LogUtil.getStackTrace(e), e);
			throw new ConfigCreatorException("Error while reading input.ini");
		}

	}

	/**
	 * gets the shortcut of depending on the given type and smartcast, selfcast
	 * and hotkey
	 * 
	 * @param type
	 *            given type, see attributes for IDs
	 * @param smartcast
	 *            true if smartcast, else false
	 * @param selfcast
	 *            true if selfcast, else false
	 * @param hotkey
	 *            the hotkey
	 * 
	 * @return the hotkey string
	 */
	private String getShortcut(int type, boolean smartcast, boolean selfcast, String hotkey) {
		//@formatter:off
		/*
		 * normal cast -> evtCastSpell1smart=0, evtCast=[btn], evtNormal=[empty], evtSmartCast=[smart][btn], evtSelfCast=[self][btn], evtSmartPlusSelfCast=[empty]
		 * smart cast -> evtCastSpell1smart=1, evtCast=[btn], evtNormal=[norm_smart][btn], evtSmartCast=[empty], evtSelfCast=[self][btn], evtSmartPlusSelfCast=[empty]
		 * self cast -> evtCastSpell1smart=0, evtCast=[norm_self][btn],evtNormal=[empty], evtSmartCast=[smart][btn], evtSelfCast=[btn], evtSmartPlusSelfCast=[empty] 
		 * smart + self cast -> evtCastSpell1smart=0, evtCast=[norm_smart+self][btn], evtNormal=[empty], evtSmartCast=[empty], evtSelfCast=[empty], evtSmartPlusSelfCast=[btn]
		 */
		//@formatter:on

		switch (type) {
		// evtUseXXXsmart
			case 0:
				// normal cast
				if (!smartcast && !selfcast)
					return "0";
				// smart cast
				else if (smartcast && !selfcast)
					return "1";
				// self cast
				else if (!smartcast && selfcast)
					return "0";
				// self + smart cast
				else if (smartcast && selfcast)
					return "0";
				break;
			// evtCast
			case 1:
				// normal cast
				if (!smartcast && !selfcast)
					return Config.getInstance().getNormalPrefix() + getHotkey(hotkey);
				// smart cast
				else if (smartcast && !selfcast)
					return Config.getInstance().getNormalPrefix() + getHotkey(hotkey);
				// self cast
				else if (!smartcast && selfcast)
					return Config.getInstance().getNormalWhenSelfcastPrefix() + getHotkey(hotkey);
				// self + smart cast
				else if (smartcast && selfcast)
					return Config.getInstance().getNormalWhenSmartAndSelfcastPrefix() + getHotkey(hotkey);
				break;
			// evtNormal
			case 2:
				// normal cast
				if (!smartcast && !selfcast)
					return "";
				// smart cast
				else if (smartcast && !selfcast)
					return Config.getInstance().getNormalWhenSmartcastPrefix() + getHotkey(hotkey);
				// self cast
				else if (!smartcast && selfcast)
					return "";
				// self + smart cast
				else if (smartcast && selfcast)
					return "";
				break;
			// evtSmartCast
			case 3:
				// normal cast
				if (!smartcast && !selfcast)
					return Config.getInstance().getSmartcastPrefix() + getHotkey(hotkey);
				// smart cast
				else if (smartcast && !selfcast)
					return "";
				// self cast
				else if (!smartcast && selfcast)
					return Config.getInstance().getSmartcastPrefix() + getHotkey(hotkey);
				// self + smart cast
				else if (smartcast && selfcast)
					return "";
				break;
			// evtSelfCast
			case 4:
				// normal cast
				if (!smartcast && !selfcast)
					return Config.getInstance().getSelfcastPrefix() + getHotkey(hotkey);
				// smart cast
				else if (smartcast && !selfcast)
					return Config.getInstance().getSelfcastPrefix() + getHotkey(hotkey);
				// self cast
				else if (!smartcast && selfcast)
					return Config.getInstance().getNormalPrefix() + getHotkey(hotkey);
				// self + smart cast
				else if (smartcast && selfcast)
					return "";
				break;
			// evtSmartSelf
			case 5:
				// normal cast
				if (!smartcast && !selfcast)
					return "";
				// smart cast
				else if (smartcast && !selfcast)
					return "";
				// self cast
				else if (!smartcast && selfcast)
					return "";
				// self + smart cast
				else if (smartcast && selfcast)
					return Config.getInstance().getNormalPrefix() + getHotkey(hotkey);
				break;
		}

		return null;
	}

	/**
	 * gets the formatted hotkey
	 * 
	 * @param hotkey
	 *            the hotkey
	 * 
	 * @return the formatted hotkey [hotkey]
	 */
	private String getHotkey(String hotkey) {
		if (hotkey.equalsIgnoreCase(Hotkeys.SPACE_SHOW))
			return Hotkeys.SPACE;
		else
			return "[" + hotkey + "]";
	}
}
