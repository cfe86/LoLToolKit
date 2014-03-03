package model;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.Config;
import config.ConfigReader;
import config.Constants;
import manager.CollectorManager;
import model.collector.interfaces.IDataCollector;
import model.exception.UpdateException;
import model.progressbar.interfaces.IProgressBar;
import model.structure.SoftwareUpdateData;
import model.structure.UpdateData;
import model.util.FileUtil;

public class UpdaterModel {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private UpdateData updateData;

	private SoftwareUpdateData softwareUpdateData;

	/**
	 * Constructor
	 */
	public UpdaterModel() {

	}

	/**
	 * gets all the update infos
	 * 
	 * @throws UpdateException
	 *             thrown if the update info couldn't be found
	 */
	public void getUpdateInfo() throws UpdateException {
		logger.log(Level.FINER, "get last update.");

		IDataCollector dc = CollectorManager.getInstance().getPreparedDataCollector();
		this.updateData = dc.getUpdateInfo();

		Config.getInstance().setDataUpdateLink(this.updateData.getPackageURL());
		logger.log(Level.FINER, "got last update from server: " + this.updateData.getLastUpate());
	}

	/**
	 * checks if an update is available
	 * 
	 * @return true if available, else false
	 * 
	 * @throws ParseException
	 *             thrown if a date couln't be parsed
	 */
	public boolean isUpdateAvailable() throws ParseException {
		logger.log(Level.FINER, "check if data update is available.");
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		Date localDate = df.parse(Config.getInstance().getLastUpdateDate());
		logger.log(Level.FINER, "local update: " + localDate.toString() + " server update: " + this.updateData.getLastUpdate().toString());

		return this.updateData.getLastUpdate().after(localDate);
	}

	/**
	 * performs an update
	 * 
	 * @param fastupdate
	 *            true if fast update, else false
	 * @param bp
	 *            the progressbar to use
	 * 
	 * @throws UpdateException
	 *             thrown if update couldn't be performed
	 * @throws ParseException
	 */
	public void update(boolean fastupdate, IProgressBar bp) throws UpdateException, ParseException {
		logger.log(Level.FINER, "update using fastupdate: " + fastupdate + " slowupdate: " + !fastupdate);

		bp.start();
		if (fastupdate) {
			IDataCollector dc = CollectorManager.getInstance().getPreparedDataCollector();
			dc.update(bp);
			Config.getInstance().setLastUpdateDate(this.updateData.getLastUpdate());
		} else {
			IDataCollector dc = CollectorManager.getInstance().getWebsiteDataCollector();
			dc.update(bp);
			Config.getInstance().setLastUpdateDate(new Date());
		}
	}

	/**
	 * gets all the software update infos
	 * 
	 * @throws ParseException
	 *             thrown if a date couln't be parsed
	 * @throws UpdateException
	 *             thrown if the update info couldn't be found
	 */
	public void getSoftwareUpdateInfo() throws ParseException, IOException {
		logger.log(Level.FINER, "get last update from: " + Config.getInstance().getSoftwareUpdateLink());

		URL myURL = new URL(Config.getInstance().getSoftwareUpdateLink());

		URLConnection urlCon = myURL.openConnection();
		urlCon.connect();

		BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
		String line;
		String info = "";
		while ((line = br.readLine()) != null)
			info += line + "\n";

		String updateDate = ConfigReader.findParameter(info, "lastUpdate", "=");
		String updateLink = ConfigReader.findParameter(info, "updateLink", "=");
		String kb = ConfigReader.findParameter(info, "updateSizeInKb", "=");
		String updateLinkExe = ConfigReader.findParameter(info, "updateLinkExe", "=");
		String kbExe = ConfigReader.findParameter(info, "updateSizeInKbExe", "=");
		String showMsg = ConfigReader.findParameter(info, "showMessage", "=");
		String msg = ConfigReader.findParameter(info, "message", "=");

		this.softwareUpdateData = new SoftwareUpdateData(updateDate, updateLink, kb, updateLinkExe, kbExe, showMsg.equals("1"), msg);
	}

	/**
	 * checks if a software update is available
	 * 
	 * @return true if available, else false
	 * @throws ParseException
	 *             thrown if a date couln't be parsed
	 */
	public boolean isSoftwareUpdateAvailable() throws ParseException {
		logger.log(Level.FINER, "check if software update is available.");
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		Date localDate = df.parse(Constants.LAST_SOFTWARE_UPDATE);
		logger.log(Level.FINER, "local update: " + localDate.toString() + " server update: " + this.softwareUpdateData.getUpdateDate());

		return this.softwareUpdateData.getLastUpdate().after(localDate);
	}

	/**
	 * returns true if a message is available
	 * 
	 * @return true if available, else false
	 */
	public boolean hasMessage() {
		return this.softwareUpdateData.isShowMsg();
	}

	/**
	 * gets the message
	 * 
	 * @return gets the message
	 */
	public String getMessage() {
		return this.softwareUpdateData.getMsg();
	}

	/**
	 * update the software and write updated software to the given path
	 * 
	 * @param path
	 *            given path
	 * 
	 * @throws IOException
	 *             thrown if the file couldn't be write
	 */
	public void updateSoftware(String path, String lang) throws IOException {
		// args: softwarePath, kb, lang, url
		// exe file
		if (FileUtil.getFileExtension(path).equalsIgnoreCase("exe")) {
			logger.log(Level.FINER, "update software from " + this.softwareUpdateData.getUpdateLinkExe());
			String cmd = "java -jar " + Constants.UPDATER_PATH + " \"" + path + "\" \"" + this.softwareUpdateData.getKbExe() + "\" \"" + lang + "\" \""
					+ this.softwareUpdateData.getUpdateLinkExe() + "\" \"" + path + "\"";
			logger.log(Level.FINER, "call update cmd: " + cmd);
			Runtime.getRuntime().exec(cmd);
		}
		// jar file
		else {
			logger.log(Level.FINER, "update software from " + this.softwareUpdateData.getUpdateLink());
			String cmd = "java -jar " + Constants.UPDATER_PATH + " \"" + path + "\" \"" + this.softwareUpdateData.getKb() + "\" \"" + lang + "\" \""
					+ this.softwareUpdateData.getUpdateLink() + "\" \"java -jar " + path + "\"";
			logger.log(Level.FINER, "call update cmd: " + cmd);
			Runtime.getRuntime().exec(cmd);
		}
	}

	public void writeUpdater() throws IOException {
		InputStream stream;
		stream = FileUtil.class.getResourceAsStream("/" + Constants.UPDATER_PATH_IN_JAR);
		// if this fails try again using relativ paths and ClassLoader
		if (stream == null) {
			stream = ClassLoader.getSystemResourceAsStream(Constants.UPDATER_PATH_IN_JAR);
		}

		if (stream == null)
			throw new IOException("File not found.");

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		byte[] data = new byte[4096];

		int nRead;
		while ((nRead = stream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		FileUtil.writeByteToFile(buffer.toByteArray(), Constants.UPDATER_PATH);
	}

	/**
	 * the the number of champs
	 * 
	 * @return the number of champs
	 */
	public int getChamps() {
		return this.updateData.getChamps();
	}

	/**
	 * gets the number of summoner spells
	 * 
	 * @return number of summoner spells
	 */
	public int getSummoners() {
		return this.updateData.getSummoner();
	}

	/**
	 * gets the number of items
	 * 
	 * @return number of items
	 */
	public int getItems() {
		return this.updateData.getItems();
	}
}