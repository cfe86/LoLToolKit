package view.window;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.table.TableModel;

import view.structure.ImagePanel;
import view.tablemodel.ChampionEditorTableModel;
import model.structure.ChampionNote;
import model.structure.SummonerSpell;
import model.util.Graphics;
import net.miginfocom.swing.MigLayout;

import com.cf.mls.MLS;

import config.Config;

public class ChampNoteEditorView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2508072482490698778L;

	/**
	 * the multi language supporter
	 */
	private MLS mls;
	/**
	 * the contentPane
	 */
	private JPanel contentPane;

	private JPanel tableP;
	private JPanel nameP;
	private JPanel runeMasterySumP;
	private JPanel dataP;
	private ImagePanel summoner1IP;
	private ImagePanel summoner2IP;
	private JTable tableT;
	private JEditorPane dataEP;
	private JLabel runepageJL;
	private JLabel masteryJL;
	private JLabel nameJL;
	private JCheckBox htmlChB;
	private JTextField nameTF;
	private JTextField runepageTF;
	private JTextField masteryTF;
	private JComboBox<String> itemsCB;
	private JButton saveB;
	private JButton cancelB;
	private JButton boldB;
	private JButton italicB;
	private JButton underlineB;
	private JButton listNrB;
	private JButton listUnnrB;
	private JButton previewB;
	private JButton addB;

	private int summonerLength = 48;

	/**
	 * Constructor
	 */
	public ChampNoteEditorView() {
		mls = new MLS("languagefiles/ChampionNoteEditorView", Config.getInstance().getCurrentLanguage());
		mls.setToolTipDuration(-1);
	}

	/**
	 * inits the window
	 */
	public void init() throws IOException {
		mls.addJFrame("window", this);

		BufferedImage img = Graphics.scale(Graphics.readImageFromJar("images/Random.png"), summonerLength, summonerLength);

		contentPane = new JPanel();
		tableP = mls.generateTitledBevelPanel("tableP", BevelBorder.LOWERED);
		nameP = mls.generateTitledBevelPanel("nameP", BevelBorder.LOWERED);
		runeMasterySumP = mls.generateTitledBevelPanel("runeMasterySumP", BevelBorder.LOWERED);
		dataP = mls.generateTitledBevelPanel("dataP", BevelBorder.LOWERED);
		summoner1IP = new ImagePanel(img);
		mls.addCustomJPanel(summoner1IP, "summoner1IP");
		summoner2IP = new ImagePanel(img);
		mls.addCustomJPanel(summoner2IP, "summoner2IP");
		tableT = mls.generateJTable("tableT");
		setTableModel(new ChampionEditorTableModel());
		dataEP = mls.generateJEditorPane("dataEP", true, true, "", "text/plain");
		htmlChB = mls.generateJCheckBox("htmlChB", false);
		runepageJL = mls.generateJLabel("runepageJL");
		masteryJL = mls.generateJLabel("masteryJL");
		nameJL = mls.generateJLabel("nameJL");
		nameTF = mls.generateJTextField("nameTF", true, true, 10, "");
		runepageTF = mls.generateJTextField("runepageTF", true, true, 10, "");
		masteryTF = mls.generateJTextField("masteryTF", true, true, 10, "");
		itemsCB = mls.generateJComboBox("itemsCB", new ArrayList<String>(), -1, true, false, null);
		saveB = mls.generateJButton("saveB");
		cancelB = mls.generateJButton("cancelB");
		boldB = mls.generateJButton("boldB");
		italicB = mls.generateJButton("italicB");
		underlineB = mls.generateJButton("underlineB");
		listNrB = mls.generateJButton("listNrB");
		listUnnrB = mls.generateJButton("listUnnrB");
		previewB = mls.generateJButton("previewB");
		addB = mls.generateJButton("addB");

		this.dataEP.setFont(new Font(this.dataEP.getFont().getName(), Font.PLAIN, Config.getInstance().getFontSize()));

		// name
		nameP.setLayout(new MigLayout("insets 5", "[shrink][shrink]", "[shrink]"));

		nameP.add(nameJL);
		nameP.add(nameTF, "grow, gapleft 10, width :150:");

		// Table
		tableP.setLayout(new MigLayout("insets 5", "[grow]", "[shrink]"));

		JScrollPane sp = new JScrollPane();
		sp.setViewportView(tableT);
		tableP.add(sp, "grow");

		// rune pages, mastery, summoner
		runeMasterySumP.setLayout(new MigLayout("insets 5", "[shrink][shrink][shrink]", "[shrink]"));

		JPanel rmP = new JPanel(new MigLayout("insets 0", "[shrink][shrink]", "[shrink][shrink]"));
		rmP.add(runepageJL, "gapbottom 5");
		rmP.add(runepageTF, ", gapbottom 5, width :200:, wrap");
		rmP.add(masteryJL);
		rmP.add(masteryTF, "width :200:");

		runeMasterySumP.add(rmP, "push");
		runeMasterySumP.add(summoner1IP, "right, height :" + summonerLength + ":, width :" + summonerLength + ":");
		runeMasterySumP.add(summoner2IP, "right, height :" + summonerLength + ":, width :" + summonerLength + ":");

		dataP.setLayout(new MigLayout("insets 5", "[grow]", "[shrink][shrink][grow]"));
		JScrollPane sp2 = new JScrollPane();
		sp2.setViewportView(dataEP);

		JPanel editorP = new JPanel(new MigLayout("insets 0", "[shrink][shrink][shrink][shrink][shrink][shrink][shrink][shrink]", "[shrink]"));

		editorP.add(boldB);
		editorP.add(italicB);
		editorP.add(underlineB);
		editorP.add(listNrB);
		editorP.add(listUnnrB);
		editorP.add(itemsCB, "gapleft 5, width :100:");
		editorP.add(addB, "push");
		editorP.add(previewB, "gapleft 15, right");

		dataP.add(htmlChB, "wrap");
		dataP.add(editorP, "gaptop 5, grow, wrap");
		dataP.add(sp2, "grow");

		// button panel
		JPanel btnP = new JPanel(new MigLayout("insets 0", "[grow][grow]", "[shrink]"));
		btnP.add(saveB, "grow");
		btnP.add(cancelB, "grow");

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("insets 5", "[grow]", "[shrink][shrink][shrink][grow][shrink]"));
		contentPane.add(nameP, "grow, wrap");
		contentPane.add(tableP, "grow, wrap, height :162:");
		contentPane.add(runeMasterySumP, "grow, wrap");
		contentPane.add(dataP, "grow, wrap");
		contentPane.add(btnP, "gaptop 5, grow");

		setMinimumSize(new Dimension(610, 830));
		pack();
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
		saveB.addActionListener(l);
		cancelB.addActionListener(l);

		boldB.addActionListener(l);
		italicB.addActionListener(l);
		underlineB.addActionListener(l);
		listNrB.addActionListener(l);
		listUnnrB.addActionListener(l);

		// itemsCB.addActionListener(l);
		addB.addActionListener(l);

		previewB.addActionListener(l);
		htmlChB.addActionListener(l);
	}

	/**
	 * sets the tablemodel to the table
	 * 
	 * @param model
	 *            the model
	 */
	public void setTableModel(TableModel model) {
		this.tableT.setModel(model);
		for (int i = 1; i < 19; i++) {
			tableT.getColumnModel().getColumn(i).setMaxWidth(25);
		}
	}

	/**
	 * sets the mouselistener to the ImagePanels
	 * 
	 * @param l
	 *            the listener
	 */
	public void setMouseListener(MouseListener l) {
		summoner1IP.addMouseListener(l);
		summoner2IP.addMouseListener(l);
	}

	public ImagePanel getsummoner1IP() {
		return this.summoner1IP;
	}

	public ImagePanel getsummoner2IP() {
		return this.summoner2IP;
	}

	/**
	 * sets the champion info which should be shown in the window
	 * 
	 * @param info
	 *            the info
	 * 
	 * @throws IOException
	 *             thrown if an image couldn't be loaded
	 */
	public void setInfo(ChampionNote info) throws IOException {
		this.nameTF.setText(info.getName());

		this.runepageTF.setText(info.getRunepage());
		this.masteryTF.setText(info.getMasteryPage());

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

		this.htmlChB.setSelected(info.isHtml());
		setEditorEnabled(this.htmlChB.isSelected());
		this.dataEP.setText(info.getText());
	}

	/**
	 * gets the champion info which is shown in the window
	 * 
	 * @return the info
	 */
	public ChampionNote getInfo() {
		ChampionNote result = new ChampionNote();
		result.setName(this.nameTF.getText());
		result.setRunepage(this.runepageTF.getText());
		result.setMasteryPage(this.masteryTF.getText());
		result.setSummonerspell1(this.summoner1IP.getToolTipText());
		result.setSummonerspell2(this.summoner2IP.getToolTipText());
		result.setHtml(this.htmlChB.isSelected());
		result.setText(this.dataEP.getText());
		result.setSkill(((ChampionEditorTableModel) this.tableT.getModel()).getSkills());

		return result;
	}

	/**
	 * sets the summonerspell 1 or 2
	 * 
	 * @param nr
	 *            number of the spell, 1 for spell 1, and 2 for spell 2
	 * @param spell
	 *            the summonerspell
	 * 
	 * @throws IOException
	 *             thrown if an image couln't be loaded
	 */
	public void setSummonerSpell(int nr, SummonerSpell spell) throws IOException {
		if (nr == 1) {
			this.summoner1IP.setImage(Graphics.scale(Graphics.readImage(spell.getImage()), summonerLength, summonerLength));
			this.summoner1IP.setToolTipText(spell.getName());
		} else {
			this.summoner2IP.setImage(Graphics.scale(Graphics.readImage(spell.getImage()), summonerLength, summonerLength));
			this.summoner2IP.setToolTipText(spell.getName());

		}
	}

	/**
	 * sets the items to the editor
	 * 
	 * @param names
	 *            a list with all item names
	 */
	public void setItems(List<String> names) {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		for (String name : names)
			model.addElement(name);

		this.itemsCB.setModel(model);
	}

	public boolean isHTMLChecked() {
		return this.htmlChB.isSelected();
	}

	public String getSelectedItem() {
		return (String) this.itemsCB.getSelectedItem();
	}

	/**
	 * gets an array containing the start and end index of the selected text
	 * 
	 * @return the indicies
	 */
	public int[] getSelectedTextIndices() {
		return new int[] { this.dataEP.getSelectionStart(), this.dataEP.getSelectionEnd() };
	}

	public String getText() {
		return this.dataEP.getText();
	}

	public void setText(String txt) {
		this.dataEP.setText(txt);
	}

	public String getSelectedText() {
		return this.dataEP.getSelectedText();
	}

	/**
	 * shows a JOptionPane asking how many rows the table should have
	 * 
	 * @return the number of rows
	 */
	public int showRowWindow() {
		try {
			String rows = JOptionPane.showInputDialog(this, "getRows");
			if (rows.trim().length() == 0)
				return 1;
			return Integer.parseInt(rows);
		} catch (NumberFormatException e) {
			showMessage("notaNumber");
			return -1;
		}
	}

	/**
	 * sets the editor buttons enabled
	 * 
	 * @param en
	 *            true if enabled, else false
	 */
	public void setEditorEnabled(boolean en) {
		this.boldB.setEnabled(en);
		this.italicB.setEnabled(en);
		this.underlineB.setEnabled(en);
		this.listNrB.setEnabled(en);
		this.listUnnrB.setEnabled(en);
		this.itemsCB.setEnabled(en);
		this.previewB.setEnabled(en);
	}

	public String getEditorText() {
		return this.dataEP.getText();
	}

	public int getEditorWidth() {
		return this.dataEP.getWidth();
	}
}