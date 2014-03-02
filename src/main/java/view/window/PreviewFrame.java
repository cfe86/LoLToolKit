package view.window;

import java.awt.Dimension;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.util.Util;
import net.miginfocom.swing.MigLayout;

import com.cf.mls.MLS;

import config.Config;

public class PreviewFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8115894745694371355L;

	/**
	 * the multi language supporter
	 */
	private MLS mls;
	/**
	 * the contentPane
	 */
	private JPanel contentPane;

	private JEditorPane dataEP;

	/**
	 * Constructor
	 */
	public PreviewFrame() {
		mls = new MLS("languagefiles/PreviewFrame", Config.getInstance().getCurrentLanguage());
		mls.setToolTipDuration(-1);
	}

	/**
	 * inits the window
	 * 
	 * @param htmlCode
	 *            html code to be shown
	 * @param width
	 *            the window width
	 * @param height
	 *            the window height
	 */
	public void init(String htmlCode, int width, int height) {

		mls.addJFrame("window", this);
		contentPane = new JPanel();

		setContentPane(contentPane);

		dataEP = mls.generateJEditorPane("dataEP", true, false, "", "text/html");
		Util.setFontToEP(dataEP);

		JScrollPane sp = new JScrollPane();
		sp.setViewportView(dataEP);
		contentPane.setLayout(new MigLayout("insets 0", "[grow]", "[grow]"));

		contentPane.add(sp, "grow");
		
		dataEP.setText(htmlCode);
		setPreferredSize(new Dimension(width, height));
		pack();

		setLocation(Util.getCenteredWindowCoordinates(this));
	}
}