package model.collector;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import config.ConfigReader;
import config.Config;
import config.Constants;
import logging.LogUtil;
import manager.ParserManager;
import model.collector.interfaces.IDataCollector;
import model.exception.UpdateException;
import model.exception.ParserException;
import model.exception.WriteException;
import model.parser.interfaces.IChampionParser;
import model.progressbar.interfaces.IProgressBar;
import model.structure.Champion;
import model.structure.UpdateData;
import model.util.FileUtil;
import model.util.Util;

public class GoogleDriveDataCollector implements IDataCollector {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * Constructor
	 */
	public GoogleDriveDataCollector() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.collector.interfaces.IDataCollector#update()
	 */
	@Override
	public void update() throws UpdateException {
		update(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.collector.interfaces.IDataCollector#update()
	 */
	@Override
	public void update(IProgressBar progressbar) throws UpdateException {
		String url = Config.getInstance().getDataUpdateLink();
		logger.log(Level.FINER, "call URL to get latest data: " + url);

		try {
			URL myURL = new URL(url);

			URLConnection urlCon = myURL.openConnection();
			urlCon.connect();

			byte[] buffer = new byte[1024];
			// get the zip file content
			ZipInputStream zis = new ZipInputStream(urlCon.getInputStream());

			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			if (progressbar != null)
				progressbar.nextStep();

			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(Constants.PATH + fileName);

				Util.createFoldAndFiles(newFile.getAbsolutePath().replace("\\", "/").replace(Constants.PATH, ""));

				String ext = FileUtil.getFileExtension(newFile.getAbsolutePath());
				// if it is the champion xml file
				if (ext != null && newFile.getAbsolutePath().endsWith(Config.getInstance().getChampionFile())) {
					IChampionParser p = ParserManager.getInstance().getChampionParser();

					// create new inputstream and parse champion
					ByteArrayOutputStream bos = new ByteArrayOutputStream();

					int len;
					while ((len = zis.read(buffer)) > 0) {
						bos.write(buffer, 0, len);
					}

					p.parse(new ByteArrayInputStream(bos.toByteArray()));

					Champion newChamp = p.getChampion();
					// if file exists parse it and merge
					logger.log(Level.FINER, "champion.xml for: " + newFile.getAbsolutePath() + " exists: " + newFile.exists());
					if (newFile.exists()) {
						try {
							p = ParserManager.getInstance().getChampionParser();
							p.parse(newFile.getAbsolutePath());
							newChamp.merge(p.getChampion());
						} catch (ParserException e) {
							logger.log(Level.SEVERE, "local champion.xml seems to be corrupted. will be overriden.");
						}

					}

					ParserManager.getInstance().getWriter().writeChampion(newChamp, newFile.getAbsolutePath());
					if (progressbar != null)
						progressbar.nextStep();
				}
				// if not write file, except for Thumbs.db files
				else if (ext != null && !newFile.getAbsolutePath().endsWith("Thumbs.db")) {
					logger.log(Level.FINER, "write file: " + newFile.getAbsolutePath());
					FileOutputStream fos = new FileOutputStream(newFile);

					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}

					fos.close();
				}

				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();
		} catch (IOException | WriteException | ParserException e) {
			logger.log(Level.SEVERE, "Error while updating data using GoogleDrive:\n" + LogUtil.getStackTrace(e), e);
			throw new UpdateException("Error while updating data using GoogleDrive");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.collector.interfaces.IDataCollector#getLastUpdated()
	 */
	@Override
	public UpdateData getUpdateInfo() throws UpdateException {
		String url = Config.getInstance().getUpdateInfoLink();
		logger.log(Level.FINER, "call URL to get latest update: " + url);

		try {
			URL myURL = new URL(url);
			URLConnection urlCon = myURL.openConnection();
			urlCon.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String line;
			String info = "";
			while ((line = br.readLine()) != null)
				info += line + "\n";

			String date = ConfigReader.findParameter(info, "date", "=");
			int champs = Integer.parseInt(ConfigReader.findParameter(info, "Champs", "="));
			int summoner = Integer.parseInt(ConfigReader.findParameter(info, "summoner", "="));
			int items = Integer.parseInt(ConfigReader.findParameter(info, "items", "="));
			String packageURL = ConfigReader.findParameter(info, "packageURL", "=");
			return new UpdateData(date, champs, summoner, items, packageURL);

		} catch (IOException e) {
			logger.log(Level.FINER, "Error while getting last update String:\n" + LogUtil.getStackTrace(e), e);
			throw new UpdateException("Error while getting last update String.");
		}
	}
}