package logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;

public class TextAreaHandler extends Handler {

	private final static int MAX_LINES = 1000;
	
	/**
	 * the textarea where to output the debug log, only one textarea can be set
	 */
	private static JTextArea ta = null;

	/**
	 * sets the output textarea
	 * 
	 * @param textarea
	 *            the textarea
	 */
	public static void setTextArea(JTextArea textarea) {
		ta = textarea;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#close()
	 */
	@Override
	public void close() throws SecurityException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#flush()
	 */
	@Override
	public void flush() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 */
	@Override
	public void publish(LogRecord rec) {
		if (ta == null)
			return;

		if (getFormatter() == null)
			setFormatter(new LogFormatter());

		String message = getFormatter().format(rec);

		// clean debug box 
		if (ta.getText().split("\n").length > MAX_LINES)
			ta.setText("");
		
		ta.append(message);
	}

}
