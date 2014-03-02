package view.window;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;

import net.miginfocom.swing.MigLayout;

import com.cf.mls.MLS;

import config.Config;

public class UpdateView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7477540308105522594L;

	/**
	 * the multi language supporter
	 */
	private MLS mls;
	/**
	 * the contentPane
	 */
	private JPanel contentPane;

	private String fastUpdateLabel;

	private JPanel updateP;
	private JLabel introJL;
	private JRadioButton fastUpdateRB;
	private JRadioButton slowUpdateRB;
	private JButton updateB;

	/**
	 * Constructor
	 */
	public UpdateView() {
		mls = new MLS("languagefiles/UpdateView", Config.getInstance().getCurrentLanguage());
		mls.setToolTipDuration(-1);
	}

	/**
	 * inits the window
	 */
	public void init() {
		mls.addJFrame("window", this);

		updateP = mls.generateTitledBevelPanel("updateP", BevelBorder.LOWERED);
		introJL = mls.generateJLabel("introJL");
		fastUpdateRB = mls.generateJRadioButton("fastUpdateRB", true, null);
		this.fastUpdateLabel = fastUpdateRB.getText();
		slowUpdateRB = mls.generateJRadioButton("slowUpdateRB", true, null);
		updateB = mls.generateJButton("updateB");

		ButtonGroup bg = new ButtonGroup();
		bg.add(fastUpdateRB);
		bg.add(slowUpdateRB);
		fastUpdateRB.setSelected(true);

		contentPane = new JPanel();
		setContentPane(contentPane);

		updateP.setLayout(new MigLayout("insets 5", "[grow]", "[shrink][shrink][shrink][shrink]"));

		updateP.add(introJL, "grow, wrap, gapbottom 10");
		updateP.add(fastUpdateRB, "grow, wrap");
		updateP.add(slowUpdateRB, "grow, wrap");
		updateP.add(updateB, "grow, gaptop 10");

		contentPane.setLayout(new MigLayout("insets 5", "[grow]", "[grow]"));

		contentPane.add(updateP, "grow, width :300:");

		int width = 460;
		// if german make it bigger
		if (mls.getLocale().equals(Locale.GERMANY))
			width = 555;
		System.out.println(width);
		setMinimumSize(new Dimension(320, width));
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
		updateB.addActionListener(l);
	}

	/**
	 * sets fast update enabled
	 * 
	 * @param en
	 *            true for enabled, else false
	 */
	public void setFastUpdateEnabled(boolean en) {
		this.fastUpdateRB.setEnabled(false);
	}

	/**
	 * sets a label which indicates if a new update using fast update is
	 * available or not
	 * 
	 * @param available
	 *            true if anew update available, else false
	 */
	public void setUpdateAvailable(boolean available) {
		if (available) {
			String str = "<font color=\"green\">(" + mls.getMessage("updateAvailable") + ")</font>";
			this.fastUpdateRB.setText("<html>" + fastUpdateLabel.replace("{0}", str) + "</html>");
		} else {
			String str = "<font color=\"red\">(" + mls.getMessage("updateNotAvailable") + ")</font>";
			this.fastUpdateRB.setText("<html>" + fastUpdateLabel.replace("{0}", str) + "</html>");
		}
	}

	/**
	 * sets the radiobutton to slow update
	 */
	public void setSlowUpdate() {
		this.slowUpdateRB.setSelected(true);
	}

	/**
	 * checks if fast update is selected
	 * 
	 * @return true for fast update, else slow is selected
	 */
	public boolean isFastUpdateSelected() {
		return this.fastUpdateRB.isSelected();
	}
}