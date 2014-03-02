package model.exception;

public class ConverterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1425224479783967112L;

	/**
	 * Constructor
	 */
	public ConverterException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            given error message
	 */
	public ConverterException(String msg) {
		super(msg);
	}
}