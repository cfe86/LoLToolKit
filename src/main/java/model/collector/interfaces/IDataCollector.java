package model.collector.interfaces;

import model.exception.UpdateException;
import model.progressbar.interfaces.IProgressBar;
import model.structure.UpdateData;

public interface IDataCollector {

	/**
	 * updates the neccessary data
	 * 
	 * @throws UpdateException
	 *             thrown if something went wrong
	 */
	public void update() throws UpdateException;

	/**
	 * updates the neccessary data
	 * 
	 * @param progressbar
	 *            a given progressbar
	 * 
	 * @throws UpdateException
	 *             thrown if something went wrong
	 */
	public void update(IProgressBar progressbar) throws UpdateException;

	/**
	 * gets an object with necessary update data
	 * 
	 * @return the update data
	 * 
	 * @throws UpdateException
	 *             thrown if something went wrong
	 */
	public UpdateData getUpdateInfo() throws UpdateException;
}
