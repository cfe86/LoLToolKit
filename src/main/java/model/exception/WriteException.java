package model.exception;

public class WriteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public WriteException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            given error message
	 */
	public WriteException(String msg) {
		super(msg);
	}
}