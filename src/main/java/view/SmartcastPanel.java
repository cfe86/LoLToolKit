package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import view.interfaces.AbstractTab;
import view.structure.ImagePanel;
import model.exception.TabInitException;
import model.structure.Champion;
import model.structure.SummonerSpell;
import model.util.Graphics;
import net.miginfocom.swing.MigLayout;

import com.cf.mls.MLS;

import config.Config;

public class SmartcastPanel extends AbstractTab {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8985278680386573365L;

	public final static int SAVE = 0;
	public final static int RESET = 1;
	public final static int CHOOSE = 2;
	private final int DONE_TIME = 1300;

	private String nameTemplate;
	private Champion currentChamp;

	/**
	 * the multi language supporter
	 */
	private MLS mls;

	private JPanel champP;
	private JPanel trinketP;
	private JPanel spellP;
	private JPanel itemP;

	private JLabel nameJL;

	private JComboBox<String> nameCB;

	private JTextField trinketHotkeyTF;
	private JTextField spell1HotkeyTF;
	private JTextField spell2HotkeyTF;
	private JTextField spell3HotkeyTF;
	private JTextField spell4HotkeyTF;
	private JTextField summonerSpell1HotkeyTF;
	private JTextField summonerSpell2HotkeyTF;
	private JTextField item1HotkeyTF;
	private JTextField item2HotkeyTF;
	private JTextField item3HotkeyTF;
	private JTextField item4HotkeyTF;
	private JTextField item5HotkeyTF;
	private JTextField item6HotkeyTF;

	private JCheckBox smartcastItem1ChB;
	private JCheckBox selfcastItem1ChB;
	private JCheckBox smartcastItem2ChB;
	private JCheckBox selfcastItem2ChB;
	private JCheckBox smartcastItem3ChB;
	private JCheckBox selfcastItem3ChB;
	private JCheckBox smartcastItem4ChB;
	private JCheckBox selfcastItem4ChB;
	private JCheckBox smartcastItem5ChB;
	private JCheckBox selfcastItem5ChB;
	private JCheckBox smartcastItem6ChB;
	private JCheckBox selfcastItem6ChB;
	private JCheckBox smartcastSpell1ChB;
	private JCheckBox selfcastSpell1ChB;
	private JCheckBox smartcastSpell2ChB;
	private JCheckBox selfcastSpell2ChB;
	private JCheckBox smartcastSpell3ChB;
	private JCheckBox selfcastSpell3ChB;
	private JCheckBox smartcastSpell4ChB;
	private JCheckBox selfcastSpell4ChB;
	private JCheckBox smartcastSummonerSpell1ChB;
	private JCheckBox selfcastSummonerSpell1ChB;
	private JCheckBox smartcastSummonerSpell2ChB;
	private JCheckBox selfcastSummonerSpell2ChB;
	private JCheckBox smartcastTrinketChB;
	private JCheckBox rangeIndicatorChB;
	private JCheckBox overrideItemsChB;

	private JButton saveB;
	private JButton resetB;
	private JButton chooseB;

	private ImagePanel champIP;
	private ImagePanel trinketIP;
	private ImagePanel spell1IP;
	private ImagePanel spell2IP;
	private ImagePanel spell3IP;
	private ImagePanel spell4IP;
	private ImagePanel summonerSpell1IP;
	private ImagePanel summonerSpell2IP;
	private ImagePanel item1IP;
	private ImagePanel item2IP;
	private ImagePanel item3IP;
	private ImagePanel item4IP;
	private ImagePanel item5IP;
	private ImagePanel item6IP;

	/**
	 * Constructor
	 */
	public SmartcastPanel() {
		mls = new MLS("languagefiles/SmartcastPanel", Config.getInstance().getCurrentLanguage());
		mls.setToolTipDuration(-1);
	}
	
	/*
	 * (non-Javadoc)
	 * @see view.interfaces.AbstractTab#init()
	 */
	@Override
	public void init() throws TabInitException {
		try {
		champP = mls.generateTitledBevelPanel("champP", BevelBorder.LOWERED);
		trinketP = mls.generateTitledBevelPanel("trinketP", BevelBorder.LOWERED);
		spellP = mls.generateTitledBevelPanel("spellP", BevelBorder.LOWERED);
		itemP = mls.generateTitledBevelPanel("itemP", BevelBorder.LOWERED);

		nameJL = mls.generateJLabel("nameJL");
		nameTemplate = nameJL.getText();

		nameCB = mls.generateJComboBox("nameCB", new ArrayList<String>(), -1, true, false, null, "nameCB");

		trinketHotkeyTF = mls.generateJTextField("trinketHotkeyTF", true, true, 0, "");
		trinketHotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		spell1HotkeyTF = mls.generateJTextField("spell1HotkeyTF", true, true, 0, "");
		spell1HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		spell2HotkeyTF = mls.generateJTextField("spell2HotkeyTF", true, true, 0, "");
		spell2HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		spell3HotkeyTF = mls.generateJTextField("spell3HotkeyTF", true, true, 0, "");
		spell3HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		spell4HotkeyTF = mls.generateJTextField("spell4HotkeyTF", true, true, 0, "");
		spell4HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		summonerSpell1HotkeyTF = mls.generateJTextField("summonerSpell1HotkeyTF", true, true, 0, "");
		summonerSpell1HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		summonerSpell2HotkeyTF = mls.generateJTextField("summonerSpell2HotkeyTF", true, true, 0, "");
		summonerSpell2HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		item1HotkeyTF = mls.generateJTextField("item1HotkeyTF", true, true, 0, "");
		item1HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		item2HotkeyTF = mls.generateJTextField("item2HotkeyTF", true, true, 0, "");
		item2HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		item3HotkeyTF = mls.generateJTextField("item3HotkeyTF", true, true, 0, "");
		item3HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		item4HotkeyTF = mls.generateJTextField("item4HotkeyTF", true, true, 0, "");
		item4HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		item5HotkeyTF = mls.generateJTextField("item5HotkeyTF", true, true, 0, "");
		item5HotkeyTF.setHorizontalAlignment(JLabel.CENTER);
		item6HotkeyTF = mls.generateJTextField("item6HotkeyTF", true, true, 0, "");
		item6HotkeyTF.setHorizontalAlignment(JLabel.CENTER);

		smartcastItem1ChB = mls.generateJCheckBox("smartcastItem1ChB", false);
		selfcastItem1ChB = mls.generateJCheckBox("selfcastItem1ChB", false);
		smartcastItem2ChB = mls.generateJCheckBox("smartcastItem2ChB", false);
		selfcastItem2ChB = mls.generateJCheckBox("selfcastItem2ChB", false);
		smartcastItem3ChB = mls.generateJCheckBox("smartcastItem3ChB", false);
		selfcastItem3ChB = mls.generateJCheckBox("selfcastItem3ChB", false);
		smartcastItem4ChB = mls.generateJCheckBox("smartcastItem4ChB", false);
		selfcastItem4ChB = mls.generateJCheckBox("selfcastItem4ChB", false);
		smartcastItem5ChB = mls.generateJCheckBox("smartcastItem5ChB", false);
		selfcastItem5ChB = mls.generateJCheckBox("selfcastItem5ChB", false);
		smartcastItem6ChB = mls.generateJCheckBox("smartcastItem6ChB", false);
		selfcastItem6ChB = mls.generateJCheckBox("selfcastItem6ChB", false);
		smartcastSpell1ChB = mls.generateJCheckBox("smartcastSpell1ChB", false);
		selfcastSpell1ChB = mls.generateJCheckBox("selfcastSpell1ChB", false);
		smartcastSpell2ChB = mls.generateJCheckBox("smartcastSpell2ChB", false);
		selfcastSpell2ChB = mls.generateJCheckBox("selfcastSpell2ChB", false);
		smartcastSpell3ChB = mls.generateJCheckBox("smartcastSpell3ChB", false);
		selfcastSpell3ChB = mls.generateJCheckBox("selfcastSpell3ChB", false);
		smartcastSpell4ChB = mls.generateJCheckBox("smartcastSpell4ChB", false);
		selfcastSpell4ChB = mls.generateJCheckBox("selfcastSpell4ChB", false);
		smartcastTrinketChB = mls.generateJCheckBox("smartcastTrinketChB", false);
		rangeIndicatorChB = mls.generateJCheckBox("rangeIndicatorChB", false);
		overrideItemsChB = mls.generateJCheckBox("overrideItemsChB", false);
		smartcastSummonerSpell1ChB = mls.generateJCheckBox("smartcastSummonerSpell1ChB", false);
		selfcastSummonerSpell1ChB = mls.generateJCheckBox("selfcastSummonerSpell1ChB", false);
		smartcastSummonerSpell2ChB = mls.generateJCheckBox("smartcastSummonerSpell2ChB", false);
		selfcastSummonerSpell2ChB = mls.generateJCheckBox("selfcastSummonerSpell2ChB", false);
		saveB = mls.generateJButton("saveB");
		resetB = mls.generateJButton("resetB");
		chooseB = mls.generateJButton("chooseB");

		BufferedImage img = Graphics.scale(Graphics.readImageFromJar("images/Random.png"), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength());
		champIP = new ImagePanel(Graphics.scale(Graphics.readImageFromJar("images/Random.png"), Config.getInstance().getChampImageLength(), Config.getInstance().getChampImageLength()));
		trinketIP = new ImagePanel(Graphics.scale(Graphics.readImageFromJar("images/Trinket.png"), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		spell1IP = new ImagePanel(img);
		spell2IP = new ImagePanel(img);
		spell3IP = new ImagePanel(img);
		spell4IP = new ImagePanel(img);
		summonerSpell1IP = new ImagePanel(img);
		summonerSpell2IP = new ImagePanel(img);
		item1IP = new ImagePanel(Graphics.scale(Graphics.readImageFromJar("images/Item1.png"), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		item2IP = new ImagePanel(Graphics.scale(Graphics.readImageFromJar("images/Item2.png"), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		item3IP = new ImagePanel(Graphics.scale(Graphics.readImageFromJar("images/Item3.png"), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		item4IP = new ImagePanel(Graphics.scale(Graphics.readImageFromJar("images/Item4.png"), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		item5IP = new ImagePanel(Graphics.scale(Graphics.readImageFromJar("images/Item5.png"), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		item6IP = new ImagePanel(Graphics.scale(Graphics.readImageFromJar("images/Item6.png"), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));

		this.mls.addCustomJPanel(item1IP, "item1IP");
		this.mls.addCustomJPanel(item2IP, "item2IP");
		this.mls.addCustomJPanel(item3IP, "item3IP");
		this.mls.addCustomJPanel(item4IP, "item4IP");
		this.mls.addCustomJPanel(item5IP, "item5IP");
		this.mls.addCustomJPanel(item6IP, "item6IP");
		this.mls.addCustomJPanel(trinketIP, "trinketIP");

		// champ and Trinket Panel
		JPanel champTrinketP = new JPanel(new MigLayout("insets 0", "[grow][shrink]", "[shrink]"));

		// // champ
		champP.setLayout(new MigLayout("insets 5", "[shrink][grow]", "[shrink]"));

		JPanel nameP = new JPanel(new MigLayout("insets 5", "[grow]", "[shrink][shrink]"));
		nameP.add(nameJL, "wrap");
		nameP.add(nameCB, "grow, gaptop 5");

		champP.add(champIP, "height :" + Config.getInstance().getChampImageLength() + ":, width :" + Config.getInstance().getChampImageLength() + ":");
		champP.add(nameP, "grow");

		// // Trinket
		trinketP.setLayout(new MigLayout("insets 5", "[shrink]", "[shrink][shrink]"));

		trinketIP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		trinketIP.add(trinketHotkeyTF, "width :20:, push, right, bottom");

		trinketP.add(trinketIP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		trinketP.add(smartcastTrinketChB);

		champTrinketP.add(champP, "grow");
		champTrinketP.add(trinketP, "grow");

		// Spells
		spellP.setLayout(new MigLayout("insets 5", "[grow][grow][grow][grow][grow][grow]", "[shrink]"));

		spell1IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		spell1IP.add(spell1HotkeyTF, "width :20:, push, right, bottom");

		spell2IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		spell2IP.add(spell2HotkeyTF, "width :20:, push, right, bottom");

		spell3IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		spell3IP.add(spell3HotkeyTF, "width :20:, push, right, bottom");

		spell4IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		spell4IP.add(spell4HotkeyTF, "width :20:, push, right, bottom");

		summonerSpell1IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		summonerSpell1IP.add(summonerSpell1HotkeyTF, "width :20:, push, right, bottom");

		summonerSpell2IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		summonerSpell2IP.add(summonerSpell2HotkeyTF, "width :20:, push, right, bottom");

		JPanel spell1P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		spell1P.add(spell1IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		spell1P.add(smartcastSpell1ChB, "wrap");
		spell1P.add(selfcastSpell1ChB);
		spellP.add(spell1P, "grow");

		JPanel spell2P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		spell2P.add(spell2IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		spell2P.add(smartcastSpell2ChB, "wrap");
		spell2P.add(selfcastSpell2ChB);
		spellP.add(spell2P, "grow");

		JPanel spell3P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		spell3P.add(spell3IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		spell3P.add(smartcastSpell3ChB, "wrap");
		spell3P.add(selfcastSpell3ChB);
		spellP.add(spell3P, "grow");

		JPanel spell4P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		spell4P.add(spell4IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		spell4P.add(smartcastSpell4ChB, "wrap");
		spell4P.add(selfcastSpell4ChB);
		spellP.add(spell4P, "grow");

		JPanel summonerSpell1P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		summonerSpell1P.add(summonerSpell1IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength()
				+ ":, center");
		summonerSpell1P.add(smartcastSummonerSpell1ChB, "wrap");
		summonerSpell1P.add(selfcastSummonerSpell1ChB);
		spellP.add(summonerSpell1P, "grow");

		JPanel summonerSpell2P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		summonerSpell2P.add(summonerSpell2IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength()
				+ ":, center");
		summonerSpell2P.add(smartcastSummonerSpell2ChB, "wrap");
		summonerSpell2P.add(selfcastSummonerSpell2ChB);
		spellP.add(summonerSpell2P, "grow");

		// Items
		itemP.setLayout(new MigLayout("insets 5", "[grow][grow][grow][grow][grow][grow]", "[shrink][shrink]"));

		item1IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		item1IP.add(item1HotkeyTF, "width :20:, push, right, bottom");

		item2IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		item2IP.add(item2HotkeyTF, "width :20:, push, right, bottom");

		item3IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		item3IP.add(item3HotkeyTF, "width :20:, push, right, bottom");

		item4IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		item4IP.add(item4HotkeyTF, "width :20:, push, right, bottom");

		item5IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		item5IP.add(item5HotkeyTF, "width :20:, push, right, bottom");

		item6IP.setLayout(new MigLayout("insets 0", "[shrink]", "[shrink]"));
		item6IP.add(item6HotkeyTF, "width :20:, push, right, bottom");

		JPanel overrideItemsP = new JPanel(new MigLayout("insets 0", "[grow]", "[shrink]"));
		overrideItemsP.add(overrideItemsChB, "grow");
		itemP.add(overrideItemsP, "span 6, grow, wrap");

		JPanel item1P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		item1P.add(item1IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		item1P.add(smartcastItem1ChB, "wrap");
		item1P.add(selfcastItem1ChB);
		itemP.add(item1P, "grow");

		JPanel item2P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		item2P.add(item2IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		item2P.add(smartcastItem2ChB, "wrap");
		item2P.add(selfcastItem2ChB);
		itemP.add(item2P, "grow");

		JPanel item3P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		item3P.add(item3IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		item3P.add(smartcastItem3ChB, "wrap");
		item3P.add(selfcastItem3ChB);
		itemP.add(item3P, "grow");

		JPanel item4P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		item4P.add(item4IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		item4P.add(smartcastItem4ChB, "wrap");
		item4P.add(selfcastItem4ChB);
		itemP.add(item4P, "grow");

		JPanel item5P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		item5P.add(item5IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		item5P.add(smartcastItem5ChB, "wrap");
		item5P.add(selfcastItem5ChB);
		itemP.add(item5P, "grow");

		JPanel item6P = new JPanel(new MigLayout("insets 5", "[shrink]", "[shrink][shrink][shrink]"));
		item6P.add(item6IP, "wrap, height :" + Config.getInstance().getSpellItemImageLength() + ":, width :" + Config.getInstance().getSpellItemImageLength() + ":, center");
		item6P.add(smartcastItem6ChB, "wrap");
		item6P.add(selfcastItem6ChB);
		itemP.add(item6P, "grow");

		// range indicator
		JPanel rangeIndicatorP = new JPanel(new MigLayout("insets 0", "[grow]", "[shrink]"));
		rangeIndicatorP.add(rangeIndicatorChB, "grow");

		// buttons
		JPanel buttonP = new JPanel(new MigLayout("insets 0", "[grow][grow]", "[shrink][shrink]"));
		buttonP.add(saveB, "grow");
		buttonP.add(resetB, "grow, gapleft 10, wrap");
		buttonP.add(chooseB, "grow, span 2, gaptop 5");

		setLayout(new MigLayout("insets 5", "[grow]", "[shrink][shrink][shrink][shrink][shrink]"));

		add(champTrinketP, "grow, wrap, gaptop 7");
		add(spellP, "grow, wrap");
		add(itemP, "grow, wrap");
		add(rangeIndicatorP, "grow, wrap");
		add(buttonP, "grow, gaptop 10");

		// set items override
		this.overrideItemsChB.setSelected(Config.getInstance().getOverrideItems());
		setItemsEnabled(Config.getInstance().getOverrideItems());
		} catch(IOException e) {
			throw new TabInitException();
		}
	}


	/**
	 * changes the language to the given new locale
	 * 
	 * @param lang
	 *            given locale
	 */
	public void changeLanguage(Locale lang) {
		this.mls.setLocale(lang);
		this.mls.translate();
		this.nameJL.setText(this.nameTemplate.replace("{0}", this.currentChamp.getSpells().getName()));
	}

	/**
	 * shows a given message
	 * 
	 * @param identifier
	 *            identifier for the translator
	 */
	public void showMessage(String identifier) {
		JOptionPane.showMessageDialog(this, mls.getMessage(identifier));
	}

	/**
	 * sets an ActionListener
	 * 
	 * @param l
	 *            the ActionListener
	 */
	public void setActionListener(ActionListener l) {
		saveB.addActionListener(l);
		resetB.addActionListener(l);
		chooseB.addActionListener(l);

		nameCB.addActionListener(l);

		smartcastItem1ChB.addActionListener(l);
		selfcastItem1ChB.addActionListener(l);
		smartcastItem2ChB.addActionListener(l);
		selfcastItem2ChB.addActionListener(l);
		smartcastItem3ChB.addActionListener(l);
		selfcastItem3ChB.addActionListener(l);
		smartcastItem4ChB.addActionListener(l);
		selfcastItem4ChB.addActionListener(l);
		smartcastItem5ChB.addActionListener(l);
		selfcastItem5ChB.addActionListener(l);
		smartcastItem6ChB.addActionListener(l);
		selfcastItem6ChB.addActionListener(l);

		overrideItemsChB.addActionListener(l);
	}

	/**
	 * sets a mouselistener to the summonerspells
	 * 
	 * @param l
	 *            the listener
	 */
	public void setMouseListener(MouseListener l) {
		summonerSpell1IP.addMouseListener(l);
		summonerSpell2IP.addMouseListener(l);
	}

	/**
	 * sets a keylistener to the hotkey textfields
	 * 
	 * @param l
	 *            the listener
	 */
	public void setKeyListener(KeyListener l) {
		trinketHotkeyTF.addKeyListener(l);
		spell1HotkeyTF.addKeyListener(l);
		spell2HotkeyTF.addKeyListener(l);
		spell3HotkeyTF.addKeyListener(l);
		spell4HotkeyTF.addKeyListener(l);
		summonerSpell1HotkeyTF.addKeyListener(l);
		summonerSpell2HotkeyTF.addKeyListener(l);
		item1HotkeyTF.addKeyListener(l);
		item2HotkeyTF.addKeyListener(l);
		item3HotkeyTF.addKeyListener(l);
		item4HotkeyTF.addKeyListener(l);
		item5HotkeyTF.addKeyListener(l);
		item6HotkeyTF.addKeyListener(l);
	}

	/**
	 * sets the itemslots enabled
	 * 
	 * @param en
	 *            true for enabled, else disabled
	 */
	public void setItemsEnabled(boolean en) {
		this.smartcastItem1ChB.setEnabled(en);
		this.smartcastItem2ChB.setEnabled(en);
		this.smartcastItem3ChB.setEnabled(en);
		this.smartcastItem4ChB.setEnabled(en);
		this.smartcastItem5ChB.setEnabled(en);
		this.smartcastItem6ChB.setEnabled(en);

		this.selfcastItem1ChB.setEnabled(en);
		this.selfcastItem2ChB.setEnabled(en);
		this.selfcastItem3ChB.setEnabled(en);
		this.selfcastItem4ChB.setEnabled(en);
		this.selfcastItem5ChB.setEnabled(en);
		this.selfcastItem6ChB.setEnabled(en);

		this.item1IP.setEnabled(en);
		this.item1HotkeyTF.setEnabled(en);
		this.item2IP.setEnabled(en);
		this.item2HotkeyTF.setEnabled(en);
		this.item3IP.setEnabled(en);
		this.item3HotkeyTF.setEnabled(en);
		this.item4IP.setEnabled(en);
		this.item4HotkeyTF.setEnabled(en);
		this.item5IP.setEnabled(en);
		this.item5HotkeyTF.setEnabled(en);
		this.item6IP.setEnabled(en);
		this.item6HotkeyTF.setEnabled(en);
	}

	public boolean isOverrideItemsChBChecked() {
		return this.overrideItemsChB.isSelected();
	}

	/**
	 * sets the given list to the championname combobox
	 * 
	 * @param names
	 *            given names
	 * @param chosenName
	 *            the selected name, null if first name should be selected
	 */
	public void setChampionNames(List<String> names, String chosenName) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		for (String name : names)
			model.addElement(name);

		this.nameCB.setModel(model);

		if (chosenName != null && chosenName.trim().length() != 0)
			this.nameCB.setSelectedItem(chosenName);
	}

	/**
	 * sets the hotkeys and spells for the given champion to the window
	 * 
	 * @param champ
	 *            the given champion
	 * 
	 * @throws IOException
	 *             thrown if an image couldn't be found
	 */
	public void setChampionData(Champion champ) throws IOException {
		this.currentChamp = champ;

		this.nameJL.setText(nameTemplate.replace("{0}", champ.getSpells().getName()));
		this.champIP.setImage(Graphics.scale(Graphics.readImage(champ.getSpells().getImage()), Config.getInstance().getChampImageLength(), Config.getInstance().getChampImageLength()));

		// set spells and tooltips
		this.spell1IP.setImage(Graphics.scale(Graphics.readImage(champ.getSpells().getSpell1Image()), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		this.spell1IP.setToolTipText(formatSpellTooltip(champ.getSpells().getSpell1(), champ.getSpells().getSpell1Descr()));
		this.spell2IP.setImage(Graphics.scale(Graphics.readImage(champ.getSpells().getSpell2Image()), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		this.spell2IP.setToolTipText(formatSpellTooltip(champ.getSpells().getSpell2(), champ.getSpells().getSpell2Descr()));
		this.spell3IP.setImage(Graphics.scale(Graphics.readImage(champ.getSpells().getSpell3Image()), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		this.spell3IP.setToolTipText(formatSpellTooltip(champ.getSpells().getSpell3(), champ.getSpells().getSpell3Descr()));
		this.spell4IP.setImage(Graphics.scale(Graphics.readImage(champ.getSpells().getSpell4Image()), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
				.getSpellItemImageLength()));
		this.spell4IP.setToolTipText(formatSpellTooltip(champ.getSpells().getSpell4(), champ.getSpells().getSpell4Descr()));

		this.summonerSpell1IP.setImage(Graphics.scale(Graphics.readImage(champ.getSpells().getSummonerSpell1Image()), Config.getInstance().getSpellItemImageLength(), Config
				.getInstance().getSpellItemImageLength()));
		this.summonerSpell1IP.setToolTipText(champ.getSpells().getSummonerSpell1());
		this.summonerSpell2IP.setImage(Graphics.scale(Graphics.readImage(champ.getSpells().getSummonerSpell2Image()), Config.getInstance().getSpellItemImageLength(), Config
				.getInstance().getSpellItemImageLength()));
		this.summonerSpell2IP.setToolTipText(champ.getSpells().getSummonerSpell2());

		// set smart and selfcast
		this.smartcastItem1ChB.setSelected(champ.getHotkeys().isSmartcastItem1());
		this.smartcastItem2ChB.setSelected(champ.getHotkeys().isSmartcastItem2());
		this.smartcastItem3ChB.setSelected(champ.getHotkeys().isSmartcastItem3());
		this.smartcastItem4ChB.setSelected(champ.getHotkeys().isSmartcastItem4());
		this.smartcastItem5ChB.setSelected(champ.getHotkeys().isSmartcastItem5());
		this.smartcastItem6ChB.setSelected(champ.getHotkeys().isSmartcastItem6());
		this.smartcastTrinketChB.setSelected(champ.getHotkeys().isSmartcastTrinket());
		this.smartcastSpell1ChB.setSelected(champ.getHotkeys().isSmartcastSpell1());
		this.smartcastSpell2ChB.setSelected(champ.getHotkeys().isSmartcastSpell2());
		this.smartcastSpell3ChB.setSelected(champ.getHotkeys().isSmartcastSpell3());
		this.smartcastSpell4ChB.setSelected(champ.getHotkeys().isSmartcastSpell4());
		this.smartcastSummonerSpell1ChB.setSelected(champ.getHotkeys().isSmartcastSummonerSpell1());
		this.smartcastSummonerSpell2ChB.setSelected(champ.getHotkeys().isSmartcastSummonerSpell2());

		this.selfcastItem1ChB.setSelected(champ.getHotkeys().isSelfcastItem1());
		this.selfcastItem2ChB.setSelected(champ.getHotkeys().isSelfcastItem2());
		this.selfcastItem3ChB.setSelected(champ.getHotkeys().isSelfcastItem3());
		this.selfcastItem4ChB.setSelected(champ.getHotkeys().isSelfcastItem4());
		this.selfcastItem5ChB.setSelected(champ.getHotkeys().isSelfcastItem5());
		this.selfcastItem6ChB.setSelected(champ.getHotkeys().isSelfcastItem6());
		this.selfcastSpell1ChB.setSelected(champ.getHotkeys().isSelfcastSpell1());
		this.selfcastSpell2ChB.setSelected(champ.getHotkeys().isSelfcastSpell2());
		this.selfcastSpell3ChB.setSelected(champ.getHotkeys().isSelfcastSpell3());
		this.selfcastSpell4ChB.setSelected(champ.getHotkeys().isSelfcastSpell4());
		this.selfcastSummonerSpell1ChB.setSelected(champ.getHotkeys().isSelfcastSummonerSpell1());
		this.selfcastSummonerSpell2ChB.setSelected(champ.getHotkeys().isSelfcastSummonerSpell2());

		// set shortcuts
		this.spell1HotkeyTF.setText(champ.getHotkeys().getSpell1());
		this.spell2HotkeyTF.setText(champ.getHotkeys().getSpell2());
		this.spell3HotkeyTF.setText(champ.getHotkeys().getSpell3());
		this.spell4HotkeyTF.setText(champ.getHotkeys().getSpell4());
		this.summonerSpell1HotkeyTF.setText(champ.getHotkeys().getSummonerSpell1());
		this.summonerSpell2HotkeyTF.setText(champ.getHotkeys().getSummonerSpell2());
		this.trinketHotkeyTF.setText(champ.getHotkeys().getTrinket());
		this.item1HotkeyTF.setText(champ.getHotkeys().getItem1());
		this.item2HotkeyTF.setText(champ.getHotkeys().getItem2());
		this.item3HotkeyTF.setText(champ.getHotkeys().getItem3());
		this.item4HotkeyTF.setText(champ.getHotkeys().getItem4());
		this.item5HotkeyTF.setText(champ.getHotkeys().getItem5());
		this.item6HotkeyTF.setText(champ.getHotkeys().getItem6());

		this.rangeIndicatorChB.setSelected(champ.getHotkeys().isShowRangeIndicator());
	}

	/**
	 * formats the spell tooltip for a given spellname and description
	 * 
	 * @param spellname
	 *            given spellname
	 * @param spellDescription
	 *            given spelldescription
	 * 
	 * @return the formatted string
	 */
	private String formatSpellTooltip(String spellname, String spellDescription) {
		String descr = spellDescription.replaceAll("\n", "<br />");
		return "<html>" + spellname + "<br/></br/><br/>" + descr + "</html>";
	}

	/**
	 * resets all champion information to default
	 */
	public void resetChampionData() {
		// set smart and selfcast
		this.smartcastItem1ChB.setSelected(false);
		this.smartcastItem2ChB.setSelected(false);
		this.smartcastItem3ChB.setSelected(false);
		this.smartcastItem4ChB.setSelected(false);
		this.smartcastItem5ChB.setSelected(false);
		this.smartcastItem6ChB.setSelected(false);
		this.smartcastTrinketChB.setSelected(false);
		this.smartcastSpell1ChB.setSelected(false);
		this.smartcastSpell2ChB.setSelected(false);
		this.smartcastSpell3ChB.setSelected(false);
		this.smartcastSpell4ChB.setSelected(false);
		this.smartcastSummonerSpell1ChB.setSelected(false);
		this.smartcastSummonerSpell2ChB.setSelected(false);

		this.selfcastItem1ChB.setSelected(false);
		this.selfcastItem2ChB.setSelected(false);
		this.selfcastItem3ChB.setSelected(false);
		this.selfcastItem4ChB.setSelected(false);
		this.selfcastItem5ChB.setSelected(false);
		this.selfcastItem6ChB.setSelected(false);
		this.selfcastSpell1ChB.setSelected(false);
		this.selfcastSpell2ChB.setSelected(false);
		this.selfcastSpell3ChB.setSelected(false);
		this.selfcastSpell4ChB.setSelected(false);
		this.selfcastSummonerSpell1ChB.setSelected(false);
		this.selfcastSummonerSpell2ChB.setSelected(false);

		// set shortcuts
		this.spell1HotkeyTF.setText("q");
		this.spell2HotkeyTF.setText("w");
		this.spell3HotkeyTF.setText("e");
		this.spell4HotkeyTF.setText("r");
		this.summonerSpell1HotkeyTF.setText("d");
		this.summonerSpell2HotkeyTF.setText("f");
		this.trinketHotkeyTF.setText("4");
		this.item1HotkeyTF.setText("1");
		this.item2HotkeyTF.setText("2");
		this.item3HotkeyTF.setText("3");
		this.item4HotkeyTF.setText("5");
		this.item5HotkeyTF.setText("6");
		this.item6HotkeyTF.setText("7");

		this.rangeIndicatorChB.setSelected(false);
	}

	/**
	 * gets the currently shown champion hotkeys etc.
	 * 
	 * @return the champion
	 */
	public Champion getChampionData() {
		// set smart and selfcast
		this.currentChamp.getHotkeys().setSmartcastItem1(this.smartcastItem1ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastItem2(this.smartcastItem2ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastItem3(this.smartcastItem3ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastItem4(this.smartcastItem4ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastItem5(this.smartcastItem5ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastItem6(this.smartcastItem6ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastTrinket(this.smartcastTrinketChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastSpell1(this.smartcastSpell1ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastSpell2(this.smartcastSpell2ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastSpell3(this.smartcastSpell3ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastSpell4(this.smartcastSpell4ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastSummonerSpell1(this.smartcastSummonerSpell1ChB.isSelected());
		this.currentChamp.getHotkeys().setSmartcastSummonerSpell2(this.smartcastSummonerSpell2ChB.isSelected());

		this.currentChamp.getHotkeys().setSelfcastItem1(this.selfcastItem1ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastItem2(this.selfcastItem2ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastItem3(this.selfcastItem3ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastItem4(this.selfcastItem4ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastItem5(this.selfcastItem5ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastItem6(this.selfcastItem6ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastSpell1(this.selfcastSpell1ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastSpell2(this.selfcastSpell2ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastSpell3(this.selfcastSpell3ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastSpell4(this.selfcastSpell4ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastSummonerSpell1(this.selfcastSummonerSpell1ChB.isSelected());
		this.currentChamp.getHotkeys().setSelfcastSummonerSpell2(this.selfcastSummonerSpell2ChB.isSelected());

		// set shortcuts
		this.currentChamp.getHotkeys().setItem1(this.item1HotkeyTF.getText());
		this.currentChamp.getHotkeys().setItem2(this.item2HotkeyTF.getText());
		this.currentChamp.getHotkeys().setItem3(this.item3HotkeyTF.getText());
		this.currentChamp.getHotkeys().setItem4(this.item4HotkeyTF.getText());
		this.currentChamp.getHotkeys().setItem5(this.item5HotkeyTF.getText());
		this.currentChamp.getHotkeys().setItem6(this.item6HotkeyTF.getText());
		this.currentChamp.getHotkeys().setSpell1(this.spell1HotkeyTF.getText());
		this.currentChamp.getHotkeys().setSpell2(this.spell2HotkeyTF.getText());
		this.currentChamp.getHotkeys().setSpell3(this.spell3HotkeyTF.getText());
		this.currentChamp.getHotkeys().setSpell4(this.spell4HotkeyTF.getText());
		this.currentChamp.getHotkeys().setSummonerSpell1(this.summonerSpell1HotkeyTF.getText());
		this.currentChamp.getHotkeys().setSummonerSpell2(this.summonerSpell2HotkeyTF.getText());
		this.currentChamp.getHotkeys().setTrinket(this.trinketHotkeyTF.getText());

		this.currentChamp.getHotkeys().setShowRangeIndicator(this.rangeIndicatorChB.isSelected());

		return this.currentChamp;
	}

	/**
	 * sets a given summonerspell to slot 1 or 2
	 * 
	 * @param nr
	 *            given slot 1 for summonerspell 1, 2 for summonerspell 2
	 * @param spell
	 *            the given summonerspell
	 * 
	 * @throws IOException
	 *             thrown if an image couldn't be found
	 */
	public void setSummonerSpell(int nr, SummonerSpell spell) throws IOException {
		if (nr == 1) {
			this.currentChamp.getSpells().setSummonerSpell1(spell.getName());
			this.summonerSpell1IP.setImage(Graphics.scale(Graphics.readImage(spell.getImage()), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
					.getSpellItemImageLength()));
			this.summonerSpell1IP.setToolTipText(spell.getName());
		} else {
			this.currentChamp.getSpells().setSummonerSpell2(spell.getName());
			this.summonerSpell2IP.setImage(Graphics.scale(Graphics.readImage(spell.getImage()), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
					.getSpellItemImageLength()));
			this.summonerSpell2IP.setToolTipText(spell.getName());
		}
	}

	public int getSelectedChampIndex() {
		return this.nameCB.getSelectedIndex();
	}

	/**
	 * sets item1 to smart or selfcast
	 * 
	 * @param smartcast
	 *            if true, smartcast will be selected and selfcast deselected,
	 *            else vice versa
	 */
	public void item1Changed(boolean smartcast) {
		if (smartcast) {
			if (this.selfcastItem1ChB.isSelected()) {
				this.smartcastItem1ChB.setSelected(true);
				this.selfcastItem1ChB.setSelected(false);
			}
		} else {
			if (this.smartcastItem1ChB.isSelected()) {
				this.selfcastItem1ChB.setSelected(true);
				this.smartcastItem1ChB.setSelected(false);
			}
		}
	}

	/**
	 * sets item2 to smart or selfcast
	 * 
	 * @param smartcast
	 *            if true, smartcast will be selected and selfcast deselected,
	 *            else vice versa
	 */
	public void item2Changed(boolean smartcast) {
		if (smartcast) {
			if (this.selfcastItem2ChB.isSelected()) {
				this.smartcastItem2ChB.setSelected(true);
				this.selfcastItem2ChB.setSelected(false);
			}
		} else {
			if (this.smartcastItem2ChB.isSelected()) {
				this.selfcastItem2ChB.setSelected(true);
				this.smartcastItem2ChB.setSelected(false);
			}
		}
	}

	/**
	 * sets item3 to smart or selfcast
	 * 
	 * @param smartcast
	 *            if true, smartcast will be selected and selfcast deselected,
	 *            else vice versa
	 */
	public void item3Changed(boolean smartcast) {
		if (smartcast) {
			if (this.selfcastItem3ChB.isSelected()) {
				this.smartcastItem3ChB.setSelected(true);
				this.selfcastItem3ChB.setSelected(false);
			}
		} else {
			if (this.smartcastItem3ChB.isSelected()) {
				this.selfcastItem3ChB.setSelected(true);
				this.smartcastItem3ChB.setSelected(false);
			}
		}
	}

	/**
	 * sets item4 to smart or selfcast
	 * 
	 * @param smartcast
	 *            if true, smartcast will be selected and selfcast deselected,
	 *            else vice versa
	 */
	public void item4Changed(boolean smartcast) {
		if (smartcast) {
			if (this.selfcastItem4ChB.isSelected()) {
				this.smartcastItem4ChB.setSelected(true);
				this.selfcastItem4ChB.setSelected(false);
			}
		} else {
			if (this.smartcastItem4ChB.isSelected()) {
				this.selfcastItem4ChB.setSelected(true);
				this.smartcastItem4ChB.setSelected(false);
			}
		}
	}

	/**
	 * sets item5 to smart or selfcast
	 * 
	 * @param smartcast
	 *            if true, smartcast will be selected and selfcast deselected,
	 *            else vice versa
	 */
	public void item5Changed(boolean smartcast) {
		if (smartcast) {
			if (this.selfcastItem5ChB.isSelected()) {
				this.smartcastItem5ChB.setSelected(true);
				this.selfcastItem5ChB.setSelected(false);
			}
		} else {
			if (this.smartcastItem5ChB.isSelected()) {
				this.selfcastItem5ChB.setSelected(true);
				this.smartcastItem5ChB.setSelected(false);
			}
		}
	}

	/**
	 * sets item6 to smart or selfcast
	 * 
	 * @param smartcast
	 *            if true, smartcast will be selected and selfcast deselected,
	 *            else vice versa
	 */
	public void item6Changed(boolean smartcast) {
		if (smartcast) {
			if (this.selfcastItem6ChB.isSelected()) {
				this.smartcastItem6ChB.setSelected(true);
				this.selfcastItem6ChB.setSelected(false);
			}
		} else {
			if (this.smartcastItem6ChB.isSelected()) {
				this.selfcastItem6ChB.setSelected(true);
				this.smartcastItem6ChB.setSelected(false);
			}
		}
	}

	public ImagePanel getSummonerSpell1IP() {
		return summonerSpell1IP;
	}

	public ImagePanel getSummonerSpell2IP() {
		return summonerSpell2IP;
	}

	/**
	 * sets a "done" label to the pressed button for a short time
	 * 
	 * @param btn
	 *            the given Button, button IDs are attributes in SmartcastView
	 */
	public void setDone(final int btn) {
		String done = "<html><font size=\"3\" color=\"green\">" + mls.getMessage("done") + "</font><html>";

		if (btn == SAVE)
			this.saveB.setText(done);
		else if (btn == RESET)
			this.resetB.setText(done);
		else if (btn == CHOOSE)
			this.chooseB.setText(done);

		Timer t = new Timer();
		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				if (btn == SAVE)
					saveB.setText(mls.getMessage("saveB"));
				else if (btn == RESET)
					resetB.setText(mls.getMessage("resetB"));
				else if (btn == CHOOSE)
					chooseB.setText(mls.getMessage("chooseB"));
			}
		};

		t.schedule(task, DONE_TIME);
	}

	/**
	 * enables the choose button
	 * 
	 * @param en
	 *            true for enabled, else false
	 */
	public void setChooseButtonEnabled(boolean en) {
		this.chooseB.setEnabled(en);
		if (en)
			this.chooseB.setText(mls.getMessage("chooseB"));
		else
			this.chooseB.setText("<html><font size=\"3\" color=\"red\">" + mls.getMessage("invalidPath") + "</font><html>");
	}
}