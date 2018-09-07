package com.ieli.tieasy.util.logs;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogPackager {

	/**
	 * Run with Administrator powers to get all logs!
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.printf("Logs saved to %s", new LogPackager().packageLog("C:/Users/Reemo/Desktop/temp/Logs", "C:/Users/Reemo/Desktop/temp/Logs/logs.zip"));
	}

	public File packageLog(String logFolder, String path) {
		// Array containing interested loggers
		String[] winLogger = { "Application", "Security", "System", "Setup", "ForwardedEvents" };

		// Start date for search --> FORMAT: yyyy-mm-ddThh:mm:ss
		String dateStart = "2018-05-12T08:50:000";
		// End date for search --> FORMAT: yyyy-mm-ddThh:mm:ss
		String dateEnd = "2018-06-12T08:59:00";
		
		// Object Zipper, create a new ZIP file at zipFilePath
		FileZipper fileZipper = new FileZipper(path);

		for (String logger : winLogger) { // for each logger contained in winLogger
			
			String currentLog = String.format("%s%s%s.evtx", fileZipper.getFile().getParent(), File.separator, logger); // path where current log will be stored

			// WinEventFetcher open and read a specific logger indicated by @logger
			// variable, fetching between tho dates @dateStart to @dateEnd.
			// The resulting log file it's stored to @currentLog.
			WinEventFetcher myFetcher = new WinEventFetcher(logger, dateStart, dateEnd, currentLog); // create a
																										// WinEventFetcher
			try {
				myFetcher.saveLog(); // call to method that read from WindowsEvent
			} catch (IOException ex) {
				ex.printStackTrace();
				// if @logger can not be read do something...
			}

			try {
				fileZipper.addToZipFile(currentLog); // add @currentLog to final ZIP file
			} catch (IOException ex) {
				ex.printStackTrace();
				// if an error occurred in compressing this file, do something...
			}

			// Delete @currentLog, if you don't need .txt files but only the archive
			File currentLogFile = new File(currentLog);
			if (currentLogFile.exists())
				currentLogFile.delete();
		}

		return fileZipper.closeFile();
	}

	/***
	 * Parse @dateStart and @DateEnd strings for correct format. Also check
	 * if @dateEnd > @dateStart
	 * 
	 * @param dateStart
	 *            Start date for search
	 * @param dateEnd
	 *            End date for search
	 * @return true if both dates are correctly formatted, false otherwise
	 */

	static boolean parseDatae(String dateStart, String dateEnd) {
		// date format object to parse date in GMT format
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss");

		Date start = null; // convert string @dateStart to a Date object
		try {
			start = df.parse(dateStart);
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("Bad dateStart string --> FORMAT yyyy-mm-ddThh:mm:ss");
		}
		Date end = null; // convert string @dateEnd to a Date object

		try {
			end = df.parse(dateEnd);
		} catch (ParseException e) {
			System.err.println("Bad dateEnd string --> FORMAT yyyy-mm-ddThh:mm:ss");
			return false;
		}

		if (!end.after(start)) {
			System.err.println("The end date can not be earlier than the start date");
			return false;
		}

		return true;
	}
}