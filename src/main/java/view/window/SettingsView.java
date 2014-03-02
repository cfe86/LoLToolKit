package view.window;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import model.structure.Hotkeys;
import net.miginfocom.swing.MigLayout;

import com.cf.mls.MLS;

import config.Config;
import config.Constants;

public class SettingsView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3575803090535872837L;

	/**
	 * the multi language supporter
	 */
	private MLS mls;
	/**
	 * the contentPane
	 */
	private JPanel contentPane;

	private JPanel lolPathP;
	private JPanel hotkeyPrefixP;
	private JPanel miscP;
	private JCheckBox useLolPathChB;
	private JLabel lolPathJL;
	private JLabel pathValidJL;
	private JLabel normalPrefixJL;
	private JLabel smartcastPrefixJL;
	private JLabel selfcastPrefixJL;
	private JLabel lvlupPrefixJL;
	private JLabel normalWhenSmartcastPrefixJL;
	private JLabel normalWhenSelfcastPrefixJL;
	private JLabel normalWhenSelfAndSmartcastPrefixJL;
	private JLabel inputPathJL;
	private JLabel gamecfgJL;
	private JLabel imageLengthJL;
	private JLabel spellItemLengthJL;
	private JLabel summonerPerLineJL;
	private JTextField lolPathTF;
	private JTextField inputTF;
	private JTextField inputPathTF;
	private JTextField gamecfgTF;
	private JButton inputOpenB;
	private JButton lolPathOpenB;
	private JButton saveB;
	private JButton cancelB;
	private JComboBox<String> normalPrefixCB;
	private JComboBox<String> smartcastPrefixCB;
	private JComboBox<String> selfcastPrefixCB;
	private JComboBox<String> lvlupPrefixCB;
	private JComboBox<String> normalWhenSmartcastPrefixCB;
	private JComboBox<String> normalWhenSelfcastPrefixCB;
	private JComboBox<String> normalWhenSelfAndSmartcastPrefixCB;
	private JComboBox<Integer> imageLengthCB;
	private JComboBox<Integer> spellItemLengthCB;
	private JComboBox<Integer> summonerPerLineCB;

	/**
	 * Constructor
	 */
	public SettingsView() {
		mls = new MLS("languagefiles/SettingsView", Config.getInstance().getCurrentLanguage());
		mls.setToolTipDuration(-1);
	}

	/**
	 * inits the window
	 */
	public void init() {
		mls.addJFrame("window", this);

		lolPathP = mls.generateTitledBevelPanel("lolPathP", BevelBorder.LOWERED);
		hotkeyPrefixP = mls.generateTitledBevelPanel("hotkeyPrefixP", BevelBorder.LOWERED);
		miscP = mls.generateTitledBevelPanel("miscP", BevelBorder.LOWERED);
		useLolPathChB = mls.generateJCheckBox("useLolPathChB", true);
		lolPathJL = mls.generateJLabel("lolPathJL");
		pathValidJL = mls.generateJLabel("pathValidJL");
		gamecfgJL = mls.generateJLabel("gamecfgJL");
		normalPrefixJL = mls.generateJLabel("normalPrefixJL");
		smartcastPrefixJL = mls.generateJLabel("smartcastPrefixJL");
		selfcastPrefixJL = mls.generateJLabel("selfcastPrefixJL");
		lvlupPrefixJL = mls.generateJLabel("lvlupPrefixJL");
		normalWhenSmartcastPrefixJL = mls.generateJLabel("normalWhenSmartcastPrefixJL");
		normalWhenSelfcastPrefixJL = mls.generateJLabel("normalWhenSelfcastPrefixJL");
		normalWhenSelfAndSmartcastPrefixJL = mls.generateJLabel("normalWhenSelfAndSmartcastPrefixJL");
		inputPathJL = mls.generateJLabel("inputPathJL");
		imageLengthJL = mls.generateJLabel("imageLengthJL");
		spellItemLengthJL = mls.generateJLabel("spellItemLengthJL");
		summonerPerLineJL = mls.generateJLabel("summonerPerLineJL");
		lolPathTF = mls.generateJTextField("lolPathTF", true, false, 10, "");
		inputTF = mls.generateJTextField("inputTF", true, false, 10, "");
		gamecfgTF = mls.generateJTextField("gamecfgTF", true, true, 10, "");
		inputPathTF = mls.generateJTextField("inputPathTF", true, true, 10, "");
		inputOpenB = mls.generateJButton("inputOpenB");
		lolPathOpenB = mls.generateJButton("lolPathOpenB");
		saveB = mls.generateJButton("saveB");
		cancelB = mls.generateJButton("cancelB");
		normalPrefixCB = mls.generateJComboBox("normalPrefixCB", new ArrayList<String>(), -1, true, false, null);
		smartcastPrefixCB = mls.generateJComboBox("smartcastPrefixCB", new ArrayList<String>(), -1, true, false, null);
		selfcastPrefixCB = mls.generateJComboBox("selfcastPrefixCB", new ArrayList<String>(), -1, true, false, null);
		lvlupPrefixCB = mls.generateJComboBox("lvlupPrefixCB", new ArrayList<String>(), -1, true, false, null);
		normalWhenSmartcastPrefixCB = mls.generateJComboBox("normalWhenSmartcastPrefixCB", new ArrayList<String>(), -1, true, false, null);
		normalWhenSelfcastPrefixCB = mls.generateJComboBox("normalWhenSelfcastPrefixCB", new ArrayList<String>(), -1, true, false, null);
		normalWhenSelfAndSmartcastPrefixCB = mls.generateJComboBox("normalWhenSelfAndSmartcastPrefixCB", new ArrayList<String>(), -1, true, false, null);
		imageLengthCB = mls.generateJComboBox("imageLengthCB", new ArrayList<Integer>(), -1, true, false, null);
		spellItemLengthCB = mls.generateJComboBox("spellItemLengthCB", new ArrayList<Integer>(), -1, true, false, null);
		summonerPerLineCB = mls.generateJComboBox("summonerPerLineCB", new ArrayList<Integer>(), -1, true, false, null);

		contentPane = new JPanel();
		setContentPane(contentPane);

		// LoL Path for config
		lolPathP.setLayout(new MigLayout("insets 5", "[shrink][grow]", "[shrink][shrink]"));

		lolPathP.add(lolPathJL);
		lolPathP.add(lolPathTF, "width :200:, gapleft 5, grow");
		int l = (int) lolPathTF.getPreferredSize().getHeight();
		lolPathP.add(lolPathOpenB, "gapleft 5, wrap, height :" + l + ":");

		lolPathP.add(pathValidJL, "span 3, center, wrap, gapbottom 10");

		// hotkeys
		hotkeyPrefixP.setLayout(new MigLayout("insets 5", "[shrink][grow]", "[shrink][shrink][shrink][shrink][shrink][shrink][shrink]"));

		hotkeyPrefixP.add(normalPrefixJL);
		hotkeyPrefixP.add(normalPrefixCB, "wrap, grow, gapleft 5");
		hotkeyPrefixP.add(smartcastPrefixJL);
		hotkeyPrefixP.add(smartcastPrefixCB, "wrap, grow, gapleft 5");
		hotkeyPrefixP.add(selfcastPrefixJL);
		hotkeyPrefixP.add(selfcastPrefixCB, "wrap, grow, gapleft 5");
		hotkeyPrefixP.add(lvlupPrefixJL);
		hotkeyPrefixP.add(lvlupPrefixCB, "wrap, grow, gapleft 5");
		hotkeyPrefixP.add(normalWhenSmartcastPrefixJL);
		hotkeyPrefixP.add(normalWhenSmartcastPrefixCB, "wrap, grow, gapleft 5");
		hotkeyPrefixP.add(normalWhenSelfcastPrefixJL);
		hotkeyPrefixP.add(normalWhenSelfcastPrefixCB, "wrap, grow, gapleft 5");
		hotkeyPrefixP.add(normalWhenSelfAndSmartcastPrefixJL);
		hotkeyPrefixP.add(normalWhenSelfAndSmartcastPrefixCB, "wrap, grow, gapleft 5");

		// misc panel
		miscP.setLayout(new MigLayout("insets 5", "[shrink][grow]", "[shrink][shrink][shrink][shrink][shrink]"));

		miscP.add(inputPathJL);
		miscP.add(inputPathTF, "wrap, grow, gapleft 5");
		miscP.add(gamecfgJL);
		miscP.add(gamecfgTF, "wrap, grow, gapleft 5");
		miscP.add(imageLengthJL);
		miscP.add(imageLengthCB, "wrap, grow, gapleft 5");
		miscP.add(spellItemLengthJL);
		miscP.add(spellItemLengthCB, "wrap, grow, gapleft 5");
		miscP.add(summonerPerLineJL);
		miscP.add(summonerPerLineCB, "grow, gapleft 5");

		// button panel
		JPanel btnP = new JPanel();
		btnP.setLayout(new MigLayout("insets 0", "[grow][grow]", "[shrink]"));

		btnP.add(saveB, "grow");
		btnP.add(cancelB, "grow");

		contentPane.setLayout(new MigLayout("insets 5", "[grow]", "[shrink][shrink][shrink]"));

		contentPane.add(lolPathP, "grow, wrap");
		contentPane.add(hotkeyPrefixP, "grow, wrap");
		contentPane.add(miscP, "grow, wrap");
		contentPane.add(btnP, "grow");

		setMinimumSize(new Dimension(349, 525));
		pack();
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
		lolPathOpenB.addActionListener(l);
		inputOpenB.addActionListener(l);

		saveB.addActionListener(l);
		cancelB.addActionListener(l);
	}

	/**
	 * sets the JLabel under the path to valid (green) or invalid (red)
	 * 
	 * @param found
	 *            true if path is valid, else false
	 */
	public void setLoLPathFound(boolean found) {
		if (found)
			this.pathValidJL.setText("<html><font size=\"2\" color=\"green\">" + mls.getMessage("valid") + "</font><html>");
		else
			this.pathValidJL.setText("<html><font size=\"2\" color=\"red\">" + mls.getMessage("invalid") + "</font><html>");
	}

	/**
	 * sets the comboboxes which contain the imageicon length, spellicon length
	 * and summonericon length
	 */
	public void setImageComboboxes() {
		DefaultComboBoxModel<Integer> imageModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<Integer> spellModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<Integer> summonerModel = new DefaultComboBoxModel<>();

		for (int i = Constants.PIXEL_LOW; i <= Constants.PIXEL_HIGH; i++) {
			imageModel.addElement(i);
			spellModel.addElement(i);
		}

		for (int i = 1; i < 6; i++)
			summonerModel.addElement(i);

		this.imageLengthCB.setModel(imageModel);
		this.spellItemLengthCB.setModel(spellModel);
		this.summonerPerLineCB.setModel(summonerModel);
	}

	/**
	 * sets the hotkey possibilities to the prefix comboboxes
	 */
	public void setPrefix() {
		DefaultComboBoxModel<String> normalModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> smartcastModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> selfcastModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> lvlupModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> normalWhenSmartcastModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> normalWhenSelfcastModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<String> normalWhenSelfSmartcastModel = new DefaultComboBoxModel<>();

		for (int i = 0; i < 4; i++) {
			String ele = Hotkeys.getPrefix(i);
			ele = ele.replace("[", "");
			ele = ele.replace("]", "");
			if (ele.trim().length() == 0)
				ele = Hotkeys.NO_PREFIX;

			normalModel.addElement(ele);
			smartcastModel.addElement(ele);
			selfcastModel.addElement(ele);
			lvlupModel.addElement(ele);
			normalWhenSmartcastModel.addElement(ele);
			normalWhenSelfcastModel.addElement(ele);
			normalWhenSelfSmartcastModel.addElement(ele);
		}

		this.normalPrefixCB.setModel(normalModel);
		this.smartcastPrefixCB.setModel(smartcastModel);
		this.selfcastPrefixCB.setModel(selfcastModel);
		this.lvlupPrefixCB.setModel(lvlupModel);
		this.normalWhenSmartcastPrefixCB.setModel(normalWhenSmartcastModel);
		this.normalWhenSelfcastPrefixCB.setModel(normalWhenSmartcastModel);
		this.normalWhenSelfAndSmartcastPrefixCB.setModel(normalWhenSelfSmartcastModel);
	}

	/**
	 * reads the Config and sets the config in to the window
	 */
	public void setConfig() {
		// lol path
		this.useLolPathChB.setSelected(Config.getInstance().getUseLolPath());
		// this.setLolPathEnabled(Config.getInstance().getUseLolPath());
		this.lolPathTF.setText(Config.getInstance().getLolPath());
		setLoLPathFound(new File(Config.getInstance().getLolPath() + "lol.launcher.exe").exists());
		this.inputTF.setText(Config.getInstance().getCustomInputIniPath());

		// Prefix
		this.normalPrefixCB.setSelectedIndex(Config.getInstance().getNormalPrefixID());
		this.smartcastPrefixCB.setSelectedIndex(Config.getInstance().getSmartcastPrefixID());
		this.selfcastPrefixCB.setSelectedIndex(Config.getInstance().getSelfcastPrefixID());
		this.lvlupPrefixCB.setSelectedIndex(Config.getInstance().getLevelupPrefixID());
		this.normalWhenSmartcastPrefixCB.setSelectedIndex(Config.getInstance().getNormalWhenSmartcastPrefixID());
		this.normalWhenSelfcastPrefixCB.setSelectedIndex(Config.getInstance().getNormalWhenSelfcastPrefixID());
		this.normalWhenSelfAndSmartcastPrefixCB.setSelectedIndex(Config.getInstance().getNormalWhenSmartAndSelfcastPrefixID());

		// misc
		this.inputPathTF.setText(Config.getInstance().getInputIni());
		this.gamecfgTF.setText(Config.getInstance().getGameCfg());
		this.imageLengthCB.setSelectedIndex(Config.getInstance().getChampImageLength() - Constants.PIXEL_LOW);
		this.spellItemLengthCB.setSelectedIndex(Config.getInstance().getSpellItemImageLength() - Constants.PIXEL_LOW);
		this.summonerPerLineCB.setSelectedIndex(Config.getInstance().getSummonerSpellsPerLine() - 1);
	}

	/**
	 * reads the config from the window and saves it to Config
	 */
	public void saveConfig() {
		// lol path
		String path = this.lolPathTF.getText();
		if (!(path.endsWith("/") || path.endsWith("\\")))
			path += "/";
		Config.getInstance().setUseLoLPath(new File(path + "lol.launcher.exe").exists() && this.useLolPathChB.isSelected());
		Config.getInstance().setLolPath(this.lolPathTF.getText());
		Config.getInstance().setCustomInputIniPath(this.inputTF.getText());

		// Prefix
		Config.getInstance().setNormalPrefixID(this.normalPrefixCB.getSelectedIndex());
		Config.getInstance().setSmartcastPrefixID(this.smartcastPrefixCB.getSelectedIndex());
		Config.getInstance().setSelfcastPrefixID(this.selfcastPrefixCB.getSelectedIndex());
		Config.getInstance().setLevelupPrefixID(this.lvlupPrefixCB.getSelectedIndex());
		Config.getInstance().setNormalWhenSmartcastPrefixID(this.normalWhenSmartcastPrefixCB.getSelectedIndex());
		Config.getInstance().setNormalWhenSelfcastPrefixID(this.normalWhenSelfcastPrefixCB.getSelectedIndex());
		Config.getInstance().setNormalWhenSmartAndSelfcastPrefixID(this.normalWhenSelfAndSmartcastPrefixCB.getSelectedIndex());

		// misc
		Config.getInstance().setInputIni(this.inputPathTF.getText());
		Config.getInstance().setGameCfg(this.gamecfgTF.getText());
		Config.getInstance().setChampionImageLength((int) this.imageLengthCB.getSelectedItem());
		Config.getInstance().setSpellItemLength((int) this.spellItemLengthCB.getSelectedItem());
		Config.getInstance().setSummonerSpellsPerLine((int) this.summonerPerLineCB.getSelectedItem());
	}

	public void setLolPath(String path) {
		this.lolPathTF.setText(path);
	}

	public void setCustomIni(String path) {
		this.inputTF.setText(path);
	}

	public boolean isLolPathSelected() {
		return this.useLolPathChB.isSelected();
	}
}