package model.progressbar.interfaces;

public interface IProgressBar {

	/**
	 * stops the progressbar
	 */
	public void stopBar();

	/**
	 * starts the progress bar thread
	 */
	public void start();

	/**
	 * called when the next step should be used
	 */
	public void nextStep();

	/**
	 * sets the max value of something
	 * 
	 * @param max
	 *            the value
	 */
	public void setMax(int max);
}
