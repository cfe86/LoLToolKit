package controller.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import controller.interfaces.ICommand;
import logging.LogUtil;
import model.UpdaterModel;
import model.exception.UpdateException;
import model.progressbar.InProgressBar;
import model.progressbar.InProgressBarUpdateFast;
import model.progressbar.InProgressBarUpdateSlow;
import model.progressbar.interfaces.IProgressBar;
import model.util.Util;
import view.window.UpdateView;

public class UpdaterController extends Observable implements ActionListener {

	/**
	 * the logger
	 */
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * the window
	 */
	private UpdateView window;
	/**
	 * the model
	 */
	private UpdaterModel model;
	
	/**
	 * called when the window is closed
	 */
	private ICommand closeCmd;

	/**
	 * Constructor
	 */
	public UpdaterController() {
		this.model = new UpdaterModel();
	}
	
	public void setCloseCmd(ICommand cmd) {
		this.closeCmd = cmd;
	}

	/**
	 * creates the window
	 * 
	 * @param checkUpdateAvailable
	 *            true if a check should be performed, else false
	 */
	public void createWindow(final boolean checkUpdateAvailable) {
		window = new UpdateView();
		window.init();
		window.setVisible(true);

		window.setActionListener(this);

		window.setLocation(Util.getCenteredWindowCoordinates(window));

		window.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});

		new Thread(new Runnable() {

			@Override
			public void run() {
				IProgressBar pb = new InProgressBar(new String[] { "get last update", "set update" }, 400, 200);
				try {
					pb.start();
					model.getUpdateInfo();
					pb.nextStep();
					if (!checkUpdateAvailable)
						window.setUpdateAvailable(true);
					else
						window.setUpdateAvailable(model.isUpdateAvailable());
				} catch (ParseException | UpdateException | ClassCastException e) {
					logger.log(Level.SEVERE, "update while getting last update date:\n" + LogUtil.getStackTrace(e), e);
					window.setFastUpdateEnabled(false);
					window.showMessage("fastUpdateUnavailable");
					window.setUpdateAvailable(false);
					window.setSlowUpdate();
				}

				pb.stopBar();
			}
		}).start();
	}

	/**
	 * close window
	 */
	public void closeWindow() {
		closeCmd.call();
		window.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("updateB"))
			updateButtonPressed();

	}

	/**
	 * update button pressed
	 */
	private void updateButtonPressed() {
		logger.log(Level.FINER, "update button pressed.");

		new Thread(new Runnable() {

			@Override
			public void run() {
				IProgressBar bp = null;
				try {
					if (window.isFastUpdateSelected())
						bp = new InProgressBarUpdateFast(model.getChamps(), model.getSummoners(), model.getItems());
					else
						bp = new InProgressBarUpdateSlow();

					model.update(window.isFastUpdateSelected(), bp);

					bp.stopBar();

					window.showMessage("updateSuccessful");

					window.setUpdateAvailable(false);
				} catch (UpdateException | ParseException e) {
					logger.log(Level.FINER, "Error while updating:\n" + LogUtil.getStackTrace(e), e);
					if (bp != null)
						bp.stopBar();
					window.showMessage("ErrorWhileUpdating");
					return;
				}
			}
		}).start();
	}
}
