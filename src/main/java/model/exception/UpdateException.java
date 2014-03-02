package model.exception;

public class UpdateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8782196573108479098L;

	/**
	 * Constructor
	 */
	public UpdateException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            given error message
	 */
	public UpdateException(String msg) {
		super(msg);
	}
}