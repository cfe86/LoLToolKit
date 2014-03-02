package model.exception;

public class UpdateRequiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6399619656490507170L;

	/**
	 * Constructor
	 */
	public UpdateRequiredException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param msg
	 *            given error message
	 */
	public UpdateRequiredException(String msg) {
		super(msg);
	}
}