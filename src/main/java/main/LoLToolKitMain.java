package main;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import logging.LogUtil;
import config.Config;
import config.Constants;
import controller.MainController;

public class LoLToolKitMain {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SecurityException,
			IOException {

		if (Constants.DEBUG)
			LogUtil.initLogging();
		else
			LogUtil.disableLogging();

		Config.init();

		UIManager.setLookAndFeel(Config.getInstance().getCurrentSkin());

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				MainController con = new MainController();
				con.createWindow();
			}
		});
	}
}