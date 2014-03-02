package model.structure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateData {

	/**
	 * date string which encodes the last update which is on the server
	 */
	private String lastUpate;
	/**
	 * number of champions
	 */
	private int champs;
	/**
	 * number of summonerspells
	 */
	private int summoner;
	/**
	 * number of items
	 */
	private int items;
	/**
	 * url where to find the data update package
	 */
	private String packageURL;

	/**
	 * Constructor
	 * 
	 * @param lastUpdate
	 *            date string which encodes the last update which is on the
	 *            server
	 * @param champs
	 *            number of champions
	 * @param summoner
	 *            number of summonerspells
	 * @param items
	 *            number of items
	 * @param packageURL
	 *            url where to find the data update package
	 */
	public UpdateData(String lastUpdate, int champs, int summoner, int items, String packageURL) {
		Logger.getLogger(this.getClass().getName()).log(
				Level.FINER,
				"got update data. last update: " + lastUpdate + " champs: " + champs + " summoner:" + summoner + " items:  " + items + " packageURL: " + packageURL);
		this.lastUpate = lastUpdate;
		this.champs = champs;
		this.summoner = summoner;
		this.items = items;
		this.packageURL = packageURL;
	}

	public String getLastUpate() {
		return lastUpate;
	}
	
	public Date getLastUpdate() throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
		return df.parse(this.lastUpate);
	}

	public String getPackageURL() {
		return packageURL;
	}

	public int getChamps() {
		return champs;
	}

	public int getSummoner() {
		return summoner;
	}

	public int getItems() {
		return items;
	}
}