package model.exception;

public class ConfigCreatorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -744297459407847363L;

	/**
	 * Constructor
	 */
	public ConfigCreatorException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            given error message
	 */
	public ConfigCreatorException(String msg) {
		super(msg);
	}
}