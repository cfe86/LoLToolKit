package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableModel;

import view.interfaces.AbstractTab;
import view.structure.ImagePanel;
import view.tablemodel.ChampionTableModel;
import model.exception.TabInitException;
import model.structure.ChampionNote;
import model.util.Graphics;
import model.util.Util;
import net.miginfocom.swing.MigLayout;

import com.cf.mls.MLS;

import config.Config;

public class ChampNotePanel extends AbstractTab {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8529808100209505618L;

	/**
	 * the multi language supporter
	 */
	private MLS mls;

	private JPanel tableP;
	private JPanel chooseP;
	private JPanel runeMasterySumP;
	private JPanel dataP;
	private ImagePanel summoner1IP;
	private ImagePanel summoner2IP;
	private JTable tableT;
	private JEditorPane dataEP;
	private JComboBox<String> positionCB;
	private JLabel runepageJL;
	private JLabel runepageShowJL;
	private JLabel masteryJL;
	private JLabel masteryShowJL;
	private JButton newB;
	private JButton editB;
	private JButton deleteB;

	private int summonerLength = 48;

	/**
	 * Constructor
	 * 
	 * @throws IOException
	 *             thrown if an image couldn't be found
	 */
	public ChampNotePanel() {
		mls = new MLS("languagefiles/ChampionNotePanel", Config.getInstance().getCurrentLanguage());
		mls.setToolTipDuration(-1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.interfaces.AbstractTab#init()
	 */
	@Override
	public void init() throws TabInitException {
		try {
			BufferedImage img = Graphics.scale(Graphics.readImageFromJar("images/Random.png"), summonerLength, summonerLength);

			tableP = mls.generateTitledBevelPanel("tableP", BevelBorder.LOWERED);
			chooseP = mls.generateTitledBevelPanel("chooseP", BevelBorder.LOWERED);
			runeMasterySumP = mls.generateTitledBevelPanel("runeMasterySumP", BevelBorder.LOWERED);
			dataP = mls.generateTitledBevelPanel("dataP", BevelBorder.LOWERED);
			summoner1IP = new ImagePanel(img);
			summoner2IP = new ImagePanel(img);
			tableT = mls.generateJTable("tableT");
			setTableModel(new ChampionTableModel());
			dataEP = mls.generateJEditorPane("dataEP", true, false, "", "text/plain");
			positionCB = mls.generateJComboBox("positionCB", new ArrayList<String>(), -1, true, false, null);
			runepageJL = mls.generateJLabel("runepageJL");
			runepageShowJL = mls.generateJLabel("runepageShowJL");
			masteryJL = mls.generateJLabel("masteryJL");
			masteryShowJL = mls.generateJLabel("masteryShowJL");
			newB = mls.generateJButton("newB");
			editB = mls.generateJButton("editB");
			deleteB = mls.generateJButton("deleteB");

			mls.addCustomJPanel(summoner1IP, "summoner1IP");
			mls.addCustomJPanel(summoner2IP, "summoner2IP");
			summoner1IP.setMinimumSize(new Dimension(summonerLength, summonerLength));
			summoner2IP.setMinimumSize(new Dimension(summonerLength, summonerLength));

			// Chooser
			chooseP.setLayout(new MigLayout("insets 5", "[shrink][shrink][shrink][shrink]", "[shrink]"));

			chooseP.add(positionCB, "width :250:, push");
			chooseP.add(deleteB, "right");
			chooseP.add(newB, "right");
			chooseP.add(editB, "right");

			// Table
			tableP.setLayout(new MigLayout("insets 5", "[grow]", "[shrink]"));

			JScrollPane sp = new JScrollPane();
			sp.setViewportView(tableT);
			sp.setMinimumSize(new Dimension(400, 108));
			sp.setPreferredSize(new Dimension(400, 112));
			tableP.add(sp, "grow, height :120:");

			// rune pages, mastery, summoner
			runeMasterySumP.setLayout(new MigLayout("insets 5", "[shrink][shrink][shrink]", "[shrink]"));

			JPanel rmP = new JPanel(new MigLayout("insets 0", "[shrink][shrink]", "[shrink][shrink]"));
			rmP.add(runepageJL, "gapbottom 5");
			rmP.add(runepageShowJL, ", gapbottom 5, wrap");
			rmP.add(masteryJL);
			rmP.add(masteryShowJL);

			runeMasterySumP.add(rmP, "push");
			runeMasterySumP.add(summoner1IP, "right, height :" + summonerLength + ":, width :" + summonerLength + ":");
			runeMasterySumP.add(summoner2IP, "right, height :" + summonerLength + ":, width :" + summonerLength + ":");

			dataP.setLayout(new MigLayout("insets 5", "[grow]", "[grow]"));
			JScrollPane sp2 = new JScrollPane();
			sp2.setViewportView(dataEP);
			dataP.add(sp2, "grow");

			setLayout(new MigLayout("insets 5", "[grow]", "[shrink][shrink][shrink][grow]"));
			add(chooseP, "grow, wrap");
			add(tableP, "grow, wrap, height :162:");
			add(runeMasterySumP, "grow, wrap");
			add(dataP, "grow");
		} catch (IOException e) {
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
		editB.addActionListener(l);
		newB.addActionListener(l);
		deleteB.addActionListener(l);
		positionCB.addActionListener(l);
	}

	/**
	 * sets all information charts for this champ to the combobox
	 * 
	 * @param names
	 *            list of all info charts
	 */
	public void setPositions(List<String> names) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		for (String name : names)
			model.addElement(name);

		this.positionCB.setModel(model);
	}

	/**
	 * selects a given info name
	 * 
	 * @param name
	 *            the name which should be chosen
	 */
	public void setPosition(String name) {
		this.positionCB.setSelectedItem(name);
	}

	/**
	 * gets the current chosen index
	 * 
	 * @return selected index
	 */
	public int getSelectedPositionIndex() {
		return this.positionCB.getSelectedIndex();
	}

	/**
	 * sets the given champion info in the window
	 * 
	 * @param info
	 *            given info
	 * 
	 * @throws IOException
	 *             thrown if an image couldn't be found
	 */
	public void setInfo(ChampionNote info) throws IOException {
		this.runepageShowJL.setText(info.getRunepage());
		this.masteryShowJL.setText(info.getMasteryPage());

		if (info.isHtml()) {
			this.dataEP.setContentType("text/html");
			Util.setFontToEP(this.dataEP);
		} else {
			this.dataEP.setContentType("text/plain");
			this.dataEP.setFont(new Font(this.dataEP.getFont().getName(), Font.PLAIN, Config.getInstance().getFontSize()));
		}

		try {
			this.summoner1IP.setImage(Graphics.scale(Graphics.readImage(Config.getInstance().getSummonerSpellsFolder() + info.getSummonerspell1().toLowerCase() + ".png"),
					summonerLength, summonerLength));
		} catch (IOException e) {
			this.summoner1IP.setImage(Graphics.scale(Graphics.readImageFromJar("images/Random.png"), summonerLength, summonerLength));
		}
		this.summoner1IP.setToolTipText(info.getSummonerspell1());
		try {
			this.summoner2IP.setImage(Graphics.scale(Graphics.readImage(Config.getInstance().getSummonerSpellsFolder() + info.getSummonerspell2().toLowerCase() + ".png"),
					summonerLength, summonerLength));
		} catch (IOException e) {
			this.summoner2IP.setImage(Graphics.scale(Graphics.readImageFromJar("images/Random.png"), summonerLength, summonerLength));
		}
		this.summoner2IP.setToolTipText(info.getSummonerspell2());
	}

	/**
	 * resets the window to be empty
	 * 
	 * @throws IOException
	 *             thrown if random images couldn't be found
	 */
	public void resetInfo() throws IOException {
		setPositions(new ArrayList<String>());
		this.runepageShowJL.setText(mls.getMessage("runepageShowJL"));
		this.masteryShowJL.setText(mls.getMessage("masteryShowJL"));

		this.dataEP.setContentType("text/plain");
		this.dataEP.setText("");

		this.summoner1IP.setImage(Graphics.scale(Graphics.readImageFromJar("images/Random.png"), summonerLength, summonerLength));
		this.summoner1IP.setToolTipText(mls.getMessage("summoner1IP"));
		this.summoner2IP.setImage(Graphics.scale(Graphics.readImageFromJar("images/Random.png"), summonerLength, summonerLength));
		this.summoner2IP.setToolTipText(mls.getMessage("summoner2IP"));

		setTableModel(new ChampionTableModel());
	}

	/**
	 * sets the champ note text
	 * 
	 * @param txt
	 *            the text
	 */
	public void setInfoText(String txt) {
		this.dataEP.setText(txt);
	}

	/**
	 * sets the given tablemodel to the table
	 * 
	 * @param model
	 *            the tablemodel
	 */
	public void setTableModel(TableModel model) {
		this.tableT.setModel(model);
		for (int i = 1; i < 19; i++) {
			tableT.getColumnModel().getColumn(i).setMaxWidth(25);
		}
	}
}