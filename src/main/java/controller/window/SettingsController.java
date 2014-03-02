package controller.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import config.Config;
import controller.interfaces.ICommand;
import model.util.Util;
import view.window.SettingsView;

public class SettingsController extends Observable implements ActionListener {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	
	/**
	 * the window
	 */
	private SettingsView window;

	/**
	 * the close command which is called, when the window is closed
	 */
	private ICommand closeCommand;
	
	/**
	 * Constructor
	 */
	public SettingsController() {

	}
	
	public void setCloseCommand(ICommand cmd) {
		this.closeCommand =cmd;
	}

	/**
	 * creates the window
	 */
	public void createWindow() {
		window = new SettingsView();

		window.init();
		window.setVisible(true);
		
		window.setActionListener(this);

		window.setImageComboboxes();
		window.setPrefix();
		window.setConfig();

		window.pack();
		
		window.setLocation(Util.getCenteredWindowCoordinates(window));

		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
	}

	/**
	 * close window
	 */
	public void closeWindow() {
		closeCommand.call();
		window.dispose();
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("saveB"))
			saveButtonPressed();
		else if (e.getActionCommand().equals("cancelB"))
			cancelButtonPressed();
		else if (e.getActionCommand().equals("lolPathOpenB"))
			openLoLPathButtonPressed();
	}

	/**
	 * save button pressed
	 */
	private void saveButtonPressed() {
		logger.log(Level.FINER, "save button pressed.");
		
		try {
			this.window.saveConfig();
			Config.getInstance().writeConfig();
			closeWindow();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while writing Config file.");
			this.window.showMessage("WriteConfigError");
		}
	}

	/**
	 * cancel button pressed
	 */
	private void cancelButtonPressed() {
		logger.log(Level.FINER, "cancel button pressed.");
		closeWindow();
	}

	/**
	 * open lol path button pressed
	 */
	private void openLoLPathButtonPressed() {
		logger.log(Level.FINER, "open lol path button pressed.");
		File file = null;
		// create JFileChooser and set Filter
		JFileChooser fc = new JFileChooser();		
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnVal = fc.showOpenDialog(window);

		// get File and parse it
		if (returnVal == JFileChooser.APPROVE_OPTION)
			file = fc.getSelectedFile();
		
		if (file == null)
			return;

		Config.getInstance().setLolPathFound(new File(file.getAbsolutePath() + "/lol.launcher.exe").exists());
		this.window.setLoLPathFound(new File(file.getAbsolutePath() + "/lol.launcher.exe").exists());
		this.window.setLolPath(file.getAbsolutePath());
	}
}