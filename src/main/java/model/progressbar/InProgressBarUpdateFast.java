package model.progressbar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import model.progressbar.interfaces.IProgressBar;
import model.util.Util;

public class InProgressBarUpdateFast extends Thread implements IProgressBar {

	/**
	 * true if enabled, else false
	 */
	private boolean enable;
	/**
	 * delay between every dot
	 */
	private int dotDelay;
	/**
	 * delay after progressbar is stopped when it fades
	 */
	private int closeDelay;
	/**
	 * the step
	 */
	private int step;
	/**
	 * strings which should be displayed at each step
	 */
	private String[] steps;
	/**
	 * current label
	 */
	private String label;
	/**
	 * number of champions
	 */
	private int champs;
	/**
	 * number of summoner spells
	 */
	private int summoner;
	/**
	 * number of items
	 */
	private int items;

	/**
	 * creates a bar
	 * 
	 * @param champs
	 *            number of champions
	 * @param summoner
	 *            number of summoner spells
	 * @param items
	 *            number of items
	 */
	public InProgressBarUpdateFast(int champs, int summoner, int items) {
		this.dotDelay = 400;
		this.closeDelay = 200;

		this.champs = champs;
		this.summoner = summoner;
		this.items = items;
		step = 0;
		steps = new String[] { "Get Champion {0}/{1}", "Get {0} Items", "Get {0} Summoner spells" };
		label = "in progress";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		enable = true;
		final JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(250, 150));
		JLabel label = new JLabel("in progress");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(label, BorderLayout.CENTER);

		frame.setLocation(Util.getCenteredWindowCoordinates(frame));

		frame.pack();
		frame.setVisible(true);

		int dots = 0;
		while (enable) {
			label.setText(this.label + " " + makeDots(dots));
			dots++;
			dots = dots % 4;
			try {
				Thread.sleep(dotDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				frame.dispose();
			}
		}, closeDelay);
	}

	/**
	 * depending on the given number prints nothing, ., .. or ...
	 * 
	 * @param num
	 *            number of dots 0-3
	 * 
	 * @return the dot string
	 */
	private String makeDots(int num) {
		switch (num) {
			case 0:
				return "";
			case 1:
				return ".";
			case 2:
				return "..";
			case 3:
				return "...";
		}

		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.progressbar.interfaces.IProgressBar#nextStep()
	 */
	@Override
	public void nextStep() {
		step++;

		if (step <= champs) {
			label = steps[0].replace("{0}", Integer.toString(step));
			label = label.replace("{1}", Integer.toString(champs));
		} else if (step <= champs + items) {
			label = steps[1].replace("{0}", Integer.toString(items));
		} else {
			label = steps[2].replace("{0}", Integer.toString(summoner));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.progressbar.interfaces.IProgressBar#stopBar()
	 */
	@Override
	public void stopBar() {
		enable = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.progressbar.interfaces.IProgressBar#setMax(int)
	 */
	@Override
	public void setMax(int max) {
		// unused
	}
}