package com.ieli.tieasy.util.logs;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WinEventFetcher {

	private String winLogger;
	private String dateStart;
	private String dateEnd;
	private String saveFilePath;
	private static String OS = System.getProperty("os.name").toLowerCase();

	public WinEventFetcher(String winLogger, String dateStart, String dateEnd, String saveFilePath) {
		this.winLogger = winLogger;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.saveFilePath = saveFilePath;
	}

	/***
	 * Read from Windows Event Viewer logs.
	 * 
	 * @return true if log correctly read, false otherwise.
	 * @throws IOException
	 */

	public boolean saveLog() throws IOException {
		ArrayList<String> args = new ArrayList<>();
		if (OS.indexOf("mac") >= 0 || (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0)) {
			new File(saveFilePath);
			return true;
		}
		args.add("wevtutil.exe");
		args.add("epl");
		args.add(winLogger);
		args.add(saveFilePath);
		args.add(String.format("/q:*[System[TimeCreated[@SystemTime>='%s' and @SystemTime<'%s']]]", dateStart, dateEnd));
		ProcessBuilder p = new ProcessBuilder().command(args);

		Process proc = p.start();

		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}