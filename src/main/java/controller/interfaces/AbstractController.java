package controller.interfaces;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import model.exception.ControllerInitException;
import view.interfaces.AbstractTab;

abstract public class AbstractController extends Observable implements Observer, ActionListener {

	/**
	 * command to dis or enable the main window
	 */
	protected ICommandEnableWindow mainWindow;

	/**
	 * initialises the controller with the given new tab, in this method the
	 * listener and so on should be set
	 * 
	 * @param tab
	 *            the given tab panel
	 * 
	 * @throws ControllerInitException
	 *             thrown if an error during init occurs
	 */
	abstract public void init(AbstractTab tab) throws ControllerInitException;

	/**
	 * sets the command to enable or disable the main window
	 * 
	 * @param cmd
	 *            the command
	 */
	public void setMainWindowCommand(ICommandEnableWindow cmd) {
		this.mainWindow = cmd;
	}
}