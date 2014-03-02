package view.window;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import view.structure.ImagePanel;
import net.miginfocom.swing.MigLayout;

import com.cf.mls.MLS;

import config.Config;
import model.structure.SummonerSpell;
import model.util.Graphics;

public class ShowSummonerSpellView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1198226182520467379L;

	/**
	 * the multi language supporter
	 */
	private MLS mls;
	/**
	 * the contentPane
	 */
	private JPanel contentPane;

	private JPanel paneP;
	private List<ImagePanel> spells;

	/**
	 * Constructor
	 */
	public ShowSummonerSpellView() {
		mls = new MLS("languagefiles/ShowSummonerSpellView", Locale.US);
		mls.setToolTipDuration(-1);
		spells = new ArrayList<>();
	}

	/**
	 * inits the window
	 * 
	 * @param spells
	 *            list with summonerspells to show
	 * 
	 * @throws IOException
	 *             thrown if an image couldn't be foundF
	 */
	public void init(List<SummonerSpell> spells) throws IOException {
		mls.addJFrame("window", this);

		int rows = spells.size() / Config.getInstance().getSummonerSpellsPerLine();

		contentPane = new JPanel();
		paneP = mls.generateTitledBevelPanel("paneP", BevelBorder.LOWERED);

		setContentPane(contentPane);

		paneP.setLayout(new GridLayout(rows, Config.getInstance().getSummonerSpellsPerLine(), 5, 5));

		// add all images
		for (SummonerSpell s : spells) {
			s.generateImagePath();
			ImagePanel p = new ImagePanel(Graphics.scale(Graphics.readImage(s.getImage()), Config.getInstance().getSpellItemImageLength(), Config.getInstance()
					.getSpellItemImageLength()));
			p.setToolTipText(s.getName());
			p.setPreferredSize(new Dimension(Config.getInstance().getSpellItemImageLength(), Config.getInstance().getSpellItemImageLength()));
			this.spells.add(p);
			paneP.add(p);
		}

		contentPane.setLayout(new MigLayout("insets 5", "[grow]", "[grow]"));
		contentPane.add(paneP, "grow");

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
	 * sets the mouselistener to every summonerspell imagepanel
	 * 
	 * @param l
	 *            the listener
	 */
	public void setMouseListener(MouseListener l) {
		for (ImagePanel p : this.spells)
			p.addMouseListener(l);
	}

	/**
	 * gets the list with all summonerspell image panels
	 * 
	 * @return list with all imagepanels
	 */
	public List<ImagePanel> getImages() {
		return this.spells;
	}
}