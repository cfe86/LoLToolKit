package logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import config.Constants;

public class LogUtil {

	/**
	 * gets the stack trace of the given exception as a string
	 * 
	 * @param e
	 *            given exception
	 * 
	 * @return the stack trace
	 */
	public static String getStackTrace(Exception e) {
		StringBuffer result = new StringBuffer();

		StackTraceElement[] ele = e.getStackTrace();
		result.append(e.toString() + "\n");
		for (int i = 0; i < ele.length; i++)
			result.append(ele[i].toString() + "\n");

		return result.toString();
	}

	/**
	 * inits logging
	 * 
	 * @throws SecurityException
	 *             thrown if logger isn't allowed to use
	 * @throws IOException
	 *             thrown if log.properties couldn't be read
	 */
	public static void initLogging() throws SecurityException, IOException {
		InputStream is = null;
		is = LogUtil.class.getResourceAsStream("/" + Constants.LOGPATH);
		if (is == null) {
			is = ClassLoader.getSystemResourceAsStream(Constants.LOGPATH);
		}

		LogManager.getLogManager().readConfiguration(is);
	}

	/**
	 * inits logging depending on if this is a JApplet or not
	 * 
	 * @param inBrowser
	 *            true if applet
	 * 
	 * @throws SecurityException
	 *             thrown if logger isn't allowed to use
	 * @throws IOException
	 *             thrown if log.properties couldn't be read
	 */
	public static void initLogging(boolean inBrowser) throws SecurityException, IOException {
		if (!inBrowser)
			initLogging();
		else {
			LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME).setLevel(Level.FINEST);
		}
	}

	/**
	 * disables logging
	 */
	public static void disableLogging() {
		LogManager.getLogManager().reset();
		LogManager.getLogManager().getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME).setLevel(Level.OFF);
	}
}
