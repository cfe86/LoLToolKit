package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logging.LogUtil;
import manager.TabManager;
import manager.structure.Controller;
import model.MainModel;
import model.UpdaterModel;
import model.exception.ControllerInitException;
import model.exception.TabInitException;
import model.exception.UpdateException;
import model.exception.ParserException;
import model.exception.WriteException;
import model.progressbar.InProgressBar;
import model.progressbar.interfaces.IProgressBar;
import model.util.PathUtil;
import model.util.Util;
import view.MainView;
import view.window.AboutView;
import view.window.DebugFrame;
import view.window.LicenseView;
import config.Config;
import config.Constants;
import controller.interfaces.ICommand;
import controller.interfaces.ICommandEnableWindow;
import controller.window.SettingsController;
import controller.window.UpdaterController;

public class MainController implements ActionListener, Observer {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the window
	 */
	private MainView window;
	/**
	 * the model
	 */
	private MainModel model;

	/**
	 * the controllers for the tabs
	 */
	private Map<String, Controller> controller;

	private boolean needUpdate;

	/**
	 * constructor
	 */
	public MainController() {
		model = new MainModel();
		controller = TabManager.getInstance().getController();
		needUpdate = false;
	}

	/**
	 * create window
	 */
	public void createWindow() {
		window = new MainView(Config.getInstance().getCurrentLanguage());

		try {
			window.init();
		} catch (IOException | TabInitException e) {
			logger.log(Level.SEVERE, "Error while init main window:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("imageNotFound");
		}

		window.setVisible(true);

		window.setActionListener(this);

		this.window.setLocation(Util.getCenteredWindowCoordinates(this.window));

		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});

		// init tabs
		for (Controller con : this.controller.values()) {
			try {
				// add maincontroller as observer is needed
				if (con.observeMain())
					con.getController().addObserver(this);

				// add enable or disable command
				con.getController().setMainWindowCommand(new ICommandEnableWindow() {

					@Override
					public void setWindowEnabled(boolean en) {
						window.setEnabled(en);
						if (en)
							window.toFront();
					}
				});
				
				// init controller
				con.getController().init(window.getTab(con.getTabIndex()));
			} catch (ControllerInitException e) {
				logger.log(Level.SEVERE, "Error while init Controller:\n" + LogUtil.getStackTrace(e), e);
				window.showMessage("InitError");
			}
		}

		// signal all panels that a champion is selected
		((SmartcastController) controller.get("smartcastTab").getController()).nameCBChanged();

		// check for new data and software updates
		new Thread(new Runnable() {

			@Override
			public void run() {
				// check for updates
				try {
					IProgressBar pb = new InProgressBar(new String[] { "checking for updates" }, 400, 200);
					pb.start();
					UpdaterModel u = new UpdaterModel();
					
					// software update
					u.getSoftwareUpdateInfo();
					
					if (u.isSoftwareUpdateAvailable()) {
						int r = window.showConfirmationMessage("softwareUpdateAvaiable");

						if (r == JOptionPane.YES_OPTION) {
							pb.stopBar();
							u.writeUpdater();
							u.updateSoftware(PathUtil.getJarPath(MainController.class), window.getCurrentLanguage().toString());
							System.exit(1);
						}
					}
						
					// data update
					u.getUpdateInfo();
					boolean update = u.isUpdateAvailable() || needUpdate;
					pb.stopBar();

					if (update) {
						int r = window.showConfirmationMessage("updateAvailable");

						if (r == JOptionPane.YES_OPTION)
							updateMI(false);
					}

				} catch (UpdateException | ParseException | IOException e) {
					logger.log(Level.SEVERE, "Error while updating:\n" + LogUtil.getStackTrace(e), e);
					window.showMessage("noInternet");
				}
			}
		}).start();
	}

	/**
	 * close window
	 */
	public void closeWindow() {
		try {
			Config.getInstance().setCurrDimension(window.getCurrentSize());
			Config.getInstance().writeConfig();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error while writing config:\n" + LogUtil.getStackTrace(e), e);
			int r = window.showConfirmationMessage("writeError");
			if (r != JOptionPane.YES_OPTION)
				return;
		}

		System.exit(1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable obs, Object obj) {
		logger.log(Level.FINER, "Got notification from " + obs.getClass().getName());

		// from smartcast con
		if (obs.getClass().getName().equals(controller.SmartcastController.class.getName())) {
			// check if smartcast sends the string "update"
			if (!(obj instanceof String))
				return;
			if (!((String) obj).equalsIgnoreCase("update"))
				return;

			this.needUpdate = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("hifiLookAndFeelMI"))
			hifiMI();
		else if (e.getActionCommand().equals("acrylLookAndFeelMI"))
			acrylMI();
		else if (e.getActionCommand().equals("aeroLookAndFeelMI"))
			aeroMI();
		else if (e.getActionCommand().equals("aluminiumLookAndFeelMI"))
			aluminiumMI();
		else if (e.getActionCommand().equals("bernsteinLookAndFeelMI"))
			bernsteinMI();
		else if (e.getActionCommand().equals("fastLookAndFeelMI"))
			fastMI();
		else if (e.getActionCommand().equals("graphiteLookAndFeelMI"))
			graphiteMI();
		else if (e.getActionCommand().equals("lunaLookAndFeelMI"))
			lunaMI();
		else if (e.getActionCommand().equals("mcWinLookAndFeelMI"))
			mcWinMI();
		else if (e.getActionCommand().equals("mintLookAndFeelMI"))
			mintMI();
		else if (e.getActionCommand().equals("noireLookAndFeelMI"))
			noireMI();
		else if (e.getActionCommand().equals("smartLookAndFeelMI"))
			smartMI();
		else if (e.getActionCommand().equals("textureLookAndFeelMI"))
			textureMI();
		else if (e.getActionCommand().equals("nimbusMI"))
			nimbusMI();
		else if (e.getActionCommand().equals("exitMI"))
			exitMI();
		else if (e.getActionCommand().equals("settingsMI"))
			settingsMI();
		else if (e.getActionCommand().equals("updateMI"))
			updateMI(true);
		else if (e.getActionCommand().equals("importMI"))
			importMI();
		else if (e.getActionCommand().equals("exportMI"))
			exportMI();
		else if (e.getActionCommand().equals("aboutMI"))
			aboutMI();
		else if (e.getActionCommand().equals("donateMI"))
			donateMI();
		else if (e.getActionCommand().equals("licensesMI"))
			licensesMI();
		else if (e.getActionCommand().equals("debugMI"))
			debugMI();
	}

	/**
	 * nimbus MenuItem
	 */
	private void nimbusMI() {
		logger.log(Level.FINER, "changed Skin to Nimbus.");
		com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default");
		changeSkin("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	}

	/**
	 * hifi MenuItem
	 */
	private void hifiMI() {
		logger.log(Level.FINER, "changed Skin to Hifi.");
		com.jtattoo.plaf.hifi.HiFiLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
	}

	/**
	 * acryl MenuItem
	 */
	private void acrylMI() {
		logger.log(Level.FINER, "changed Skin to Acryl.");
		com.jtattoo.plaf.acryl.AcrylLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
	}

	/**
	 * aero MenuItem
	 */
	private void aeroMI() {
		logger.log(Level.FINER, "changed Skin to Aero.");
		com.jtattoo.plaf.aero.AeroLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.aero.AeroLookAndFeel");
	}

	/**
	 * aluminium MenuItem
	 */
	private void aluminiumMI() {
		logger.log(Level.FINER, "changed Skin to Aluminium.");
		com.jtattoo.plaf.aluminium.AluminiumLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
	}

	/**
	 * bernstein MenuItem
	 */
	private void bernsteinMI() {
		logger.log(Level.FINER, "changed Skin to Bernstein.");
		com.jtattoo.plaf.bernstein.BernsteinLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
	}

	/**
	 * fast MenuItem
	 */
	private void fastMI() {
		logger.log(Level.FINER, "changed Skin to Fast.");
		com.jtattoo.plaf.fast.FastLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.fast.FastLookAndFeel");
	}

	/**
	 * graphite MenuItem
	 */
	private void graphiteMI() {
		logger.log(Level.FINER, "changed Skin to Graphite.");
		com.jtattoo.plaf.graphite.GraphiteLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
	}

	/**
	 * luna MenuItem
	 */
	private void lunaMI() {
		logger.log(Level.FINER, "changed Skin to Luna.");
		com.jtattoo.plaf.luna.LunaLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.luna.LunaLookAndFeel");
	}

	/**
	 * mcWin MenuItem
	 */
	private void mcWinMI() {
		logger.log(Level.FINER, "changed Skin to McWin.");
		com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
	}

	/**
	 * Mint MenuItem
	 */
	private void mintMI() {
		logger.log(Level.FINER, "changed Skin to Mint.");
		com.jtattoo.plaf.mint.MintLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.mint.MintLookAndFeel");
	}

	/**
	 * Noire MenuItem
	 */
	private void noireMI() {
		logger.log(Level.FINER, "changed Skin to Noire.");
		com.jtattoo.plaf.noire.NoireLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.noire.NoireLookAndFeel");
	}

	/**
	 * Smart MenuItem
	 */
	private void smartMI() {
		logger.log(Level.FINER, "changed Skin to Smart.");
		com.jtattoo.plaf.smart.SmartLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.smart.SmartLookAndFeel");
	}

	/**
	 * Texture MenuItem
	 */
	private void textureMI() {
		logger.log(Level.FINER, "changed Skin to Texture.");
		com.jtattoo.plaf.texture.TextureLookAndFeel.setTheme("Default");
		changeSkin("com.jtattoo.plaf.texture.TextureLookAndFeel");
	}

	/**
	 * Exit MenuItem
	 */
	private void exitMI() {
		logger.log(Level.FINER, "exit pressed.");
		closeWindow();
	}

	/**
	 * Settings MenuItem
	 */
	private void settingsMI() {
		logger.log(Level.FINER, "settings pressed.");

		this.window.setEnabled(false);
		SettingsController con = new SettingsController();
		con.setCloseCommand(new ICommand() {

			@Override
			public void call() {
				window.setEnabled(true);
				((SmartcastController) controller.get("smartcastTab").getController()).checkChooseButton();
			}
		});
		con.createWindow();
	}

	/**
	 * Update MenuItem
	 * 
	 * @param checkUpdateAvailable
	 *            true if an update check should be performed, else false
	 */
	private void updateMI(boolean checkUpdateAvailable) {
		logger.log(Level.FINER, "update pressed.");

		this.window.setEnabled(false);
		UpdaterController con = new UpdaterController();
		con.setCloseCmd(new ICommand() {

			@Override
			public void call() {
				window.setEnabled(true);

				try {
					// init smartcast view new and refresh the gui
					controller.get("smartcastTab").getController().init(window.getTab(controller.get("smartcastTab").getTabIndex()));
					((SmartcastController) controller.get("smartcastTab").getController()).nameCBChanged();
				} catch (ControllerInitException e) {
					logger.log(Level.SEVERE, "Error while loading champ. Maybe update required:\n" + LogUtil.getStackTrace(e), e);
					window.showMessage("noUpdateMade");
				}
			}
		});
		con.createWindow(checkUpdateAvailable);
	}

	/**
	 * Import MenuItem
	 */
	private void importMI() {
		logger.log(Level.FINER, "import pressed.");

		new Thread(new Runnable() {
			@Override
			public void run() {
				File file = null;
				// create JFileChooser
				JFileChooser fc = new JFileChooser();

				int returnVal = fc.showOpenDialog(window);

				// get File and parse it
				if (returnVal == JFileChooser.APPROVE_OPTION)
					file = fc.getSelectedFile();

				if (file == null)
					return;

				try {
					IProgressBar pb = new InProgressBar();
					pb.start();
					model.importChampions(file.getAbsolutePath());
					pb.stopBar();
					// call the smartcast controller to renew gui
					window.showMessage("importSuccessful");
					((SmartcastController) controller.get("smartcastTab").getController()).nameCBChanged();
				} catch (ParserException | IOException e) {
					logger.log(Level.SEVERE, "Error while importing data:\n" + LogUtil.getStackTrace(e), e);
					window.showMessage("importError");
				} catch (WriteException e) {
					logger.log(Level.SEVERE, "Error while writing imported data:\n" + LogUtil.getStackTrace(e), e);
					window.showMessage("importWriteError");
				}
			}
		}).start();
	}

	/**
	 * Export MenuItem
	 */
	private void exportMI() {
		logger.log(Level.FINER, "export pressed.");
		new Thread(new Runnable() {
			@Override
			public void run() {
				File file = null;
				// create JFileChooser
				JFileChooser fc = new JFileChooser();

				int returnVal = fc.showOpenDialog(window);

				// get File and parse it
				if (returnVal == JFileChooser.APPROVE_OPTION)
					file = fc.getSelectedFile();

				if (file == null)
					return;

				try {
					IProgressBar pb = new InProgressBar();
					pb.start();
					model.exportChampions(file.getAbsolutePath());
					pb.stopBar();
					window.showMessage("exportSuccessful");
				} catch (ParserException | WriteException e) {
					logger.log(Level.SEVERE, "Error while exporting data:\n" + LogUtil.getStackTrace(e), e);
					window.showMessage("exportError");
				}
			}
		}).start();
	}

	/**
	 * Donate MenuItem
	 */
	private void donateMI() {
		logger.log(Level.FINER, "donate pressed.");
		try {
			int r = window.showConfirmationMessage("openDonationURL");
			if (r != JOptionPane.YES_OPTION)
				return;

			java.awt.Desktop.getDesktop().browse(java.net.URI.create(Constants.donateURL));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while opening url:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("urlopenError");
		}
	}

	/**
	 * About MenuItem
	 */
	private void aboutMI() {
		logger.log(Level.FINER, "about pressed.");

		AboutView v = new AboutView();
		v.init();
		v.setLocation(Util.getCenteredWindowCoordinates(v));
		v.setVisible(true);
	}

	/**
	 * License MenuItem
	 */
	private void licensesMI() {
		logger.log(Level.FINER, "licenses pressed.");

		try {
			LicenseView v = new LicenseView();
			v.init();
			v.setLocation(Util.getCenteredWindowCoordinates(v));
			v.setVisible(true);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while opening license window:\n" + LogUtil.getStackTrace(e), e);
			window.showMessage("licenseError");
		}
	}

	/**
	 * Debug MenuItem
	 */
	private void debugMI() {
		logger.log(Level.FINER, "Debug pressed.");
		try {
			DebugFrame w = new DebugFrame();
			w.init();
			w.setVisible(true);
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, "tried to open debug console twice.");
		}
	}

	/**
	 * Changes the skin
	 * 
	 * @param skin
	 *            skin path
	 */
	private void changeSkin(String skin) {
		logger.log(Level.FINER, "changed Skin: " + skin);
		try {
			Config.getInstance().setcurrentSkin(skin);
			UIManager.setLookAndFeel(skin);
			SwingUtilities.updateComponentTreeUI(window);
			window.pack();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			logger.log(Level.SEVERE, "Error while changing skin:\n" + LogUtil.getStackTrace(e), e);
		}
	}
}