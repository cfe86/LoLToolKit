package model.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.text.html.HTMLDocument;

import config.Config;
import config.Constants;

public class Util {

	/**
	 * calculates the coordinates of the left top corner of the given window to
	 * center it depending on the current solution
	 * 
	 * @param window
	 *            given window
	 * 
	 * @return centered coordinates
	 */
	public static Point getCenteredWindowCoordinates(JFrame window) {
		Point result = new Point();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();

		result.setLocation(dimension.width / 2 - window.getWidth() / 2, dimension.height / 2 - window.getHeight() / 2);

		return result;
	}

	/**
	 * sets a font to an EditorPane. Fontsize is defined in the Config.
	 * 
	 * @param pane
	 *            the editor pane
	 */
	public static void setFontToEP(JEditorPane pane) {
		Font font = new Font(pane.getFont().getFamily(), Font.PLAIN, Config.getInstance().getFontSize());
		String bodyRule = "body { font-family: " + font.getFamily() + "; " + "font-size: " + font.getSize() + "pt; }";
		((HTMLDocument) pane.getDocument()).getStyleSheet().addRule(bodyRule);
	}

	/**
	 * checks if all folders and files exists, if not creats them
	 * 
	 * @param path
	 *            given filepath in relative form
	 * 
	 * @throws IOException
	 */
	public static void createFoldAndFiles(String path) throws IOException {
		final Logger logger = Logger.getLogger(Util.class.getName());
		logger.log(Level.FINER, "create files for path: " + path);
		if (path.charAt(1) == ':')
			path = path.substring(3);
		else if (path.startsWith("/") || path.startsWith("\\"))
			path = path.substring(1);

		path = path.replace("\\", "/");

		String[] tmp = path.split("/");
		String folder = Constants.PATH;
		for (int i = 0; i < tmp.length; i++) {
			folder += ((i != 0) ? "/" : "") + tmp[i];

			if (!new File(folder).exists()) {
				if (FileUtil.getFileExtension(folder) != null) {
					logger.log(Level.FINER, "create file: " + folder + " created: " + new File(folder).createNewFile());
				} else {
					logger.log(Level.FINER, "create folder: " + folder + " created: " + new File(folder).mkdir());
				}
			}
		}
	}
}