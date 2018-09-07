package com.ieli.tieasy.util.logs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZipper {

	private final File file;
	private FileOutputStream fos;
	private ZipOutputStream zos;

	public FileZipper(String zipPath) {
		this.file = new File(zipPath);

		try {
			this.fos = new FileOutputStream(this.file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.zos = new ZipOutputStream(fos);
	}

	/***
	 * Add a localfile to a zip archive.
	 * 
	 * @param filePath
	 *            The full path of the file that has to be compressed.
	 * @throws IOException
	 *             if an error occurred in reading input file or writing to the
	 *             archive
	 */

	public void addToZipFile(String filePath) throws IOException {

		System.out.println("Writing '" + filePath + "' to zip file");

		File file = new File(filePath);
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zos.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}
			zos.closeEntry();
			fis.close();
		}

	}

	/***
	 * Create and close the ZIP file, storing it on the drive at @zipPath
	 */
	public File closeFile() {
		try {
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.file;
	}

	public File getFile() {
		return file;
	}
}
