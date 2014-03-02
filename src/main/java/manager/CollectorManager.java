package manager;

import model.collector.GoogleDriveDataCollector;
import model.collector.WebsiteDataCollector;
import model.collector.interfaces.IDataCollector;

public class CollectorManager {

	/**
	 * the instance
	 */
	private static CollectorManager instance;
	
	/**
	 * gets the instance
	 * 
	 * @return the instance
	 */
	public static CollectorManager getInstance() {
		if (instance == null)
			instance = new CollectorManager();
		
		return instance;
	}
	
	/**
	 * Constructor
	 */
	private CollectorManager() {
		
	}
	
	/**
	 * Gets the Website Datacollector
	 * 
	 * @return the collector
	 */
	public IDataCollector getWebsiteDataCollector() {
		return new WebsiteDataCollector();
	}

	/**
	 * Gets the prepared Datacollector
	 * 
	 * @return the collector
	 */
	public IDataCollector getPreparedDataCollector() {
		return new GoogleDriveDataCollector();
	}
}