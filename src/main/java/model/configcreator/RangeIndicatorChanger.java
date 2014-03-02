package model.configcreator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.Config;
import logging.LogUtil;
import model.configcreator.interfaces.IRangeIndicatorChanger;
import model.exception.ConfigCreatorException;

public class RangeIndicatorChanger implements IRangeIndicatorChanger {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the indicator String which has to be set to 1 or 0
	 */
	private final String INDICATOR_STRING = "SmartCastOnKeyRelease";

	/**
	 * Constructor
	 */
	public RangeIndicatorChanger() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * model.configcreator.interfaces.IRangeIndicatorChanger#update(boolean)
	 */
	@Override
	public void update(boolean indicatorEnabled) throws ConfigCreatorException {
		String path = Config.getInstance().getLolPath() + Config.getInstance().getGameCfg();
		logger.log(Level.FINER, "update game.cfg at " + path + " exists: " + new File(path).exists());

		if (!new File(path).exists())
			return;

		try {
			// read game.cfg
			BufferedReader br = new BufferedReader(new FileReader(new File(path)));

			String line;
			String cfg = "";
			while ((line = br.readLine()) != null) {
				// if line is found change it, if not just save line
				if (line.startsWith(INDICATOR_STRING))
					cfg += INDICATOR_STRING + "=" + (indicatorEnabled ? "1" : "0") + "\n";
				else
					cfg += line.trim() + "\n";
			}
			br.close();
			cfg = cfg.trim();

			// write
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
			bw.write(cfg);
			bw.close();

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error while changing game.cfg:\n" + LogUtil.getStackTrace(e), e);
			throw new ConfigCreatorException("Error while changing game.cfg");
		}
	}
}