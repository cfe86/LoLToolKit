package config;

import model.util.FileUtil;
import model.util.PathUtil;

public class Constants {

	/**
	 * debugger on or off
	 */
	public final static boolean DEBUG = true;
	
	/**
	 * Path to the jar file if a jar, else path to working directory with an
	 * ending / This is neccessary because in some cases relative paths don't
	 * work
	 */
	private final static String JAR_PATH = FileUtil.getFilePath(PathUtil.getJarPath(Constants.class)) + "/";
	private final static String WORKSPACE_PATH = PathUtil.getWorkspacePath().replace("\\", "/");
	public final static String PATH = PathUtil.isInJar(Constants.class) ? JAR_PATH.replace("%20", " ") : WORKSPACE_PATH.replace("%20", " ");

	/**
	 * the version
	 */
	public final static String VERSION = "1.1";
	
	/**
	 * the relative config path
	 */
	public final static String CONFIG_PATH = "settings.conf";

	/**
	 * the url where the update file can be found
	 */
	public final static String UPDATE_FILE_URL = "https://docs.google.com/uc?authuser=0&id=0B_RbeehtjF99RmVyckhPYjdYR0E&export=download";
	
	/**
	 * time when the last data update is made when this software was compiled
	 * attention: this string will be overriden with the value from
	 * settings.conf and is just a default value
	 */
	public final static String LAST_DATA_UPDATE = "Wed Jan 01 00:00:01 CET 2014";


	/**
	 * the url where to software update file can be found
	 */
	public final static String SOFTWARE_UPDATE_FILE_URL = "https://docs.google.com/uc?authuser=0&id=0B_RbeehtjF99alh5NFBRazk2WEE&export=download";
	
	/**
	 * time when the last software update is made when this software was
	 * compiled attention: this string will be overriden with the value from
	 * settings.conf and is just a default value
	 */
	public final static String LAST_SOFTWARE_UPDATE = "Thu May 01 01:39:01 CET 2014";
	
	/**
	 * the path to the updater inside of the jar file
	 */
	public final static String UPDATER_PATH_IN_JAR = "data/Updater.jar";

	/**
	 * the path where to save the updater
	 */
	public final static String UPDATER_PATH = JAR_PATH + "updater.jar";
	
	/**
	 * the log path to the log.properties
	 */
	public final static String LOGPATH = "data/log.properties";

	/**
	 * paypal donation url
	 */
	public final static String donateURL = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=8FDUEPAE4FP24";

	/**
	 * min number of pixel for images
	 */
	public final static int PIXEL_LOW = 32;

	/**
	 * highest number of pixel for images
	 */
	public final static int PIXEL_HIGH = 160;

	/**
	 * nothing ID
	 */
	public final static int NOTHING = 0;

	/**
	 * Alt ID
	 */
	public final static int ALT = 1;

	/**
	 * CRTL ID
	 */
	public final static int CTRL = 2;

	/**
	 * Shift ID
	 */
	public final static int SHIFT = 3;
}