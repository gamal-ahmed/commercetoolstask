package de.task.utils;

/**
 * this class contains system properties which will not change in the run time
 */
public class Properties {

	/**
	 * this attribute is the maximum number of attempts to reconnect with the
	 * server if uploading failed
	 */
	public final static int MAX_TRIES = 3;
	/**
	 * this attribute control if the file is very large to limit the loaded
	 * elements in memory and not send huge data to the server to avoid server
	 * failing response
	 */
	public final static int MEMORY_MAX_LIMIT = 500;
	/**
	 * CSV file path
	 */
	public final static String CSV_FILE_PATH = "E:/customers.csv";
	/**
	 * senderEmail
	 */
	public final static String SENDER_EMAIL = "";

}
