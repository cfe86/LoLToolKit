package view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import view.interfaces.AbstractTab;

import com.cf.mls.MLS;
import com.cf.mls.extension.LanguageMenuExtension;

import config.Config;
import config.Constants;
import manager.TabManager;
import manager.structure.Tab;
import model.exception.TabInitException;
import net.miginfocom.swing.MigLayout;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3172688540921699213L;

	/**
	 * the multi language supporter
	 */
	private MLS mls;
	/**
	 * the contentPane
	 */
	private JPanel contentPane;

	private List<Tab> tabs;

	private JMenu languageM;
	private JMenu fileM;
	private JMenu helpM;
	private JMenu skinsM;
	private JMenu settingsM;
	private JMenuItem hifiLookAndFeelMI;
	private JMenuItem acrylLookAndFeelMI;
	private JMenuItem aeroLookAndFeelMI;
	private JMenuItem aluminiumLookAndFeelMI;
	private JMenuItem bernsteinLookAndFeelMI;
	private JMenuItem FastLookAndFeelMI;
	private JMenuItem graphiteLookAndFeelMI;
	private JMenuItem lunaLookAndFeelMI;
	private JMenuItem mcWinLookAndFeelMI;
	private JMenuItem mintLookAndFeelMI;
	private JMenuItem noireLookAndFeelMI;
	private JMenuItem smartLookAndFeelMI;
	private JMenuItem textureLookAndFeelMI;
	private JMenuItem nimbusMI;
	private JMenuItem exitMI;
	private JMenuItem settingsMI;
	private JMenuItem updateMI;
	private JMenuItem importMI;
	private JMenuItem exportMI;
	private JMenuItem donateMI;
	private JMenuItem aboutMI;
	private JMenuItem licensesMI;
	private JMenuItem debugMI;

	/**
	 * Constructor
	 * 
	 * @param locale
	 *            given locale to init the language
	 */
	public MainView(Locale locale) {
		this.mls = new MLS("languagefiles/MainView", locale);
		this.mls.setConfigPath("mls.conf");
		this.mls.setToolTipDuration(-1);
		this.tabs = TabManager.getInstance().getTabs();
	}

	/**
	 * inits the window
	 * 
	 * @throws IOException
	 *             thrown if an image couldn't be found
	 * @throws TabInitException
	 */
	public void init() throws IOException, TabInitException {
		mls.addJFrame("window", this);
		JTabbedPane tabP = mls.generateJTabbedPane("tabP", true);
		languageM = mls.generateJMenu("languageM");
		fileM = mls.generateJMenu("fileM");
		helpM = mls.generateJMenu("helpM");
		settingsM = mls.generateJMenu("settingsM");
		skinsM = mls.generateJMenu("skinsM");
		hifiLookAndFeelMI = mls.generateJMenuItem("hifiLookAndFeelMI");
		acrylLookAndFeelMI = mls.generateJMenuItem("acrylLookAndFeelMI");
		aeroLookAndFeelMI = mls.generateJMenuItem("aeroLookAndFeelMI");
		aluminiumLookAndFeelMI = mls.generateJMenuItem("aluminiumLookAndFeelMI");
		bernsteinLookAndFeelMI = mls.generateJMenuItem("bernsteinLookAndFeelMI");
		FastLookAndFeelMI = mls.generateJMenuItem("fastLookAndFeelMI");
		graphiteLookAndFeelMI = mls.generateJMenuItem("graphiteLookAndFeelMI");
		lunaLookAndFeelMI = mls.generateJMenuItem("lunaLookAndFeelMI");
		mcWinLookAndFeelMI = mls.generateJMenuItem("mcWinLookAndFeelMI");
		mintLookAndFeelMI = mls.generateJMenuItem("mintLookAndFeelMI");
		noireLookAndFeelMI = mls.generateJMenuItem("noireLookAndFeelMI");
		smartLookAndFeelMI = mls.generateJMenuItem("smartLookAndFeelMI");
		textureLookAndFeelMI = mls.generateJMenuItem("textureLookAndFeelMI");
		nimbusMI = mls.generateJMenuItem("nimbusMI");
		exitMI = mls.generateJMenuItem("exitMI");
		settingsMI = mls.generateJMenuItem("settingsMI");
		updateMI = mls.generateJMenuItem("updateMI");
		importMI = mls.generateJMenuItem("importMI");
		exportMI = mls.generateJMenuItem("exportMI");
		donateMI = mls.generateJMenuItem("donateMI");
		aboutMI = mls.generateJMenuItem("aboutMI");
		licensesMI = mls.generateJMenuItem("licensesMI");
		debugMI = mls.generateJMenuItem("debugMI");

		contentPane = new JPanel(new MigLayout("insets 5", "[grow]", "[grow]"));

		JMenuBar menubar = mls.generateJMenuBar("menubar");
		setJMenuBar(menubar);

		menubar.add(fileM);
		menubar.add(skinsM);
		menubar.add(languageM);
		menubar.add(settingsM);
		menubar.add(helpM);

		fileM.add(importMI);
		fileM.add(exportMI);
		fileM.addSeparator();
		fileM.add(exitMI);

		settingsM.add(settingsMI);
		settingsM.addSeparator();
		settingsM.add(updateMI);
		if (Constants.DEBUG) {
			settingsM.addSeparator();
			settingsM.add(debugMI);
		}

		skinsM.add(acrylLookAndFeelMI);
		skinsM.add(aeroLookAndFeelMI);
		skinsM.add(aluminiumLookAndFeelMI);
		skinsM.add(bernsteinLookAndFeelMI);
		skinsM.add(FastLookAndFeelMI);
		skinsM.add(graphiteLookAndFeelMI);
		skinsM.add(hifiLookAndFeelMI);
		skinsM.add(lunaLookAndFeelMI);
		skinsM.add(mcWinLookAndFeelMI);
		skinsM.add(mintLookAndFeelMI);
		skinsM.add(noireLookAndFeelMI);
		skinsM.add(smartLookAndFeelMI);
		skinsM.add(textureLookAndFeelMI);

		helpM.add(donateMI);
		helpM.addSeparator();
		helpM.add(licensesMI);
		helpM.addSeparator();
		helpM.add(aboutMI);

		setContentPane(contentPane);

		// init tabs
		for (Tab t : this.tabs) {
			t.getTab().init();
			mls.addTab(tabP, t.getTab(), t.getIdentifier());
		}

		contentPane.add(tabP, "grow");

		mls.addCheckBoxLanguageMenuItem(languageM, mls.getLocale(), new LanguageMenuExtension() {

			@Override
			public void changeLanguage(Locale lang) {

				for (Tab t : tabs) {
					t.getTab().changeLanguage(lang);
					t.getTab().revalidate();
				}

				Config.getInstance().setCurrentLanguage(lang);
				revalidate();
				pack();
			}
		});

		setMinimumSize(new Dimension(675, 755));
		setPreferredSize(Config.getInstance().getCurrDimension());
		pack();
	}

	/**
	 * gets the current size of the window
	 * 
	 * @return the dimension
	 */
	public Dimension getCurrentSize() {
		return getSize();
	}

	public Locale getCurrentLanguage() {
		return mls.getLocale();
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
	 * shows a given message and asks for yes or no
	 * 
	 * @param identifier
	 *            identifier for the translator
	 * 
	 * @return the JOPtionPane.YES or NO answer
	 */
	public int showConfirmationMessage(String identifier) {
		return JOptionPane.showConfirmDialog(this, this.mls.getMessage(identifier));
	}

	/**
	 * sets an ActionListener
	 * 
	 * @param l
	 *            the ActionListener
	 */
	public void setActionListener(ActionListener l) {
		hifiLookAndFeelMI.addActionListener(l);
		acrylLookAndFeelMI.addActionListener(l);
		aeroLookAndFeelMI.addActionListener(l);
		aluminiumLookAndFeelMI.addActionListener(l);
		bernsteinLookAndFeelMI.addActionListener(l);
		FastLookAndFeelMI.addActionListener(l);
		graphiteLookAndFeelMI.addActionListener(l);
		lunaLookAndFeelMI.addActionListener(l);
		mcWinLookAndFeelMI.addActionListener(l);
		mintLookAndFeelMI.addActionListener(l);
		noireLookAndFeelMI.addActionListener(l);
		smartLookAndFeelMI.addActionListener(l);
		textureLookAndFeelMI.addActionListener(l);
		nimbusMI.addActionListener(l);

		exitMI.addActionListener(l);

		settingsMI.addActionListener(l);
		updateMI.addActionListener(l);
		debugMI.addActionListener(l);

		importMI.addActionListener(l);
		exportMI.addActionListener(l);
		donateMI.addActionListener(l);
		aboutMI.addActionListener(l);
		licensesMI.addActionListener(l);
	}

	public AbstractTab getTab(int index) {
		return this.tabs.get(index).getTab();
	}
}