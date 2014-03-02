package view.interfaces;

import java.util.Locale;

import javax.swing.JPanel;

import model.exception.TabInitException;

abstract public class AbstractTab extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8332877415008979092L;

	
	abstract public void init() throws TabInitException;
	
	abstract public void changeLanguage(Locale lang);
}