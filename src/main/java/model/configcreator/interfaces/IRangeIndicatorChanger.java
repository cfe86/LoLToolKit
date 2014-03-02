package model.configcreator.interfaces;

import model.exception.ConfigCreatorException;

public interface IRangeIndicatorChanger {

	/**
	 * updates the game.cfg to set the range indicator
	 * 
	 * @param indicatorEnabled
	 *            true if set to 1, else to 0
	 * 
	 * @throws ConfigCreatorException
	 *             thrown if range indicator couldn't be set
	 */
	public void update(boolean indicatorEnabled) throws ConfigCreatorException;
}